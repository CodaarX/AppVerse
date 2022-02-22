package com.decagon.n26_p3_usecase.features.locationTracker.workManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkManager(context: Context, params: WorkerParameters): Worker(context, params) {

    override fun doWork(): Result {
        return try {
            // Do the work here

            Result.success()
        } catch (e: Exception) { Result.failure() }
    }



    override fun onStopped() {
        super.onStopped()
    }
}
