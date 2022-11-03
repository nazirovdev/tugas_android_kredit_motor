package com.example.p7_64.adapter.motor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.p7_64.R
import com.example.p7_64.model.motor.MotorResponseItem

class MotorAdapter(private val listMotor: List<MotorResponseItem>): RecyclerView.Adapter<MotorAdapter.MotorViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class MotorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvKdMotor: TextView = itemView.findViewById(R.id.tvKdMotor)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MotorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_motor, parent, false)
        return MotorViewHolder(view)
    }

    override fun onBindViewHolder(holder: MotorViewHolder, position: Int) {
        holder.tvName.text = listMotor[position].nama
        holder.tvKdMotor.text = listMotor[position].kdmotor
        holder.tvHarga.text = listMotor[position].harga

        holder.itemView.setOnClickListener {
            onItemClickCallback.itemClicked(listMotor[position])
        }
    }

    override fun getItemCount(): Int {
        return listMotor.size
    }

    interface OnItemClickCallback {
        fun itemClicked(motor: MotorResponseItem)
    }
}