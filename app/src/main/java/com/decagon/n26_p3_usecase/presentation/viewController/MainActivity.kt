package com.decagon.n26_p3_usecase.presentation.viewController

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.databinding.ActivityMainBinding
import com.decagon.n26_p3_usecase.presentation.viewModel.JokeViewModel
import com.decagon.n26_p3_usecase.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.ActionBar


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null
    private  val viewModel : JokeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

//        val bar: android.app.ActionBar? = actionBar
//        bar?.setBackgroundDrawable(ColorDrawable(Color.rgb(19,48,81)))

        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(android.R.color.black)));

        binding!!.progressBar.hideView()
        binding!!.revealAnswer.hideView()
        binding!!.snackBar.hideView()
        binding!!.answerViewTwo.hideView()


        binding!!.getJokes.setOnClickListener {
            viewModel.getProgrammingJokes()
            binding!!.revealAnswer.hideView()
            binding!!.answerViewTwo.hideView()
            initObservers()
        }

        binding!!.revealAnswer.setOnClickListener {
            binding!!.answerViewTwo.showView()
        }

    }

    private fun initObservers(){

        viewModel.jokeState.observe(this, { resource ->

            when (resource){

                is Resource.Success -> {

                    binding?.let { binding ->

                        binding.title.text = "Programming Jokes"
                        binding.answerView.showView()
                        binding.answerCardView.showView()
                        binding.getJokes.showView()
                        binding.progressBar.hideView()

                        when (resource.data?.type) {
                            "single" -> {
                                binding.answerViewTwo.hideView()
                                binding.revealAnswer.hideView()
                                binding.snackBar.hideView()
                                binding.answerView.text = resource.data.joke
                            }

                            "twopart" -> {
                                binding.answerView.text = resource.data.setup
                                binding.answerViewTwo.text = resource.data.delivery
                                binding.answerView.showView()
                                binding.revealAnswer.showView()

                            }
                        }
                    }
                }

                is Resource.Error -> {
                    binding?.let { binding ->
                        binding.progressBar.hideView()
                        binding.answerCardView.hideView()
                        binding.revealAnswer.hideView()
                        binding.title.text = "Error"
                        binding.revealAnswer.hideView()
                        binding.answerViewTwo.hideView()
                        binding.answerView.hideView()
                        binding.getJokes.showView()
                    }
                    showSnackBar(binding?.snackBar!!, "An error occurred")
                }

                is Resource.Loading -> {

                    binding?.progressBar?.showView()

                    binding?.let { binding ->

                        binding.title.text = "Loading"
                        binding.answerViewTwo.hideView()
                        binding.snackBar.hideView()
                        binding.answerView.hideView()
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}