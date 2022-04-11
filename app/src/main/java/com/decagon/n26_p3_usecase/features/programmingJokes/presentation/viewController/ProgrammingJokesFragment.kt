package com.decagon.n26_p3_usecase.features.programmingJokes.presentation.viewController

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.decagon.n26_p3_usecase.commons.ui.*
import com.decagon.n26_p3_usecase.commons.utils.*
import com.decagon.n26_p3_usecase.core.baseClasses.BaseFragment
import com.decagon.n26_p3_usecase.core.presentation.MainActivity
import com.decagon.n26_p3_usecase.databinding.FragmentProgrammingJokesBinding
import com.decagon.n26_p3_usecase.features.programmingJokes.presentation.viewModel.JokeViewModel
import com.decagon.n26_p3_usecase.features.programmingJokes.utils.JokesConstants
import com.decagon.n26_p3_usecase.features.wallpaper.utils.WallPaperConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProgrammingJokesFragment : BaseFragment() {

    private var binding : FragmentProgrammingJokesBinding? = null
    private  val viewModel : JokeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProgrammingJokesBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = "Jokes"

        binding!!.progressBar.hideView()
        binding!!.revealAnswer.hideView()
        binding!!.snackBar.hideView()
        binding!!.answerViewTwo.hideView()

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

        viewModel.jokeState.observe(viewLifecycleOwner) { resource ->

            when (resource) {

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
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


    private fun verifyExit() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    GenericDialogueBuilder.showDialogue(
                        requireContext(),
                        R.drawable.ic_dialog_alert,
                        "Exit App",
                        "Are you sure you want to exit?",
                        R.drawable.ic_dialog_alert,
                        "Yes",
                        "No",
                        {exitPositiveButtonClicked()},
                        {exitNegativeButtonClicked()}
                    )
                }

                fun exitPositiveButtonClicked() {
                    requireActivity().finish()
                }

                fun exitNegativeButtonClicked() {
                    // do nothing
                }
            })
        }
    }