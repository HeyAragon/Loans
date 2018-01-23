package com.hackhome.loans.common.tinker;

import android.content.Context;
import android.content.SharedPreferences;

import com.hackhome.loans.common.Constants;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by aragon on 2017/12/9 0009.
 */

public class TinkerSharePreferenceUtils {

    private static final String HISTORY_PATCH = "history_patch";

    private static Context mContext;
    private String mCurrentPatchVersion;
    private SharedPreferences.Editor mEditor;
    private ArrayList<String> mPatches = new ArrayList<>();

    private SharedPreferences mPreferences;

    public String getCurrenPatchVersion() {
        return mCurrentPatchVersion;
    }

    public void setCurrentPatchVersion(String currenPatchVersion) {
        mCurrentPatchVersion = currenPatchVersion;
    }

    private static class ClassHolder {
        private static final TinkerSharePreferenceUtils INSTANCE = new TinkerSharePreferenceUtils();
    }

    public static TinkerSharePreferenceUtils getInstance() {
        mContext = TinkerLoanApplication.getLoanApplication();
        return ClassHolder.INSTANCE;
    }

    private TinkerSharePreferenceUtils() {
        initPreference();
    }

    private void initPreference() {
        mPreferences = mContext.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (mEditor == null) {
            mEditor = mPreferences.edit();
        }
        String patch = mPreferences.getString(HISTORY_PATCH, null);
        String[] patches;
        if (patch != null) {
            patches = patch.split("[$]{2}");
            Collections.addAll(mPatches, patches);
        }
    }

    public void addPatch(String patch) {
        patch = patch.toLowerCase();
        if (mPatches.contains(patch)) {
            mPatches.remove(patch);
        }
        mPatches.add(0, patch);

        StringBuilder builder = new StringBuilder();
        for (String string : mPatches) {
            builder.append(string).append("$$");
        }
        if (mEditor == null) return;
        mEditor.putString(HISTORY_PATCH, builder.toString());
        mEditor.commit();
    }

    public ArrayList<String> getPatches() {
        return mPatches;
    }
}
