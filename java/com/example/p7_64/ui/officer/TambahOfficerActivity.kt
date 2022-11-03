package com.example.p7_64.ui.officer

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.p7_64.R
import com.example.p7_64.databinding.ActivityTambahOfficerBinding
import com.google.android.material.textfield.TextInputEditText

class TambahOfficerActivity : AppCompatActivity() {
    private lateinit var btnSimpanOfficer: Button
    private lateinit var btnBatalOfficer: Button

    private lateinit var tvAddNameOfficer: TextInputEditText
    private lateinit var tvAddKodeOfficer: TextInputEditText
    private lateinit var tvAddJabatanOfficer: TextInputEditText

    private lateinit var officerViewModel: OffiverViewModel
    private lateinit var binding: ActivityTambahOfficerBinding

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahOfficerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Tambah Petugas"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvAddNameOfficer = findViewById(R.id.tvAddNameOfficer)
        tvAddKodeOfficer = findViewById(R.id.tvAddKodeOfficer)
        tvAddJabatanOfficer = findViewById(R.id.tvAddJabatanOfficer)

        btnSimpanOfficer = findViewById(R.id.btnAddSimpanOfficer)
        btnBatalOfficer = findViewById(R.id.btnAddBatalOfficer)

        officerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[OffiverViewModel::class.java]

        Glide.with(this)
            .load(R.drawable.officer)
            .apply(RequestOptions().override(100))
            .into(binding.picTOfficer)

        btnSimpanOfficer.setOnClickListener {
            if (tvAddKodeOfficer.text!!.isEmpty() ||
            tvAddNameOfficer.text!!.isEmpty() ||
            tvAddJabatanOfficer.text!!.isEmpty()) {
                Toast.makeText(this, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                addDataofficer()
                Toast.makeText(this, "Data behasil ditambah", Toast.LENGTH_SHORT).show()
            }
        }

        officerViewModel.isAdded.observe(this) {
            if (it) {
                finish()
            }
        }

        btnBatalOfficer.setOnClickListener {
            finish()
        }
    }

    private fun addDataofficer() {
        officerViewModel.addOfficer(
            tvAddKodeOfficer.text.toString(),
            tvAddNameOfficer.text.toString(),
            tvAddJabatanOfficer.text.toString())
    }
}