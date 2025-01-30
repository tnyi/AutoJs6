package org.autojs.autojs.ui.main.scripts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.android.schedulers.AndroidSchedulers
import org.autojs.autojs.app.GlobalAppContext
import org.autojs.autojs.model.explorer.ExplorerItem
import org.autojs.autojs.model.script.Scripts
import org.autojs.autojs.tool.SimpleObserver
import org.autojs.autojs.ui.common.ScriptOperations
import org.autojs.autojs.ui.explorer.ExplorerView
import org.autojs.autojs.ui.main.FloatingActionMenu
import org.autojs.autojs.ui.main.FloatingActionMenu.OnFloatingActionButtonClickListener
import org.autojs.autojs.ui.main.QueryEvent
import org.autojs.autojs.ui.main.ViewPagerFragment
import org.autojs.autojs.ui.main.ViewStatesManageable
import org.autojs.autojs.ui.project.ProjectConfigActivity
import org.autojs.autojs.util.IntentUtils
import org.autojs.autojs6.R
import org.autojs.autojs6.databinding.FragmentExplorerBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Stardust on Mar 13, 2017.
 * Modified by SuperMonster003 as of Mar 20, 2022.
 * Transformed by SuperMonster003 on Mar 31, 2023.
 */
open class ExplorerFragment : ViewPagerFragment(0), OnFloatingActionButtonClickListener, ViewStatesManageable {

    private var binding: FragmentExplorerBinding? = null

    private var mExplorerView: ExplorerView? = null
    private var mFloatingActionMenu: FloatingActionMenu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentExplorerBinding.inflate(inflater, container, false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mExplorerView = binding!!.itemList.apply {
            setOnItemClickListener(object : ExplorerView.OnItemClickListener {
                override fun onItemClick(view: View?, item: ExplorerItem) {
                    when {
                        item.isTextEditable -> Scripts.edit(requireActivity(), item.toScriptFile())
                        item.isInstallable -> ApkInfoDialogManager.showApkInfoDialog(requireActivity(), item)
                        item.isMediaMenu || item.isMediaPlayable -> MediaInfoDialogManager.showMediaInfoDialog(requireActivity(), item)
                        else -> viewFile(item)
                    }
                }
            })
        }
        restoreViewStates()
    }

    private fun viewFile(item: ExplorerItem) = IntentUtils.viewFile(GlobalAppContext.get(), item.path)

    override fun onFabClick(fab: FloatingActionButton) {
        mFloatingActionMenu ?: let {
            mFloatingActionMenu = requireActivity().findViewById<FloatingActionMenu?>(R.id.floating_action_menu).also { menu ->
                menu.state
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SimpleObserver<Boolean?>() {
                        override fun onNext(expanding: Boolean) {
                            fab.animate()
                                .rotation((if (expanding) 45 else 0).toFloat())
                                .setDuration(300)
                                .start()
                        }
                    })
                menu.setOnFloatingActionButtonClickListener(this)
            }
        }
        mFloatingActionMenu!!.let { if (it.isExpanded) it.collapse() else it.expand() }
    }

    override fun onBackPressed(activity: Activity): Boolean {
        mFloatingActionMenu?.let {
            if (it.isExpanded) {
                it.collapse()
                return@onBackPressed true
            }
        }
        mExplorerView?.let {
            if (it.canGoBack()) {
                it.goBack()
                return@onBackPressed true
            }
        }
        return false
    }

    override fun onPageHide() {
        super.onPageHide()
        mFloatingActionMenu?.let { if (it.isExpanded) it.collapse() }
    }

    @Subscribe
    fun onQuerySummit(event: QueryEvent) {
        if (!isShown) {
            return
        }
        if (event === QueryEvent.CLEAR) {
            mExplorerView?.setFilter(null)
            return
        }
        mExplorerView?.setFilter { item: ExplorerItem -> item.name.contains(event.query) }
    }

    override fun onStop() {
        super.onStop()
        if (activity?.isFinishing == false) {
            saveViewStates()
        }
        mExplorerView?.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        ExplorerView.clearViewStates()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        mExplorerView = null
        mFloatingActionMenu = null
    }

    override fun onDetach() {
        super.onDetach()
        mFloatingActionMenu?.setOnFloatingActionButtonClickListener(null)
    }

    override fun onClick(button: FloatingActionButton, pos: Int) {
        mExplorerView?.let { view ->
            when (pos) {
                0 -> ScriptOperations(context, view, view.currentPage)
                    .newDirectory()
                1 -> ScriptOperations(context, view, view.currentPage)
                    .newFile()
                2 -> ScriptOperations(context, view, view.currentPage)
                    .importFile()
                3 -> context?.startActivity(
                    Intent(context, ProjectConfigActivity::class.java)
                        .putExtra(ProjectConfigActivity.EXTRA_PARENT_DIRECTORY, view.currentPage.path)
                        .putExtra(ProjectConfigActivity.EXTRA_NEW_PROJECT, true)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                else -> {}
            }
        }
    }

    override fun saveViewStates() {
        mExplorerView?.saveViewStates()
    }

    override fun restoreViewStates() {
        mExplorerView?.restoreViewStates()
    }

}