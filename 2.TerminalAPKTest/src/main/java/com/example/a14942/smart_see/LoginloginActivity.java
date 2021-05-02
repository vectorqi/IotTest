package com.example.a14942.smart_see;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.Toast;

public class LoginloginActivity extends BaseActivity {

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private EditText hostEdit;

    private EditText portEdit;

    private Button login;

    private CheckBox rememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Toast.makeText(this,"请确保网络已连接", Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        hostEdit = (EditText) findViewById(R.id.host);
        portEdit = (EditText) findViewById(R.id.port);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
        login = (Button) findViewById(R.id.login);
        boolean isRemember = pref.getBoolean("remember_password",false);
        if (isRemember){
            String host = pref.getString("host","");
            String port = pref.getString("port","");
            hostEdit.setText(host);
            portEdit.setText(port);
            rememberPass.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String host = hostEdit.getText().toString();
                String port = portEdit.getText().toString();
                if (port == null || host.equals("")){
                    Toast.makeText(LoginloginActivity.this,"请输入服务器IP",Toast.LENGTH_SHORT).show();
                } else  if (host == null || port.equals("")){
                    Toast.makeText(LoginloginActivity.this,"请输入端口号",Toast.LENGTH_SHORT).show();
                } else {
                    editor = pref.edit();
                    if (rememberPass.isChecked()) {
                        editor.putBoolean("remember_password",true);
                        editor.putString("host",host);
                        editor.putString("port",port);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginloginActivity.this,MainActivity.class);
                    intent.putExtra("host", host);
                    intent.putExtra("port", port);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}

