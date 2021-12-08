package com.decagon.n26_p3_usecase.features.onlineRadio.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.media.MediaPlayer
import com.decagon.n26_p3_usecase.databinding.FragmentOnlineRadioBinding
import java.io.IOException
import android.app.Activity
import android.media.AudioManager
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException


class OnlineRadioFragment : Fragment(), View.OnClickListener {

    private var playSeekBar: ProgressBar? = null
    private var buttonPlay: Button? = null
    private var buttonStopPlay: Button? = null
    private var player: MediaPlayer? = null
    var prepare = false
    lateinit var binding : FragmentOnlineRadioBinding
    val url = "http://server-23.stream-server.nl:8438" // your URL here

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnlineRadioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeMediaPlayer()
        initializeUIElements()
    }

    override fun onResume() {
        super.onResume()

//        if (player!!.isPlaying) stopPlaying() else startPlaying()
    }

    private fun initializeUIElements() {

        playSeekBar =  binding.progressBar1
        playSeekBar!!.max = 100
        playSeekBar!!.visibility = View.INVISIBLE

        buttonPlay = binding.buttonPlay
        buttonPlay!!.setOnClickListener(this)

        buttonStopPlay = binding.buttonStopPlay
        buttonStopPlay!!.isEnabled = false
        buttonStopPlay!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v === buttonPlay) {
            startPlaying()
        } else if (v === buttonStopPlay) {
            stopPlaying()
        }
    }

    private fun startPlaying() {
        buttonStopPlay!!.isEnabled = true
        buttonPlay!!.isEnabled = false
        playSeekBar!!.visibility = View.VISIBLE
        if (prepare) player!!.start()
        player!!.setVolume(0.5F, 0.5F)
    }

    private fun stopPlaying() {
        if (player!!.isPlaying) {
            player!!.stop()
            player!!.release()
            initializeMediaPlayer()
        }
        buttonPlay!!.isEnabled = true
        buttonStopPlay!!.isEnabled = false
        playSeekBar!!.visibility = View.INVISIBLE
    }

    private fun initializeMediaPlayer() {
        player = MediaPlayer()
        player!!.setAudioStreamType(AudioManager.STREAM_MUSIC)

        try {
            player!!.setDataSource(url)
            player!!.prepareAsync()
            prepare = true
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        player!!.setOnPreparedListener { prepare = true }

        player!!.setOnBufferingUpdateListener { mp, percent ->
            playSeekBar!!.secondaryProgress = percent
            Log.i("Buffering", "" + percent)
        }
    }

    override fun onPause() {
        super.onPause()
        if (player!!.isPlaying) {
            player!!.stop()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if(prepare) player!!.release()
    }

}
