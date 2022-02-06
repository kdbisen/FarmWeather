package com.allometry.farmweather.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "fav_tbl")
data class Favorite(
   // val id: UUID,
    @NonNull
    @PrimaryKey
    @ColumnInfo
    val city: String,
    @ColumnInfo
    val country: String, )
