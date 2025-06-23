# EasyXposed

[简体中文](README_CN.md)||English

This project aims to simplify the development process of Xposed modules (including LSP modules). Although I have optimized the framework, it still depends on the Xposed environment. By integrating Xposed and adding practical features (such as no-reboot and no-debug modifications), the project greatly reduces the amount of code, and hook operations can be completed with just a string.

Before you start, please confirm the version of Xposed and select the corresponding branch to clone. After cloning, in theory, no extra configuration is needed and it can be run directly.

## Features
This project is designed as an Xposed template, allowing you to have your own dedicated Xposed project. A paradise for pure love warriors!
1. The project has integrated the complete Xposed environment, saving you from the hassle of finding suitable jars and configuring Xposed prerequisites due to outdated information.
2. Some commonly used features have been added: such as no-reboot and no-debug modifications for applications, as well as various common hook methods.
3. Hooks can be directly accomplished using strings, so you don't have to worry about classLoaders anymore. It's simple and efficient!!
4. Some commonly used hook-related functions have been encapsulated, greatly reducing the amount of code.

## Quick Start
1. Confirm which version of Xposed you need: regular Xposed or LSPXposed.
2. Select the corresponding branch: master = Xposed, LSP = LSPXposed.
3. Click the green **Use this template** button and choose **Create a new repository**.
4. Fill in the name for your Xposed project and finally click **Create repository from template**.
   For example: If you name it HelloWorld, a new HelloWorld project with all the template codes but no git history will appear in your GitHub repository.
5. Finally, clone your project (e.g., HelloWorld) normally. No configuration issues to worry about; in theory, it can be run directly.

## LSPXposed (LSP Branch)
In view of the widespread use of the LSP framework, I have adapted the project. The advantage of this branch is that no reboot is needed at all; however, you need to manually select the application.

### Environment Dependencies
- Magisk: It is recommended to use Zygisk (the latest version)
- LSPosed: Download and install the LSPosed module, which needs to match Magisk. (It is recommended to install LSPosed-Zygisk)

### Development Process
1. Clone the project and switch to the LSP branch.
2. Write code: Directly enter the EasyHooker class and write code according to your needs.
3. Run the project: After installing and deploying to your phone, enable the corresponding application in LSP.

## Regular Xposed (master Branch)
The most commonly used branch. No need to manually select applications; just modify the code to achieve no-reboot. However, after the first installation of this application, a reboot is required.

### Environment Preparation
This module depends on the Xposed framework, and you need to complete the installation first (you can skip this step if it is already installed):
[Xposed Installation Tutorial](<url id="d1ccd18gts4fmtqk62ng" type="url" status="parsed" title="Xposed Beginner's Guide to Installing the Xposed Framework_CSDN Blog for Honor Phone" wc="1548">https://blog.csdn.net/qq_40194392/article/details/83013443</url> )

### Development Process
1. Use AndroidStudio to clone your project locally.
2. Build the project: The first build may automatically download some dependent packages.
3. Run the project: Click Run to install the APK to your phone and enable the module through Installer.
  - The first enabling requires a phone reboot to complete the loading.
  - No reboot is needed for subsequent development; just run it directly to achieve hook.

### Code Writing
After completing the preparation work, directly enter the EasyHooker class:
1. Fill in the package name to be hooked.
2. Write the hook logic and run it, and it will take effect without reboot.

## Common Issues
1. Hook failure
  - Cause: Abnormal loading of the Xposed module
  - Solution: Uninstall the module and re-enable it in Installer. Restart the phone if necessary.
2. Project fails to run
  - Cause: Gradle build failure
  - Solution: Check the Gradle version and configuration, or refer to the [Gradle official website](https://gradle.org/releases  ) for manual installation.

## Usage Tips
1. Currently, only single-application hooking is supported. When switching target applications: For the regular version, just modify the package name in EasyHooker. For the LSP version, switch the selected application.
2. The project depends on the phone's ROOT permission and XposedInstaller.

## Project Description
1. Common Classes
  - EasyHooker: The core class for hook logic processing
  - Tool: Includes log, extra-long log, stack, and parameter information output
  - Hool: Some encapsulated hook methods, currently supporting simplified hooks for entire classes, single methods, and constructors.
2. Feedback and Support
  - If you encounter any issues or have suggestions for improvement, please submit Issues.