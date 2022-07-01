package com.backgroundtest;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.widget.Toast;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import com.facebook.react.util.RNLog;

public class ThreadModule extends ReactContextBaseJavaModule {
    ReactApplicationContext reactContext;

    ThreadModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "ThreadModule";
    }

    @ReactMethod
    public void LogTest() {
        RNLog.e("LogTest");
    }

    @ReactMethod
    public void show(String message, int duration) {
        ReactApplicationContext context = getReactApplicationContext();
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("SHORT", Toast.LENGTH_SHORT);
        constants.put("LONG", Toast.LENGTH_LONG);
        return constants;
    }
}