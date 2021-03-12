package com.example.mymovieskotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymovieskotlin.pojo.FavouriteMovieInfo
import com.example.mymovieskotlin.pojo.MovieInfo

@Database(
    entities = [MovieInfo::class, FavouriteMovieInfo::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private const val DB_NAME = "main.db"
        private var db: AppDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun movieInfoDao(): MovieInfoDao
}