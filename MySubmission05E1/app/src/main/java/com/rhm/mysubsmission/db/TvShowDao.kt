package com.rhm.mysubsmission.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TvShowDao {

    @Insert
    suspend fun addTvShow(tvShow: TvShowTable)

    @Query("SELECT * FROM TvShowTable ORDER BY id ASC")
    suspend fun getAllTvShow(): List<TvShowTable>

    @Query("SELECT * FROM TvShowTable WHERE title = 'qq'")
    suspend fun getSpecialTvShow(): List<TvShowTable>

    @Query("SELECT COUNT(title) FROM TvShowTable WHERE title = :search")
    suspend fun getCountTvShow(search: String): Int

    @Query("DElETE FROM TvShowTable WHERE title = :search")
    suspend fun getDeleteTvShow(search: String): Int

    @Insert
    suspend fun addMultipleTvShow(vararg tvShow: TvShowTable)

    @Delete
    suspend fun deleteTvShow(tvShow: TvShowTable)
}