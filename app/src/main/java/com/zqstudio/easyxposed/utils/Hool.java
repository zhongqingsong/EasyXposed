package com.zqstudio.easyxposed.utils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * CreateDate：2020/6/12 9:35
 * author：ShiYong.Z
 * version：1.1
 * Description：	封装的一些常用的方法，免去了一些重复的代码。
 */
public final class Hool {

	private static ClassLoader classLoader;

	/**
	 * 保存游戏的classloader
	 * @param loader 实例
	 * @return true，成功获取
	 */
	public static boolean saveClassloader(ClassLoader loader){
		boolean result = false;
		if (loader != null){
			classLoader = loader;
			result = true;
		}
		return result;
	}

	/**
	 * 通过字符串获取类
	 * @param clazzName 完整的类名
	 * @return 类
	 */
	public static Class<?> clazzForName(String clazzName){
		Class<?> result = null;
		try {
			result = Class.forName(clazzName, false, classLoader);
		} catch (ClassNotFoundException e) {
			Tool.showException(e);
		}
		return result;
	}

	/**
	 * hook构造方法
	 * @param clazzName	类名
	 * @param params	构造的参数列表。注意：最后一个为hooker构造。
	 */
	public static void hookConstructor(String clazzName, Object... params){
		Class<?> clazz = clazzForName(clazzName);
		try{
			XposedHelpers.findAndHookConstructor(clazz,params);
		}catch(Exception e){
			Tool.showException(e);
		}
	}
	/**
	 * hook所有的构造方法
	 * @param clazzName	类名
	 * @param params	hook构造
	 */
	public static void hookAllConstructor(String clazzName, XC_MethodHook params){
		Class<?> clazz = clazzForName(clazzName);
		try{
			XposedBridge.hookAllConstructors(clazz,params);
		}catch(Exception e){
			Tool.showException(e);
		}
	}


	/**
	 * hook指定的方法（不区分是否为静态）
	 * @param clazzName		类名
	 * @param methodName	方法名
	 * @param params		参数列表：最后一个为hooker方法。
	 */
	public static void hookMethod(String clazzName, String methodName, Object... params){
		Class<?> clazz = clazzForName(clazzName);
		try{
			XposedHelpers.findAndHookMethod(clazz,methodName,params);
		}catch(Exception e){
			Tool.showException(e);
		}
	}
	/**
	 * hook指定类里的所有同名的方法
	 * @param clazzName		类名
	 * @param methodName	方法名
	 * @param xcMethodHook	hooker方法
	 */
	public static void hookAllMethod(String clazzName, String methodName, XC_MethodHook xcMethodHook){
		Class<?> clazz = clazzForName(clazzName);
		try{
			XposedBridge.hookAllMethods(clazz, methodName, xcMethodHook);
		}catch(Exception e){
			Tool.showException(e);
		}
	}

}
