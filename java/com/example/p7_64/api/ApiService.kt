package com.example.p7_64.api

import com.example.p7_64.model.credit.CreditResponseItem
import com.example.p7_64.model.creditor.CreditorResponseItem
import com.example.p7_64.model.motor.MotorResponseItem
import com.example.p7_64.model.officer.OfficerResponseItem
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    @GET("tbmotor.php?operasi")
    fun getAllMotor(
        @Query("operasi") operasi: String
    ): Call<ArrayList<MotorResponseItem>>

    @GET("tbmotor.php?operasi&idmotor")
    fun getMotorById(
        @Query("operasi") operasi: String,
        @Query("idmotor") idmotor: String
    ): Call<List<MotorResponseItem>>

    @DELETE("tbmotor.php?operasi&idmotor")
    fun deleteMotor(
        @Query("operasi") operasi: String,
        @Query("idmotor") idmotor: String
    ): Call<Void>

    @PUT("tbmotor.php?operasi&idmotor&kdmotor&nama&harga")
    fun editMotor(
        @Query("operasi") operasi: String,
        @Query("idmotor") idmotor: String,
        @Query("kdmotor") kdmotor: String,
        @Query("nama") nama: String,
        @Query("harga") harga: String,
    ): Call<Void>

    @POST("tbmotor.php?operasi&kdmotor&nama&harga")
    fun addMotor(
        @Query("operasi") operasi: String,
        @Query("kdmotor") kdmotor: String,
        @Query("nama") nama: String,
        @Query("harga") harga: String,
    ): Call<Void>

    @GET("tbpetugas.php?operasi")
    fun getOfficers(
        @Query("operasi") operasi: String
    ): Call<List<OfficerResponseItem>>

    @GET("tbpetugas.php?operasi&idpetugas")
    fun getOfficerByid(
        @Query("operasi") operasi: String,
        @Query("idpetugas") idpetugas: String
    ): Call<List<OfficerResponseItem>>

    @PUT("tbpetugas.php?operasi&idpetugas&kdpetugas&nama&jabatan")
    fun putOfficer(
        @Query("operasi") operasi: String,
        @Query("idpetugas") idpetugas: String,
        @Query("nama") nama: String,
        @Query("kdpetugas") kdpetugas: String,
        @Query("jabatan") jabatan: String,
    ): Call<Void>

    @DELETE("tbpetugas.php?operasi&idpetugas")
    fun deleteOfficer(
        @Query("operasi") operasi: String,
        @Query("idpetugas") idpetugas: String
    ): Call<Void>

    @POST("tbpetugas.php?operasi&kdpetugas&nama&jabatan")
    fun postOfficer(
        @Query("operasi") operasi: String,
        @Query("kdpetugas") kdpetugas: String,
        @Query("nama") nama: String,
        @Query("jabatan") jabatan: String,
    ): Call<Void>

    @GET("tbkreditor.php?operasi")
    fun getCreditor(
        @Query("operasi") operasi: String
    ): Call<List<CreditorResponseItem>>

    @PUT("tbkreditor.php?operasi&idkreditor&pekerjaan&telp&alamat&nama")
    fun editCreditor(
        @Query("operasi") operasi: String,
        @Query("idkreditor") idkreditor: String,
        @Query("nama") nama: String,
        @Query("pekerjaan") pekerjaan: String,
        @Query("alamat") alamat: String,
        @Query("telp") telp: String,
    ): Call<Void>

    @DELETE("tbkreditor.php?operasi&idkreditor")
    fun deleteCreditor(
        @Query("operasi") operasi: String,
        @Query("idkreditor") idkreditor: String,
    ): Call<Void>

    @POST("tbkreditor.php?operasi&nama&telp&alamat&pekerjaan")
    fun postCreditor(
        @Query("operasi") operasi: String,
        @Query("nama") nama: String,
        @Query("pekerjaan") pekerjaan: String,
        @Query("alamat") alamat: String,
        @Query("telp") telp: String,
    ): Call<Void>

    @GET("tbkredit.php?operasi")
    fun getCredit(
        @Query("operasi") operasi: String
    ): Call<List<CreditResponseItem>>

    @DELETE("tbkredit.php?operasi&invoice")
    fun deleteCredit(
        @Query("operasi") operasi: String,
        @Query("invoice") invoice: String
    ): Call<Void>

    @POST("tbkredit.php?operasi&idkreditor&kdmotor&hrgtunai&dp&hrgkredit&bunga&lama&totalkredit&angsuran")
    fun postCredit(
        @Query("operasi") operasi: String,
        @Query("idkreditor") idkreditor: String,
        @Query("kdmotor") kdmotor: String,
        @Query("hrgtunai") hrgtunai: String,
        @Query("dp") dp: String,
        @Query("hrgkredit") hrgkredit: String,
        @Query("bunga") bunga: String,
        @Query("lama") lama: String,
        @Query("totalkredit") totalkredit: String,
        @Query("angsuran") angsuran: String,
    ):Call<Void>
}