package com.example.p7_64.ui.motor

import android.annotation.SuppressLint
import android.net.NetworkRequest
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.p7_64.R
import com.example.p7_64.databinding.ActivityTambahMotorBinding
import com.google.android.material.textfield.TextInputEditText

class TambahMotorActivity : AppCompatActivity() {
    private lateinit var btnSimpan: Button
    private lateinit var btnBatal: Button

    private lateinit var tvAddName: TextInputEditText
    private lateinit var tvAddKode: TextInputEditText
    private lateinit var tvAddHarga: TextInputEditText

    private lateinit var motorViewModel: MotorViewModel
    private lateinit var binding: ActivityTambahMotorBinding

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahMotorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Tambah Motor"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvAddKode = findViewById(R.id.tvAddKode)
        tvAddName = findViewById(R.id.tvAddName)
        tvAddHarga = findViewById(R.id.tvAddHarga)

        btnSimpan = findViewById(R.id.btnAddSimpan)
        btnBatal = findViewById(R.id.btnAddBatal)

        motorViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MotorViewModel::class.java]

        Glide.with(this)
            .load(R.drawable.motorbike)
            .apply(RequestOptions().override(100))
            .into(binding.picTMotor)

        btnSimpan.setOnClickListener {
            if (tvAddKode.text!!.isEmpty() ||
                tvAddName.text!!.isEmpty() ||
                tvAddHarga.text!!.isEmpty()) {
                Toast.makeText(this, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else {
                addDataMotor()
                Toast.makeText(this, "Data berhasil ditambah", Toast.LENGTH_SHORT).show()
            }
        }

        motorViewModel.isAdded.observe(this) {
            if (it) {
                finish()
            }
        }

        btnBatal.setOnClickListener {
            finish()
        }
    }

    private fun addDataMotor() {
        motorViewModel.addMotor(
            tvAddKode.text.toString(),
            tvAddName.text.toString(),
            tvAddHarga.text.toString()
        )
    }
}