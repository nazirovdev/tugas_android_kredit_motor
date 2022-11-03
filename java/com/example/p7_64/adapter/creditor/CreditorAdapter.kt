package com.example.p7_64.adapter.creditor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.p7_64.R
import com.example.p7_64.model.creditor.CreditorResponseItem

class CreditorAdapter(private val listCreditor: List<CreditorResponseItem>): RecyclerView.Adapter<CreditorAdapter.CreditorViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class CreditorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvNameCreditor)
        val tvPekerjaan: TextView = itemView.findViewById(R.id.tvPekerjaanCreditor)
        val tvTelp: TextView = itemView.findViewById(R.id.tvTelpCreditor)
        val tvAlamat: TextView = itemView.findViewById(R.id.tvAlamatCreditor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_creditor, parent, false)
        return CreditorViewHolder(view)
    }

    override fun onBindViewHolder(holder: CreditorViewHolder, position: Int) {
        holder.tvName.text = listCreditor[position].nama
        holder.tvPekerjaan.text = listCreditor[position].pekerjaan
        holder.tvTelp.text = listCreditor[position].telp
        holder.tvAlamat.text = listCreditor[position].alamat

        holder.itemView.setOnClickListener {
            onItemClickCallback.itemClicked(listCreditor[position])
        }
    }

    override fun getItemCount(): Int {
        return listCreditor.size
    }

    interface OnItemClickCallback {
        fun itemClicked(creditor: CreditorResponseItem)
    }

}