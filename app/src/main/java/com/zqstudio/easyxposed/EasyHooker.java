package com.zqstudio.easyxposed;

import com.zqstudio.easyxposed.utils.Tool;
import android.content.ContentResolver;
import android.location.LocationManager;
import android.provider.Settings;
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
		Class test = clazzForName("io.virtualapp.sandvxposed64");
		hookMethod("com.ironsource.sdk.controller.IronSourceWebView$JSInterface",
				"onAdWindowsClosed", test, new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						super.beforeHookedMethod(param);
						showStack();
					}
				});
		hookMethod(LocationManager.class, "isProviderEnabled", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String provider = (String) param.args[0];
                if (provider.equals(LocationManager.GPS_PROVIDER)) {
                    XposedBridge.log("Spoofing GPS enabled status for provider: " + provider);
                    param.setResult(true); 
                }
            }
        });
	hookMethod(Settings.Secure.class, "getInt", ContentResolver.class, String.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String name = (String) param.args[1];
                if (name.equals("location_mode")) {
                    XposedBridge.log("Settings.Secure.getInt called with name: " + name + " and default value: " + param.args[2]);
                    XposedBridge.log("Spoofing location_mode as enabled");
                    param.setResult(3); 
                }
            }
        });
	}
}
