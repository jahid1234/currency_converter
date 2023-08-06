package com.example.assaignment_currency_converter

import com.example.assaignment_currency_converter.api.ServiceApi
import com.example.assaignment_currency_converter.models.OpenExchangePojo
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class RepositoryTest {

    @Mock
    lateinit var serviceApi: ServiceApi

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getCurrency_emptyList() = runBlocking{

    }
}