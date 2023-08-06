package com.example.assaignment_currency_converter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.assaignment_currency_converter.api.RetrofitHelper
import com.example.assaignment_currency_converter.api.ServiceApi
import com.example.assaignment_currency_converter.database.CurrencyDatabase
import com.example.assaignment_currency_converter.repository.OpenExchangeRepository
import com.example.assaignment_currency_converter.viewModels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.*


class ViewModelTest {

    @get:Rule
    val instantTaskExecutorRule  = InstantTaskExecutorRule()


    lateinit var database : CurrencyDatabase
    lateinit var service : ServiceApi
    lateinit var repository : OpenExchangeRepository
    //private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setUp(){
       // Dispatchers.setMain(testDispatcher)
      /*  database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CurrencyDatabase::class.java
        ).allowMainThreadQueries().build()
*/
        database  = (ApplicationProvider.getApplicationContext() as CurrencyConverterApplication).database
        service = RetrofitHelper.getInstance().create(ServiceApi::class.java)

       // val database  = ( ApplicationProvider.getApplicationContext() as CurrencyConverterApplication).database

        repository = OpenExchangeRepository(service, database,  ApplicationProvider.getApplicationContext())
    }

    @Test
    fun testRates() = runBlocking{
        val test = MainViewModel(repository)
        test.getRates("21ec5fc817be452896336e840772e5f3")

        val result = database.currencyRateDao().getCurrencyAll().getOrAwaitValue()
        Assert.assertEquals(169,result.size)
    }

    @After
    fun tearDown(){
       // database.close()
    }
}