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
import com.l3git.randombomb.databinding.FragmentBombDisplayBinding


class BombDisplayFragment : Fragment() {

    private var _binding: FragmentBombDisplayBinding? = null
    private val binding get() = _binding!!
    private val args: BombDisplayFragmentArgs by navArgs()

    private val numbers = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) //Numbers for button shuffle
    private val defuseNumber = mutableListOf<Int>() //Number we type in and use to check with defuseNumber
    private val defuseCode = mutableListOf<Int>() //Numbers for defuseCode


    private var mediaPlayerSiren: MediaPlayer? = null //inside clear BTN
    private var mediaPlayerBeeper: MediaPlayer? = null //inside Timer
    private var mediaPlayerBombArming: MediaPlayer? = null // Bomb Arming
    private var mediaPlayerBoomBombSound: MediaPlayer? = null //inside end Timer
    private var mediaPlayerGameWon: MediaPlayer? = null // inside bomb disarmed

    //    ///////TIMER////////////TIMER//////////////TIMER////////////TIMER/////////
    private lateinit var timerCountdownTimer: CountDownTimer
    private var isArming: Boolean = false
    private var timeInMill = 0L

    ///////TIMER////////////TIMER//////////////TIMER////////////TIMER/////////

    private lateinit var countdownTimer: CountDownTimer
    private var isRunning: Boolean = false
    var timeInMilliSeconds = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mediaPlayerSiren = MediaPlayer.create(context, R.raw.emergency_siren_short_bursttwo) //Loads sound for clear BTN
        mediaPlayerBeeper = MediaPlayer.create(context, R.raw.beeper) //Loads sound for timer
        mediaPlayerBombArming = MediaPlayer.create(context, R.raw.alarmhugescifsp) //Loads Arming Bomb
        mediaPlayerBoomBombSound = MediaPlayer.create(context, R.raw.explosionbomb) //Loads Game lost
        mediaPlayerGameWon = MediaPlayer.create(context, R.raw.orchestralvictoryfanfare) // Loads Game own

    }


    private fun btnOn() {
        binding.button0.isEnabled = true
        binding.button1.isEnabled = true
        binding.button2.isEnabled = true
        binding.button3.isEnabled = true
        binding.button4.isEnabled = true
        binding.button5.isEnabled = true
        binding.button6.isEnabled = true
        binding.button7.isEnabled = true
        binding.button8.isEnabled = true
        binding.button9.isEnabled = true
    }


    private fun btnOff() {
        binding.button0.isEnabled = false
        binding.button1.isEnabled = false
        binding.button2.isEnabled = false
        binding.button3.isEnabled = false
        binding.button4.isEnabled = false
        binding.button5.isEnabled = false
        binding.button6.isEnabled = false
        binding.button7.isEnabled = false
        binding.button8.isEnabled = false
        binding.button9.isEnabled = false
    }


    private fun btnActionOff() {
        binding.enterBtn.isEnabled = false
        binding.clearBtn.isEnabled = false
    }

    private fun btnActionOn() {
        binding.enterBtn.isEnabled = true
        binding.clearBtn.isEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBombDisplayBinding.inflate(inflater, container, false)


        if (isArming) {
            pauseTimer()

        } else {
            timeInMill = args.armtime.toLong() * 1000L + 1000L
            startArmingTimer(timeInMill)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Disable buttons on start
        btnOff()
        btnActionOff()

        // Shuffles the code and sets it
        defuseCode.shuffle()
        binding.diffuseCodeTxt.text = defuseCode.toString()
        shuffleAndSet()
        populateDefuseCode()

        binding.button0.setOnClickListener {
            defuseNumber.add(numbers[0])
            binding.pinEntered.text = defuseNumber.toString()
            shuffleAndSet()

        }
        binding.button1.setOnClickListener {
            defuseNumber.add(numbers[1])
            binding.pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }
        binding.button2.setOnClickListener {
            defuseNumber.add(numbers[2])
            binding.pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }
        binding.button3.setOnClickListener {
            defuseNumber.add(numbers[3])
            binding.pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }
        binding.button4.setOnClickListener {
            defuseNumber.add(numbers[4])
            binding.pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }
        binding.button5.setOnClickListener {
            defuseNumber.add(numbers[5])
            binding.pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }
        binding.button6.setOnClickListener {
            defuseNumber.add(numbers[6])
            binding.pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }
        binding.button7.setOnClickListener {
            defuseNumber.add(numbers[7])
            binding.pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }
        binding.button8.setOnClickListener {
            defuseNumber.add(numbers[8])
            binding.pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }
        binding.button9.setOnClickListener {
            defuseNumber.add(numbers[9])
            binding.pinEntered.text = defuseNumber.toString()
            shuffleAndSet()
        }

        binding.clearBtn.setOnClickListener {

            defuseCode.shuffle()
            binding.diffuseCodeTxt.text = defuseCode.toString()
            binding.pinEntered.text = defuseNumber.toString()
            btnOn()
            wrongGuess()

            mediaPlayerSiren?.start() //plays sound
        }

        binding.enterBtn.setOnClickListener {
            val listSize = defuseNumber.size
            var i = 0
            if (listSize == 6) {// When they enter code to disarm bomb == 10
                while (i <= 5) {
                    if (defuseCode.elementAt(i) == defuseNumber.elementAt(i)) { // Checks all numbers to see if they match to defuse
                        i++
                        if (i == 6) { // If all defuse code matches, this will defuse the bomb
                            timerHasEndedGameWon()

                        }
                    } else {
                        btnOn()
                        mediaPlayerSiren?.start()
                        i = 11
                        populateDefuseCode()
                        wrongGuess()
                        binding.pinEntered.text = defuseNumber.toString()
                        binding.diffuseCodeTxt.text = defuseCode.toString()

                    }
                }
            }
        }

    }

    fun populateDefuseCode() {

        defuseCode.add(numbers[0])
        defuseCode.add(numbers[1])
        defuseCode.add(numbers[2])
        defuseCode.add(numbers[3])
        defuseCode.add(numbers[4])
        defuseCode.add(numbers[5])
        binding.diffuseCodeTxt.text = defuseCode.toString()
    }

    fun shuffleAndSet() {
        numbers.shuffle()

        val listSize = defuseNumber.size
        if (listSize == 6) {// when list is 10
            Toast.makeText(context, "Code entered", Toast.LENGTH_SHORT).show()
            btnOff() // turns off buttons when max code
        }
        binding.button0.text = numbers[0].toString()
        binding.button1.text = numbers[1].toString()
        binding.button2.text = numbers[2].toString()
        binding.button3.text = numbers[3].toString()
        binding.button4.text = numbers[4].toString()
        binding.button5.text = numbers[5].toString()
        binding.button6.text = numbers[6].toString()
        binding.button7.text = numbers[7].toString()
        binding.button8.text = numbers[8].toString()
        binding.button9.text = numbers[9].toString()
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
        binding.countDownTimerDisplay.text = "$minute:$seconds"


    }

    fun updateArmingTextUI() {
        val minute = (timeInMill / 1000) / 60
        val seconds = (timeInMill / 1000) % 60
        binding.countDownTimerDisplay.text = "$minute:$seconds"
        mediaPlayerBombArming?.start()

    }

    private fun timerHasEnded() {

        countdownTimer.cancel()
        isRunning = false
        mediaPlayerBeeper?.stop()
        mediaPlayerSiren?.stop()


        mediaPlayerBoomBombSound?.start()
        val action = BombDisplayFragmentDirections.actionBombDisplayFragmentToGameOverFragment(gameWon = false)
        view?.let { Navigation.findNavController(it).navigate(action) }
    }


    private fun timerHasEndedGameWon() {

        countdownTimer.cancel()
        isRunning = false


        mediaPlayerGameWon?.start()
        val action = BombDisplayFragmentDirections.actionBombDisplayFragmentToGameOverFragment(gameWon = true)
        view?.let { Navigation.findNavController(it).navigate(action) }

    }

    private fun armingHasEnded() {
        timerCountdownTimer.cancel()
        isArming = false
        Toast.makeText(context, "Bomb Armed", Toast.LENGTH_SHORT).show()
        mediaPlayerBombArming?.stop()
        bombStarted()
        binding.txtArming.isVisible = false

    }

    private fun bombStarted() {
        btnOn()
        val myNumber = args.timerrr
        isRunning = true

        binding.txtHint.isVisible = true
        binding.diffuseCodeTxt.isVisible = true

        timeInMilliSeconds = myNumber.toLong() * 60000L
        startTimer(timeInMilliSeconds)

    }

    private fun wrongGuess() {

        val myGuess = args.wrongguess * 1000
        defuseNumber.clear()
        defuseCode.clear()

        shuffleAndSet()

        binding.pinEntered.text = defuseNumber.toString()
        pauseTimer() // used for wrong guss
        resetTimer() // used for wrong guss
        populateDefuseCode()


        startTimer(timeInMilliSeconds - myGuess.toLong())
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}