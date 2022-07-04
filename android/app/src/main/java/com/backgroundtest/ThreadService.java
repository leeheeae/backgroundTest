package com.backgroundtest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.os.Handler;
import android.os.Build;
import android.os.IBinder;
import android.app.Notification;
import android.app.PendingIntent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationManager;
import android.app.NotificationChannel;

import android.util.Log;
import com.facebook.react.HeadlessJsTaskService;

//https://developer.android.com/reference/android/app/Service#onCreate()
public class ThreadService extends Service {
    // NotificationId는 0이면 안됨
    private static final int NOTIFICATION_ID = 12345;
    private static final String CHANNEL_ID = "ThreadTest";

    // notification 채널 생성
    private void createNotificationChannel() {
        // 26버전 이상만 실행
        // 이상부터 channel_Id 설정을 꼭 해줘야함
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH; // 알림 중요도 표시
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Background", importance);
            channel.setDescription("CHANEL DESCRIPTION");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    // 서비스를 소멸시킬때 사용
    @Override
    public void onDestroy() {
        super.onDestroy();
        // runnable handler를 삭제
        // this.handler.removeCallbacks(this.runnableCode);
    }

    // onStartCommand 서비스 실행하는 요청 메소드
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // this.handler.post(this.runnableCode); // module의 startService()
        Context context = getApplicationContext();

        createNotificationChannel();
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("THREADTEST")
                .setContentText("테스트")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .build();

        // // foreground 시작 startForeground(키, 실행할 값)
        startForeground(NOTIFICATION_ID, notification);

        /*
         * 1) START_REDELIVER_INTENT
         * 시스템이 서비스를 중단하면 서비스를 다시 생성한다.
         * 그리고 이 서비스에 전달된 마지막 인텐트로 onStartCommand()를 호출한다.
         * 모든 보류 인텐트가 차례로 전달된다.
         * 파일 다운로드와 같은 서비스에 적합하다.
         *  
         * (2) START_STICKY
         * 시스템이 서비스를 중단하면 서비스를 다시 생성한다.
         * 마지막 인텐트를 전달하지 않고 null 인텐트로 onStartcommand()를 호출한다.
         * 명령을 실행하진 않지만 작업을 기다리는 미디어 플레이어와 같은 서비스에 적합하다.
         *  
         * (3) START_NOT_STICKY
         * 시스템이 서비스를 중단하면 서비스를 재생성하지 않는다.
         * 다시 시작하려는 경우에 적합하다.
         * 
         */
        return START_REDELIVER_INTENT;

    }
}