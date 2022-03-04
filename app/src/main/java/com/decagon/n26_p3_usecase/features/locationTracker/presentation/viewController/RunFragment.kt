package com.decagon.n26_p3_usecase.features.locationTracker.presentation.viewController

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.utils.GenericDialogueBuilder
import com.decagon.n26_p3_usecase.commons.ui.toast
import com.decagon.n26_p3_usecase.core.baseClasses.BaseFragment
import com.decagon.n26_p3_usecase.databinding.FragmentRunBinding
import com.decagon.n26_p3_usecase.features.locationTracker.data.mediator.SortType
import com.decagon.n26_p3_usecase.features.locationTracker.data.mediator.viewModel.RunMediatorViewModel
import com.decagon.n26_p3_usecase.features.locationTracker.presentation.adapter.RunAdapter
import com.decagon.n26_p3_usecase.features.locationTracker.utils.TrackingUtils
import com.example.mike_utils.MikeUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class RunFragment : BaseFragment(), EasyPermissions.PermissionCallbacks {

    //private val viewModel: MainViewModel by viewModels()
    private val viewModel: RunMediatorViewModel by viewModels()
    lateinit var binding : FragmentRunBinding
    private val runRecyclerViewAdapter: RunAdapter by lazy {
        RunAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentRunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        (activity as MainActivity).supportActionBar?.title = "Run Options"
        setListeners()
        checkPermissions()
        setRecyclerView()
    }

    private fun setListeners(){
        binding.runFragmentNewRunsFab.setOnClickListener { findNavController().navigate(R.id.action_runFragment_to_trackcingFragment) }
        binding.settingsLayout.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        binding.sortLayout.setOnClickListener { inflateBottomSheet() }
        binding.deleteLayout.setOnClickListener {
            if(runRecyclerViewAdapter.itemCount > 0){
                toast( requireContext(),"deleted")
                confirmRemoval()
            } else {
                toast( requireContext(),"No data to delete")
            }
        }
    }

    private fun setRecyclerView(){
         binding.rvRuns.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = runRecyclerViewAdapter
        }
        binding.rvRuns.itemAnimator  = SlideInLeftAnimator().apply {
            addDuration = 1000
        }
        observeViewModel()
    }

    private fun checkPermissions(){
        if(TrackingUtils.hasLocationPermission(requireContext())){ return }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "Please grant location permission to use this feature",
                TrackingUtils.LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "Please grant location permission to use this feature",
                TrackingUtils.LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if(requestCode == TrackingUtils.LOCATION_PERMISSION_REQUEST_CODE){
            if(!TrackingUtils.hasLocationPermission(requireContext())){ checkPermissions() }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else { checkPermissions() }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun inflateBottomSheet(){

        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogStyle)

        val bottomSheetView: View = layoutInflater.inflate(
            R.layout.run_bottom_sheet,
            view?.findViewById(R.id.run_sort_bottom_sheet_root_layout) as LinearLayout?
        )

        val dateButton: RadioButton? = bottomSheetView.findViewById(R.id.sort_by_date_radio_button)
        val timeButton: RadioButton? = bottomSheetView.findViewById(R.id.sort_by_running_time)
        val distanceButton: RadioButton? = bottomSheetView.findViewById(R.id.sort_by_distance)
        val speedButton: RadioButton? = bottomSheetView.findViewById(R.id.sort_by_average_speed)
        val caloriesButton: RadioButton? = bottomSheetView.findViewById(R.id.sort_by_calories_burned)

        arrayOf(dateButton, timeButton, distanceButton, speedButton, caloriesButton).forEach {
            it?.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    when(it.id){
                        R.id.sort_by_date_radio_button -> {
                            sortedBy("Date") {  viewModel.sortRuns(SortType.DATE) }
                            dialog.dismiss()
                        }
                        R.id.sort_by_running_time -> {
                            sortedBy("Time") {   viewModel.sortRuns(SortType.RUNNING_TIME) }
                            dialog.dismiss()
                        }
                        R.id.sort_by_distance -> {
                            sortedBy("Distance") {   viewModel.sortRuns(SortType.DISTANCE) }
                            dialog.dismiss()
                        }
                        R.id.sort_by_average_speed -> {
                            sortedBy("Speed") {   viewModel.sortRuns(SortType.AVG_SPEED) }
                            dialog.dismiss()
                        }
                        R.id.sort_by_calories_burned -> {
                            sortedBy("Calories") {   viewModel.sortRuns(SortType.CALORIES_BURNED) }
                            dialog.dismiss()
                        }
                    }
                }
            }
        }

        dialog.setContentView(bottomSheetView)
        dialog.show()
    }

    private fun confirmRemoval() {
        GenericDialogueBuilder.showDialogue(requireContext(), R.style.Theme_AppCompat_Dialog_Alert,
            "Are you sure you want to delete all the todos?",
            "Delete All",
            R.drawable.ic_delete,
            "Delete All",
            "Cancel",
            { viewModel.deleteAllRuns() },
            { toast(requireContext(), "Cancelled") })
    }

    private fun sortedBy(sortBy: String, action: (() -> Unit)) {
        action.invoke()
        observeViewModel()
        toast(requireContext(), "Sorted by $sortBy")
    }

    private fun observeViewModel(){
        viewModel.runs.observe(viewLifecycleOwner) {

            runRecyclerViewAdapter.submitList(it)
        }
    }

}