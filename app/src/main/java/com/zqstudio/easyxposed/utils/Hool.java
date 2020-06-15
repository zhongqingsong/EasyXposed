package com.zqstudio.easyxposed.utils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

import static com.zqstudio.easyxposed.utils.Tool.clazzForName;

/**
 * CreateDate：2020/6/12 9:35
 * author：ShiYong.Z
 * version：1.0
 * Description：	封装的一些常用的方法，免去了一些重复的代码。
 *
 */
public final class Hool {

	public static void hookAllMethod(String clazzName, String methodName, XC_MethodHook xcMethodHook){
		Class clazz = clazzForName(clazzName);
		XposedBridge.hookAllMethods(clazz, methodName, xcMethodHook);
	}

	public static void hookConstructor(String clazzName, Object... params){
		Class clazz = clazzForName(clazzName);
		XposedHelpers.findAndHookConstructor(clazz,params);
	}

	public static void hookMethod(String clazzName, String methodName, Object... params){
		Class clazz = clazzForName(clazzName);
		XposedHelpers.findAndHookMethod(clazz,methodName,params);
	}
}
