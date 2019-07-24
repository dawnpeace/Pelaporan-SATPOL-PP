package com.example.pelaporanpolisi.Model;

public class ProfileModel {
    private int id;
    private String nama;
    private String alamat;
    private String profile_url;
    private String nomor_identitas;
    private String email;

    public ProfileModel(int id, String nama, String alamat, String profile_url, String nomor_identitas, String email) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.profile_url = profile_url;
        this.nomor_identitas = nomor_identitas;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public String getNomor_identitas() {
        return nomor_identitas;
    }

    public String getEmail() {
        return email;
    }
}
