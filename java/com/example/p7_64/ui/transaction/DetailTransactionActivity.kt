package com.example.p7_64.ui.transaction

import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.p7_64.R
import com.example.p7_64.databinding.ActivityDetailTransactionBinding
import com.google.android.material.textfield.TextInputEditText
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.VerticalPositionMark
import java.io.File
import java.io.FileOutputStream
import java.text.NumberFormat
import java.util.*

class DetailTransactionActivity : AppCompatActivity() {
    private lateinit var transactionViewModel: TransactionViewModel

    private lateinit var invoice: TextInputEditText
    private lateinit var idkreditor: TextInputEditText
    private lateinit var nama: TextInputEditText
    private lateinit var alamat: TextInputEditText
    private lateinit var tanggal: TextInputEditText
    private lateinit var nmotor: TextInputEditText
    private lateinit var kdmotor: TextInputEditText
    private lateinit var hrgtunai: TextInputEditText
    private lateinit var dp: TextInputEditText
    private lateinit var angsuran: TextInputEditText
    private lateinit var bunga: TextInputEditText
    private lateinit var lama: TextInputEditText
    private lateinit var hrgkredit: TextInputEditText
    private lateinit var totalkredit: TextInputEditText

    private lateinit var btnHapus: Button
    private lateinit var btnBatal: Button
    private lateinit var btnPrintPdf: Button

    private lateinit var binding: ActivityDetailTransactionBinding

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailTransactionBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Kredit"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        transactionViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[TransactionViewModel::class.java]

        val inv = intent.getStringExtra("DATA_INVOICE")

        invoice = binding.tvInvoice
        idkreditor = binding.tvIdCreditor
        nama = binding.tvName
        alamat = binding.tvAlamat
        tanggal = binding.tvTanggal
        nmotor = binding.tvNameMotor
        kdmotor = binding.tvKodeMotor
        hrgtunai = binding.tvHargaTunai
        dp = binding.tvDp
        angsuran = binding.tvAngsuran
        bunga = binding.tvBunga
        lama = binding.tvLama
        hrgkredit = binding.tvHargaKredit
        totalkredit = binding.tvTotalKredit

        btnHapus = binding.btnHapus
        btnBatal = binding.btnBatal
        btnPrintPdf = binding.btnPrint

        Glide.with(this)
            .load(R.drawable.client)
            .apply(RequestOptions().override(100))
            .into(binding.imageView)

        transactionViewModel.itemCredit.observe(this) {
            invoice.setText(it.invoice.toString())
            idkreditor.setText(it.idkreditor)
            nama.setText(it.nama)
            alamat.setText(it.alamat)
            tanggal.setText(it.tanggal)
            nmotor.setText(it.nmotor)
            kdmotor.setText(it.kdmotor)
            hrgtunai.setText(it.hrgtunai)
            dp.setText(it.dp)
            angsuran.setText(it.angsuran)
            bunga.setText(it.bunga)
            lama.setText(it.lama)
            hrgkredit.setText(it.hrgkredit)
            totalkredit.setText(it.totalkredit)
        }

