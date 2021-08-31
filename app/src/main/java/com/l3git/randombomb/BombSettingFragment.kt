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


class BombSettingFragment : Fragment() {



    private lateinit var editTime: EditText
    private lateinit var editMinusTime: EditText
    private lateinit var editArmTime: EditText
    private lateinit var btnStart: Button


    private lateinit var bombSettingsFragment: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bomb_settings, container, false)
        bombSettingsFragment = view.findViewById(R.id.bombSettingsLayout)

        editTime = view.findViewById(R.id.bombTimer)
        editMinusTime = view.findViewById(R.id.editMinusTime)
        editArmTime = view.findViewById(R.id.editArmTime)
        btnStart = view.findViewById(R.id.btnStart)


        btnStart.setOnClickListener{
            if (editTime.text.toString().isNotEmpty() && editMinusTime.text.toString().isNotEmpty() && editArmTime.text.toString().isNotEmpty()){

                val amount = editTime.text.toString().toInt()
                val minusTime = editMinusTime.text.toString().toInt()
                val armTime = editArmTime.text.toString().toInt()

                if(amount > 0 && minusTime > 0 && armTime > 0){
                    val action = BombSettingFragmentDirections.actionBombSettingFragmentToBombDisplayFragment(amount,minusTime,armTime)
                    findNavController(view).navigate(action)
                }else{
                    Toast.makeText(context, "E0 input can't be 0", Toast.LENGTH_SHORT).show()
                }
            }

        }

        return view
    }
}