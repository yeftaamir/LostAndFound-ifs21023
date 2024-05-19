package com.ifs21023.lostandfound.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize //Menandai kelas ini untuk diimplementasikan oleh interface
@Entity(tableName = "delcom_lostfounds")

class DelcomLostFoundEntity (
    @PrimaryKey(autoGenerate = false) // jika id salah maka id tidak akan di-generate secara otomatis oleh Room.
    val id: Int,
    //Menandai kolom dalam tabel database dan menghubungkannya dengan properti di kelas.
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "is_completed")
    var isCompleted: Int,
    @ColumnInfo(name = "cover")
    var cover: String?,
    @ColumnInfo(name = "created_at")
    var createdAt: String,
    @ColumnInfo(name = "updated_at")
    var updatedAt: String,
    @ColumnInfo(name = "status")
    var status: String,
//    @ColumnInfo(name = "userId")
//    var userId: Int
    @ColumnInfo(name = "is_me")
    var isMe: Int,
) : Parcelable