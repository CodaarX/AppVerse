package com.decagon.n26_p3_usecase.features.locateMe.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class CoroutineWorker(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
//        val dao = AppDatabase.getDatabase(applicationContext).dao()
        return withContext(Dispatchers.IO){
//            try {
//                val user = async { Data.getCoroutineUser(inputData.getInt("USER_ID", 0)) }
//                dao.addCoroutineUser(user.await())
                Result.success()
//            }catch (e: Exception){
//                Result.failure()
//            }
        }
    }
}