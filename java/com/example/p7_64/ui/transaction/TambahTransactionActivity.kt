package com.example.p7_64.ui.transaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.p7_64.R
import com.example.p7_64.databinding.ActivityTambahTransactionBinding
import com.google.android.material.textfield.TextInputEditText

class TambahTransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTambahTransactionBinding
    private lateinit var transactionViewModel: TransactionViewModel

    private lateinit var acidKreditor: AutoCompleteTextView
    private lateinit var acKdMotor: AutoCompleteTextView

    private lateinit var itemIdKreditor: ArrayAdapter<String>
    private lateinit var itemKdMotor: ArrayAdapter<String>

    private lateinit var etNamaMotor: TextInputEditText
    private lateinit var etHarga: TextInputEditText
    private lateinit var etDpUangMuka: TextInputEditText
    private lateinit var etHargaKredit: TextInputEditText
    private lateinit var etBungaTahun: TextInputEditText
    private lateinit var etLamaAngsuran: TextInputEditText
    private lateinit var etTotalKredit: TextInputEditText
    private lateinit var etAngsuranPerBulan: TextInputEditText
    private lateinit var btnReset: Button
    private lateinit var btnSimpan: Button

    private lateinit var btnHitung: Button

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTambahTransactionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "Pengajuan Kredit"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        acidKreditor = binding.acNamaKreditor
        acKdMotor = binding.acKdMotor
        etNamaMotor = binding.etNamaMotor
        etHarga = binding.etHarga
        btnHitung = binding.btnHitung
        etDpUangMuka = binding.etDpUangMuka
        etHargaKredit = binding.etHargaKredit
        etBungaTahun = binding.etBungaTahun
        etLamaAngsuran = binding.etLamaAngsuran
        etLamaAngsuran = binding.etLamaAngsuran
        etTotalKredit = binding.etTotalKredit
        etAngsuranPerBulan = binding.etAngsuranPerBulan
        btnReset = binding.btnReset
        btnSimpan = binding.btnSimpan

        transactionViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[TransactionViewModel::class.java]

        Glide.with(this)
            .load(R.drawable.client)
            .apply(RequestOptions().override(100))
            .into(binding.picTTransaction)

        transactionViewModel.itemsCreditor.observe(this) {
            itemIdKreditor = ArrayAdapter(this, R.layout.item_client_name, it)
            acidKreditor.setAdapter(itemIdKreditor)
        }

        transactionViewModel.itemsMotor.observe(this) {
            itemKdMotor = ArrayAdapter(this, R.layout.item_client_name, it)
            acKdMotor.setAdapter(itemKdMotor)

            acKdMotor.setOnItemClickListener(object: AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    transactionViewModel.getMotor(parent?.getItemAtPosition(position).toString())

                    transactionViewModel.itemMotor.observe(this@TambahTransactionActivity) {
                        etNamaMotor.setText(it.kdmotor.toString())
                        etHarga.setText(it.harga.toString())
                    }
                }
            })
        }

        transactionViewModel.isAdding.observe(this) {
            if (it) {
                finish()
                Toast.makeText(this@TambahTransactionActivity, "Data berhasil ditambah", Toast.LENGTH_SHORT).show()
            }
        }

        btnHitung.setOnClickListener {
            if (etHarga.text!!.isEmpty() || etDpUangMuka.text!!.isEmpty() || etBungaTahun.text!!.isEmpty() || etLamaAngsuran.text!!.isEmpty()) {
                Toast.makeText(this@TambahTransactionActivity, "Jika kosong maka harap isi angka 0", Toast.LENGTH_LONG).show()
            }else {
                val hargaKredit = etHarga.text.toString().toInt() - etDpUangMuka.text.toString().toInt()
                val totalKredit = hargaKredit + (hargaKredit * (etBungaTahun.text.toString().toInt() / 100) * 12)
                val angsuran = totalKredit / (if (etLamaAngsuran.text.toString().toInt() == 0) 1 else etLamaAngsuran.text.toString().toInt())

                etHargaKredit.setText(hargaKredit.toString())
                etTotalKredit.setText(totalKredit.toString())
                etAngsuranPerBulan.setText(angsuran.toString())
            }
        }

        btnReset.setOnClickListener {
            acKdMotor.text.clear()
            acidKreditor.text.clear()
            etNamaMotor.text!!.clear()
            etHarga.text!!.clear()
            etDpUangMuka.text!!.clear()
            etBungaTahun.text!!.clear()
            etLamaAngsuran.text!!.clear()
            etHargaKredit.text!!.clear()
            etTotalKredit.text!!.clear()
            etAngsuranPerBulan.text!!.clear()
        }

        btnSimpan.setOnClickListener {
            if (acKdMotor.text.isEmpty() ||
                acidKreditor.text.isEmpty() ||
                etNamaMotor.text!!.isEmpty() ||
                etHarga.text!!.isEmpty() ||
                etDpUangMuka.text!!.isEmpty() ||
                etBungaTahun.text!!.isEmpty() ||
                etLamaAngsuran.text!!.isEmpty() ||
                etHargaKredit.text!!.isEmpty() ||
                etTotalKredit.text!!.isEmpty() ||
                etAngsuranPerBulan.text!!.isEmpty()) {
                Toast.makeText(this@TambahTransactionActivity, "Pastikan Form tidak ada yang kosong !", Toast.LENGTH_LONG).show()
            }else {
                transactionViewModel.addCredit(
                    acidKreditor.text.toString(),
                    etNamaMotor.text.toString(),
                    etHarga.text.toString(),
                    etDpUangMuka.text.toString(),
                    etHargaKredit.text.toString(),
                    etBungaTahun.text.toString(),
                    etLamaAngsuran.text.toString(),
                    etTotalKredit.text.toString(),
                    etAngsuranPerBulan.text.toString(),
                )
            }
        }

        transactionViewModel.getItemsIdCreditor()
        transactionViewModel.getItemsKodeMotor()
    }
}