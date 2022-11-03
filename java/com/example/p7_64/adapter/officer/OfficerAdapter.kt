package com.example.p7_64.adapter.officer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.p7_64.R
import com.example.p7_64.model.officer.OfficerResponseItem

class OfficerAdapter(private val listOfficer: List<OfficerResponseItem>): RecyclerView.Adapter<OfficerAdapter.OfficerViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class OfficerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvNameOfficer: TextView = itemView.findViewById(R.id.tvNameOfficer)
        val tvKdOfficer: TextView = itemView.findViewById(R.id.tvKodeOfficer)
        val tvJabatan: TextView = itemView.findViewById(R.id.tvJabatanOfficer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfficerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_officer, parent, false)
        return OfficerViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfficerViewHolder, position: Int) {
        holder.tvNameOfficer.text = listOfficer[position].nama
        holder.tvKdOfficer.text = listOfficer[position].kdpetugas
        holder.tvJabatan.text = listOfficer[position].jabatan

        holder.itemView.setOnClickListener {
            onItemClickCallback.itemClicked(listOfficer[position])
        }
    }

    override fun getItemCount(): Int {
        return listOfficer.size
    }

    interface OnItemClickCallback {
        fun itemClicked(officer: OfficerResponseItem)
    }
}