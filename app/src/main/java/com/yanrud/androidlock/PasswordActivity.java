package com.yanrud.androidlock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.yanrud.androidlock.Utils.AppLockConstants;
import com.takwolf.android.lock9.Lock9View;

    public class PasswordActivity extends AppCompatActivity {
        Lock9View lock9View;
        SharedPreferences sharedPreferences;
        Context context;
        Button forgetPassword;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            context = getApplicationContext();
            setContentView(R.layout.activity_password);



            forgetPassword = (Button) findViewById(R.id.forgetPassword);
            lock9View = (Lock9View) findViewById(R.id.lock_9_view);
            sharedPreferences = getSharedPreferences(AppLockConstants.MyPREFERENCES, MODE_PRIVATE);
            lock9View.setCallBack(new Lock9View.CallBack() {
                @Override
                public void onFinish(String password) {
                    if (sharedPreferences.getString(AppLockConstants.PASSWORD, "").matches(password)) {
                        Toast.makeText(getApplicationContext(), "Berhasil : Pola Sesuai", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(PasswordActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Pola salah, Coba lagi!", Toast.LENGTH_SHORT).show();

                    }
                }
            });

            forgetPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(PasswordActivity.this, PasswordRecoveryActivity.class);
                    startActivity(i);

                }
            });
        }


    }
