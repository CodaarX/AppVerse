package com.michael.appverse.features.wallpaper.workManager

class WorkSetUp {

//    private val manager = WorkManager.getInstance(application)

//    // Our work constraints
//    private val constraints = Constraints.Builder()
//        .setRequiredNetworkType(NetworkType.CONNECTED)
//        .setRequiresBatteryNotLow(true)
//        .build()
//
//    // Define OneTime work
//    private val oneTimeWorker = OneTimeWorkRequest.Builder(WorkManager::class.java)
//        .setConstraints(constraints)
//        .build()
//
//    // Define Periodic work
//    private val periodicWork = PeriodicWorkRequest.Builder(WorkManager::class.java, 15, TimeUnit.MINUTES)
//        .setConstraints(constraints)
//        .build()

//    // Work using coroutines
//    private val coroutinesWork = PeriodicWorkRequestBuilder<CoroutineWork>(15, TimeUnit.MINUTES)
//        .setInputData(getData())
//        .build()


//    // Create our data
//    private fun getData() = Data.Builder().putInt("USER_ID", (9999..99999).random()).build()
//
//    fun startWork(){
//        manager.enqueue(listOf(oneTimeWorker, periodicWork))
//    }
}