package com.example.p7_64.ui.creditor

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.p7_64.R
import com.example.p7_64.adapter.creditor.CreditorAdapter
import com.example.p7_64.model.creditor.CreditorResponseItem

class CreditorActivity : AppCompatActivity() {
    private lateinit var rvCreditor: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvIsEmpty: TextView

    private lateinit var creditorViewModel: CreditorViewModel

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creditor)

        supportActionBar?.title = "Data Kreditor"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvCreditor = findViewById(R.id.rv_creditor)
        progressBar = findViewById(R.id.progressCreditorBar)
        tvIsEmpty = findViewById(R.id.tvIsEmptyCreditor)

        creditorViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[CreditorViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        rvCreditor.layoutManager = layoutManager
        rvCreditor.setHasFixedSize(true)

        setIsLoading()
        isEmpty()
        showAllDataCreditor()
    }

    override fun onStart() {
        super.onStart()

        setIsLoading()
        isEmpty()
        showAllDataCreditor()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.iconAdd -> {
                val moveToAddPage = Intent(this, TambahCreditorActivity::class.java)
                startActivity(moveToAddPage)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setIsLoading() {
        creditorViewModel.isLoading.observe(this) {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun isEmpty() {
        creditorViewModel.isEmpty.observe(this) {
            if (it) {
                tvIsEmpty.visibility = View.VISIBLE
            } else {
                tvIsEmpty.visibility = View.GONE
            }
        }
    }

    private fun showAllDataCreditor() {
        creditorViewModel.creditors.observe(this) {
            val creditorAdapter = CreditorAdapter(it)
            rvCreditor.adapter = creditorAdapter

            creditorAdapter.setOnItemClickCallback(object: CreditorAdapter.OnItemClickCallback {
                override fun itemClicked(creditor: CreditorResponseItem) {
                    val moveToDetailCreditor = Intent(this@CreditorActivity, DetailCreditorActivity::class.java)
                    moveToDetailCreditor.putExtra("ID_STRING", creditor.idkreditor)
                    startActivity(moveToDetailCreditor)
                }
            })
        }

        creditorViewModel.getAllCreditor()
    }
}