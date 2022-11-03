package com.example.p7_64.model.credit

import com.google.gson.annotations.SerializedName

data class CreditResponse(

	@field:SerializedName("CreditResponse")
	val creditResponse: List<CreditResponseItem?>? = null
)

data class CreditResponseItem(

	@field:SerializedName("idkreditor")
	val idkreditor: String? = null,

	@field:SerializedName("hrgtunai")
	val hrgtunai: String? = null,

	@field:SerializedName("dp")
	val dp: String? = null,

	@field:SerializedName("angsuran")
	val angsuran: String? = null,

	@field:SerializedName("nmotor")
	val nmotor: String? = null,

	@field:SerializedName("bunga")
	val bunga: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("kdmotor")
	val kdmotor: String? = null,

	@field:SerializedName("hrgkredit")
	val hrgkredit: String? = null,

	@field:SerializedName("totalkredit")
	val totalkredit: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("invoice")
	val invoice: String? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("lama")
	val lama: String? = null
)
