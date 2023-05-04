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


/**
 * CreateDate：2020/6/12 9:27
 * author：ShiYong.Z
 * version：1.0
 * Description： 本模块是结合Xposed进行了一定的改造，使之成为一个完
 * 整的可以直接使用的多功能的简易框架。同时，尽可能的考虑到了效率问题。
 *
 *
 ********************************************************
 *	   本类的任何修改，必须重启。其他的所以地方，修改才免重启	*
 ********************************************************
 */
public final class XposedLoder implements IXposedHookLoadPackage,IXposedHookZygoteInit {
	/*
		配置信息：一旦修改，必须重启手机
		1、想要debug的应用：	因为debug需要在init时操作，所以每次修改都需要重启手机
		2、模块名：			本项目最后生成的模块的名称，即包名。
		3、真正的hook的类：	真正的hook代码所在的类。
	 */
	private final String toDebugApp, moduleName, realHookClass;
	public XposedLoder() {
		toDebugApp = "com.want.debug.app";
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
		// 过滤常见包名
		if (Tool.appFilter(lpparam.packageName)){
			return;
		}

		/*
			开启第一层：
			将xposed的classloader替换为Application的classloader：
			解决宿主程序存在多个 dex 文件时，可能发生 ClassNotFound 的问题。
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
	 * 		安装app以后，系统会在 /data/app/ 下备份.apk文件。动态加载这个apk文件，然后得到里面的逻辑。
	 */
	private void invokeHandleHookMethod(Context context,
										XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
		// 先动态加载模块的apk
		File apkFile = findApkFile(context);
		if (apkFile == null) {
			return;
		}

		/*
			开启第二层：
		 	加载apk中的hook处理类，并反射调用它的handleLoadPackage。
		 */
		PathClassLoader pathClassLoader = new PathClassLoader(apkFile.getAbsolutePath(), ClassLoader.getSystemClassLoader());
		Class<?> cls = Class.forName(realHookClass, true, pathClassLoader);
		Object instance = cls.newInstance();
		Method method = cls.getDeclaredMethod("handleLoadPackage", XC_LoadPackage.LoadPackageParam.class);
		method.invoke(instance, loadPackageParam);
	}

	// 根据包名，手动构建Context，然后调用getPackageCodePath()来定位apk
	private File findApkFile(Context context) {
		try {
			Context moudleContext = context.createPackageContext(moduleName,
					Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
			String apkPath = moudleContext.getPackageCodePath();
			return new File(apkPath);
		} catch (Exception e) {
			Tool.showException(e);
		}
		return null;
	}

	/*
		在每个进程启动时，就直接修改进程的启动参数，完成应用的debug修改
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
					Tool.showLog(strName + " can debug now !");
				}else{
					Tool.showLog(strName + " change fail .");
				}
			}
		});
	}
}
