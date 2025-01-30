<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://s1.imagehub.cc/images/2023/03/07/af8ed087c9d354b9ab6142aae7bbafb6.png" alt="autojs6-banner_800×224" border="0" width="704" />
  </p>

  <p>Android 平台支持无障碍服务的 JavaScript 自动化工具</p>

  <p>
    <a href="http://download.autojs6.com"><img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/SuperMonster003/AutoJs6"/></a>
    <a href="http://issues.autojs6.com"><img alt="GitHub closed issues" src="https://img.shields.io/github/issues/SuperMonster003/AutoJs6?color=009688"/></a>
    <a href="http://commit.autojs6.com/99a1d8490fac5b6d55f6f183db59ad833a2064ed"><img alt="Created" src="https://img.shields.io/date/1636632233?color=2e7d32&label=created"/></a>
    <br>
    <a href="https://github.com/mozilla/rhino"><img alt="Rhino" src="https://img.shields.io/badge/rhino-1.7.16--snapshot-A24232"/></a>
    <a href="https://developer.android.com/studio/archive"><img alt="Android Studio" src="https://img.shields.io/badge/android%20studio-2022.1+-B64FC8"/></a>
    <br>
    <a href="https://www.codefactor.io/repository/github/SuperMonster003/AutoJs6"><img alt="CodeFactor Grade" src="https://www.codefactor.io/repository/github/SuperMonster003/AutoJs6/badge"/></a>
    <a href="https://www.jetbrains.com/?from=AutoJs6"><img alt="JetBrains supporter" src="https://img.shields.io/badge/supporter-JetBrains-ee4677"/></a>
    <a href="http://project.autojs6.com/blob/master/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/SuperMonster003/AutoJs6?color=534BAE"/></a>
  </p>
</div>

******

### 语言 (Languages)

******

