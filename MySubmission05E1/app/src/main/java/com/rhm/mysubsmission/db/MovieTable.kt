package com.rhm.mysubsmission.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class MovieTable(
    val title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}