package com.zqstudio.easyxposed.easy;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.zqstudio.easyxposed.BuildConfig;
import com.zqstudio.easyxposed.EasyHooker;
import com.zqstudio.easyxposed.utils.Tool;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.PathClassLoader;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static com.zqstudio.easyxposed.utils.Tool.myLog;

/**
 * CreateDate：2020/6/12 9:27
 * author：ShiYong.Z
 * version：1.0
 * Description：
 */
public final class XposedLoder implements IXposedHookLoadPackage,IXposedHookZygoteInit {
	private final String toDebugApp, moduleName, realHookClass;
	public XposedLoder() {
		toDebugApp = "test.abc";
		moduleName = "com.zqstudio.easyxposed";
		realHookClass = EasyHooker.class.getName();
	}

	@Override
	public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {
		// 排除系统应用
		if (lpparam.appInfo == null || (lpparam.appInfo.flags
				& (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) == 1) {
			return;
		}
		// 过滤指定的包名：增删改查后需要重启（新框架待确认）
		if (Tool.appFilter(lpparam.packageName)){
			return;
		}

		/*
			将xposed的classloader替换为Application的classloader：
			解决宿主程序存在多个.dex文件时，有时候ClassNotFound的问题
		 */
		XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				Context context = (Context) param.args[0];
				lpparam.classLoader = context.getClassLoader();
				invokeHandleHookMethod(context, lpparam);
			}
		});
	}

	/**
	 * 安装app以后，系统会在/data/app/下备份了一份.apk文件，通过动态加载这个apk文件，调用相应的方法
	 * 这样就可以实现，只需要第一次重启，以后修改hook代码就不用重启了
	 *
	 * @param context           context参数
	 * @param loadPackageParam  传入XC_LoadPackage.LoadPackageParam参数
	 * @throws Throwable 		抛出各种异常
	 */
	private void invokeHandleHookMethod(Context context,
										XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
		File apkFile = findApkFile(context);
		if (apkFile == null) {
			throw new RuntimeException("模块APK失败");
		}
		//加载指定的hook逻辑处理类，并调用它的handleHook方法
		PathClassLoader pathClassLoader = new PathClassLoader(apkFile.getAbsolutePath(), ClassLoader.getSystemClassLoader());
		Class<?> cls = Class.forName(realHookClass, true, pathClassLoader);
		Object instance = cls.newInstance();
		Method method = cls.getDeclaredMethod("handleLoadPackage", XC_LoadPackage.LoadPackageParam.class);
		method.invoke(instance, loadPackageParam);
	}

	/**
	 * 根据包名构建目标Context,并调用getPackageCodePath()来定位apk
	 */
	private File findApkFile(Context context) {
		try {
			Context moudleContext = context.createPackageContext(moduleName,
					Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
			String apkPath = moudleContext.getPackageCodePath();
			return new File(apkPath);
		} catch (Exception e) {
			myLog("findApkFile " + e);
		}
		return null;
	}

	/*
		在每个线程启动时，就直接修改参数。完成应用的debug修改
		xref: /frameworks/base/core/java/android/os/Process.java
	*/
	@Override
	public void initZygote(final IXposedHookZygoteInit.StartupParam startupParam) {
		XposedBridge.hookAllMethods(android.os.Process.class, "start", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				super.beforeHookedMethod(param);

				String strName = (String) param.args[1];
				if (!toDebugApp.equals(strName)){
					return;
				}

				int id = 5;
				int DEBUG_ENABLE_DEBUGGER = 0x1;

				int flags = (Integer) param.args[id];
				if ((flags & DEBUG_ENABLE_DEBUGGER) == 0) {
					flags |= DEBUG_ENABLE_DEBUGGER;
				}
				param.args[id] = flags;
				if (BuildConfig.DEBUG) {
					myLog(strName + " can debug now !");
				}else{
					myLog(strName + " change fail .");
				}
			}
		});
	}
}
