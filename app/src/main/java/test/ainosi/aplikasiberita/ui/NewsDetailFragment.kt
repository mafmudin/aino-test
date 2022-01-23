package test.ainosi.aplikasiberita.ui

import dagger.hilt.android.AndroidEntryPoint
import test.ainosi.aplikasiberita.R
import test.ainosi.aplikasiberita.base.BaseFragment
import test.ainosi.aplikasiberita.databinding.FragmentNewsDetailBinding
import test.ainosi.aplikasiberita.databinding.FragmentNewsListBinding
import test.ainosi.aplikasiberita.viewmodel.NewsViewModel

@AndroidEntryPoint
class NewsDetailFragment(viewModel: NewsViewModel? = null)
    : BaseFragment<FragmentNewsDetailBinding, NewsViewModel>(
    R.layout.fragment_news_list,
    NewsViewModel::class.java,
    viewModel
    ){
    override fun getViewBinding() = FragmentNewsDetailBinding.inflate(layoutInflater)
}