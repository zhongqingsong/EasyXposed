package com.zqstudio.easyxposed;

import com.zqstudio.easyxposed.utils.Tool;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static com.zqstudio.easyxposed.utils.Hool.hookAllMethod;
import static com.zqstudio.easyxposed.utils.Tool.myLog;
import static com.zqstudio.easyxposed.utils.Tool.showStack;

/**
 * CreateDate：2020/6/12 9:30
 * author：ShiYong.Z
 * version：1.0
 * Description： 模块真正意义上的hook代码所在。
 * 		实际上，本项目相当于有 三层 hook。第一层来hook所有的应用，第二层过滤出真正的应用，第三层开始hook。
 * 	第一层：就是Loader。在过滤掉大部分的应用后，让少部分的应用进入第二层hook。
 * 	第二层：本类的handleLoad，根据实际的要被hook的应用，过滤出真正要被hook的应用。
 * 	第三层：即 gameHook，真正意义上的应用被hook的具体逻辑。
 */
public final class EasyHooker implements IXposedHookLoadPackage {
	@Override
	public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {

		// TODO 实际要被hook的应用包名
		String strApp = "com.tellurionmobile.realmcraft";
		if (!strApp.equals(lpparam.packageName)){
			return;
		}

		Tool.classLoader = lpparam.classLoader;
		myLog("Hook Version = 9.7");
		gameHook();
	}

	private void gameHook(){
		hookAllMethod("com.ironsource.sdk.controller.IronSourceWebView$JSInterface",
				"onAdWindowsClosed", new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						showStack();
					}
				});
	}
}
