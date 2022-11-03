package com.example.p7_64.ui.creditor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.p7_64.api.ApiConfig
import com.example.p7_64.model.creditor.CreditorResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreditorViewModel: ViewModel() {
    private val _creditors = MutableLiveData<List<CreditorResponseItem>>()
    val creditors: LiveData<List<CreditorResponseItem>> = _creditors

    private val _creditor = MutableLiveData<CreditorResponseItem>()
    val creditor: LiveData<CreditorResponseItem> = _creditor

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _isEdited = MutableLiveData<Boolean>()
    val isEdited: LiveData<Boolean> = _isEdited

    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted

    private val _isAdded = MutableLiveData<Boolean>()
    val isAdded: LiveData<Boolean> = _isAdded

    fun getAllCreditor() {
        _isLoading.postValue(true)

        ApiConfig.getApiService().getCreditor("view")
            .enqueue(object : Callback<List<CreditorResponseItem>> {
                override fun onResponse(
                    call: Call<List<CreditorResponseItem>>,
                    response: Response<List<CreditorResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        _isLoading.postValue(false)

                        val responseBody = response.body()!!

                        if (responseBody.isEmpty()) {
                            _isEmpty.postValue(true)
                            _creditors.postValue(responseBody)
                        }else {
                            _isEmpty.postValue(false)
                            _creditors.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<List<CreditorResponseItem>>, t: Throwable) {
                    _isLoading.postValue(true)
                }

            })
    }

    fun getCreditorById(id: String) {
        ApiConfig.getApiService().getCreditor("view")
            .enqueue(object : Callback<List<CreditorResponseItem>> {
                override fun onResponse(
                    call: Call<List<CreditorResponseItem>>,
                    response: Response<List<CreditorResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()!!
                        val idKreditor = id

                        for (creditor in responseBody) {
                            if (creditor.idkreditor == idKreditor) {
                                _creditor.postValue(creditor)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<CreditorResponseItem>>, t: Throwable) {
                    _isLoading.postValue(true)
                }
            })
    }

    fun putCreditor(idkreditor: String, nama: String, pekerjaan: String, alamat: String, telp: String) {
        ApiConfig.getApiService().editCreditor(
            "update",
            idkreditor,
            nama,
            pekerjaan,
            alamat,
            telp,
        ).enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    _isEdited.postValue(true)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _isLoading.postValue(true)
            }
        })
    }

    fun deleteCreditorById(idkreditor: String) {
        ApiConfig.getApiService().deleteCreditor("delete", idkreditor)
            .enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        _isDeleted.postValue(true)
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    _isLoading.postValue(true)
                }

            })
    }

    fun addCreditor(name: String, pekerjaan: String, alamat: String, telp: String) {
        ApiConfig.getApiService().postCreditor(
            "insert",
            name,
            pekerjaan,
            alamat,
            telp,
        ).enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    _isAdded.postValue(true)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _isLoading.postValue(true)
            }
        })
    }
}