        transactionViewModel.isDeleted.observe(this) {
            if (it) {
                Toast.makeText(this@DetailTransactionActivity, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        transactionViewModel.getCreditClientByInvoice(inv.toString())

        btnHapus.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Peringatan")
            builder.setMessage("Yakin ingin menghapus data ?")

            builder.setPositiveButton("Ya"){
                    dialogInterface, which ->
                run {
                    transactionViewModel.deleteCreditById(inv.toString())
                }
            }

            builder.setNegativeButton("Tidak"){
                    dialogInterface, which ->
            }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

        btnPrintPdf.setOnClickListener {
            transactionViewModel.btnPrintClicked()
        }

        transactionViewModel.isPrinted.observe(this) {
            if (it) {
                createPdf()
                Toast.makeText(this@DetailTransactionActivity, "Data berhasil dicetak", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnBatal.setOnClickListener {
            finish()
        }
    }

    private fun createPdf() {
        val path = Environment.getExternalStorageDirectory().absolutePath + "/DataKredit"
        val dir = File(path)

        if (!dir.exists()) {
            dir.mkdir()
        }

        val file = File(dir, "data_kredit.pdf")
        val fileOutputStream = FileOutputStream(file)

        val document = Document()
        PdfWriter.getInstance(document, fileOutputStream)

        document.open()

        val titleStyle = Font(Font.FontFamily.COURIER, 20.0f, Font.BOLD, BaseColor(0, 153, 204, 255))
        val title = Paragraph(
            "Data Kredit",
            titleStyle
        )
        document.add(title)

        document.add(Chunk.NEWLINE)
        document.add(Chunk.NEWLINE)

        val localId = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localId)

        transactionViewModel.itemCredit.observe(this) {
            val p1 = Paragraph("Invoice :")
            p1.add(Chunk(VerticalPositionMark()))
            p1.add(it.invoice)
            document.add(p1)

            val p2 = Paragraph("Tanggal :")
            p2.add(Chunk(VerticalPositionMark()))
            p2.add(it.tanggal)
            document.add(p2)

            val p3 = Paragraph("ID Kreditor :")
            p3.add(Chunk(VerticalPositionMark()))
            p3.add(it.idkreditor)
            document.add(p3)

            val p4 = Paragraph("Nama :")
            p4.add(Chunk(VerticalPositionMark()))
            p4.add(it.nama)
            document.add(p4)

            val p5 = Paragraph("Alamat :")
            p5.add(Chunk(VerticalPositionMark()))
            p5.add(it.alamat)
            document.add(p5)

            val p6 = Paragraph("Kode Motor :")
            p6.add(Chunk(VerticalPositionMark()))
            p6.add(it.kdmotor)
            document.add(p6)

            val p7 = Paragraph("Nama Motor :")
            p7.add(Chunk(VerticalPositionMark()))
            p7.add(it.nmotor)
            document.add(p7)

            val p8 = Paragraph("Harga Tunai :")
            p8.add(Chunk(VerticalPositionMark()))
            p8.add(formatRupiah.format(it.hrgtunai!!.toDouble()))
            document.add(p8)

            val p9 = Paragraph("DP :")
            p9.add(Chunk(VerticalPositionMark()))
            p9.add(formatRupiah.format(it.dp!!.toDouble()))
            document.add(p9)

            val p10 = Paragraph("Harga Kredit :")
            p10.add(Chunk(VerticalPositionMark()))
            p10.add(formatRupiah.format(it.hrgkredit!!.toDouble()))
            document.add(p10)

            val p11 = Paragraph("Bunga :")
            p11.add(Chunk(VerticalPositionMark()))
            p11.add(it.bunga + "% / tahun")
            document.add(p11)

            val p12 = Paragraph("Lama :")
            p12.add(Chunk(VerticalPositionMark()))
            p12.add(it.lama)
            document.add(p12)

            val p13 = Paragraph("Total Kredit :")
            p13.add(Chunk(VerticalPositionMark()))
            p13.add(formatRupiah.format(it.totalkredit!!.toDouble()))
            document.add(p13)

            val p14 = Paragraph("Angsuran / Bln :")
            p14.add(Chunk(VerticalPositionMark()))
            p14.add(formatRupiah.format(it.angsuran!!.toDouble()))
            document.add(p14)
        }

        document.add(Chunk.NEWLINE)
        document.add(Chunk.NEWLINE)
        document.add(Chunk.NEWLINE)
        document.add(Chunk.NEWLINE)

        val endPdf = Paragraph("Menyatakan bahwa data diatas benar")
        document.add(endPdf)

        document.add(Chunk.NEWLINE)
        document.add(Chunk.NEWLINE)

        val endPdf1 = Paragraph("Semarang, 01-Feb-2019")
        document.add(endPdf1)

        document.add(Chunk.NEWLINE)
        document.add(Chunk.NEWLINE)

        val endPdf2 = Paragraph("Sales")
        document.add(endPdf2)

        document.close()
    }
}