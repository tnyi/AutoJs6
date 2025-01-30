<!--suppress HtmlDeprecatedAttribute, HttpUrlsUsage -->

<div align="center">
  <p>
    <img src="https://s1.imagehub.cc/images/2023/03/07/af8ed087c9d354b9ab6142aae7bbafb6.png" alt="autojs6-banner_800×224" border="0" width="704" />
  </p>

  <p>JavaScript automation tool supporting accessibility service on the Android platform</p>

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

### Languages

******

The current README.md supports the following languages:

 - [简体中文 [zh-Hans]](http://project.autojs6.com/blob/master/.readme/README-zh-Hans.md)
 - English [en] # current

******

### Introduction

******

[Auto.js](https://github.com/hyb1996/Auto.js) is a JavaScript automation tool software for the Android platform that supports [Accessibility Service](https://developer.android.com/guide/topics/ui/accessibility/service?hl=en).

Auto.js was first released by [hyb1996](https://github.com/hyb1996) on `2017/01/27` and ceased maintenance on `2020/03/13`, with the final version name being `4.1.1 Alpha2` and build number `461`.

AutoJs6 is based on the final project of Auto.js, undergoing secondary development on `2021/12/01` and continues to be open-source and free.

This project is based on AutoJs6 for secondary development, adding independent script log saving and support for websocket protocol to send to third-party interfaces.



******

### Functions

******

* Can be used as a JavaScript IDE (code completion/variable renaming/code formatting)
* Supports automation based on [Accessibility Service](https://developer.android.com/reference/android/accessibilityservice/AccessibilityService)
* Supports floating button quick actions (script recording and running/view package name and activity/layout analysis)
* Supports Selector API and provides control traversal/information retrieval/control operations (similar to [UiAutomator](https://developer.android.com/training/testing/ui-automator))
* Supports layout interface analysis (similar to Android Studio's LayoutInspector)
* Supports recording functionality and playback of recorded actions
* Supports screen capture/save screenshots/color search in images/image matching
* Supports [E4X](https://en.wikipedia.org/wiki/E4X) (ECMAScript for XML) for interface writing
* Supports packaging script files or projects into APK files
* Supports expanded functionality using Root privileges (screen click/slide/record/Shell)
* Supports use as a Tasker plugin
* Supports connection with VSCode for desktop development (requires [AutoJs6-VSCode-Extension](http://vscext-project.autojs6.com) plugin)

******

### Environment

******

- Android Operating System
- [API](https://developer.android.com/guide/topics/manifest/uses-sdk-element#ApiLevels) [24](https://developer.android.com/reference/android/os/Build.VERSION_CODES#N) ([7.0](https://zh.wikipedia.org/wiki/Android_Nougat)) [[N](https://developer.android.com/reference/android/os/Build.VERSION_CODES#N)] and above

******

### Manual

******

* [Application Documentation](https://docs.autojs6.com)
* [User Manual (to be written)](https://docs.autojs6.com/#/manual)
* [Troubleshooting](https://docs.autojs6.com/#/qa)
* [Project Compilation and Build](#project-compilation-and-build)
* [Script Development Assistance](#script-development-assistance)

******

### Major Changes

******

Compared to the final open-source version `4.1.1 Alpha2` of Auto.js, the main upgrades or changes in AutoJs6 are:

* Supports obtaining ADB privileges through [Shizuku](https://shizuku.rikka.app/introduction/) and using system API
* Supports building [WebSocket](https://docs.autojs6.com/#/webSocketType) instances to perform network requests based on the [WebSocket protocol](https://en.wikipedia.org/wiki/WebSocket)
* New modules [ [base64](https://docs.autojs6.com/#/base64) / [crypto](https://docs.autojs6.com/#/crypto) / [sqlite](https://docs.autojs6.com/#/sqlite) / [i18n](https://docs.autojs6.com/#/i18n) / [notice](https://docs.autojs6.com/#/notice) / [ocr](https://docs.autojs6.com/#/ocr) / [opencc](https://docs.autojs6.com/#/opencc) / [qrcode](https://docs.autojs6.com/#/qrcode) / [shizuku](https://docs.autojs6.com/#/shizuku) / ... ]
* Multilingual support [ Spanish / French / Russian / Arabic / Japanese / Korean / English / Simplified Chinese / Traditional Chinese / ... ]
* Night mode adaptation [ Settings page / Documentation page / Layout analysis page / Floating window / ... ]
* [VSCode plugin](http://vscext-project.autojs6.com) supports both client (LAN) and server (LAN/ADB) connection methods
* [Rhino](https://github.com/mozilla/rhino/) engine upgraded from [v1.7.7.2](https://github.com/mozilla/rhino/releases/tag/Rhino1_7_7_2_Release) to [v1.7.16-SNAPSHOT](http://rhino.autojs6.com/blob/master/gradle.properties#L3)
    * Unicode [code point](https://developer.mozilla.org/en-US/docs/Glossary/Code_point) escape support for [supplementary plane](https://en.wikipedia.org/wiki/Plane_(Unicode)#Supplementary_Multilingual_Plane) characters
       ```javascript
       '\u{1D160}'; /* stands for "𝅘𝅥𝅮", traditional method: '\uD834\uDD60'. */
       ```
    * Supports [Object.values()](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/values)
       ```javascript
       Object.values({name: 'Max', age: 4}); // ['max', 4]
       ```
    * Supports [Array.prototype.includes()](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/includes)
       ```javascript
       [10, 20, NaN].includes(20); // true
       ```
    * Supports [BigInt](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/BigInt)
       ```javascript
       typeof 567n === 'bigint'; // true
       ```
    * Supports [template strings](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Template_literals)
       ```javascript
       `Lucky number: ${(Math.random() * 100).toFixed(0)}`
       ```
    * View more new features of the Rhino engine [here](http://project.autojs6.com/blob/master/app/src/main/assets-app/doc/RHINO.md)
    * View Rhino engine [compatibility list](https://mozilla.github.io/rhino/compat/engines.html)

******

### <a id="project-compilation-and-build"></a>Project Compilation and Build

******

For debugging or developing the AutoJs6 open-source project, you can use [Android Studio](https://pro.autojs.org/) (a product of [Google](https://www.google.com/)) or [IntelliJ IDEA](https://www.jetbrains.com/idea/) (a product of [Jetbrains](https://www.jetbrains.com/)).

This section introduces the compilation and build methods of the AutoJs6 open-source project using Android Studio as an example. IntelliJ IDEA is similar.

#### Android Studio Preparation

Download `Android Studio Ladybug Feature Drop | 2024.2.2 RC 2` version (choose one as needed):

- [android-studio-2024.2.2.12-windows.exe](https://redirector.gvt1.com/edgedl/android/studio/install/2024.2.2.12/android-studio-2024.2.2.12-windows.exe) (1.14 GB)
- [android-studio-2024.2.2.12-windows.zip](https://redirector.gvt1.com/edgedl/android/studio/ide-zips/2024.2.2.12/android-studio-2024.2.2.12-windows.zip) (1.15 GB)

> Note: The release date for the above version is Dec 16, 2024. To download other versions, or if the above link is invalid, you can visit the [Android Studio release archive](https://developer.android.com/studio/archive?hl=en) page.

Install or extract the above file, then run the Android Studio software (e.g., `"D:\android-studio\bin\studio64.exe"`).

#### Android SDK Preparation

> Note: If the Android SDK is already installed on the computer system, this section can be skipped.

Use `CTRL + ALT + S` in Android Studio to open the settings page:

```text
Appearance & Behavior -> 
System Settings -> 
Android SDK
```

If the `Android SDK Location` field is blank, click the `Edit` button on the right and click `Next` multiple times in the pop-up window.

> Note: During the process, one or more relevant agreements may need to be accepted to continue.

Once the related resources are downloaded and installed, click the `Finish` button.  
The `Android SDK Location` field above will auto-populate with the path, completing the SDK preparation work.

#### Android SDK Tools Preparation

AutoJs6 requires certain SDK tools (like NDK and CMake).

> Note: If all required Android SDK Tools for AutoJs6 are already installed on the computer system, this section can be skipped.

Use `CTRL + ALT + S` in Android Studio to open the settings page:

```text
Appearance & Behavior -> 
System Settings -> 
Android SDK -> 
SDK Tools (located in the right window)
```

Check `Show Package Details`, click NDK and CMake respectively to ensure the corresponding version tools are checked, the version information of the SDK tools is located in the `version.properties` file in the root directory of the AutoJs6 project.

#### JDK Preparation

The `JDK (Java Development Kit)` version required for the AutoJs6 project should be at least `17`, but `19` or higher is recommended.

As of Jan 1, 2025, AutoJs6 supports up to version `23` of the JDK.

> Note: If the JDK is already installed on the computer system and the version meets the above requirements, this section can be skipped.

JDK can be downloaded directly via the IDE or from the [Oracle website](https://www.oracle.com/java/technologies/downloads/).

Use `CTRL + ALT + S` in Android Studio to open the settings page:

```text
Build, Execution, Deployment -> 
Build Tools -> 
Gradle
```

`Gradle JDK` can be chosen or added with different versions of JDK.

If a suitable version of the JDK (>= `17`) already exists in the list, it can be selected directly.  
Alternatively, you can choose `Download JDK` to download a suitable JDK, click the `Download` button and wait for the download to complete.  
You can also choose `Add JDK` to add an existing local JDK, locating its directory and completing the JDK addition.

#### AutoJs6 Resources Cloning

Click `Get from VCS` button on the main page of Android Studio.  
Fill `URL` field with `https://github.com/SuperMonster003/AutoJs6.git`,  
The `Directory` field can be modified to a specific path if needed.  
Click the `Clone` button, wait for the AutoJs6 project resources to be cloned to the local device.

> Note: The above process may require the installation of [Git](https://git-scm.com/download).

#### AutoJs6 Project Building

After cloning, Android Studio will open the AutoJs6 project window and automatically complete the preliminary `Dependencies` download and Gradle build work.

> Note: The above process may take a long time. If network conditions are poor, it may need to be retried multiple times (click the Retry button).

Upon completion of the build, the `Build` tab in Android Studio will show a message similar to `BUILD SUCCESSFUL in 1h 17m 34s`.

Package the project and generate an APK file that can be installed on an Android device:

- Debug Version
    - `Build` -> `Build Bundle(s) / APK(s)` -> `Build APK(s)`
    - Generate debug version package with default signature
    - Path example: `"D:\AutoJs6\app\build\outputs\apk\debug\"`
- Release Version
    - `Build` -> `Generate Signed Bundle / APK`
    - Select `APK` option
    - Prepare the signing file (create new or select existing), generate the signed release package
    - Path example: `"D:\AutoJs6\app\release\"`

> Reference: [Android Docs](https://developer.android.com/studio/run?hl=zh-cn)

******

### <a id="script-development-assistance"></a>Script Development Assistance

******

To develop scripts that run with AutoJs6, appropriate development tools must be used:

- [VSCode](https://code.visualstudio.com/download) / [WebStorm](https://www.jetbrains.com/webstorm/download/) / [HBuilderX](https://www.dcloud.io/hbuilderx.html) ...

For writing and debugging scripts on a PC, the VSCode plugin can enable PC-to-phone connectivity:

- [AutoJs6-VSCode-Extension](http://vscext-project.autojs6.com) - AutoJs6 debugger (VSCode platform plugin)

When writing code using development tools, code completion functionality can better assist developers in completing code:

- [AutoJs6-TypeScript-Declarations](http://dts-project.autojs6.com) - AutoJs6 declaration files (code completion)

When writing code, AutoJs6-related API and usage can be consulted in the documentation at any time:

- [AutoJs6-Documentation](http://docs-project.autojs6.com) - AutoJs6 application documentation

Existing script development projects can serve as references and inspire creativity for personal script projects:

- [Ant-Forest](https://github.com/TonyJiangWJ/Ant-Forest) - Ant Forest energy auto-collect script by [TonyJiangWJ](https://github.com/TonyJiangWJ)
- [Ant-Forest](https://github.com/SuperMonster003/Ant-Forest) - Ant Forest energy auto-collect script by [SuperMonster003](https://github.com/SuperMonster003)
- [autojs](https://github.com/e1399579/autojs) - Auto.js utility scripts by [e1399579](https://github.com/e1399579)
- [autojsDemo](https://github.com/snailuncle/autojsDemo) - Auto.js demonstration example by [snailuncle](https://github.com/snailuncle)
- [autojs related repositories](https://github.com/topics/autojs) - All repositories related to the autojs topic on GitHub

******

### Contribution

******

Thank you to everyone who contributed to the AutoJs6 project development.

|     <span style="word-break:keep-all;white-space:nowrap">Contributors</span>     |                   <span style="word-break:keep-all;white-space:nowrap">Number of Commits</span>                    | <span style="word-break:keep-all;white-space:nowrap">Recent Submissions</span> |
|:----------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------:|
|     <span style="word-break:keep-all;white-space:nowrap">[luckyloogn](https://github.com/luckyloogn)</span>      |       <span style="word-break:keep-all;white-space:nowrap">[3](https://github.com/SuperMonster003/AutoJs6/commits?author=luckyloogn)</span>        |                    <span style="word-break:keep-all;white-space:nowrap">`2025/01/01`</span>                    |
|           <span style="word-break:keep-all;white-space:nowrap">[kvii](https://github.com/kvii)</span>            |          <span style="word-break:keep-all;white-space:nowrap">[1](https://github.com/SuperMonster003/AutoJs6/commits?author=kvii)</span>           |                    <span style="word-break:keep-all;white-space:nowrap">`2024/10/16`</span>                    |
|  <span style="word-break:keep-all;white-space:nowrap">[chenguangming](https://github.com/chenguangming)</span>   | <span style="word-break:keep-all;white-space:nowrap">[2](https://github.com/SuperMonster003/AutoJs6/pulls?q=is%3Apr+author%3Achenguangming)</span> |                    <span style="word-break:keep-all;white-space:nowrap">`2024/05/14`</span>                    |
|         <span style="word-break:keep-all;white-space:nowrap">[LZX284](https://github.com/LZX284)</span>          |         <span style="word-break:keep-all;white-space:nowrap">[17](https://github.com/SuperMonster003/AutoJs6/commits?author=LZX284)</span>         |                    <span style="word-break:keep-all;white-space:nowrap">`2023/11/19`</span>                    |
|    <span style="word-break:keep-all;white-space:nowrap">[TonyJiangWJ](https://github.com/TonyJiangWJ)</span>     |       <span style="word-break:keep-all;white-space:nowrap">[4](https://github.com/SuperMonster003/AutoJs6/commits?author=TonyJiangWJ)</span>       |                    <span style="word-break:keep-all;white-space:nowrap">`2023/10/31`</span>                    |
| <span style="word-break:keep-all;white-space:nowrap">[little&#x2011;alei](https://github.com/little-alei)</span> |      <span style="word-break:keep-all;white-space:nowrap">[12](https://github.com/SuperMonster003/AutoJs6/commits?author=little-alei)</span>       |                    <span style="word-break:keep-all;white-space:nowrap">`2023/07/12`</span>                    |
|         <span style="word-break:keep-all;white-space:nowrap">[aiselp](https://github.com/aiselp)</span>          |    <span style="word-break:keep-all;white-space:nowrap">[6](https://github.com/SuperMonster003/AutoJs6/pulls?q=is%3Apr+author%3Aaiselp)</span>     |                    <span style="word-break:keep-all;white-space:nowrap">`2023/06/14`</span>                    |
|          <span style="word-break:keep-all;white-space:nowrap">[LYS86](https://github.com/LYS86)</span>           |          <span style="word-break:keep-all;white-space:nowrap">[2](https://github.com/SuperMonster003/AutoJs6/commits?author=LYS86)</span>          |                    <span style="word-break:keep-all;white-space:nowrap">`2023/06/03`</span>                    |

Data updated on Jan 1, 2025.

Data entries sorted in descending order by `recent submissions`.

Newly initiated, unprocessed Pull Requests will be included in the data statistics after merging.

Some contributors do not appear correctly in the [GitHub Contributors](https://github.com/SuperMonster003/AutoJs6/graphs/contributors) due to empty contribution records, but their contribution records can still be viewed via [Pull Request](https://github.com/SuperMonster003/AutoJs6/pulls).

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