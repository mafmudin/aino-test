package test.ainosi.aplikasiberita.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import test.ainosi.aplikasiberita.CoroutineTestUtil.Companion.toDeferred
import test.ainosi.aplikasiberita.CoroutinesTestRule
import test.ainosi.aplikasiberita.DummyRetValueTest
import test.ainosi.aplikasiberita.data.FakeRepositoryImpl
import test.ainosi.aplikasiberita.data.Resource
import test.ainosi.aplikasiberita.model.newslist.NewsListResponse
import test.ainosi.aplikasiberita.model.searchnews.SearchNewsResponse

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {
    private lateinit var viewModel : NewsViewModel

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val coroutinesTestRule: CoroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var fakeRepositoryImpl: FakeRepositoryImpl

    @Before
    fun init(){
        viewModel = NewsViewModel(fakeRepositoryImpl)
    }

    @Test
    fun `test news from remote`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val observer = mock<Observer<Resource<NewsListResponse>>>()
        val dummyData = Resource.success(DummyRetValueTest.newsList<NewsViewModel>())

        dummyData.data?.responseStatus = "1"
        `when`(fakeRepositoryImpl.getDailyNews(DummyRetValueTest.day)).thenReturn(dummyData.data!!.toDeferred())

        viewModel.getNewsTry(DummyRetValueTest.day)
        val result = viewModel.result.value

        verify(fakeRepositoryImpl).getDailyNews(DummyRetValueTest.day).toDeferred()
        Assert.assertEquals(dummyData, result)

        viewModel.result.observeForever(observer)
        verify(observer).onChanged(dummyData)
    }

    @Test
    fun `test search page 0`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val observer = mock<Observer<Resource<SearchNewsResponse>>>()
        val dummyData = Resource.success(DummyRetValueTest.search<NewsViewModel>())

        dummyData.data?.responseStatus = "1"
        `when`(fakeRepositoryImpl.searchNews(DummyRetValueTest.query, DummyRetValueTest.page))
            .thenReturn(dummyData.data!!.toDeferred())

        viewModel.searchNews(DummyRetValueTest.query, DummyRetValueTest.page)
        val result = viewModel.resultSearch.value

        verify(fakeRepositoryImpl).searchNews(DummyRetValueTest.query, DummyRetValueTest.page).toDeferred()
        Assert.assertEquals(dummyData, result)

        viewModel.resultSearch.observeForever(observer)
        verify(observer).onChanged(dummyData)
    }
}