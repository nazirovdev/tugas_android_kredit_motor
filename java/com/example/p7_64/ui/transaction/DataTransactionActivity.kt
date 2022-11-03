package com.example.p7_64.ui.transaction

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.p7_64.R
import com.example.p7_64.adapter.credit.CreditAdapter
import com.example.p7_64.model.credit.CreditResponseItem

class DataTransactionActivity : AppCompatActivity() {
    private lateinit var rvClient: RecyclerView
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var progressOfficerBar: ProgressBar
    private lateinit var tvIsEmptyOfficer: TextView

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_transaction)

        supportActionBar?.title = "Data Kredit"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        checkPermission()
        requestPermission()

        rvClient = findViewById(R.id.rvClient)
        progressOfficerBar = findViewById(R.id.progressBar)
        tvIsEmptyOfficer = findViewById(R.id.tvIsEmpty)

        transactionViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[TransactionViewModel::class.java]

        val linearManager = LinearLayoutManager(this)
        rvClient.layoutManager = linearManager
        rvClient.setHasFixedSize(true)

        setIsLoading()
        isEmpty()
        showDataCreditClient()
    }

    private fun setIsLoading() {
        transactionViewModel.isLoading.observe(this) {
            if (it) {
                progressOfficerBar.visibility = View.VISIBLE
            } else {
                progressOfficerBar.visibility = View.GONE
            }
        }
    }

    private fun isEmpty() {
        transactionViewModel.isEmpty.observe(this) {
            if (it) {
                tvIsEmptyOfficer.visibility = View.VISIBLE
            } else {
                tvIsEmptyOfficer.visibility = View.GONE
            }
        }
    }

    override fun onStart() {
        setIsLoading()
        isEmpty()
        showDataCreditClient()

        super.onStart()
    }

    private fun showDataCreditClient() {
        transactionViewModel.setDataCreditClient()

        transactionViewModel.client.observe(this@DataTransactionActivity) {
            val adapter = CreditAdapter(it)
            rvClient.adapter = adapter

            adapter.setOnItemClicker(object: CreditAdapter.OnItemClicked {
                override fun itemDetail(client: CreditResponseItem) {
                    val intent = Intent(this@DataTransactionActivity, DetailTransactionActivity::class.java)
                    intent.putExtra("DATA_INVOICE", client.invoice)
                    startActivity(intent)
                }
            })
        }
    }

    private fun checkPermission(): Boolean {
        val permission_first = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permission_second = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE)

        return permission_first == PackageManager.PERMISSION_GRANTED && permission_second == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
            200
        )
    }
}