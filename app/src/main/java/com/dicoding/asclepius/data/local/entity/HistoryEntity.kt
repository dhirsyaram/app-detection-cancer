package com.dicoding.asclepius.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "image")
    var image: String = "",

    @ColumnInfo(name = "label")
    var label: String = "",

    @ColumnInfo(name = "score")
    var score: String = ""
)
