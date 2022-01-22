package test.ainosi.aplikasiberita.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import test.ainosi.aplikasiberita.utility.Constant

abstract class AppDatabase : RoomDatabase(){
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