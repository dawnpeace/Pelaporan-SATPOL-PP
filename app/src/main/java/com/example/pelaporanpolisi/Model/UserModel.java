package com.example.pelaporanpolisi.Model;

public class UserModel {
    private int id;
    private String nama;
    private String email;
    private String nomor_identitas;
    private int verified;
    private String jenis;
    private String created_at;
    private String updated_at;

    public UserModel(int id, String nama, String email, String nomor_identitas, int verified, String jenis, String created_at, String updated_at) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.nomor_identitas = nomor_identitas;
        this.verified = verified;
        this.jenis = jenis;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getNomor_identitas() {
        return nomor_identitas;
    }

    public int getVerified() {
        return verified;
    }

    public String getJenis() {
        return jenis;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}