package com.backgroundtest;

import androidx.work.Worker;
import androidx.work.WorkManager;
import androidx.work.ForegroundInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import androidx.core.app.NotificationCompat;
import android.content.Context;
import android.os.Build;
import android.app.PendingIntent;
import android.content.Intent;
import androidx.work.WorkerParameters;
import com.facebook.react.HeadlessJsTaskService;

public class WorkService extends Worker {
    public WorkService(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }
    private static final int SERVICE_NOTIFICATION_ID = 12345;
    private static final String CHANNEL_ID = "ThreadTest";

    private NotificationManager notificationManager;

    //Work 실행
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        Intent service = new Intent(context, HeadlessEventService.class);
        // Mark the Worker as important
        String progress = "Starting Download";
        setForegroundAsync(createForegroundInfo(progress));

        getApplicationContext().startService(service);

        // 백그라운드 작업을 처리하는 동안 장치가 절전모드로 전환되지 않도록 웨이크 잠금을 획득
        HeadlessJsTaskService.acquireWakeLockNow(context);
        return Result.success();
    }
    //
    private void NotificationSetting() {


    }

    //Foreground에서 실행
    private ForegroundInfo createForegroundInfo(String progress) {
        Context context = getApplicationContext();
        String id = context.getString(R.string.notification_channel_id);
        String title = context.getString(R.string.notification_title);

        // This PendingIntent can be used to cancel the worker
        PendingIntent intent = WorkManager.getInstance(context)
                .createCancelPendingIntent(getId());

        // Build a notification using bytesRead and contentLength
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(context);
        }

        Notification notification = new NotificationCompat.Builder(context, id)
                .setContentTitle(title)
                .setTicker(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                // Add the cancel action to the notification which can
                // be used to cancel the worker
                // .addAction(android.R.drawable.ic_delete, cancel, intent)
                .build();

        return new ForegroundInfo(SERVICE_NOTIFICATION_ID, notification);
    }


    //notification channel create
    //@RequiresApi(Build.VERSION_CODES.O)
    private void createChannel(Context context) {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Background", importance);
        channel.setDescription("CHANEL DESCRIPTION");
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}