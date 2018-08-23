package com.ctyon.practicedemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class CommonUtil {

	public static void launchActivity(Context context, Class<?> activity) {
		Intent intent = new Intent(context, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivity(intent);
	}

	public static void launchActivity(Context context, Class<?> activity, Bundle bundle) {
		Intent intent = new Intent(context, activity);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivity(intent);
	}

	public static void launchActivity(Context context, Class<?> activity, Bundle bundle, int flag) {
		Intent intent = new Intent(context, activity);
		intent.putExtras(bundle);
		intent.addFlags(flag);
		context.startActivity(intent);
	}
	
	public static void launchActivityForResult(Context context, Class<?> activity, int RequestCode) {
		Intent intent = new Intent(context, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		((Activity)context).startActivityForResult(intent, RequestCode);
	}

	public static boolean isNull(String string) {
		if (string == null || string.equals("")) {
			return true;
		}
		return false;
	}

	public static <T> boolean isNull(List<T> list) {
		if (list == null || list.size() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isNull(String[] strings) {
		if (strings == null || strings.length == 0) {
			return true;
		}
		return false;
	}

	// �ж��Ƿ��ֻ���
	public static boolean isPhoneNum(String string) {
		if (string.length() == 11) {
			String telRegex = "^[1][34578]\\d{9}$";
			return string.matches(telRegex);
		}
		return false;
	}

	public static String formatPhoneNum(String string) {
		if (string.length() == 11) {
			String telRegex = "(?<=\\d{3})(\\d{4})";
			return string.replaceAll(telRegex, " $1");
		}
		return null;
	}

	public static boolean isServerIp(String string) {
		if (CommonUtil.isNull(string)) {
			return false;
		}
		if (string.length() < 7 || string.length() > 15) {
			String telRegex = "^((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))";
			return string.matches(telRegex);
		}
		return false;
	}

	public static void installApk(Context mContext, File apkFile) {
		if (apkFile == null || !apkFile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkFile.getPath()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}

	public static int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			versionCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}


	public static int getVersionCode(Context context, String apkPath) {
		int versionCode = 0;
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(apkPath,
				PackageManager.GET_ACTIVITIES);
		if (info != null) {
			versionCode = info.versionCode;
		}
		return versionCode;
	}

	public static Drawable getAppIcon(Context context, String packname) {
		Drawable drawable = null;
		try {
			PackageManager pm = context.getPackageManager();
			ApplicationInfo info = pm.getApplicationInfo(packname, 0);
			if (info != null) {
				drawable = info.loadIcon(pm);
			}
		} catch (NameNotFoundException e) {
			LogUtil.i("getAppIcon-notFound");
			e.printStackTrace();
		}
		return drawable;
	}

	public static String getAppName(Context context, String packname) {
		String name = null;
		try {
			PackageManager pm = context.getPackageManager();
			ApplicationInfo info = pm.getApplicationInfo(packname, 0);
			if (info != null) {
				name = info.loadLabel(pm).toString();
			}
		} catch (NameNotFoundException e) {
			LogUtil.i("getAppName-notFound");
			e.printStackTrace();
		}
		return name;
	}

	public static String getVersionName(Context context) throws Exception {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packInfo = packageManager.getPackageInfo(
				context.getPackageName(), 0);
		String version = packInfo.versionName;
		return version;
	}

	public static boolean isPwdLengthValid(String newPwd, String confirmNewPwd) {
		if (newPwd.length() < 6 || newPwd.length() > 20) {
			return false;
		}
		return true;
	}

	public static boolean isPwdValid(String newPwd, String confirmNewPwd) {
		if (!newPwd.equals(confirmNewPwd)) {
			return false;
		}
		return true;
	}

	public static boolean isNewPwdEqualsOld(String newPwd, String oldPwd) {
		if (newPwd.equals(oldPwd)) {
			return true;
		}
		return false;
	}

	public static String getMEID(Context context) {
		//+"IMSI:"+tm.getSubscriberId()+"\n"
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}
	public static String getIMEI(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		Class clazz = tm.getClass();
		try {
			Method getImei = clazz.getDeclaredMethod("getImei",int.class);
			return getImei.invoke(tm,0).toString();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return  null;
	}
	public static String getIMSI(Context context){

		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getSubscriberId();
	}

	public static String getIccid(Context context){

		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getSimSerialNumber();
	}


	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resId = context.getResources().getIdentifier("status_bar_height",
				"dimen", "android");
		if (resId > 0) {
			result = context.getResources().getDimensionPixelSize(resId);
		}
		return result;
	}

	public static String str2MD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] byteDigest = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < byteDigest.length; offset++) {
				i = byteDigest[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void hideSoftInput(Context context, View view) {
//		view.requestFocus();
//		view.requestFocusFromTouch();
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive())
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); 
	}

	public static int hexStr2Int(String s){
		return Integer.parseInt(s.replace("0x",""), 16);
	}
}