package com.example.p7_64.ui.officer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.p7_64.api.ApiConfig
import com.example.p7_64.model.officer.OfficerResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OffiverViewModel: ViewModel() {
    private val _officers = MutableLiveData<List<OfficerResponseItem>>()
    val officers: LiveData<List<OfficerResponseItem>> = _officers

    private val _officer = MutableLiveData<OfficerResponseItem>()
    val officer: LiveData<OfficerResponseItem> = _officer

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted

    private val _isEdited = MutableLiveData<Boolean>()
    val isEdited: LiveData<Boolean> = _isEdited

    private val _isAdded = MutableLiveData<Boolean>()
    val isAdded: LiveData<Boolean> = _isAdded

    fun setOfficer() {
        _isLoading.postValue(true)
        ApiConfig.getApiService().getOfficers("view")
            .enqueue(object: Callback<List<OfficerResponseItem>> {
                override fun onResponse(
                    call: Call<List<OfficerResponseItem>>,
                    response: Response<List<OfficerResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        _isLoading.postValue(false)

                        val responseBody = response.body()!!

                        if (responseBody.isEmpty()) {
                            _isEmpty.postValue(true)
                            _officers.postValue(responseBody)
                        }else {
                            _isEmpty.postValue(false)
                            _officers.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<List<OfficerResponseItem>>, t: Throwable) {
                    _isLoading.postValue(true)
                }
            })
    }

    fun setOfficerById(idOfficer: String) {
        ApiConfig.getApiService().getOfficerByid("get_petugas_by_kdpetugas", idOfficer)
            .enqueue(object : Callback<List<OfficerResponseItem>> {
                override fun onResponse(
                    call: Call<List<OfficerResponseItem>>,
                    response: Response<List<OfficerResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        val dataOfficer = response.body()?.get(0)!!
                        _officer.postValue(dataOfficer)
                    }
                }

                override fun onFailure(call: Call<List<OfficerResponseItem>>, t: Throwable) {
                    _isLoading.postValue(true)
                }
            })
    }

    fun deleteOfficer(idOfficer: String) {
        ApiConfig.getApiService().deleteOfficer("delete", idOfficer)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        _isDeleted.postValue(true)
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    _isDeleted.postValue(false)
                }
            })
    }

    fun editOfficer(idOfficer: String, name: String, kd: String, jabatan: String) {
        ApiConfig.getApiService().putOfficer(
            "update",
            idOfficer,
            name,
            kd,
            jabatan
        ).enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    _isEdited.postValue(true)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _isEdited.postValue(false)
            }
        })
    }

    fun addOfficer(kd: String, name: String, jabatan: String) {
        ApiConfig.getApiService().postOfficer(
            "insert",
            kd,
            name,
            jabatan
        ).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    _isAdded.postValue(true)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _isAdded.postValue(false)
            }
        })
    }
}