package com.example.p7_64.model.officer

import com.google.gson.annotations.SerializedName

data class OfficerResponse(

	@field:SerializedName("OfficerResponse")
	val officerResponse: List<OfficerResponseItem?>? = null
)

data class OfficerResponseItem(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("kdpetugas")
	val kdpetugas: String? = null,

	@field:SerializedName("jabatan")
	val jabatan: String? = null,

	@field:SerializedName("idpetugas")
	val idpetugas: String? = null
)
