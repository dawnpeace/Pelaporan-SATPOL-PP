package com.example.pelaporanpolisi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pelaporanpolisi.Model.ReportModel;
import com.example.pelaporanpolisi.Model.ReportModel;
import com.example.pelaporanpolisi.NewReportActivity;
import com.example.pelaporanpolisi.R;

import java.util.List;

public class NewReportAdapter extends RecyclerView.Adapter<NewReportAdapter.MyViewHolder> {

    private Context context;
    private List<ReportModel> model;

    public NewReportAdapter(Context context, List<ReportModel> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_report_item,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_title.setText(model.get(i).getJenis_pelanggaran());
        myViewHolder.tv_description.setText(model.get(i).getKeterangan());
        myViewHolder.tv_date.setText(model.get(i).getLaporan_masuk());
        myViewHolder.cv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewReportActivity.class);
                intent.putExtra("report_id",model.get(myViewHolder.getAdapterPosition()).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return model == null ? 0 : model.size();
    }

    protected static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title, tv_description, tv_date;
        CardView cv_report;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_date = itemView.findViewById(R.id.tv_date);
            cv_report = itemView.findViewById(R.id.cv_report);
        }
    }

}
