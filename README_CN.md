# EasyXposed

简体中文 || [English](README.md)

本项目旨在简化 Xposed 模块（包括 LSP 模块）的开发流程。尽管我对框架进行了优化，但仍需依赖 Xposed 环境。项目通过集成 Xposed 并增设实用功能（如免重启、免 debug 修改等），大幅减少了代码量，hook 操作仅需字符串即可完成。

在开始前，请确认 Xposed 版本，并选择对应分支进行 clone。完成 clone 后，理论上无需额外配置即可直接运行。

## 特色
项目专门设计为xposed模板，让你拥有自己的专属的Xposed项目。纯爱战士狂喜！
1. 项目已集成完整的Xposed的环境内容，省去了因年代久远，找不到合适的jar以及来回配置xposed前置的繁琐。
2. 添加了一些常用的功能：如应用免重启、免debug修改等，以及各种常用的hook方法。
3. hook直接使用字符串即可完成，再也不用去纠结什么 classLoader 了，简单高效！！
4. 封装了一些常用的hook相关功能，极大的减少了代码量。

## 快速上手
1. 确认自己需要的xposed是哪个版本：普通xposed，还是LSPXposed。
2. 然后选择对应的分支：master=Xposed，LSP=LSPXposed。
3. 点击绿色的 **Use this template**，选择 **Create a new repository**。
4. 填写好自己的xposed项目的项目名，最后点击 **Create repository from template**。  
   比如：取名 HelloWorld。那么你的Github仓库中就会出现一个全新、没有git记录的，但拥有本模板所有代码的HelloWorld项目。
5. 最后，正常Clone你的项目（如 HelloWorld），无需纠结配置问题，理论上可以直接运行。

## LSPXposed（分支LSP）
鉴于 LSP 框架的广泛使用，我已经对项目进行了适配。本分支优点是，一次重启都不需要；但是需要手动勾选应用。

### 环境依赖
- Magisk：推荐使用Zygisk（最新版本）
- LSPosed：下载安装LSPosed模块，需要和Magisk匹配。（推荐安装 LSPosed-Zygisk）

### 开发流程
1. 克隆项目并切换至 LSP 分支。
2. 编写代码：直接进入 EasyHooker 类，根据需求编写代码。
3. 运行项目：安装部署至手机后，在 LSP 中勾选对应应用生效。

## 普通Xposed（分支master）
最常用的分支，不用手动勾选应用，直接改代码即可实现免重启；但是首次安装本应用后，需要重启一次。

### 环境准备
本模块依赖 Xposed 框架，需先完成安装（已安装可跳过）：
[Xposed 安装教程](https://blog.csdn.net/qq_40194392/article/details/83013443)

### 开发流程
1. 使用AndroidStudio将你的项目到clone本地。
2. build项目：首次构建可能自动下载依赖一些依赖包。
3. 运行项目：点击 Run 后，安装 APK 至手机并通过 Installer 启用模块。
   - 初次启用需重启手机完成加载
   - 后续开发无需重启，直接运行即可实现 hook

### 代码编写
在完成准备工作后，直接进入 EasyHooker 类：
1. 填写需要hook的包名。
2. 编写 hook 逻辑并运行，无需重启即可生效。

## 常见问题
1. hook 失效
   - 原因：xposed模块加载异常
   - 方案：卸载模块并在 Installer 中重新启用，必要时重启手机
2. 项目无法运行
   - 原因：gradle构建失败
   - 方案：检查 Gradle 版本及配置，或参考 [Gradle 官网](https://gradle.org/releases) 手动安装

## 使用提示
1. 目前仅支持单应用 hook，切换目标应用时：普通版只需修改 EasyHooker 中的包名，LSP版切换下勾选的应用即可。
2. 项目依赖手机的ROOT权限，以及XposedInstaller等内容。

## 项目说明
1. 常用类
   - EasyHooker：hook 逻辑处理核心类
   - Tool：包括日志、超长日志、堆栈，以及参数信息等输出
   - Hool：封装好的一些hook的方法，目前支持整个类、单个方法、构造的简单化hook。
2. 反馈与支持
   - 遇到问题或有改进建议，请提交 Issues
