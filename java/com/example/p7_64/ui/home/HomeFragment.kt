package com.example.p7_64.ui.home

import android.content.Intent
import android.net.NetworkRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.p7_64.R
import com.example.p7_64.databinding.FragmentHomeBinding
import com.example.p7_64.ui.creditor.CreditorActivity
import com.example.p7_64.ui.motor.MotorActivity
import com.example.p7_64.ui.officer.OfficerActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var cardOfficer: CardView
    private lateinit var cardMotor: CardView
    private lateinit var cardCreditor: CardView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        cardOfficer = binding.cardOfficer
        cardMotor = binding.cardMotor
        cardCreditor = binding.cardCreditor

        Glide.with(this)
            .load(R.drawable.officer)
            .apply(RequestOptions().override(70))
            .into(binding.picDataPetugas)

        Glide.with(this)
            .load(R.drawable.creditor)
            .apply(RequestOptions().override(70))
            .into(binding.picDataKreditor)

        Glide.with(this)
            .load(R.drawable.motorbike)
            .apply(RequestOptions().override(70))
            .into(binding.picDataMotor)

        cardOfficer.setOnClickListener {
            val moveToOfficerPage = Intent(this@HomeFragment.context, OfficerActivity::class.java)
            startActivity(moveToOfficerPage)
        }

        cardMotor.setOnClickListener {
            val moveToMotorPage = Intent(this@HomeFragment.context, MotorActivity::class.java)
            startActivity(moveToMotorPage)
        }

        cardCreditor.setOnClickListener {
            val moveToCreditorPage = Intent(this@HomeFragment.context, CreditorActivity::class.java)
            startActivity(moveToCreditorPage)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}