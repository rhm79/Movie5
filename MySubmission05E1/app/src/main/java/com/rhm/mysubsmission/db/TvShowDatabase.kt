package com.rhm.mysubsmission.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TvShowTable::class], version = 1
)

abstract class TvShowDatabase : RoomDatabase() {

    abstract fun getTvShowDao(): TvShowDao

    companion object {
        @Volatile
        private var INSTANCE: TvShowDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            TvShowDatabase::class.java,
            "TvShowFavorite"
        ).build()
    }
}