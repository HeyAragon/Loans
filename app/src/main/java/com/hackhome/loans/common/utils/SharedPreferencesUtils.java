package com.hackhome.loans.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.hackhome.loans.LoanApplication;
import com.hackhome.loans.common.Constants;

import java.util.HashSet;
import java.util.Set;


public class SharedPreferencesUtils {

	private static SharedPreferencesUtils instance;
	private SharedPreferences settings;
	private Context mContext;
	private Editor editor;
	
	public synchronized static SharedPreferencesUtils getInstance(){
		if(instance == null){
			instance = new SharedPreferencesUtils();
		}

		return instance;
	}
	
	private SharedPreferencesUtils(){
		if (mContext==null) {
			mContext = LoanApplication.getInstance();
		}

		if(settings == null){
			settings = mContext.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		}
		
		if(editor == null){
			editor = settings.edit();
		}
	}
	
	public void putString(String key, String value){
		editor.putString(key, value);
		editor.commit();
	}
	
	public void putInt(String key, int value){
		editor.putInt(key, value);
		editor.commit();
	}
	
	public void putBoolean(String key, boolean value){
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public void putLong(String key, long value){
		editor.putLong(key, value);
		editor.commit();
	}
	
	public int queryInt(String key){
		return settings.getInt(key, -1);
	}

	public int queryInt(String key, int defaultValue){
		return settings.getInt(key,defaultValue);
	}
	
	public long queryLong(String key){
		return settings.getLong(key, -1);
	}
	
	public String queryString(String key){
		return settings.getString(key,"null");
	}

	public String queryString(String key, String defaultValue){
		return settings.getString(key,defaultValue);
	}
	
	public boolean queryBoolean(String key, boolean defaultValue){
		return settings.getBoolean(key,defaultValue);
	}

	public void clearLocalUserCache(){
		editor.putString("jsessionid",""); // 登录授权码
		editor.putString("username", ""); // 登录名
		editor.putString("userid", ""); // 登录名
		editor.commit();
	}
	
}
