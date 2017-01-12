package com.xilehang.tencent.tools;

import java.io.File;

import android.os.Environment;

/**
 * @author wuweiqi
 * @version 创建时间：2016-11-23 下午1:57:27 类说明
 */
public class GlobealSetting {
	public static String SD_PATH = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + File.separator;
	public static String RES_PATH = Environment.getExternalStorageDirectory()
			.getAbsolutePath()
			+ File.separator
			+ "Soarup"
			+ File.separator
			+ "www5"
			+ File.separator
			+ "sharecoapp"
			+ File.separator
			+ "xlhEdit" + File.separator + "daishaupdate" + File.separator;
	public static String BACKGROUND_PATH = RES_PATH + "background/";
	public static String LUNBO_PATH = RES_PATH + "lunbo/";
	public static String SHOW_PATH = RES_PATH + "show/";
	public static String videopath = SD_PATH+"Soarup/www5/sharecoapp/xlhEdit/xingzhan_800/xingzhan.mp4";


}
