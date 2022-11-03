package com.example.p7_64.ui.creditor

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.p7_64.R
import com.example.p7_64.databinding.ActivityDetailCreditorBinding
import com.google.android.material.textfield.TextInputEditText

class DetailCreditorActivity : AppCompatActivity() {
    private lateinit var etNameCreditor: TextInputEditText
    private lateinit var etPekerjaanCreditor: TextInputEditText
    private lateinit var etTelpCreditor: TextInputEditText
    private lateinit var etAlamatCreditor: TextInputEditText

    private lateinit var btnBatalCreditor: Button
    private lateinit var btnHapusCreditor: Button
    private lateinit var btnEditCreditor: Button

    private lateinit var creditorViewModel: CreditorViewModel
    private lateinit var binding: ActivityDetailCreditorBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("MissingInflatedId")

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCreditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Kreditor"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        creditorViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[CreditorViewModel::class.java]

        etNameCreditor = findViewById(R.id.tvDetailNameCreditor)
        etPekerjaanCreditor = findViewById(R.id.tvDetailPekerjaanCreditor)
        etTelpCreditor = findViewById(R.id.tvDetailTelpCreditor)
        etAlamatCreditor = findViewById(R.id.tvDetailAlamatCreditor)

        btnBatalCreditor = findViewById(R.id.btnBatalCreditor)
        btnHapusCreditor = findViewById(R.id.btnHapusCreditor)
        btnEditCreditor = findViewById(R.id.btnEditCreditor)

        Glide.with(this)
            .load(R.drawable.creditor)
            .apply(RequestOptions().override(100))
            .into(binding.picDCreditor)

        val idKreditor = intent.getStringExtra("ID_STRING")

        if (idKreditor != null) {
            getDataCreditor(idKreditor)
        }
    }

    private fun getDataCreditor(idKreditor: String) {
        creditorViewModel.creditor.observe(this) {data ->
            etNameCreditor.setText(data.nama.toString())
            etPekerjaanCreditor.setText(data.pekerjaan.toString())
            etTelpCreditor.setText(data.telp.toString())
            etAlamatCreditor.setText(data.alamat.toString())

            btnEditCreditor.setOnClickListener {
                if (etNameCreditor.text!!.isEmpty() ||
                    etPekerjaanCreditor.text!!.isEmpty() ||
                    etTelpCreditor.text!!.isEmpty() ||
                    etAlamatCreditor.text!!.isEmpty()) {

                    etNameCreditor.setText(data.nama.toString())
                    etPekerjaanCreditor.setText(data.pekerjaan.toString())
                    etAlamatCreditor.setText(data.telp.toString())
                    etTelpCreditor.setText(data.alamat.toString())

                    Toast.makeText(this@DetailCreditorActivity, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }else {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Peringatan")
                    builder.setMessage("Yakin ingin mengedit data ?")

                    builder.setPositiveButton("Ya"){
                            dialogInterface, which ->
                        run {
                            editCreditor(
                                idKreditor,
                                etNameCreditor.text.toString(),
                                etPekerjaanCreditor.text.toString(),
                                etAlamatCreditor.text.toString(),
                                etTelpCreditor.text.toString()
                            )

                            Toast.makeText(this@DetailCreditorActivity, "Data berhasil diedit", Toast.LENGTH_SHORT).show()
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

            creditorViewModel.isEdited.observe(this) {
                if (it) {
                    finish()
                }
            }

            btnHapusCreditor.setOnClickListener {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Peringatan")
                builder.setMessage("Yakin ingin menghapus data ?")

                builder.setPositiveButton("Ya"){
                        dialogInterface, which ->
                    run {
                        deleteCreditor(idKreditor)
                        Toast.makeText(this@DetailCreditorActivity, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                    }
                }

                builder.setNegativeButton("Tidak"){
                        dialogInterface, which ->
                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }

            creditorViewModel.isDeleted.observe(this) {
                if (it) {
                    finish()
                }
            }

            btnBatalCreditor.setOnClickListener {
                finish()
            }
        }

        creditorViewModel.getCreditorById(idKreditor)
    }

    private fun editCreditor(idkreditor: String, nama: String, pekerjaan: String, alamat: String, telp: String) {
        creditorViewModel.putCreditor(
            idkreditor,
            nama,
            pekerjaan,
            alamat,
            telp,
        )
    }

    private fun deleteCreditor(idkreditor: String) {
        creditorViewModel.deleteCreditorById(idkreditor)
    }
}