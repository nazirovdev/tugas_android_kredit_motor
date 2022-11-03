package com.example.p7_64.ui.motor

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
import com.example.p7_64.adapter.motor.MotorAdapter
import com.example.p7_64.model.motor.MotorResponseItem

class MotorActivity : AppCompatActivity() {
    private lateinit var rvMotor: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvIsEmpty: TextView
    private lateinit var motorViewModel: MotorViewModel

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motor)

        supportActionBar?.title = "Data Motor"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvMotor = findViewById(R.id.rv_motor)
        progressBar = findViewById(R.id.progressBar)
        tvIsEmpty = findViewById(R.id.tvIsEmpty)
        motorViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MotorViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        rvMotor.layoutManager = layoutManager
        rvMotor.setHasFixedSize(true)

        setIsLoading()
        isEmpty()
        showAllMotor()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.iconAdd -> {
                val moveToAddPage = Intent(this, TambahMotorActivity::class.java)
                startActivity(moveToAddPage)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()

        setIsLoading()
        isEmpty()
        showAllMotor()
    }

    private fun setIsLoading() {
        motorViewModel.isLoading.observe(this){
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun isEmpty() {
        motorViewModel.isEmpty.observe(this) {
            if (it) {
                tvIsEmpty.visibility = View.VISIBLE
            } else {
                tvIsEmpty.visibility = View.GONE
            }
        }
    }

    private fun showAllMotor() {
        motorViewModel.motors.observe(this) {
            val motorAdapter = MotorAdapter(it)
            rvMotor.adapter = motorAdapter

            motorAdapter.setOnItemClickCallback(object: MotorAdapter.OnItemClickCallback {
                override fun itemClicked(motor: MotorResponseItem) {
                    val moveToDetailMotor = Intent(this@MotorActivity, DetailMotorActivity::class.java)
                    moveToDetailMotor.putExtra("ID_STRING", motor.idmotor)
                    startActivity(moveToDetailMotor)
                }
            })
        }

        motorViewModel.getAllMotor()
    }
}