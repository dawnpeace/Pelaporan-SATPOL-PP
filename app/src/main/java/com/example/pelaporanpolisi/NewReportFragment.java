package com.example.pelaporanpolisi;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pelaporanpolisi.Adapter.NewReportAdapter;
import com.example.pelaporanpolisi.Model.NewReportModel;
import com.example.pelaporanpolisi.Model.ReportModel;
import com.example.pelaporanpolisi.RetrofitInterface.ReportInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewReportFragment extends Fragment {

    RecyclerView rv_new_report;
    SharedPrefHelper sharedPrefHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_report_fragment,container,false);
        rv_new_report = view.findViewById(R.id.rv_new_report);
        loadData();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPrefHelper = SharedPrefHelper.getInstance(context);
    }

    private void loadData(){
        Retrofit retrofit = RetrofitInstance.getRetrofit(sharedPrefHelper.getInterceptor());
        ReportInterface report = retrofit.create(ReportInterface.class);
        Call<NewReportModel> call = report.getNewReport();
        call.enqueue(new Callback<NewReportModel>() {
            @Override
            public void onResponse(Call<NewReportModel> call, Response<NewReportModel> response) {
                if(response.isSuccessful()){
                    rv_new_report.setLayoutManager(new LinearLayoutManager(getContext()));
                    NewReportAdapter adapter = new NewReportAdapter(getContext(),response.body().getData());
                    rv_new_report.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewReportModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
