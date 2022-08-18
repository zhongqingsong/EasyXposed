package com.zqstudio.easyxposed;

import com.zqstudio.easyxposed.utils.Tool;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static com.zqstudio.easyxposed.utils.Hool.hookMethod;
import static com.zqstudio.easyxposed.utils.Tool.clazzForName;
import static com.zqstudio.easyxposed.utils.Tool.myLog;
import static com.zqstudio.easyxposed.utils.Tool.showStack;


public final class EasyHooker implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
		// WARN：去LSP中确认，实际要被hook的应用包名

		Tool.classLoader = lpparam.classLoader;
		myLog("Hook Version = 0.1");
		appHook();
	}

	private void appHook(){
		Class test = clazzForName("test.a.b.c");
		hookMethod("com.ironsource.sdk.controller.IronSourceWebView$JSInterface",
				"onAdWindowsClosed", test, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						showStack();
					}
				});
	}
}
