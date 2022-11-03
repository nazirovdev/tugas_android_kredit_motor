package com.example.p7_64.ui.motor

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.p7_64.R
import com.example.p7_64.databinding.ActivityDetailMotorBinding
import com.google.android.material.textfield.TextInputEditText

class DetailMotorActivity : AppCompatActivity() {
    private lateinit var etName: TextInputEditText
    private lateinit var etKode: TextInputEditText
    private lateinit var etharga: TextInputEditText

    private lateinit var btnBatal: Button
    private lateinit var btnHapus: Button
    private lateinit var btnEdit: Button

    private lateinit var motorViewModel: MotorViewModel

    private lateinit var binding: ActivityDetailMotorBinding

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMotorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Motor"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        etName = findViewById(R.id.tvDetailName)
        etKode = findViewById(R.id.tvDetailKode)
        etharga = findViewById(R.id.tvDetailHarga)

        btnBatal = findViewById((R.id.btnBatal))
        btnHapus = findViewById((R.id.btnHapus))
        btnEdit = findViewById(R.id.btnEdit)

        motorViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MotorViewModel::class.java]

        Glide.with(this)
            .load(R.drawable.motorbike)
            .apply(RequestOptions().override(100))
            .into(binding.picDMotor)

        val idMotor = intent.getStringExtra("ID_STRING")

        if (idMotor != null) {
            getDataMotorById(idMotor)

            btnHapus.setOnClickListener {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Peringatan")
                builder.setMessage("Yakin ingin menghapus data ?")

                builder.setPositiveButton("Ya"){
                        dialogInterface, which ->
                    run {
                        deleteMotor(idMotor)
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

            motorViewModel.isDeleted.observe(this) {
                if (it) {
                    finish()
                }
            }

            btnBatal.setOnClickListener {
                finish()
            }

            btnEdit.setOnClickListener {
                if (etName.text!!.isEmpty() ||
                    etKode.text!!.isEmpty() ||
                    etharga.text!!.isEmpty()) {
                    getDataMotorById(idMotor)
                    Toast.makeText(this, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }else {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Peringatan")
                    builder.setMessage("Yakin ingin mengedit data ?")

                    builder.setPositiveButton("Ya"){
                            dialogInterface, which ->
                        run {
                            editMotor(idMotor)
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

            motorViewModel.isEdited.observe(this) {
                if (it) {
                    finish()
                }
            }
        }
    }

    fun getDataMotorById(idMotor: String) {
        motorViewModel.motor.observe(this) {
            etName.setText(it.nama)
            etKode.setText(it.kdmotor)
            etharga.setText(it.harga)
        }

        motorViewModel.getMotorById(idMotor)
    }

    fun deleteMotor(idMotor: String) {
        motorViewModel.deleteMotorById(idMotor)
    }

    fun editMotor(idMotor: String) {
        motorViewModel.editMotorById(
            idMotor,
            etKode.text.toString(),
            etName.text.toString(),
            etharga.text.toString()
        )
    }
}