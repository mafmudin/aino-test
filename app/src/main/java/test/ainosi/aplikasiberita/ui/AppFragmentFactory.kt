package test.ainosi.aplikasiberita.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import test.ainosi.aplikasiberita.repository.NewsRepository
import test.ainosi.aplikasiberita.ui.fragment.NewsDetailFragment
import test.ainosi.aplikasiberita.ui.fragment.NewsFragment
import test.ainosi.aplikasiberita.viewmodel.NewsViewModel
import javax.inject.Inject

class AppFragmentFactory @Inject constructor(
    private val newsRepository: NewsRepository
) : FragmentFactory(){
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            NewsFragment::class.java.name -> NewsFragment(NewsViewModel(newsRepository))
            NewsDetailFragment::class.java.name -> NewsDetailFragment(NewsViewModel(newsRepository))
          else -> super.instantiate(classLoader, className)
        }
    }
}