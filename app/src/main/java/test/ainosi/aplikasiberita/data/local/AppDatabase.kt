package test.ainosi.aplikasiberita.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import test.ainosi.aplikasiberita.data.local.converter.MediaConverter
import test.ainosi.aplikasiberita.data.local.converter.MediaMetadataConverter
import test.ainosi.aplikasiberita.data.local.converter.StringListConverter
import test.ainosi.aplikasiberita.model.newslist.News
import test.ainosi.aplikasiberita.utility.Constant

@Database(version = 2, entities = [News::class])

@TypeConverters(MediaConverter::class, MediaMetadataConverter::class, StringListConverter::class)

abstract class AppDatabase : RoomDatabase(){
    abstract fun newsDao(): NewsDao
    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val dbName = Constant.DATABASE_NAME

        fun getDataBase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, dbName)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}