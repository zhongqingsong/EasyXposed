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
 * Description：
 */
public final class EasyHooker implements IXposedHookLoadPackage {
	@Override
	public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
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
