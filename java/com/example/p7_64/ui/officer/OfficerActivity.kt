package com.example.p7_64.ui.officer

import android.annotation.SuppressLint
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
import com.example.p7_64.adapter.officer.OfficerAdapter
import com.example.p7_64.model.officer.OfficerResponseItem

class OfficerActivity : AppCompatActivity() {
    private lateinit var rvOfficer: RecyclerView
    private lateinit var progressOfficerBar: ProgressBar
    private lateinit var tvIsEmptyOfficer: TextView
    private lateinit var offiverViewModel: OffiverViewModel

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_officer)

        supportActionBar?.title = "Data Petugas"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        offiverViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[OffiverViewModel::class.java]

        rvOfficer = findViewById(R.id.rv_officer)
        progressOfficerBar = findViewById(R.id.progressOfficeBar)
        tvIsEmptyOfficer = findViewById(R.id.tvIsEmptyOfficer)

        val layoutManager = LinearLayoutManager(this)
        rvOfficer.layoutManager = layoutManager
        rvOfficer.setHasFixedSize(true)

        setIsLoading()
        isEmpty()
        showAllOfficer()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.iconAdd -> {
                val moveToAddPage = Intent(this, TambahOfficerActivity::class.java)
                startActivity(moveToAddPage)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        setIsLoading()
        isEmpty()
        showAllOfficer()

        super.onStart()
    }

    private fun setIsLoading() {
        offiverViewModel.isLoading.observe(this) {
            if (it) {
                progressOfficerBar.visibility = View.VISIBLE
            } else {
                progressOfficerBar.visibility = View.GONE
            }
        }
    }

    private fun isEmpty() {
        offiverViewModel.isEmpty.observe(this) {
            if (it) {
                tvIsEmptyOfficer.visibility = View.VISIBLE
            } else {
                tvIsEmptyOfficer.visibility = View.GONE
            }
        }
    }

    private fun showAllOfficer() {
        offiverViewModel.officers.observe(this) {
            val adapter = OfficerAdapter(it)
            rvOfficer.adapter = adapter
            adapter.setOnItemClickCallback(object: OfficerAdapter.OnItemClickCallback {
                override fun itemClicked(officer: OfficerResponseItem) {
                    val intent = Intent(this@OfficerActivity, DetailOfficerActivity::class.java)
                    intent.putExtra("ID_STRING", officer.idpetugas)
                    startActivity(intent)
                }
            })
        }

        offiverViewModel.setOfficer()
    }
}