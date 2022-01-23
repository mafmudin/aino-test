package test.ainosi.aplikasiberita.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import test.ainosi.aplikasiberita.model.newslist.News

@Dao
interface NewsDao {
    @Insert(onConflict = REPLACE)
    fun insertNews(news: List<News>)

    @Query("select * from news")
    fun getLocalNews(): LiveData<MutableList<News>>
}