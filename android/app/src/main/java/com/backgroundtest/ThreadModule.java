package com.backgroundtest;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.widget.Toast;
import android.content.Intent;
import android.util.Log;
import android.content.Context;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.WorkManager;
import androidx.work.PeriodicWorkRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.facebook.react.util.RNLog;

public class ThreadModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext reactContext;
    private PeriodicWorkRequest workRequest;

    ThreadModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
        workRequest = new PeriodicWorkRequest.Builder(WorkService.class, 10, TimeUnit.MILLISECONDS).build();
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
    public void startService() {
        Intent dataIntent = new Intent(reactContext, ThreadService.class);
        reactContext.startService(dataIntent);
    }

    @ReactMethod
    public void stopService() {
        reactContext.stopService(new Intent(reactContext,
                ThreadService.class));
    }

    @ReactMethod
    public void workerStart() {
        WorkManager.getInstance().enqueueUniquePeriodicWork("WorkService", ExistingPeriodicWorkPolicy.KEEP,
                workRequest);
    }

    @ReactMethod
    public void workerStop() {
        WorkManager.getInstance().cancelUniqueWork("WorkService");
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