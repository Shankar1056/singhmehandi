package apextechies.singhmehandi.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import apextechies.singhmehandi.AppController;

public class ClsGeneral {
	public static Context mContext;

	public static void setPreferences(Context context, String key, String value) {
		mContext = context;
		SharedPreferences.Editor editor = mContext.getSharedPreferences(
				"WED_APP", Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getPreferences(Context context, String key) {
		mContext = context;
		SharedPreferences prefs = mContext.getSharedPreferences("WED_APP",
				Context.MODE_PRIVATE);
		String text = prefs.getString(key, "");
		return text;
	}


	public static void setPreferences(String key, String value) {
		SharedPreferences.Editor editor = AppController.getInstance().getSharedPreferences(
				"WED_APP", Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void setPreferences(String key, boolean value) {
		SharedPreferences.Editor editor = AppController.getInstance().getSharedPreferences(
				"WED_APP", Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void setPreferences(String key, int value) {
		SharedPreferences.Editor editor = AppController.getInstance().getSharedPreferences(
				"WED_APP", Context.MODE_PRIVATE).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static boolean getBoolPreferences(String key) {
		SharedPreferences prefs = AppController.getInstance().getSharedPreferences("WED_APP",
				Context.MODE_PRIVATE);
		return prefs.getBoolean(key, false);
	}

	public static String getStrPreferences(String key) {
		SharedPreferences prefs = AppController.getInstance().getSharedPreferences("WED_APP",
				Context.MODE_PRIVATE);
		return prefs.getString(key, "");
	}

	public static int getIntPreferences(String key) {
		SharedPreferences prefs = AppController.getInstance().getSharedPreferences("WED_APP",
				Context.MODE_PRIVATE);
		return prefs.getInt(key, 0);
	}


	public static boolean isNetworkAvailable(Context context)
	{

		ConnectivityManager connectivity  = null;
		boolean isNetworkAvail = false;

		try
		{
			connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

			if (connectivity != null)
			{
				NetworkInfo[] info = connectivity.getAllNetworkInfo();

				if (info != null)
				{
					for (int i = 0; i < info.length; i++)
					{
						if (info[i].getState() == NetworkInfo.State.CONNECTED)
						{

							return true;
						}
					}
				}
			}
			return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(connectivity !=null)
			{
				connectivity = null;
			}
		}
		return isNetworkAvail;
	}


}
