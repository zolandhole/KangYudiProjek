package com.example.androidlock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.androidlock.Utils.AppLockConstants;

import java.util.ArrayList;
import java.util.List;

public class PasswordRecoveryActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    Spinner questionsSpinner;
    EditText answer;
    Button confirmButton;
    int questionNumber = 0;

    private TextInputLayout inputLayoutName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_recovery_password);
        //Google Analytics


        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        confirmButton = (Button) findViewById(R.id.confirmButton);
        questionsSpinner = (Spinner) findViewById(R.id.questionsSpinner);
        answer = (EditText) findViewById(R.id.answer);

        sharedPreferences = getSharedPreferences(AppLockConstants.MyPREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();


        List<String> list = new ArrayList<String>();
        list.add("Pilih pertanyaan keamanan Anda?");
        list.add("Siapa nama binatang peliharaanmu?");
        list.add("Siapa guru Favoritmu?");
        list.add("Siapa aktor favoritmu?");
        list.add("Siapa aktris favoritmu?");
        list.add("Apakah Makanan Kesukaanmu?");
        list.add("Siapa pesepakbola favoritmu?");

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionsSpinner.setAdapter(stringArrayAdapter);

        questionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                questionNumber = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionNumber != 0 && !answer.getText().toString().isEmpty()) {
                    if (sharedPreferences.getInt(AppLockConstants.QUESTION_NUMBER, 0) == questionNumber && sharedPreferences.getString(AppLockConstants.ANSWER, "").matches(answer.getText().toString())) {
                        editor.putBoolean(AppLockConstants.IS_PASSWORD_SET, false);
                        editor.commit();
                        editor.putString(AppLockConstants.PASSWORD, "");
                        editor.commit();
                        Intent i = new Intent(PasswordRecoveryActivity.this, PasswordSetActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Pertanyaan dan jawaban anda tidak sesuai", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Pilih pertanyaan dan isi jawaban", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}