当前自述文件 `README.md` 支持以下语言:

 - 简体中文 [zh-Hans] # 当前
 - [English [en]](http://project.autojs6.com/blob/master/.readme/README-en.md)


******

### 简介

******

[Auto.js](https://github.com/hyb1996/Auto.js) 是一款 Android 平台支持 [无障碍服务](https://developer.android.com/guide/topics/ui/accessibility/service?hl=zh-cn) 的 JavaScript 自动化工具软件.

Auto.js 由 [hyb1996](https://github.com/hyb1996) 于 `2017/01/27` 初次发布, 于 `2020/03/13` 停止维护, 最终版本名称为 `4.1.1 Alpha2`, 构建版本号为 `461`.

AutoJs6 在 Auto.js 最终项目的基础上, 于 `2021/12/01` 进行二次开发, 继续保持开源免费.

本项目基于AutoJs6进行二次开发，增加了脚本日志独立保存并支持websocket协议，发送至三方接口。


******

### 功能

******

* 可用作 JavaScript IDE (代码补全/变量重命名/代码格式化)
* 支持基于 [无障碍服务](https://developer.android.com/reference/android/accessibilityservice/AccessibilityService) 的自动化操作
* 支持浮动按钮快捷操作 (脚本录制及运行/查看包名及活动/布局分析)
* 支持选择器 API 并提供控件遍历/获取信息/控件操作 (类似 [UiAutomator](https://developer.android.com/training/testing/ui-automator))
* 支持布局界面分析 (类似 Android Studio 的 LayoutInspector)
* 支持录制功能及录制回放
* 支持屏幕截图/保存截图/图片找色/图片匹配
* 支持 [E4X](https://zh.wikipedia.org/wiki/E4X) (ECMAScript for XML) 编写界面
* 支持将脚本文件或项目打包为 APK 文件
* 支持利用 Root 权限扩展功能 (屏幕点击/滑动/录制/Shell)
* 支持作为 Tasker 插件使用
* 支持与 VSCode 连接并进行桌面开发 (需要 [AutoJs6-VSCode-Extension](http://vscext-project.autojs6.com) 插件)

******

### 环境

******

- Android 操作系统
- [API](https://developer.android.com/guide/topics/manifest/uses-sdk-element#ApiLevels) [24](https://developer.android.com/reference/android/os/Build.VERSION_CODES#N) ([7.0](https://zh.wikipedia.org/wiki/Android_Nougat)) [[N](https://developer.android.com/reference/android/os/Build.VERSION_CODES#N)] 及以上

******

### 指南

******

* [应用文档](https://docs.autojs6.com)
* [使用手册 (待编写)](https://docs.autojs6.com/#/manual)
* [疑难解答](https://docs.autojs6.com/#/qa)
* [项目编译构建](#project-compilation-and-build)
* [脚本开发辅助](#script-development-assistance)

******

### 主要变更

******

相较于 Auto.js 最终开源版本 `4.1.1 Alpha2`, AutoJs6 主要进行了以下升级或变更:

* 支持通过 [Shizuku](https://shizuku.rikka.app/introduction/) 获得 ADB 特权并使用系统 API
* 支持构建 [WebSocket](https://docs.autojs6.com/#/webSocketType) 实例以完成基于 [WebSocket 协议](https://zh.wikipedia.org/wiki/WebSocket) 的网络请求
* 新增模块 [ [base64](https://docs.autojs6.com/#/base64) / [crypto](https://docs.autojs6.com/#/crypto) / [sqlite](https://docs.autojs6.com/#/sqlite) / [i18n](https://docs.autojs6.com/#/i18n) / [notice](https://docs.autojs6.com/#/notice) / [ocr](https://docs.autojs6.com/#/ocr) / [opencc](https://docs.autojs6.com/#/opencc) / [qrcode](https://docs.autojs6.com/#/qrcode) / [shizuku](https://docs.autojs6.com/#/shizuku) / ... ]
* 多语言适配 [ 西 / 法 / 俄 / 阿 / 日 / 韩 / 英 / 简中 / 繁中 / ... ]
* 夜间模式适配 [ 设置页面 / 文档页面 / 布局分析页面 / 浮动窗口 / ... ]
* [VSCode 插件](http://vscext-project.autojs6.com) 支持客户端 (LAN) 及服务端 (LAN/ADB) 连接方式
* [Rhino](https://github.com/mozilla/rhino/) 引擎由 [v1.7.7.2](https://github.com/mozilla/rhino/releases/tag/Rhino1_7_7_2_Release) 升级至 [v1.7.16-SNAPSHOT](http://rhino.autojs6.com/blob/master/gradle.properties#L3)
    * Unicode [码位](https://developer.mozilla.org/zh-CN/docs/Glossary/Code_point) 转义支持 [辅助平面](https://zh.wikipedia.org/wiki/Unicode%E5%AD%97%E7%AC%A6%E5%B9%B3%E9%9D%A2%E6%98%A0%E5%B0%84#%E7%AC%AC%E4%B8%80%E8%BC%94%E5%8A%A9%E5%B9%B3%E9%9D%A2) 字符
       ```javascript
       '\u{1D160}'; /* 表示 "𝅘𝅥𝅮", 传统方式: '\uD834\uDD60'. */
       ```
    * 支持 [Object.values()](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Object/values)
       ```javascript
       Object.values({name: 'Max', age: 4}); // ['max', 4]
       ```
    * 支持 [Array.prototype.includes()](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Array/includes)
       ```javascript
       [10, 20, NaN].includes(20); // true
       ```
    * 支持 [BigInt](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/BigInt)
       ```javascript
       typeof 567n === 'bigint'; // true
       ```
    * 支持 [模板字符串](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Template_literals)
       ```javascript
       `Lucky number: ${(Math.random() * 100).toFixed(0)}`
       ```
    * 查看 Rhino 引擎 [更多新特性](http://project.autojs6.com/blob/master/app/src/main/assets-app/doc/RHINO.md)
    * 查看 Rhino 引擎 [兼容性列表](https://mozilla.github.io/rhino/compat/engines.html)




******

### <a id="project-compilation-and-build"></a>项目编译构建

******

如需对 AutoJs6 开源项目进行调试或开发, 可使用 [Android Studio](https://pro.autojs.org/) ([Google](https://www.google.com/) 公司产品) 或 [IntelliJ IDEA](https://www.jetbrains.com/idea/) ([Jetbrains](https://www.jetbrains.com/) 公司产品).

本小节以 Android Studio 为例介绍 AutoJs6 开源项目的编译构建方法, IntelliJ IDEA 与之类似.

#### Android Studio 准备

下载 `Android Studio Ladybug Feature Drop | 2024.2.2 RC 2` 版本 (按需选择其一):

- [android-studio-2024.2.2.12-windows.exe](https://redirector.gvt1.com/edgedl/android/studio/install/2024.2.2.12/android-studio-2024.2.2.12-windows.exe) (1.14 GB)
- [android-studio-2024.2.2.12-windows.zip](https://redirector.gvt1.com/edgedl/android/studio/ide-zips/2024.2.2.12/android-studio-2024.2.2.12-windows.zip) (1.15 GB)

> 注: 上述版本发布时间为 2024 年 12 月 16 日. 如需下载其他版本, 或上述链接已失效, 可访问 [Android Studio 发行版本归档](https://developer.android.com/studio/archive?hl=en) 页面.

安装或解压上述文件, 运行 Android Studio 软件 (如 `"D:\android-studio\bin\studio64.exe"`).

#### Android SDK 准备

> 注: 如果计算机系统已安装 Android SDK (安卓软件开发工具包), 则可跳过此小节内容.

在 Android Studio 软件中使用快捷键 `CTRL + ALT + S` 打开设置页面:

```text
Appearance & Behavior (外观与表现) -> 
System Settings (系统设置) -> 
Android SDK (安卓软件开发工具包)
```

`Android SDK Location (安卓软件开发工具包位置)` 处如果是空白内容, 可点击右侧 `Edit (编辑)` 按钮, 在弹出的窗口中多次点击 `Next (下一步)`.

> 注: 过程中可能需要同意一个或多个相关协议才能继续.

待相关资源下载并安装完毕, 点击 `Finish (完成)` 按钮.  
上述 `Android SDK Location (安卓软件开发工具包位置)` 处将自动完成路径填写, SDK 准备工作随即完成.

#### Android SDK Tools 准备

AutoJs6 需要使用部分 SDK 工具 (如 NDK 及 CMake).

> 注: 如果计算机系统已安装 AutoJs6 全部所需的 Android SDK Tools, 则可跳过此小节内容.

在 Android Studio 软件中使用快捷键 `CTRL + ALT + S` 打开设置页面:

```text
Appearance & Behavior (外观与表现) -> 
System Settings (系统设置) -> 
Android SDK (安卓软件开发工具包) -> 
SDK Tools (SDK 工具) (位于右侧窗口)
```

勾选 `Show Package Details (显示包详情)`, 依次点击 NDK 及 CMake, 确保相应版本的工具已勾选, SDK 工具的版本信息位于 AutoJs6 项目根目录的 `version.properties` 文件中.

#### JDK 准备

AutoJs6 项目依赖的 `JDK (Java 开发工具包)` 发行版本不低于 `17`, 但建议不低于 `19`.

截至 2025 年 1 月 1 日, AutoJs6 可支持 JDK 最高版本为 `23`.

> 注: 如果计算机系统已安装 JDK 且版本满足上述要求, 则可跳过此小节内容.

JDK 可使用 IDE 直接下载, 或访问 [Oracle 网站](https://www.oracle.com/java/technologies/downloads/) 下载.

在 Android Studio 软件中使用快捷键 `CTRL + ALT + S` 打开设置页面:

```text
Build, Execution, Deployment (构建, 执行, 开发) -> 
Build Tools (构建工具) -> 
Gradle
```

`Gradle JDK` 处可选择或添加不同版本的 JDK.

如果列表中已存在合适版本的 JDK (>= `17`), 则直接选择即可.  
否则可以选择 `Download JDK (下载 JDK)` 下载合适的 JDK, 点击 `Download (下载)` 按钮并等待下载完成.  
也可以选择 `Add JDK (添加 JDK)` 添加已存在的本地 JDK, 定位其目录并完成 JDK 添加.

#### AutoJs6 资源克隆

在 Android Studio 主页面点击 `Get from VCS (从版本控制系统获取)` 按钮.  
`URL (统一资源定位地址)` 处填入 `https://github.com/SuperMonster003/AutoJs6.git`,  
`Directory (目录)` 处可根据需要修改为特定路径.  
点击 `Clone (克隆)` 按钮, 等待 AutoJs6 项目资源在设备本地完成克隆.

> 注: 上述过程可能需要安装 [Git (分布式版本控制系统)](https://git-scm.com/download).

#### AutoJs6 项目构建

克隆完成后, Android Studio 将打开 AutoJs6 的项目窗口, 并自动完成初步的 `Dependencies (依赖)` 下载及 Gradle 构建工作.

> 注: 上述过程可能非常耗时. 若网络条件欠佳, 可能需要重试多次 (点击 Retry 按钮).

构建完成后, Android Studio 的 `Build` 标签页将出现类似 `BUILD SUCCESSFUL in 1h 17m 34s` 的消息.

打包项目并生成可安装到安卓设备的 APK 文件:

- 调试版 (Debug Version)
    - `Build (构建)` -> `Build Bundle(s) / APK(s)` -> `Build APK(s)`
    - 生成带默认签名的调试版安装包
    - 路径示例: `"D:\AutoJs6\app\build\outputs\apk\debug\"`
- 发布版 (Release Version)
    - `Build (构建)` -> `Generate Signed Bundle / APK`
    - 选择 `APK` 选项
    - 准备好签名文件 (新建或选取), 生成已签名的发布版安装包
    - 路径示例: `"D:\AutoJs6\app\release\"`

> 参阅: [Android Docs](https://developer.android.com/studio/run?hl=zh-cn)

******

### <a id="script-development-assistance"></a>脚本开发辅助

******

开发 AutoJs6 可运行的脚本, 需使用合适的开发工具:

- [VSCode](https://code.visualstudio.com/download) / [WebStorm](https://www.jetbrains.com/webstorm/download/) / [HBuilderX](https://www.dcloud.io/hbuilderx.html) ...

如需在 PC 上进行脚本编写与调试, VSCode 插件可以实现 PC 与手机的互联:

- [AutoJs6-VSCode-Extension](http://vscext-project.autojs6.com) - AutoJs6 调试器 (VSCode 平台插件)

使用开发工具编写代码时, 代码智能补全功能可以更好地辅助开发者完成代码编写:

- [AutoJs6-TypeScript-Declarations](http://dts-project.autojs6.com) - AutoJs6 声明文件 (代码智能补全)

编写代码时, AutoJs6 相关 API 及使用方式, 可随时查阅应用文档:

- [AutoJs6-Documentation](http://docs-project.autojs6.com) - AutoJs6 应用文档

现有的脚本开发项目可作为参考, 激发个人脚本项目的创作灵感:

- [Ant-Forest](https://github.com/TonyJiangWJ/Ant-Forest) - 蚂蚁森林能量自动收取脚本 by [TonyJiangWJ](https://github.com/TonyJiangWJ)
- [Ant-Forest](https://github.com/SuperMonster003/Ant-Forest) - 蚂蚁森林能量自动收取脚本 by [SuperMonster003](https://github.com/SuperMonster003)
- [autojs](https://github.com/e1399579/autojs) - Auto.js 实用脚本 by  [e1399579](https://github.com/e1399579)
- [autojsDemo](https://github.com/snailuncle/autojsDemo) - Auto.js 演示示例 by  [snailuncle](https://github.com/snailuncle)
- [autojs 相关仓库](https://github.com/topics/autojs) - GitHub 与 autojs 话题相关的全部仓库

******

### 贡献参与

******

感谢每一位参与 AutoJs6 项目开发的贡献人员.

|     <span style="word-break:keep-all;white-space:nowrap">贡献人员</span>     |                   <span style="word-break:keep-all;white-space:nowrap">提交数</span>                    | <span style="word-break:keep-all;white-space:nowrap">最近提交</span> |
|:----------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------:|
|     <span style="word-break:keep-all;white-space:nowrap">[luckyloogn](https://github.com/luckyloogn)</span>      |       <span style="word-break:keep-all;white-space:nowrap">[3](https://github.com/SuperMonster003/AutoJs6/commits?author=luckyloogn)</span>        |                    <span style="word-break:keep-all;white-space:nowrap">`2025/01/01`</span>                    |
|           <span style="word-break:keep-all;white-space:nowrap">[kvii](https://github.com/kvii)</span>            |          <span style="word-break:keep-all;white-space:nowrap">[1](https://github.com/SuperMonster003/AutoJs6/commits?author=kvii)</span>           |                    <span style="word-break:keep-all;white-space:nowrap">`2024/10/16`</span>                    |
|  <span style="word-break:keep-all;white-space:nowrap">[chenguangming](https://github.com/chenguangming)</span>   | <span style="word-break:keep-all;white-space:nowrap">[2](https://github.com/SuperMonster003/AutoJs6/pulls?q=is%3Apr+author%3Achenguangming)</span> |                    <span style="word-break:keep-all;white-space:nowrap">`2024/05/14`</span>                    |
|         <span style="word-break:keep-all;white-space:nowrap">[LZX284](https://github.com/LZX284)</span>          |         <span style="word-break:keep-all;white-space:nowrap">[17](https://github.com/SuperMonster003/AutoJs6/commits?author=LZX284)</span>         |                    <span style="word-break:keep-all;white-space:nowrap">`2023/11/19`</span>                    |
|    <span style="word-break:keep-all;white-space:nowrap">[TonyJiangWJ](https://github.com/TonyJiangWJ)</span>     |       <span style="word-break:keep-all;white-space:nowrap">[4](https://github.com/SuperMonster003/AutoJs6/commits?author=TonyJiangWJ)</span>       |                    <span style="word-break:keep-all;white-space:nowrap">`2023/10/31`</span>                    |
| <span style="word-break:keep-all;white-space:nowrap">[little&#x2011;alei](https://github.com/little-alei)</span> |      <span style="word-break:keep-all;white-space:nowrap">[12](https://github.com/SuperMonster003/AutoJs6/commits?author=little-alei)</span>       |                    <span style="word-break:keep-all;white-space:nowrap">`2023/07/12`</span>                    |
|         <span style="word-break:keep-all;white-space:nowrap">[aiselp](https://github.com/aiselp)</span>          |    <span style="word-break:keep-all;white-space:nowrap">[6](https://github.com/SuperMonster003/AutoJs6/pulls?q=is%3Apr+author%3Aaiselp)</span>     |                    <span style="word-break:keep-all;white-space:nowrap">`2023/06/14`</span>                    |
|          <span style="word-break:keep-all;white-space:nowrap">[LYS86](https://github.com/LYS86)</span>           |          <span style="word-break:keep-all;white-space:nowrap">[2](https://github.com/SuperMonster003/AutoJs6/commits?author=LYS86)</span>          |                    <span style="word-break:keep-all;white-space:nowrap">`2023/06/03`</span>                    |

数据更新于 2025 年 1 月 1 日.

数据条目按 `最近提交` 降序排序.

新发起的暂未处理的 Pull Request, 将在合并处理后加入数据统计.

部分贡献人员在 [GitHub Contributors](https://github.com/SuperMonster003/AutoJs6/graphs/contributors) 未能正常出现, 其提交记录为空, 仍可通过 [Pull Request](https://github.com/SuperMonster003/AutoJs6/pulls) 查看贡献记录.

[//]: # (
    # --------------------------------------------------------------#
    # Before committing and pushing to the remote GitHub repository #
    # --------------------------------------------------------------#
    - CHANGELOG.md
        - Update entries for AutoJs6 by checking all changed files
        - Update entries for Gradle plugins [ implementation ]
        - Update version name and released date
        - Append related GitHub issues to changelog entries
    - README.md
        - The summary of the latest changelog for committing to Git [ DO NOT commit or push ]
        - Update badges like [ android studio / rhino / ... ]
        - Update android studio download links and version names
        - Update contribution section
    - Remove the part like [ alpha / beta / ... ] of VERSION_NAME in version.properties
    - Update dependencies TypeScript declarations if needed.
    - Re-generate documentation/markdown by running the python script
    - Check the two-way versions for AutoJs6 and VSCode ext, then publish the ext to Microsoft
    - Run Gradle task "app:assembleInrtRelease"
    - Build APK to determine the final VERSION_BUILD field
    - Run Gradle task "app:appendDigestToReleasedFiles"
    - Commit and push to GitHub
    - Publish the latest release with signed APKs
)