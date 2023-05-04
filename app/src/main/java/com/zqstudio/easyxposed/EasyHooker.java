package com.zqstudio.easyxposed;

import com.zqstudio.easyxposed.utils.Tool;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static com.zqstudio.easyxposed.utils.Hool.hookMethod;
import static com.zqstudio.easyxposed.utils.Tool.clazzForName;
import static com.zqstudio.easyxposed.utils.Tool.showLog;
import static com.zqstudio.easyxposed.utils.Tool.showStack;

/**
 * CreateDate：2020/6/12 9:30
 * author：ShiYong.Z
 * version：1.0
 * Description： 模块真正意义上的hook代码所在。采用了三层结构，这三个层次保证了免重启。
 *
 * 	第一层：XposedLoder。	主要作用，作为模块使用，对所有的应用生效。
 * 			次要作用，如果所有应用都hook，效率极低；所以这里就会过滤掉大部分的应用，让少部分的应用进入第二层。
 * 	第二层：本类的handleLoadPackage。	从初选应用中，过滤出真正要被hook的应用。
 * 			因为第二层已经不属于模块的初始hook了，属于模块的hook逻辑。
 * 	第三层：方法appHook。			真正意义上的hook逻辑，这里也是我们需要对应用进行实际操作的地方。
 */
public final class EasyHooker implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {

		// TODO 实际要被hook的应用包名
		String strApp = "com.test.app";
		if (!strApp.equals(lpparam.packageName)){
			return;
		}

		// 开启第三层
		if (Tool.saveClassloader(lpparam.classLoader)){
			showLog("Hook Version = 0.1");
			appHook();
		}else{
			showLog("Hook cant get Classloader, check !!!");
		}
	}

	private void appHook(){
		Class test = clazzForName("test.a.b.c");
		hookMethod("com.sdk.controller.WebView$JSInterface",
				"onWindowsClosed", test, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						showStack();
					}
				});
	}
}
