package com.l3git.randombomb

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button

import android.widget.TextView
import android.widget.Toast

import androidx.core.view.isVisible
import androidx.navigation.Navigation

import androidx.navigation.fragment.navArgs


class BombDisplayFragment : Fragment() {


    private val args: BombDisplayFragmentArgs by navArgs()


    private val numbers = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) //Numbers for button shuffle
    private val defuseNumber = mutableListOf<Int>() //Number we type in and use to check with defuseNumber

    private val defuseCode = mutableListOf<Int>() //Numbers for defuseCode

    private var mediaPlayerSiren: MediaPlayer? = null //inside clear BTN
    private var mediaPlayerBeeper: MediaPlayer? = null //inside Timer
    private var mediaPlayerBombArming: MediaPlayer? = null // Bomb Arming



    private lateinit var enterBtn: Button
    private lateinit var clearBtn: Button
    private lateinit var button0: Button
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    private lateinit var button7: Button
    private lateinit var button8: Button
    private lateinit var button9: Button


    lateinit var defuseTxt: TextView
    lateinit var pinEntered: TextView //pos 0
    lateinit var txtCountDownTimerDisplay: TextView
    lateinit var txtArming: TextView
    lateinit var txtHint: TextView

    //    ///////TIMER////////////TIMER//////////////TIMER////////////TIMER/////////

//    ............

    private lateinit var timerCountdownTimer: CountDownTimer
    private var isArming: Boolean = false
    private var timeInMill = 0L

//    ............

    private lateinit var countdownTimer: CountDownTimer
    private var isRunning: Boolean = false
    var timeInMilliSeconds = 0L

    ///////TIMER////////////TIMER//////////////TIMER////////////TIMER/////////


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mediaPlayerSiren = MediaPlayer.create(context, R.raw.emergency_siren_short_bursttwo) //Loads sound for clear BTN
        mediaPlayerBeeper = MediaPlayer.create(context, R.raw.beeper) //Loads sound for timer
        mediaPlayerBombArming = MediaPlayer.create(context, R.raw.alarmhugescifsp) //Loads Arming Bomb


    }

    private fun btnOn() {
        button0.isEnabled = true
        button1.isEnabled = true
        button2.isEnabled = true
        button3.isEnabled = true
        button4.isEnabled = true
        button5.isEnabled = true
        button6.isEnabled = true
        button7.isEnabled = true
        button8.isEnabled = true
        button9.isEnabled = true

    }

    private fun btnOff() {
        button0.isEnabled = false
        button1.isEnabled = false
        button2.isEnabled = false
        button3.isEnabled = false
        button4.isEnabled = false
        button5.isEnabled = false
        button6.isEnabled = false
        button7.isEnabled = false
        button8.isEnabled = false
        button9.isEnabled = false


    }

    private fun btnActionOff() {
        enterBtn.isEnabled = false
        clearBtn.isEnabled = false

    }
    private fun btnActionOn() {
        enterBtn.isEnabled = true
        clearBtn.isEnabled = true

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bomb_display, container, false)
        txtCountDownTimerDisplay = view.findViewById(R.id.countDownTimerDisplay)
        val myArmTime = args.armtime // get the arm time from other fragment for timer


        // Timer /// Timer // Timer  // Timer /// Timer // Timer  // Timer /// Timer // Timer

        if (isArming) {
            pauseTimer()

        } else {
            timeInMill = myArmTime.toLong() * 1000L + 1000L
            startArmingTimer(timeInMill)
        }

