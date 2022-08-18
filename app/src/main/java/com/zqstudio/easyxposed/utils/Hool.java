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

	/**
	 * hook指定类的所有同名方法，方便处理方法重载。
	 * @param clazzName		类名
	 * @param methodName	方法名
	 * @param xcMethodHook	替换的方法
	 */
	public static void hookSameMethod(String clazzName, String methodName, XC_MethodHook xcMethodHook){
		Class clazz = clazzForName(clazzName);
		try{
			XposedBridge.hookAllMethods(clazz, methodName, xcMethodHook);
		}catch(Exception e){
			myException(e);
		}
	}

	/**
	 * hook指定类的构造方法
	 * @param clazzName	类名
	 * @param params	对应的构造方法的参数
	 */
	public static void hookConstructor(String clazzName, Object... params){
		Class clazz = clazzForName(clazzName);
		try{
			XposedHelpers.findAndHookConstructor(clazz,params);
		}catch(Exception e){
			myException(e);
		}
	}

	/**
	 * hook指定的方法
	 * @param clazzName		类名
	 * @param methodName	方法名
	 * @param params		方法的参数，以及对应的替换方法
	 */
	public static void hookMethod(String clazzName, String methodName, Object... params){
		Class clazz = clazzForName(clazzName);
		try{
			XposedHelpers.findAndHookMethod(clazz,methodName,params);
		}catch(Exception e){
			myException(e);
		}
	}
}
