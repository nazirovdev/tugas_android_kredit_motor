package com.example.p7_64.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.p7_64.api.ApiConfig
import com.example.p7_64.model.credit.CreditResponseItem
import com.example.p7_64.model.creditor.CreditorResponseItem
import com.example.p7_64.model.motor.MotorResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private var _client = MutableLiveData<List<CreditResponseItem>>()
    var client: LiveData<List<CreditResponseItem>> = _client

    private var _isDeleted = MutableLiveData<Boolean>()
    var isDeleted: LiveData<Boolean> = _isDeleted

    private var _isAdding = MutableLiveData<Boolean>()
    var isAdding: LiveData<Boolean> = _isAdding

    private val _itemsMotor = MutableLiveData<ArrayList<String>>()
    val itemsMotor: LiveData<ArrayList<String>> = _itemsMotor

    private val _itemsCreditor = MutableLiveData<List<String>>()
    val itemsCreditor: LiveData<List<String>> = _itemsCreditor

    private val _itemMotor = MutableLiveData<MotorResponseItem>()
    val itemMotor: LiveData<MotorResponseItem> = _itemMotor

    private val _itemCredit = MutableLiveData<CreditResponseItem>()
    val itemCredit: LiveData<CreditResponseItem> = _itemCredit

    private val _isPrinted = MutableLiveData<Boolean>()
    val isPrinted: LiveData<Boolean> = _isPrinted

    fun setDataCreditClient() {
        _isLoading.postValue(true)

        ApiConfig.getApiService().getCredit("query_kredit")
            .enqueue(object: Callback<List<CreditResponseItem>> {
                override fun onResponse(
                    call: Call<List<CreditResponseItem>>,
                    response: Response<List<CreditResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        _isLoading.postValue(false)
                        val data = response.body()!!

                        if (data.isEmpty()) {
                            _client.postValue(data)
                            _isEmpty.postValue(true)
                        }else {
                            _client.postValue(data)
                            _isEmpty.postValue(false)
                        }
                    }
                }

                override fun onFailure(call: Call<List<CreditResponseItem>>, t: Throwable) {
                    _isLoading.postValue(true)
                }
            })
    }

    fun deleteCreditById(id: String) {
        ApiConfig.getApiService().deleteCredit("hapus_kredit", id)
            .enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        _isDeleted.postValue(true)
                    }else {
                        _isDeleted.postValue(false)
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    _isDeleted.postValue(false)
                }
            })
    }

    fun getItemsKodeMotor() {
        ApiConfig.getApiService().getAllMotor("view")
            .enqueue(object: Callback<ArrayList<MotorResponseItem>> {
                override fun onResponse(
                    call: Call<ArrayList<MotorResponseItem>>,
                    response: Response<ArrayList<MotorResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()!!

                        val tmp = ArrayList<String>()

                        for (d in data) {
                            tmp.add(d.idmotor.toString())
                        }

                        _itemsMotor.postValue(tmp)
                    }
                }

                override fun onFailure(call: Call<ArrayList<MotorResponseItem>>, t: Throwable) {
                    _isLoading.postValue(true)
                }
            })
    }

    fun getItemsIdCreditor() {
        ApiConfig.getApiService().getCreditor("view")
            .enqueue(object: Callback<List<CreditorResponseItem>> {
                override fun onResponse(
                    call: Call<List<CreditorResponseItem>>,
                    response: Response<List<CreditorResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()!!
                        val tmp = ArrayList<String>()

                        for (d in data) {
                            tmp.add(d.idkreditor.toString())
                        }

                        _itemsCreditor.postValue(tmp)
                    }
                }

                override fun onFailure(call: Call<List<CreditorResponseItem>>, t: Throwable) {
                    _isLoading.postValue(true)
                }

            })
    }

    fun getMotor(idMotor: String) {
        ApiConfig.getApiService().getMotorById("get_motor_by_kdmotor", idMotor)
            .enqueue(object: Callback<List<MotorResponseItem>> {
                override fun onResponse(
                    call: Call<List<MotorResponseItem>>,
                    response: Response<List<MotorResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()?.get(0)!!
                        _itemMotor.postValue(data)
                    }
                }

                override fun onFailure(call: Call<List<MotorResponseItem>>, t: Throwable) {
                    _isLoading.postValue(true)
                }

            })
    }

    fun addCredit(
        idKreditor: String,
        kdmotor: String,
        hrgtunai: String,
        dp: String,
        hrgkredit: String,
        bunga: String,
        lama: String,
        totalkredit: String,
        angsuran: String,
    ) {
        ApiConfig.getApiService().postCredit(
            "simpan_kredit",
            idKreditor,
            kdmotor,
            hrgtunai,
            dp,
            hrgkredit,
            bunga,
            lama,
            totalkredit,
            angsuran,
        ).enqueue(object: Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                if (response.isSuccessful) {
                    _isAdding.postValue(true)
                }else {
                    _isAdding.postValue(false)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _isAdding.postValue(false)
            }
        })
    }

    fun btnPrintClicked() {
        _isPrinted.postValue(true)
    }

    fun getCreditClientByInvoice(invoice: String) {
        ApiConfig.getApiService().getCredit("query_kredit")
            .enqueue(object: Callback<List<CreditResponseItem>> {
                override fun onResponse(
                    call: Call<List<CreditResponseItem>>,
                    response: Response<List<CreditResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()!!
                        for (d in data) {
                            if (d.invoice == invoice) {
                                val item = data.get(0)
                                _itemCredit.postValue(item)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<CreditResponseItem>>, t: Throwable) {
                    _isLoading.postValue(true)
                }
            })
    }
}