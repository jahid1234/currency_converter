package com.example.assaignment_currency_converter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Result::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyRateDao() : CurrencyRateDao

    companion object{
        @Volatile
        private var INSTANCE: CurrencyDatabase? = null

        fun getDatabase(context: Context): CurrencyDatabase {
            if (INSTANCE == null) {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context,
                        CurrencyDatabase::class.java,
                        "currencyDB")
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}