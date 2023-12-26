
package com.abdellatif.allinonevideodownloader;
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

public class RegisterActivity extends AppCompatActivity {

    EditText loginInput, email, phone, password;
    Button register;
    TextView login;
    boolean isNameValid, isEmailValid, isPhoneValid, isPasswordValid;
    TextInputLayout loginInputError, emailError, phoneError, passError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginInput = (EditText) findViewById(R.id.loginInput);
        email = (EditText) findViewById(R.id.email);

        password = (EditText) findViewById(R.id.password);
        login = (TextView) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        loginInputError = (TextInputLayout) findViewById(R.id.loginInputError);
        emailError = (TextInputLayout) findViewById(R.id.emailError);

        passError = (TextInputLayout) findViewById(R.id.passError);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void SetValidation() {


        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            emailError.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            emailError.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else  {
            isEmailValid = true;
            emailError.setErrorEnabled(false);
        }



        // Check for a valid password.
        if (password.getText().toString().isEmpty()) {
            passError.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (password.getText().length() < 6) {
            passError.setError(getResources().getString(R.string.error_invalid_password));
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
        }

        if (isEmailValid && isPasswordValid) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://api-downloader-video.somee.com/users/register?Login="
                            +loginInput.getText().toString()
                            +"&Password="
                            +password.getText().toString()
                            +"&Email="
                            +email.getText().toString())
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
                                                            Toast.makeText(getApplicationContext(), "Регистрация успешно пройдена", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Неверные учётные данные", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }catch (Exception e){

                                                    }
                                                }
                                            }
            );
            Toast.makeText(getApplicationContext(), "Регистрация успешно пройдена", Toast.LENGTH_SHORT).show();
        }

    }

}

