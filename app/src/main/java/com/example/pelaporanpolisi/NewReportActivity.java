package com.example.pelaporanpolisi;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pelaporanpolisi.Model.ReportItemModel;
import com.example.pelaporanpolisi.RetrofitInterface.ReportInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewReportActivity extends AppCompatActivity {

    private TextView tv_title,tv_description,tv_date,tv_reporter;
    private ImageView iv_location;
    private Button btn_location, btn_take;
    private SharedPrefHelper sharedPrefHelper;
    private int report_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pelaporan SATPOL PP");
        sharedPrefHelper = SharedPrefHelper.getInstance(this);
        initView();
        Intent data = getIntent();
        report_id = data.getIntExtra("report_id",0);
        loadData(report_id);
    }

    private void initView(){
        tv_title = findViewById(R.id.tv_title);
        tv_description = findViewById(R.id.tv_description);
        tv_date = findViewById(R.id.tv_date);
        tv_reporter = findViewById(R.id.tv_reporter);
        btn_location = findViewById(R.id.btn_location);
        iv_location = findViewById(R.id.iv_location);
        btn_take = findViewById(R.id.btn_take);

        btn_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder logoutAlert = new AlertDialog.Builder(NewReportActivity.this);

                logoutAlert.setMessage("Ambil Laporan ?")
                        .setCancelable(true)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Retrofit retrofit = RetrofitInstance.getRetrofit(sharedPrefHelper.getInterceptor());
                                ReportInterface reportInterface = retrofit.create(ReportInterface.class);
                                Call<Void> call = reportInterface.takeReport(report_id);
                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.isSuccessful()){
                                            Toast.makeText(NewReportActivity.this, "Laporan berhasil diambil !", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(NewReportActivity.this, "Terjadi kesalahan err :"+response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(NewReportActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
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
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void loadData(int id){
        Retrofit retrofit = RetrofitInstance.getRetrofit(sharedPrefHelper.getInterceptor());
        ReportInterface reportInterface = retrofit.create(ReportInterface.class);
        Call<ReportItemModel> call = reportInterface.getItemReport(id);
        call.enqueue(new Callback<ReportItemModel>() {
            @Override
            public void onResponse(Call<ReportItemModel> call, Response<ReportItemModel> response) {
                if(response.isSuccessful()){
                    final ReportItemModel model = response.body();
                    tv_title.setText(model.getName());
                    tv_description.setText(model.getKeterangan());
                    tv_reporter.setText(model.getPelapor());
                    tv_date.setText(model.getTanggal_lapor());
                    Glide.with(NewReportActivity.this).load(model.getPicture_url()).into(iv_location);
                    btn_location.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(NewReportActivity.this,MapsActivity.class);
                            intent.putExtra("lat",model.getLat());
                            intent.putExtra("lng",model.getLng());
                            startActivity(intent);
                        }
                    });

                } else {
                    Toast.makeText(NewReportActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReportItemModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(report_id);
    }
}
