package com.ifs21023.lostandfound.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ifs21023.lostandfound.data.local.entity.DelcomLostFoundEntity

@Database(entities = [DelcomLostFoundEntity::class], version = 2, exportSchema = false)
abstract class DelcomLostFoundDatabase : RoomDatabase() {
    abstract fun delcomLostFoundDao(): IDelcomLostFoundDao

    companion object {
        private const val Database_NAME = "DelcomLostFound.db"

        @Volatile
        private var INSTANCE: DelcomLostFoundDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): DelcomLostFoundDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    DelcomLostFoundDatabase::class.java,
                    Database_NAME
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { INSTANCE = it }
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Contoh migrasi: menambahkan kolom baru
                // database.execSQL("ALTER TABLE DelcomLostFoundEntity ADD COLUMN new_column INTEGER NOT NULL DEFAULT 0")
                // Tambahkan pernyataan SQL yang diperlukan untuk mengubah skema database
            }
        }
    }
}
