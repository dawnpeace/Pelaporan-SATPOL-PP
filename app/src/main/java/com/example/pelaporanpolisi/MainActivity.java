package com.example.pelaporanpolisi;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pelaporanpolisi.RetrofitInterface.Authentication;
import com.example.pelaporanpolisi.Tasks.LogoutTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    SharedPrefHelper sharedPrefHelper;
    private Fragment new_report_fragment, on_progress_fragment;
    private int WRITE_STORAGE_PERMISSION_CODE = 1;
    private int READ_STORAGE_PERMISSION_CODE = 2;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        private Fragment newReportFragment = new NewReportFragment();
        private Fragment onProgressFragment = new OnProgressFragment();

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment mFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_new:
                    mFragment = newReportFragment;
                    break;
                case R.id.navigation_on_progress:
                    mFragment = onProgressFragment;
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_layout, mFragment).commit();
            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Pelaporan SATPOL PP");
        sharedPrefHelper = SharedPrefHelper.getInstance(this);
        requestStoragePermission();
        storeTokenOnceAvailable();
        BottomNavigationView navView = findViewById(R.id.bnv_main);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_layout, new NewReportFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_logout:
                AlertDialog.Builder logoutAlert = new AlertDialog.Builder(this, R.style.AlertDialog);
                logoutAlert.setMessage("Apakah anda yakin untuk keluar ?")
                        .setCancelable(true)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create();
                logoutAlert.show();
        }
        return true;
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Akses Penyimpanan")
                    .setMessage("Izinkan Aplikasi untuk mengakses Penyimpanan ?")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "Aksi tidak diizinkan", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_CODE);
        }
    }

    private void storeTokenOnceAvailable() {
        if(sharedPrefHelper.getFirebaseToken() != null && !sharedPrefHelper.issetFCMToken() && sharedPrefHelper.isLoggedIn()){
            Retrofit retrofit = RetrofitInstance.getRetrofit(sharedPrefHelper.getInterceptor());
            Authentication authentication = retrofit.create(Authentication.class);
            Call<Void> call = authentication.storeFirebaseToken(sharedPrefHelper.getFirebaseToken());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        Log.d("FCMTOKENLOGGEDIN", "onResponse: "+sharedPrefHelper.getFirebaseToken());
                        sharedPrefHelper.setFcmTokenAvailability(true);
                    } else {
                        Log.d("FCMTOKEN", "onResponse: something wrong"+response.code());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.d("FCMTOKEN", "onFailure: server failed");
                }
            });
        }
    }

    private void logout(){
        Retrofit retrofit = RetrofitInstance.getRetrofit(sharedPrefHelper.getInterceptor());
        Authentication authentication = retrofit.create(Authentication.class);
        Call<Void> call = authentication.destroyFirebaseToken();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    new LogoutTask().execute();
                    sharedPrefHelper.logout();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    ActivityCompat.finishAffinity(MainActivity.this);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, ""+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
