package com.example.pelaporanpolisi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelaporanpolisi.Model.AuthenticationModel;
import com.example.pelaporanpolisi.RetrofitInterface.Authentication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    TextView tv_userid, tv_password;
    Button btn_login;
    String userid, password;
    SharedPrefHelper sharedPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPrefHelper = SharedPrefHelper.getInstance(this);
        if(sharedPrefHelper.isLoggedIn()){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        initView();

    }

    private void initView(){
        tv_userid = findViewById(R.id.tv_userid);
        tv_password = findViewById(R.id.tv_password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userid = tv_userid.getText().toString();
                password = tv_password.getText().toString();

                if(userid.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Nomor Identitas/Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                login(userid,password);
            }
        });
    }

    private void login(String username, String password){
        Retrofit retrofit = RetrofitInstance.getRetrofit();
        Authentication authentication = retrofit.create(Authentication.class);
        Call<AuthenticationModel> call = authentication.login(username,password);
        call.enqueue(new Callback<AuthenticationModel>() {
            @Override
            public void onResponse(Call<AuthenticationModel> call, Response<AuthenticationModel> response) {
                if(response.isSuccessful()){
                    sharedPrefHelper.storeToken(response.body().getAccess_token());
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Akun tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthenticationModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
