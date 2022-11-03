package com.example.p7_64.ui.creditor

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.p7_64.R
import com.example.p7_64.databinding.ActivityTambahCreditorBinding
import com.google.android.material.textfield.TextInputEditText

class TambahCreditorActivity : AppCompatActivity() {
    private lateinit var btnSimpanCreditor: Button
    private lateinit var btnBatalCreditor: Button

    private lateinit var tvAddNameCreditor: TextInputEditText
    private lateinit var tvAddPekerjaanCreditor: TextInputEditText
    private lateinit var tvAddAlamatCreditor: TextInputEditText
    private lateinit var tvAddTelpCreditor: TextInputEditText

    private lateinit var creditorViewModel: CreditorViewModel
    private lateinit var binding: ActivityTambahCreditorBinding

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahCreditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Tambah Kreditor"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvAddNameCreditor = findViewById(R.id.tvAddNameCreditor)
        tvAddPekerjaanCreditor = findViewById(R.id.tvAddPekerjaanCreditor)
        tvAddAlamatCreditor = findViewById(R.id.tvAddAlamatCreditor)
        tvAddTelpCreditor = findViewById(R.id.tvAddTelpCreditor)

        btnSimpanCreditor = findViewById(R.id.btnAddSimpanCreditor)
        btnBatalCreditor = findViewById(R.id.btnAddBatalCreditor)

        creditorViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[CreditorViewModel::class.java]

        Glide.with(this)
            .load(R.drawable.creditor)
            .apply(RequestOptions().override(100))
            .into(binding.picTCreditor)

        btnSimpanCreditor.setOnClickListener {
            if (tvAddNameCreditor.text!!.isEmpty() ||
                tvAddPekerjaanCreditor.text!!.isEmpty() ||
                tvAddAlamatCreditor.text!!.isEmpty() ||
                tvAddTelpCreditor.text!!.isEmpty()) {
                Toast.makeText(this@TambahCreditorActivity, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else {
                addDataCreditor(
                    tvAddNameCreditor.text.toString(),
                    tvAddPekerjaanCreditor.text.toString(),
                    tvAddAlamatCreditor.text.toString(),
                    tvAddTelpCreditor.text.toString()
                )

                Toast.makeText(this@TambahCreditorActivity, "Data berhasil ditambah", Toast.LENGTH_SHORT).show()
            }
        }

        creditorViewModel.isAdded.observe(this) {
            if (it) {
                finish()
            }
        }

        btnBatalCreditor.setOnClickListener {
            finish()
        }
    }

    fun addDataCreditor(name: String, pekerjaan: String, alamat: String, telp: String) {
        if (name.isEmpty()) {
            tvAddNameCreditor.error = "Nama harus diisi"
        }else if (pekerjaan.isEmpty()) {
            tvAddPekerjaanCreditor.error = "Pekrjaan harus diisi"
        }else if (alamat.isEmpty()) {
            tvAddAlamatCreditor.error = "Alamat harus diisi"
        }else if (telp.isEmpty()) {
            tvAddTelpCreditor.error = "Telp harus diisi"
        }else {
            creditorViewModel.addCreditor(
                name,
                pekerjaan,
                alamat,
                telp,
            )
        }
    }
}