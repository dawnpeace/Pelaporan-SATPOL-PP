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
import com.example.pelaporanpolisi.Adapter.OnProgressAdapter;
import com.example.pelaporanpolisi.Model.NewReportModel;
import com.example.pelaporanpolisi.Model.ReportModel;
import com.example.pelaporanpolisi.RetrofitInterface.ReportInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OnProgressFragment extends Fragment {

    private RecyclerView rv_on_progress;
    SharedPrefHelper sharedPrefHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.on_progress_fragment,container,false);
        rv_on_progress = view.findViewById(R.id.rv_on_progress);
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
        Call<List<ReportModel>> call = report.getOnProgressReport();
        call.enqueue(new Callback<List<ReportModel>>() {
            @Override
            public void onResponse(Call<List<ReportModel>> call, Response<List<ReportModel>> response) {
                if(response.isSuccessful()){
                    rv_on_progress.setLayoutManager(new LinearLayoutManager(getContext()));
                    OnProgressAdapter adapter = new OnProgressAdapter(getContext(),response.body());
                    rv_on_progress.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), ""+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ReportModel>> call, Throwable t) {
                Toast.makeText(getContext(), ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
