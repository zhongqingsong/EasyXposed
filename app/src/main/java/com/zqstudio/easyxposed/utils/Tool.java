package com.zqstudio.easyxposed.utils;

import android.util.Log;

/**
 * CreateDate：2020/6/12 9:31
 * author：ShiYong.Z
 * version：1.1
 * Description：	常用的工具类。
 */
public final class Tool {

	private static final String TAG = "zqLog";
	private static final String[] arrContain = {"com.google", "com.android"};

	private static ClassLoader classLoader;


	// 核心块
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
			showException(e);
		}
		return result;
	}

	/**
	 * 过滤进程名：可自行添加要过滤的包名前部分
	 * @param pkgName 应用包名
	 * @return true，被过滤
	 */
	public static boolean appFilter(String pkgName){
		for (String str : arrContain){
			if (pkgName.startsWith(str)){
				return true;
			}
		}
		return false;
	}


	// 日志块

	/**
	 * 普通日志
	 * @param msg 标准的日志输出
	 */
	public static void showLog(String msg){
		Log.w(TAG, msg);
	}

	/**
	 * 打印调用本方法时的堆栈
	 */
	public static void showStack(){
		Log.e(TAG, Log.getStackTraceString(new Throwable()));
	}

	/**
	 * 打印异常信息
	 * @param e 异常
	 */
	public static void showException(Exception e){
		Log.e(TAG, e.toString());
	}

	/**
	 * 输出任意长数据（String的极限长度）
	 * @param str 大型字符串
	 */
	public static void showMessage(String str){
		int len = str.length();
		int nFileLength = 512 * 7;
		if (len > nFileLength){
			showLog("Long Data Size = " + len);
			println(str);
		}else{
			showLog(str);
		}
	}

	/**
	 * 打印方法的所有的参数名及其数据
	 * @param args XC_MethodHook.MethodHookParam#args
	 * @return 参数列表
	 */
	public static String parseArgs(Object[] args){
		String result = "";
		if (args == null) return result;
		StringBuilder str = new StringBuilder();
		for (Object obj : args){
			str.append(obj.toString()).append("   ");
		}
		result = str.toString().trim();
		return result;
	}

	private static void println(String longMsg){
		String buf;
		int start = 0, end, nSize = 3096, len = longMsg.length();
		while (start < len) {
			end = Math.min((start + nSize), len);
			buf = longMsg.substring(start, end);
			start += nSize;
			Log.d(TAG, buf);
		}
	}
}
