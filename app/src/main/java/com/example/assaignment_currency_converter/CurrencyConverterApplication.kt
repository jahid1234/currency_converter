package com.example.assaignment_currency_converter

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import com.example.assaignment_currency_converter.api.RetrofitHelper
import com.example.assaignment_currency_converter.api.ServiceApi
import com.example.assaignment_currency_converter.database.CurrencyDatabase
import com.example.assaignment_currency_converter.repository.OpenExchangeRepository
import com.example.assaignment_currency_converter.worker.CurrencyDetailsDownloadWorker
import java.util.concurrent.TimeUnit

class CurrencyConverterApplication : Application() {

    lateinit var applicationRepository: OpenExchangeRepository
    lateinit var database: CurrencyDatabase

    override fun onCreate() {
        super.onCreate()
        initialize()
        setWorker()
    }

    private fun setWorker() {
        val networkConstraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workerRequest = PeriodicWorkRequest.Builder(CurrencyDetailsDownloadWorker::class.java,30,TimeUnit.MINUTES).setConstraints(networkConstraints).build()
        WorkManager.getInstance(this).enqueue(workerRequest)


    }

    private fun initialize() {
        val service = RetrofitHelper.getInstance().create(ServiceApi::class.java)
        database = CurrencyDatabase.getDatabase(applicationContext)
        applicationRepository = OpenExchangeRepository(service, database, applicationContext)
    }
}