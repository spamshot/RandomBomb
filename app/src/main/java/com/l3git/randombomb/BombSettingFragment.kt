package com.l3git.randombomb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.l3git.randombomb.databinding.FragmentBombSettingsBinding


class BombSettingFragment : Fragment() {

    private var _binding: FragmentBombSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       _binding = FragmentBombSettingsBinding.inflate(inflater, container, false)

        MobileAds.initialize(requireContext()) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)


        binding.btnStart.setOnClickListener{
            if (binding.etArmTime.text.toString().isNotEmpty() && binding.etWrongGuess.text.toString().isNotEmpty() && binding.etBombTimer.text.toString().isNotEmpty()){

                val amountBombTime = binding.etBombTimer.text.toString().toInt()
                val wrongGuessTime = binding.etWrongGuess.text.toString().toInt()
                val armTime = binding.etArmTime.text.toString().toInt()

                if(amountBombTime > 0 && wrongGuessTime > 0 && armTime > 0){
                    val action = BombSettingFragmentDirections.actionBombSettingFragmentToBombDisplayFragment(amountBombTime,wrongGuessTime,armTime)
                    view?.let{findNavController(it).navigate(action)}
                }else{
                    Toast.makeText(context, "Input can't be 0", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}