package com.example.p7_64.model.motor

import com.google.gson.annotations.SerializedName

data class MotorResponse(

	@field:SerializedName("MotorResponse")
	val motorResponse: ArrayList<MotorResponseItem?>? = null
)

data class MotorResponseItem(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("harga")
	val harga: String? = null,

	@field:SerializedName("idmotor")
	val idmotor: String? = null,

	@field:SerializedName("kdmotor")
	val kdmotor: String? = null
)
