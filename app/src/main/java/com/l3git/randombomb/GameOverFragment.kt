package com.l3git.randombomb


import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.l3git.randombomb.databinding.FragmentGameOverBinding


class GameOverFragment : Fragment() {


    private var _binding: FragmentGameOverBinding? = null
    private val binding get() = _binding!!
    private val args: GameOverFragmentArgs by navArgs()

    private var mediaPlayerBoomBombSound: MediaPlayer? = null //inside end Timer
    private var mediaPlayerGameWon: MediaPlayer? = null // inside bomb disarmed


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPlayerBoomBombSound = MediaPlayer.create(context, R.raw.explosionbomb) //Loads Game lost
        mediaPlayerGameWon = MediaPlayer.create(context, R.raw.orchestralvictoryfanfare) // Loads Game own

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        // Inflate the layout for this fragment
       _binding = FragmentGameOverBinding.inflate(inflater, container, false)
        val gameStatus = args.gameWon

        MobileAds.initialize(requireContext()) {}

        val adRequest = AdRequest.Builder().build()
            binding.adView.loadAd(adRequest)

        if (!gameStatus){
            Toast.makeText(context, "Game Lost!!", Toast.LENGTH_SHORT).show()
            binding.bdImg.setImageResource(R.drawable.deadimage)

            mediaPlayerBoomBombSound?.start()
        }else{
            Toast.makeText(context, "Game Won", Toast.LENGTH_SHORT).show()
            binding.bdImg.setImageResource(R.drawable.ggbro)
            mediaPlayerGameWon?.start()
        }

        binding.btnSettings.setOnClickListener {

            val action = GameOverFragmentDirections.actionGameOverFragmentToBombSettingFragment()
            view?.let{ Navigation.findNavController(it).navigate(action)}

        }

        return binding.root
    }
}