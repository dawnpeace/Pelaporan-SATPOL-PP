package com.example.pelaporanpolisi.Model;

public class ReportModel {
    private int id;
    private String jenis_pelanggaran;
    private String keterangan;
    private String pelapor;
    private String laporan_masuk;

    public ReportModel(int id, String jenis_pelanggaran, String keterangan, String pelapor, String laporan_masuk) {
        this.id = id;
        this.jenis_pelanggaran = jenis_pelanggaran;
        this.keterangan = keterangan;
        this.pelapor = pelapor;
        this.laporan_masuk = laporan_masuk;
    }

    public int getId() {
        return id;
    }

    public String getJenis_pelanggaran() {
        return jenis_pelanggaran;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getPelapor() {
        return pelapor;
    }

    public String getLaporan_masuk() {
        return laporan_masuk;
    }
}
