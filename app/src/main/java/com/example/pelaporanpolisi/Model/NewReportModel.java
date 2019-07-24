package com.example.pelaporanpolisi.Model;

import java.util.List;

public class NewReportModel {
    private int current_page;
    private List<ReportModel> data;
    private String first_page_url;
    private int from;
    private int last_page;
    private String last_page_url;
    private String next_page_url;
    private String path;
    private int per_page;
    private String prev_page_url;
    private int to;
    private int total;

    public NewReportModel(int current_page, List<ReportModel> data, String first_page_url, int from, int last_page, String last_page_url, String next_page_url, String path, int per_page, String prev_page_url, int to, int total) {
        this.current_page = current_page;
        this.data = data;
        this.first_page_url = first_page_url;
        this.from = from;
        this.last_page = last_page;
        this.last_page_url = last_page_url;
        this.next_page_url = next_page_url;
        this.path = path;
        this.per_page = per_page;
        this.prev_page_url = prev_page_url;
        this.to = to;
        this.total = total;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public List<ReportModel> getData() {
        return data;
    }

    public String getFirst_page_url() {
        return first_page_url;
    }

    public int getFrom() {
        return from;
    }

    public int getLast_page() {
        return last_page;
    }

    public String getLast_page_url() {
        return last_page_url;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public String getPath() {
        return path;
    }

    public int getPer_page() {
        return per_page;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public int getTo() {
        return to;
    }

    public int getTotal() {
        return total;
    }
}
