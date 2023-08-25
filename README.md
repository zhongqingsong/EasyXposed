# EasyXposed
本项目只是用来方便编写Xposed的模块的一个项目模板，简单点说，就是一个提前准备好了所有的内容的Xposed项目模板。  
使用本模板后，clone项目，然后就可以编写自己的Xposed代码啦！省去了每次写Xposed模块，都要来回折腾Xposed环境的这些问题！！  
因此，我们仍然需要Xposed的配套环境。  

# 特色
项目已转变为模板，让你拥有自己的专属的xposed项目。纯爱战士狂喜。  
1、项目已集成Xposed的jar包，省去了因年代久远，找不到合适的jar的苦恼。  
2、添加了一些常用的功能：如应用免重启、免debug修改等，以及各种常用的hook方法。  
3、再也不用去纠结什么class loader了，hook什么的全都使用字符串即可完成，简单高效！！  
4、一些常用的hook相关的功能的封装，最大程度上的减少了代码量。  

# 快速上手
1、确认自己的是哪个版本的xposed：是普通的xposed，还是LSPXposed。  
2、然后选择对应的分支进行：master=Xposed，LSP=LSPXposed。  
3、点击绿色的 Use this template，选择 Create a new repository。  
4、填写好自己的这个xposed项目的项目名，最后点击 Create repository from template。  
比如：取名 HelloWorld。那么你的仓库中就会出现一个全新、没有git记录的，但拥有本模板所有代码的HelloWorld项目。  
5、最后，正常Clone你的项目（如 HelloWorld），无需纠结配置问题，理论上可以直接运行。  

## LSPXposed（分支LSP）  
这是目前使用最多的是Xposed改版框架了。在此，本项目也适当的调整下项目，已适配LSP框架。  
1、本模块此时使用，需要依赖LSP框架。其需要的环境，这里简单提及一下：  
Magisk：推荐使用Zygisk（最新版本）  
LSPosed：下载安装LSPosed模块，需要和Magisk匹配。（推荐安装 LSPosed-Zygisk）  
2、编写代码  
直接进入 EasyHooker 类，然后根据实际需求，编写代码即可！  
3、运行项目  
直接点击Run按钮，安装到手机后，去LSP里确认本模块需要对谁生效。勾选对应的应用。  

## 普通Xposed（分支master）  
1、安装环境  
因为使用时，本模块需要依赖Xposed，所以，需要先完成Xposed的安装。如果已经安装好了Xposed，可以跳过本步骤，到下一步。  
https://blog.csdn.net/qq_40194392/article/details/83013443  

2、准备模块  
使用AndroidStudio将项目到clone本地。然后，直接Build一下，此时可能自行调整下运行环境；或者会自动构建下载需要的jar包等。  
正常情况下，完成上述步骤后，点击 Run 即可正常运行起来。  
当APK安装到手机上后，即可开始到Installer里勾选上我们的这个模块。然后第一次需要重启下手机，完成模块的加载。后续编写代码后，就不用再次重启了，就像写普通代码一样，直接运行即可实现hook。  

# 使用指南  
1、Clone项目  
根据自己的xposde框架，选择对应的分支，Create repository from template；接着clone自己的项目。  
2、编写代码  
上述的准备工作完成后，直接进入 EasyHooker 类，然后根据实际需求，编写代码即可！  
- 填写需要hook的应用的包名  
- 编写对应的hook逻辑  
- 开始Run，就可以hook啦。不再需要什么重启操作！！！  

## 常见错误
1、如果发现hook失效了，大部分原因都是xposed模块加载异常。可以尝试卸载、重新安装模块，或去Installer调整模块。切记，对模块本身做了操作后，是需要正常的重启手机。  
当然，只要本模块成功过一次后，就可以模块免重启了。后续无论hook什么应用，都可以不用重启了。  
2、项目运行不起来，大概率是gradle构建失败了。可以自行百度具体的原因。  
3、（较小概率）如果是Gradle安装失败，那么大概率就需要手动进行本地安装了：  
A、访问Gradle的官网，然后下载对应的gradle版本 https://gradle.org/releases  
B、找到并进入以下路径 "C:\Users\用户名\.gradle\wrapper\dists\gradle-用户自己的本地版本-all\一组字符串\"  
C、进入后，会发现可能有类似 gradle-6.5.1-all.zip.lck 之类的文件，之后将下载好的gradle的文件复制到上面的文件夹中（如果已经有东西了，就全部删掉）  
D、重启AndroidStudio，并重新打开项目，进行build。  

## 温馨提示  
0、为了方便，目前只支持单个应用的hook。如果需要切换hook其他应用，直接修改EasyHooker里的包名，然后run，hook即可立即生效，无需重启手机。  
1、本项目只是集成了xposed内容，仍然需要手机拥有ROOT权限以及XposedInstaller等内容。  

## 说明  
1、常用类  
一些提前封装好的类，方便进行代码的编写。  
- EasyHooker：整个xposed模块具体的用来进行hook逻辑处理的地方。  
- Tool：包括日志、超长日志、堆栈，以及参数信息等输出。以及最重要的获取class的封装方式。  
- Hool：封装好的一些hook的方法，目前支持整个类、单个方法、构造的简单化hook。

2、有任何的建议、问题，请提Issues。  
