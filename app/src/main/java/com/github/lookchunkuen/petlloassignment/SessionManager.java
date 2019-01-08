package com.github.lookchunkuen.petlloassignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    private static final String PREF_NAME = "com.github.lookchunkuen.petlloassignment";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    int PRIVATE_MODE = 0;



    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void CreateSession(String name, String email){
        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public boolean IsLoggedIn(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }

    public void CheckLogin(){
        if(!this.IsLoggedIn()){
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            ((HomeActivity)context).finish();
        }
    }

    public HashMap<String, String> GetUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));

        return user;
    }

    public void Logout(){
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((HomeActivity)context).finish();
    }
}
