package com.example.assaignment_currency_converter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.assaignment_currency_converter.database.CurrencyDatabase
import com.example.assaignment_currency_converter.database.CurrencyRate
import com.example.assaignment_currency_converter.database.CurrencyRateDao
import kotlinx.coroutines.runBlocking
import org.junit.*

class CurrencyRateDaoTest {

    @get:Rule
    val instantTaskExecutorRule  = InstantTaskExecutorRule()

    lateinit var database : CurrencyDatabase
    lateinit var currencyRateDao : CurrencyRateDao

    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CurrencyDatabase::class.java
        ).allowMainThreadQueries().build()

        currencyRateDao = database.currencyRateDao()
    }

    @Test
    fun insertCurrencyDetails() = runBlocking{
        val currencyRate = CurrencyRate(0,"JPY", "141.79")
        currencyRateDao.addCurrency(currencyRate)

        val result = currencyRateDao.getCurrencyAll().getOrAwaitValue()

        Assert.assertEquals(1,result.size)
        Assert.assertEquals("JPY",result[0].currencyName)
    }

    @Test
    fun deleteTest() = runBlocking{
        val currencyRate = CurrencyRate(0,"JPY", "141.79")
        currencyRateDao.addCurrency(currencyRate)

        currencyRateDao.deleteAll()

        val result = currencyRateDao.getCurrencyAll().getOrAwaitValue()

        Assert.assertEquals(0,result.size)
    }

    @After
    fun tearDown(){
        database.close()
    }
}