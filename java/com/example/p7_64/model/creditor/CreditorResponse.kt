package com.example.p7_64.model.creditor

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreditorResponseItem(
	@field:SerializedName("telp")
	val telp: String? = null,

	@field:SerializedName("idkreditor")
	val idkreditor: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("pekerjaan")
	val pekerjaan: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
) : Parcelable
