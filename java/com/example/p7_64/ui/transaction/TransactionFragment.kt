package com.example.p7_64.ui.transaction

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.p7_64.R
import com.example.p7_64.databinding.FragmentTransactionBinding

class TransactionFragment : Fragment() {
    private lateinit var cardCredit: CardView
    private lateinit var cardApplication: CardView

    private var _binding: FragmentTransactionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Glide.with(this)
            .load(R.drawable.officer)
            .apply(RequestOptions().override(70))
            .into(binding.picDataKredit)

        Glide.with(this)
            .load(R.drawable.data_credit)
            .apply(RequestOptions().override(70))
            .into(binding.picPenKredit)

        cardCredit = binding.cardCredit
        cardApplication = binding.cardApplication

        cardCredit.setOnClickListener {
            val moveToPage = Intent(this@TransactionFragment.context, DataTransactionActivity::class.java)
            startActivity(moveToPage)
        }

        cardApplication.setOnClickListener {
            val moveToPage = Intent(this@TransactionFragment.context, TambahTransactionActivity::class.java)
            startActivity(moveToPage)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}