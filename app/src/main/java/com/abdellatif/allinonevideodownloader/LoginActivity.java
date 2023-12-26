package com.abdellatif.allinonevideodownloader;
import android.app.Activity;
import android.content.Intent;
import android.util.Patterns;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {

    EditText loginInput, password;
    Button login;
    TextView register;
    boolean isEmailValid, isPasswordValid;
    TextInputLayout emailError, passError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginInput = (EditText) findViewById(R.id.loginInput);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        emailError = (TextInputLayout) findViewById(R.id.emailError);
        passError = (TextInputLayout) findViewById(R.id.passError);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void SetValidation() {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://api-downloader-video.somee.com/users/log?Login="
                +loginInput.getText().toString()
                +"&Password="
                +password.getText().toString())
                .build();

       client.newCall(request).enqueue(new Callback() {
           @Override
           public void onFailure(@NonNull Call call, @NonNull IOException e) {
               Toast.makeText(getApplicationContext(), "Ошибка при подключении к сети", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
              // Toast.makeText(getApplicationContext(), "Авторизация пройдена", Toast.LENGTH_SHORT).show();
               try {


                   if (response.isSuccessful()) {
                       String resp = response.body().toString();
                       Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                       startActivity(intent);
                   } else {
                       Toast.makeText(getApplicationContext(), "Неверные учётные данные", Toast.LENGTH_SHORT).show();
                   }
               }catch (Exception e){

               }
           }
       }
           );
//           }
//       } {
//            if (!response.isSuccessful()) {
//                Toast.makeText(getApplicationContext(), "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
//            }
//            Toast.makeText(getApplicationContext(), "Авторизация пройдена", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);






    }



}