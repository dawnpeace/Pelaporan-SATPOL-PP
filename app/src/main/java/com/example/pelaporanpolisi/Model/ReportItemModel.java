package com.example.pelaporanpolisi.Model;

public class ReportItemModel {
    private String nama;
    private String keterangan;
    private String picture_url;
    private String pelapor;
    private int status;
    private String tanggal_lapor;
    private double lat;
    private double lng;

    public ReportItemModel(String nama, String keterangan, String picture_url, String pelapor, int status, String tanggal_lapor, double lat, double lng) {
        this.nama = nama;
        this.keterangan = keterangan;
        this.picture_url = picture_url;
        this.pelapor = pelapor;
        this.status = status;
        this.tanggal_lapor = tanggal_lapor;
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getName() {
        return nama;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public String getPelapor() {
        return pelapor;
    }

    public int getStatus() {
        return status;
    }

    public String getTanggal_lapor() {
        return tanggal_lapor;
    }
}
