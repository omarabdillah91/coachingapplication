package utility;

import android.content.SharedPreferences;

import unilever.coachingform.MainApp;

/**
 * Created by adrian on 8/19/2016.
 */
public class SharedPreferenceUtil {

    private static SharedPreferences sharedPreferences;

    public static synchronized SharedPreferences getInstance(){
        if(sharedPreferences == null){
            sharedPreferences = MainApp.getContext().getSharedPreferences("shared",0);
        }
        return sharedPreferences;
    }

    public static void putString(String key, String value){
        SharedPreferences.Editor editor = SharedPreferenceUtil.getInstance().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key) {
        return SharedPreferenceUtil.getInstance().getString(key, "");
    }
}
