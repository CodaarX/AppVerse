package com.decagon.n26_p3_usecase.features.programmingJokes.presentation.viewController

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.decagon.n26_p3_usecase.commons.utils.*
import com.decagon.n26_p3_usecase.databinding.FragmentProgrammingJokesBinding
import com.decagon.n26_p3_usecase.features.programmingJokes.presentation.viewModel.JokeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProgrammingJokesFragment : Fragment() {

    private var binding : FragmentProgrammingJokesBinding? = null
    private  val viewModel : JokeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProgrammingJokesBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.progressBar.hideView()
        binding!!.revealAnswer.hideView()
        binding!!.snackBar.hideView()
        binding!!.answerViewTwo.hideView()


        NetworkLiveData.observeForever {

        }

    }


    override fun onResume() {
        super.onResume()

        binding!!.getJokes.setOnClickListener {


            if (NetworkLiveData.isNetworkAvailable()) viewModel.getProgrammingJokes() else toast(requireContext(), "No Network available.")

            binding!!.revealAnswer.hideView()
            binding!!.answerViewTwo.hideView()
            initObservers()
        }

        binding!!.revealAnswer.setOnClickListener {
            binding!!.answerViewTwo.showView()
            binding!!.revealAnswer.hideView()
        }
    }

    private fun initObservers(){

        viewModel.jokeState.observe(viewLifecycleOwner, { resource ->

            when (resource){

                is Resource.Success -> {

                    binding?.let { binding ->

                        binding.title.text = "Programming Jokes"
                        binding.answerView.showView()
                        binding.answerCardView.showView()
                        binding.getJokes.showView()
                        binding.progressBar.hideView()

                        when (resource.data.type) {
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
                    log(resource.message)
                    showSnackBar(binding?.snackBar!!, resource.message)
                }

                is Resource.Loading -> {

                    binding?.progressBar?.showView()

                    binding?.let { binding ->

                        binding.title.text = resource.message
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