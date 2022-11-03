package com.example.p7_64.ui.motor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.p7_64.api.ApiConfig
import com.example.p7_64.model.motor.MotorResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MotorViewModel: ViewModel() {

    private val _motors = MutableLiveData<ArrayList<MotorResponseItem>>()
    val motors: LiveData<ArrayList<MotorResponseItem>> = _motors

    private val _motor = MutableLiveData<MotorResponseItem>()
    val motor: LiveData<MotorResponseItem> = _motor

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted

    private val _isEdited = MutableLiveData<Boolean>()
    val isEdited: LiveData<Boolean> = _isEdited

    private val _isAdded = MutableLiveData<Boolean>()
    val isAdded: LiveData<Boolean> = _isAdded

    fun getAllMotor() {
        _isLoading.postValue(true)

        ApiConfig.getApiService().getAllMotor("view")
            .enqueue(object : Callback<ArrayList<MotorResponseItem>> {
                override fun onResponse(
                    call: Call<ArrayList<MotorResponseItem>>,
                    response: Response<ArrayList<MotorResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        _isLoading.postValue(false)

                        val responseBody = response.body()!!

                        if (responseBody.isEmpty()) {
                            _isEmpty.postValue(true)
                            _motors.postValue(responseBody)
                        }else {
                            _isEmpty.postValue(false)
                            _motors.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<MotorResponseItem>>, t: Throwable) {
                    _isLoading.postValue(true)
                }

            })
    }

    fun getMotorById(idMotor: String) {
        ApiConfig.getApiService().getMotorById("get_motor_by_kdmotor", idMotor)
            .enqueue(object : Callback<List<MotorResponseItem>> {
                override fun onResponse(
                    call: Call<List<MotorResponseItem>>,
                    response: Response<List<MotorResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        val dataMotor = response.body()?.get(0)!!
                        _motor.postValue(dataMotor)
                    }
                }

                override fun onFailure(call: Call<List<MotorResponseItem>>, t: Throwable) {
                    _isLoading.postValue(true)
                }

            })
    }

    fun deleteMotorById(idMotor: String) {
        ApiConfig.getApiService().deleteMotor("delete", idMotor)
            .enqueue(object : Callback<Void> {
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

    fun editMotorById(idMotor: String, kode: String, name: String, harga: String) {
        ApiConfig.getApiService().editMotor(
            "update",
            idMotor,
            kode,
            name,
            harga
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

    fun addMotor(kode: String, name: String, harga: String) {
        ApiConfig.getApiService().addMotor(
            "insert",
            kode,
            name,
            harga
        ).enqueue(object : Callback<Void> {
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