package com.ctyon.practicedemo.utils;

import android.util.Log;


public class LogUtil {

	private static final String TAG = "ctyon";

	private static boolean OUTPUT_ENABLE = false;

	public static void setOutputEnable(boolean enable) {
		OUTPUT_ENABLE = enable;
	}

	public static void i(String msg) {
		if (msg == null) {
			return;
		}
		if (OUTPUT_ENABLE) {
			Log.i(TAG, msg);
		}
	}

	public static void e(String msg) {
		if (msg == null) {
			return;
		}
		if (OUTPUT_ENABLE) {
			Log.e(TAG, msg);
		}
	}
}
