package com.example.pelaporanpolisi.RetrofitInterface;

import com.example.pelaporanpolisi.Model.NewReportModel;
import com.example.pelaporanpolisi.Model.ReportItemModel;
import com.example.pelaporanpolisi.Model.ReportModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReportInterface {
    @GET("polisi/laporan")
    Call<NewReportModel> getNewReport();

    @GET("polisi/laporan?status=ditangani")
    Call<List<ReportModel>> getOnProgressReport();

    @GET("polisi/laporan/{id}/lihat")
    Call<ReportItemModel> getItemReport(@Path("id") int report_id);

    @PUT("polisi/laporan/{id}/ambil")
    Call<Void> takeReport(@Path("id") int report_id);

    @PUT("polisi/laporan/{id}/selesai")
    Call<Void> finishReport(@Path("id") int report_id);
}
