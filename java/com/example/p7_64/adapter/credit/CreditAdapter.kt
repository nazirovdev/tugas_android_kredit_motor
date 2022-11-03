package com.example.p7_64.adapter.credit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.p7_64.R
import com.example.p7_64.model.credit.CreditResponseItem

class CreditAdapter(private val listClient: List<CreditResponseItem>): RecyclerView.Adapter<CreditAdapter.CreditViewHolder>() {
    private lateinit var onItemClicked: OnItemClicked

    fun setOnItemClicker(onItemClicked: OnItemClicked) {
        this.onItemClicked = onItemClicked
    }

    class CreditViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvInvoiceClient: TextView = itemView.findViewById(R.id.tvInvoiceClient)
        val tvNameClient: TextView = itemView.findViewById(R.id.tvNameClient)
        val btnDetail: Button = itemView.findViewById(R.id.btnDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_client, parent, false)
        return CreditViewHolder(view)
    }

    override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
        holder.tvInvoiceClient.text = "INV ${listClient[position].invoice}"
        holder.tvNameClient.text = listClient[position].nama
        holder.btnDetail.setOnClickListener {
            onItemClicked.itemDetail(listClient[position])
        }
    }

    override fun getItemCount(): Int {
        return listClient.size
    }

    interface OnItemClicked {
        fun itemDetail(client: CreditResponseItem)
    }
}