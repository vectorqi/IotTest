package com.hapher.alarmdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hapher.alarmdemo.R;
import com.hapher.alarmdemo.util.SendMailUtil;
import com.hapher.alarmdemo.util.ShareUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MailActivity extends AppCompatActivity {

    private EditText sendAddEt, mailAuthCode,
            sendServer, sendPortNumber, toAddEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
    }

    public void senTextMail(View view) {

        sendAddEt = findViewById(R.id.sendAddEt);
        mailAuthCode = findViewById(R.id.mailAuthCode);
        sendServer = findViewById(R.id.sendServer);
        sendPortNumber = findViewById(R.id.sendPortNumber);
        toAddEt = findViewById(R.id.toAddEt);
        ShareUtils.putString(this, "FROM_ADD", sendAddEt.getText().toString().trim());
        ShareUtils.putString(this, "FROM_PSW", mailAuthCode.getText().toString().trim());
        ShareUtils.putString(this, "HOST", sendServer.getText().toString().trim());
        ShareUtils.putString(this, "PORT", sendPortNumber.getText().toString().trim());

        SendMailUtil.send(toAddEt.getText().toString());
        Toast.makeText(MailActivity.this, "邮件已发送", Toast.LENGTH_SHORT).show();
    }

    public void sendFileMail(View view) {

        sendAddEt = findViewById(R.id.sendAddEt);
        mailAuthCode = findViewById(R.id.mailAuthCode);
        sendServer = findViewById(R.id.sendServer);
        sendPortNumber = findViewById(R.id.sendPortNumber);
        toAddEt = findViewById(R.id.toAddEt);
        ShareUtils.putString(this, "FROM_ADD", sendAddEt.getText().toString().trim());
        ShareUtils.putString(this, "FROM_PSW", mailAuthCode.getText().toString().trim());
        ShareUtils.putString(this, "HOST", sendServer.getText().toString().trim());
        ShareUtils.putString(this, "PORT", sendPortNumber.getText().toString().trim());

        File file = new File("这里填写要添加附件的本地文件的路径地址");
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            String str = "hello world";
            byte[] data = str.getBytes();
            os.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) os.close();
            } catch (IOException e) {
            }
        }
        SendMailUtil.send(file, toAddEt.getText().toString());
        Toast.makeText(MailActivity.this, "邮件已发送", Toast.LENGTH_SHORT).show();
    }

}
