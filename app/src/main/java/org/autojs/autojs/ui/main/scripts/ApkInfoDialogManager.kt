package org.autojs.autojs.ui.main.scripts

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager.GET_META_DATA
import android.os.Build
import android.view.LayoutInflater
import android.view.View.MeasureSpec.UNSPECIFIED
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.jaredrummler.apkparser.ApkParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.autojs.autojs.model.explorer.ExplorerItem
import org.autojs.autojs.runtime.api.AppUtils
import org.autojs.autojs6.R
import org.autojs.autojs6.databinding.ApkFileInfoDialogListItemBinding
import java.io.File

object ApkInfoDialogManager {

    @JvmStatic
    @SuppressLint("SetTextI18n")
    fun showApkInfoDialog(context: Context, explorerItem: ExplorerItem) {
        val binding = ApkFileInfoDialogListItemBinding.inflate(LayoutInflater.from(context))
        val root = binding.root as ViewGroup

        val dialog = MaterialDialog.Builder(context)
            .title(explorerItem.name)
            .customView(root, false)
            .autoDismiss(false)
            .iconRes(R.drawable.transparent)
            .limitIconToDefaultSize()
            .positiveText(R.string.text_install)
            .onPositive { materialDialog, _ ->
                materialDialog.dismiss()
                explorerItem.install(context)
            }
            .positiveColorRes(R.color.dialog_button_attraction)
            .negativeText(R.string.text_cancel)
            .onNegative { materialDialog, _ -> materialDialog.dismiss() }
            .neutralColorRes(R.color.dialog_button_hint)
            .show()

        val apkFilePath = explorerItem.toScriptFile().absolutePath
        val packageManager = context.packageManager

        CoroutineScope(Dispatchers.Main).launch {
            val packageInfo = withContext(Dispatchers.IO) {
                runCatching { packageManager.getPackageArchiveInfo(apkFilePath, GET_META_DATA) }.getOrNull()
            }
            val applicationInfo = packageInfo?.applicationInfo

            val packageName = packageInfo?.packageName
            val versionName = packageInfo?.versionName
            val versionCode = packageInfo?.let {
                when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> it.longVersionCode
                    else -> @Suppress("DEPRECATION") it.versionCode.toLong()
                }
            }

            withContext(Dispatchers.Main) {
                // @Hint by SuperMonster003 on Nov 27, 2024.
                //  ! Prioritize handling "installed version" to determine whether to display its content view.
                //  ! This is the only content view that needs to be considered before displaying the dialog.
                //  ! It is now safe to display the dialog immediately as all content views have placeholders.
                //  ! zh-CN:
                //  ! 优先处理 "已安装版本", 决定是否显示其内容视图.
                //  ! 这是唯一一个在显示对话框之前需要考虑的内容视图.
                //  ! 此时可立即安全显示对话框, 因所有内容视图均已完成占位.
                bindInstalledVersion(binding, context, packageName)

                restoreEssentialViews(binding, context)
                updateGuidelines(binding)

                packageName?.let { binding.packageNameValue.text = it }
                binding.deviceSdkValue.text = "${Build.VERSION.SDK_INT}"
                bindVersionInfo(binding, context, versionName, versionCode)

                dialog.setIcon(applicationInfo?.apply {
                    sourceDir = apkFilePath
                    publicSourceDir = apkFilePath
                }?.loadIcon(packageManager) ?: context.getDrawable(R.drawable.ic_packaging))

                val apkInfo = getApkInfo(explorerItem.toScriptFile())

                binding.labelNameValue.setTextIfAbsent { apkInfo?.label }
                binding.packageNameValue.setTextIfAbsent { apkInfo?.packageName }
                binding.minSdkValue.setTextIfAbsent { apkInfo?.minSdkVersion?.toString() }
                binding.targetSdkValue.setTextIfAbsent { apkInfo?.targetSdkVersion?.toString() }

                if (apkInfo != null) dialog.getActionButton(DialogAction.NEUTRAL).let { neutralButton ->
                    neutralButton.isVisible = true
                    neutralButton.text = "Manifest"
                    neutralButton.setOnClickListener { DisplayManifestActivity.launch(context, apkInfo.manifestXml, apkInfo.usesPermissions) }
                }
            }
        }
    }

    private suspend fun getApkInfo(apkFile: File): ApkInfo? = withContext(Dispatchers.IO) {
        runCatching {
            ApkParser.create(apkFile).use { parser ->
                val meta = runCatching { parser.apkMeta }.getOrNull()
                val label = meta?.label
                val packageName = meta?.packageName
                val minSdkVersion = meta?.minSdkVersion?.toIntOrNull()
                val targetSdkVersion = meta?.targetSdkVersion?.toIntOrNull()
                val usesPermissions = meta?.usesPermissions ?: emptyList()
                val manifestXml = parser.manifestXml
                ApkInfo(label, packageName, minSdkVersion, targetSdkVersion, usesPermissions, manifestXml)
            }
        }.getOrNull()
    }

    private fun bindVersionInfo(binding: ApkFileInfoDialogListItemBinding, context: Context, versionName: String?, versionCode: Long?) {
        when {
            versionName != null && versionCode != null -> {
                binding.versionPlaceholderLabel.text = context.getString(R.string.text_version)
                binding.versionPlaceholderValue.text = context.getString(R.string.text_full_version_info, versionName, versionCode)
            }
            else -> versionName?.let {
                binding.versionPlaceholderLabel.text = context.getString(R.string.text_version_name)
                binding.versionPlaceholderValue.text = it
            } ?: versionCode?.let {
                binding.versionPlaceholderLabel.text = context.getString(R.string.text_version_code)
                binding.versionPlaceholderValue.text = "$it"
            } ?: run { binding.versionPlaceholderValue.text = context.getString(R.string.text_unknown) }
        }
    }

    private fun TextView.setTextIfAbsent(f: () -> String?) {
        if (this.text == context.getString(R.string.ellipsis_six)) {
            this.text = f.invoke() ?: context.getString(R.string.text_unknown)
        }
    }

    private fun bindInstalledVersion(binding: ApkFileInfoDialogListItemBinding, context: Context, packageName: String?) {
        packageName?.let {
            AppUtils.getInstalledVersionInfo(it)?.let { versionInfo ->
                binding.installedVersionParent.isVisible = true
                binding.installedVersionValue.text = context.getString(R.string.text_full_version_info, versionInfo.versionName, versionInfo.versionCode)
            }
        } ?: run { binding.installedVersionValue.text = context.getString(R.string.text_unknown) }
    }

    private fun restoreEssentialViews(binding: ApkFileInfoDialogListItemBinding, context: Context) {
        binding.labelNameLabel.text = context.getString(R.string.text_label_name)
        binding.labelNameColon.isVisible = true
        binding.labelNameValue.isVisible = true
        binding.packageNameLabel.text = context.getString(R.string.apk_info_package_name)
        binding.packageNameColon.isVisible = true
        binding.packageNameValue.isVisible = true
        binding.versionPlaceholderLabel.text = context.getString(R.string.text_version)
        binding.versionPlaceholderColon.isVisible = true
        binding.versionPlaceholderValue.isVisible = true
        binding.minSdkLabel.text = context.getString(R.string.apk_info_min_sdk)
        binding.minSdkColon.isVisible = true
        binding.minSdkValue.isVisible = true
        binding.targetSdkLabel.text = context.getString(R.string.apk_info_target_sdk)
        binding.targetSdkColon.isVisible = true
        binding.targetSdkValue.isVisible = true
        binding.deviceSdkLabel.text = context.getString(R.string.apk_info_device_sdk)
        binding.deviceSdkColon.isVisible = true
        binding.deviceSdkValue.isVisible = true
    }

    private fun updateGuidelines(binding: ApkFileInfoDialogListItemBinding) {
        val filteredBindings = listOf(
            binding.labelNameLabel to binding.labelNameGuideline,
            binding.packageNameLabel to binding.packageNameGuideline,
            binding.versionPlaceholderLabel to binding.versionPlaceholderGuideline,
            binding.minSdkLabel to binding.minSdkGuideline,
            binding.targetSdkLabel to binding.targetSdkGuideline,
            binding.installedVersionLabel to binding.installedVersionGuideline,
            binding.deviceSdkLabel to binding.deviceSdkGuideline,
        ).filter { (it.first.parent as? ConstraintLayout)?.isVisible == true }

        @Suppress("DuplicatedCode")
        val maxWidth = filteredBindings.maxOfOrNull { it.first.apply { measure(UNSPECIFIED, UNSPECIFIED) }.measuredWidth } ?: return

        filteredBindings.forEach { (_, guideline) ->
            guideline.layoutParams = (guideline.layoutParams as ConstraintLayout.LayoutParams).also {
                it.guideBegin = maxWidth
            }
        }
    }

    private data class ApkInfo(
        val label: String?,
        val packageName: String?,
        val minSdkVersion: Int?,
        val targetSdkVersion: Int?,
        val usesPermissions: List<String>,
        val manifestXml: String,
    )

}