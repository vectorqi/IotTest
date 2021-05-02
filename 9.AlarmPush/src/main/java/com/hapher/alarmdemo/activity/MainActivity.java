package com.hapher.alarmdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hapher.alarmdemo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button phone;
    private static String phoneNumber = "13071663867";
    public static final String SMS_ACTION = "SMS_DELIVERED_ACTION";
    public static final String TAG = "MainActivity";
    private SentBroadcastReceiver receiver;
    private IntentFilter filter;
    private Button smsButton;
    private Button mailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone = findViewById(R.id.phone);
        smsButton = findViewById(R.id.SMS);
        mailButton = findViewById(R.id.mail);
        phone.setOnClickListener(this);
        smsButton.setOnClickListener(this);
        mailButton.setOnClickListener(this);

        filter = new IntentFilter();
        filter.addAction(SMS_ACTION);
        receiver = new SentBroadcastReceiver();
        registerReceiver(receiver, filter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone:
                callPhone();
                break;
            case R.id.SMS:
                sendMessage();
                break;
            case R.id.mail:
                Intent intent = new Intent(this, MailActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNumber);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            return;
        }
        startActivity(intent);
    }

    public void sendMessage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
            return;
        }
        SmsManager sms = SmsManager.getDefault();
        Intent intent = new Intent(SMS_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        sms.sendTextMessage(phoneNumber, null, "alarm", pendingIntent, null);
    }

    public void sendMail() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions.length < 1) {
            return;
        }
        if (permissions[0] == Manifest.permission.SEND_SMS) {
            switch (requestCode) {
                case 1:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        sendMessage();
                    } else {
                        Toast.makeText(this, "you are not allowed to send a message!", Toast.LENGTH_LONG).show();
                    }
            }
        } else {
            switch (requestCode) {
                case 1:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        callPhone();
                    } else {
                        Toast.makeText(this, "you are not allowed to make a call!", Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public class SentBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive: ");
            if (intent.getAction().equals(SMS_ACTION)) {
                int result = getResultCode();
                if (result == Activity.RESULT_OK) {
                    Toast.makeText(MainActivity.this, "Send SMS successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to send SMS", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.receiver);
    }
}