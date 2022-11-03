package com.example.p7_64.ui.officer

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.p7_64.R
import com.example.p7_64.databinding.ActivityDetailOfficerBinding
import com.google.android.material.textfield.TextInputEditText

class DetailOfficerActivity : AppCompatActivity() {
    private lateinit var etNameOfficer: TextInputEditText
    private lateinit var etKodeOfficer: TextInputEditText
    private lateinit var etJbatanOfficer: TextInputEditText

    private lateinit var btnBatalOfficer: Button
    private lateinit var btnHapusOfficer: Button
    private lateinit var btnEditOfficer: Button

    private lateinit var officerViewModel: OffiverViewModel
    private lateinit var binding: ActivityDetailOfficerBinding

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOfficerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Petugas"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        etNameOfficer = findViewById(R.id.tvDetailNameOfficer)
        etKodeOfficer = findViewById(R.id.tvDetailKodeOfficer)
        etJbatanOfficer = findViewById(R.id.tvDetailJabatanOfficer)

        btnBatalOfficer = findViewById((R.id.btnBatalOfficer))
        btnHapusOfficer = findViewById((R.id.btnHapusOfficer))
        btnEditOfficer = findViewById(R.id.btnEditOfficer)

        officerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[OffiverViewModel::class.java]

        Glide.with(this)
            .load(R.drawable.officer)
            .apply(RequestOptions().override(100))
            .into(binding.picDOfficer)

        val idOfficer = intent.getStringExtra("ID_STRING")

        if (idOfficer != null) {
            getOfficerById(idOfficer)

            btnHapusOfficer.setOnClickListener {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Peringatan")
                builder.setMessage("Yakin ingin menghapus data ?")

                builder.setPositiveButton("Ya"){
                        dialogInterface, which ->
                    run {
                        deleteOfficerById(idOfficer)
                        Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                    }
                }

                builder.setNegativeButton("Tidak"){
                        dialogInterface, which ->
                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }

            officerViewModel.isDeleted.observe(this) {
                if (it) {
                    finish()
                }
            }

            btnBatalOfficer.setOnClickListener {
                finish()
            }

            btnEditOfficer.setOnClickListener {
                if (etNameOfficer.text!!.isEmpty() ||
                etKodeOfficer.text!!.isEmpty() ||
                etJbatanOfficer.text!!.isEmpty()) {
                    getOfficerById(idOfficer)
                    Toast.makeText(this, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }else {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Peringatan")
                    builder.setMessage("Yakin ingin mengedit data ?")

                    builder.setPositiveButton("Ya"){
                        dialogInterface, which ->
                        run {
                            editOfficerById(idOfficer)
                            Toast.makeText(this, "Data berhasil diedit", Toast.LENGTH_SHORT).show()
                        }
                    }

                    builder.setNegativeButton("Tidak"){
                            dialogInterface, which ->
                    }

                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }
            }

            officerViewModel.isEdited.observe(this) {
                if (it) {
                    finish()
                }
            }
        }
    }

    fun getOfficerById(idOfficer: String) {
        officerViewModel.officer.observe(this) {
            etNameOfficer.setText(it.nama)
            etKodeOfficer.setText(it.kdpetugas)
            etJbatanOfficer.setText(it.jabatan)
        }

        officerViewModel.setOfficerById(idOfficer)
    }

    fun deleteOfficerById(idOfficer: String) {
        officerViewModel.deleteOfficer(idOfficer)
    }

    fun editOfficerById(idOfficer: String) {
        officerViewModel.editOfficer(
            idOfficer,
            etNameOfficer.text.toString(),
            etKodeOfficer.text.toString(),
            etJbatanOfficer.text.toString()
        )
    }
}