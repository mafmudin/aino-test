package test.ainosi.aplikasiberita

import test.ainosi.aplikasiberita.model.newslist.NewsListResponse
import test.ainosi.aplikasiberita.model.searchnews.SearchNewsResponse

object DummyRetValueTest {

   val NEWS_LIST = "news_list.json"
   val SEARCH_RESULT = "search.json"


    inline fun <reified BASE> newsList(): NewsListResponse {
        return JsonToPojoConverter.convertJson<BASE, NewsListResponse>(NEWS_LIST)
    }

    inline fun <reified BASE> search(): SearchNewsResponse {
        return JsonToPojoConverter.convertJson<BASE, SearchNewsResponse>(SEARCH_RESULT)
    }

    val day = "1"
    val query = "covid"
    val page = 0
}