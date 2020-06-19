package com.zqstudio.easyxposed.utils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

import static com.zqstudio.easyxposed.utils.Tool.clazzForName;
import static com.zqstudio.easyxposed.utils.Tool.myException;

/**
 * CreateDate：2020/6/12 9:35
 * author：ShiYong.Z
 * version：1.0
 * Description：	封装的一些常用的方法，免去了一些重复的代码。
 *		1、只使用于EasyHooker里面的方法
 */
public final class Hool {

	public static void hookAllMethod(String clazzName, String methodName, XC_MethodHook xcMethodHook){
		Class clazz = clazzForName(clazzName);
		try{
			XposedBridge.hookAllMethods(clazz, methodName, xcMethodHook);
		}catch(Exception e){
			myException(e);
		}
	}

	public static void hookConstructor(String clazzName, Object... params){
		Class clazz = clazzForName(clazzName);
		try{
			XposedHelpers.findAndHookConstructor(clazz,params);
		}catch(Exception e){
			myException(e);
		}
	}

	public static void hookMethod(String clazzName, String methodName, Object... params){
		Class clazz = clazzForName(clazzName);
		try{
			XposedHelpers.findAndHookMethod(clazz,methodName,params);
		}catch(Exception e){
			myException(e);
		}
	}
}
