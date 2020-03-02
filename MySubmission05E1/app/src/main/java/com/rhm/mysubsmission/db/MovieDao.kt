package com.rhm.mysubsmission.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert
    suspend fun addMovie(movie: MovieTable)

    @Query("SELECT * FROM MovieTable ORDER BY id ASC")
    suspend fun getAllMovie(): List<MovieTable>

    @Query("SELECT * FROM MovieTable ORDER BY id ASC")
    fun getFavMovie(): List<MovieTable>

    @Query("SELECT * FROM MovieTable WHERE title = 'qq'")
    suspend fun getSpecialMovie(): List<MovieTable>

    @Query("SELECT COUNT(title) FROM MovieTable WHERE title = :search")
    suspend fun getCountMovie(search: String): Int

    @Query("DELETE FROM MovieTable WHERE title = :search")
    suspend fun getDeleteMovie(search: String): Int

    @Insert
    suspend fun addMultipleMovies(vararg movie: MovieTable)

    @Delete
    suspend fun deleteMovie(movie: MovieTable)
}