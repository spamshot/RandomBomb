package com.l3git.randombomb

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs


class GameOverFragment : Fragment() {


    private val args: GameOverFragmentArgs by navArgs()

    private lateinit var btnSettings: Button
    private lateinit var bdImage: ImageView



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
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_over, container, false)
        val gameStatus = args.gameWon

        bdImage = view.findViewById(R.id.bdImg)

        if (!gameStatus){
            Toast.makeText(context, "Game Lost!!", Toast.LENGTH_SHORT).show()
            bdImage.setImageResource(R.drawable.deadimage)

            mediaPlayerBoomBombSound?.start()
        }else{
            Toast.makeText(context, "Game Won", Toast.LENGTH_SHORT).show()
            bdImage.setImageResource(R.drawable.ggbro)
            mediaPlayerGameWon?.start()
        }


        btnSettings = view.findViewById(R.id.btnSettings)

        btnSettings.setOnClickListener {

            val action = GameOverFragmentDirections.actionGameOverFragmentToBombSettingFragment()
            Navigation.findNavController(view).navigate(action)

        }


        return view
    }


}