//        }..........^............Main...........^..................^.....Main...........................

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        clearBtn = view.findViewById(R.id.clearBtn)
        enterBtn = view.findViewById(R.id.enterBtn)
        button0 = view.findViewById(R.id.button0)
        button1 = view.findViewById(R.id.button1)
        button2 = view.findViewById(R.id.button2)
        button3 = view.findViewById(R.id.button3)
        button4 = view.findViewById(R.id.button4)
        button5 = view.findViewById(R.id.button5)
        button6 = view.findViewById(R.id.button6)
        button7 = view.findViewById(R.id.button7)
        button8 = view.findViewById(R.id.button8)
        button9 = view.findViewById(R.id.button9)



        defuseTxt = view.findViewById(R.id.diffuseCodeTxt)
        pinEntered = view.findViewById(R.id.pinEntered)

        txtArming = view.findViewById(R.id.txtArming)
        txtHint = view.findViewById(R.id.txtHint)


        //Disable buttons on start
        btnOff()
        btnActionOff()




        defuseCode.shuffle()
        defuseTxt.text = defuseCode.toString()
        shuffleAndSet()

        populateDefuseCode()

        button0.setOnClickListener {
            defuseNumber.add(numbers[0])
            pinEntered.text = defuseNumber.toString()
            shuffleAndSet()

        }
        button1.setOnClickListener {
            defuseNumber.add(numbers[1])
            pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }
        button2.setOnClickListener {
            defuseNumber.add(numbers[2])
            pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }
        button3.setOnClickListener {
            defuseNumber.add(numbers[3])
            pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }
        button4.setOnClickListener {
            defuseNumber.add(numbers[4])
            pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }
        button5.setOnClickListener {
            defuseNumber.add(numbers[5])
            pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }
        button6.setOnClickListener {
            defuseNumber.add(numbers[6])
            pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }
        button7.setOnClickListener {
            defuseNumber.add(numbers[7])
            pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }
        button8.setOnClickListener {
            defuseNumber.add(numbers[8])
            pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }
        button9.setOnClickListener {
            defuseNumber.add(numbers[9])
            pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }


        clearBtn.setOnClickListener {

            defuseCode.shuffle()
            defuseTxt.text = defuseCode.toString()
            pinEntered.text = defuseNumber.toString()
            btnOn() //todo
            wrongGuess()

            mediaPlayerSiren?.start() //plays sound

        }

        enterBtn.setOnClickListener {
            val listSize = defuseNumber.size
            var i = 0
            if (listSize == 6) {// When they enter code to disarm bomb == 10
                while (i <= 5) {
                    if (defuseCode.elementAt(i) == defuseNumber.elementAt(i)) { // Checks all numbers to see if they match to defuse
                        i++
                        if(i == 6){ // If all defuse code matches, this will defuse the bomb
                            timerHasEndedGameWon()

                        }
                    } else {
                        btnOn()
                        mediaPlayerSiren?.start()
                        i = 11
                        populateDefuseCode()
                        wrongGuess()
                        pinEntered.text = defuseNumber.toString()
                        defuseTxt.text = defuseCode.toString()


                    }
                }
            }
        }

    }

    fun populateDefuseCode(){

        defuseCode.add(numbers[0])
        defuseCode.add(numbers[1])
        defuseCode.add(numbers[2])
        defuseCode.add(numbers[3])
        defuseCode.add(numbers[4])
        defuseCode.add(numbers[5])
        defuseTxt.text = defuseCode.toString()

    }

    fun shuffleAndSet() {
        numbers.shuffle()

        val listSize = defuseNumber.size
        if (listSize == 6) {// when list is 10
            Toast.makeText(context, "Code entered", Toast.LENGTH_SHORT).show()
            btnOff() // turns off buttons when max code
        }
        button0.text = numbers[0].toString()
        button1.text = numbers[1].toString()
        button2.text = numbers[2].toString()
        button3.text = numbers[3].toString()
        button4.text = numbers[4].toString()
        button5.text = numbers[5].toString()
        button6.text = numbers[6].toString()
        button7.text = numbers[7].toString()
        button8.text = numbers[8].toString()
        button9.text = numbers[9].toString()
    }


    private fun pauseTimer() {

        countdownTimer.cancel()
        isRunning = false

    }


    private fun startTimer(time_in_seconds: Long) {
        countdownTimer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {
                timerHasEnded()

            }
            override fun onTick(p0: Long) {
                timeInMilliSeconds = p0
                updateTextUI()
                mediaPlayerBeeper?.start()
            }
        }
        countdownTimer.start()
        isRunning = true


    }

    private fun startArmingTimer(time_in_seconds: Long) {
        timerCountdownTimer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {
                armingHasEnded()
                btnActionOn()

            }
            override fun onTick(p0: Long) {
                timeInMill = p0
                updateArmingTextUI()
            }

        }

        timerCountdownTimer.start()
        isArming = true


    }

    private fun resetTimer() {
        timeInMilliSeconds -= 3000L
        updateTextUI()

    }

    fun updateTextUI() {
        val minute = (timeInMilliSeconds / 1000) / 60
        val seconds = (timeInMilliSeconds / 1000) % 60
        txtCountDownTimerDisplay.text = "$minute:$seconds"


    }

    fun updateArmingTextUI() {
        val minute = (timeInMill / 1000) / 60
        val seconds = (timeInMill / 1000) % 60
        txtCountDownTimerDisplay.text = "$minute:$seconds"
        mediaPlayerBombArming?.start()

    }


    private fun timerHasEnded() {

        countdownTimer.cancel()
        isRunning = false
        mediaPlayerBeeper?.stop()
        mediaPlayerSiren?.stop()


        val action = BombDisplayFragmentDirections.actionBombDisplayFragmentToGameOverFragment(gameWon = false)
        view?.let { Navigation.findNavController(it).navigate(action) }
    }


    private fun timerHasEndedGameWon() {

        countdownTimer.cancel()
        isRunning = false

        val action = BombDisplayFragmentDirections.actionBombDisplayFragmentToGameOverFragment(gameWon = true)
        view?.let { Navigation.findNavController(it).navigate(action) }

    }


    private fun armingHasEnded() {
        timerCountdownTimer.cancel()
        isArming = false
        Toast.makeText(context, "Bomb Armed", Toast.LENGTH_SHORT).show()
        mediaPlayerBombArming?.stop()
        bombStarted()
        txtArming.isVisible = false


    }

    private fun bombStarted(){
        btnOn()
        val myNumber = args.timerrr
        isRunning = true

        txtHint.isVisible = true
        defuseTxt.isVisible = true

        timeInMilliSeconds = myNumber.toLong() * 60000L
        startTimer(timeInMilliSeconds)

    }

    private fun wrongGuess(){

        val myGuess = args.wrongguess * 1000
        defuseNumber.clear()
        defuseCode.clear()

        shuffleAndSet()

        pinEntered.text = defuseNumber.toString()
        pauseTimer() // used for wrong guss
        resetTimer() // used for wrong guss
        populateDefuseCode()


        startTimer(timeInMilliSeconds - myGuess.toLong())
    }


//////TIMER///////////////////////////////TIMER//////////////////////////////TIMER//////////////////////////////TIMER/////////////////

}