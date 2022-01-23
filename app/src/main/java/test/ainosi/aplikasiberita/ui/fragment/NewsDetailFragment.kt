package test.ainosi.aplikasiberita.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import test.ainosi.aplikasiberita.R
import test.ainosi.aplikasiberita.base.BaseFragment
import test.ainosi.aplikasiberita.databinding.FragmentNewsDetailBinding
import test.ainosi.aplikasiberita.viewmodel.NewsViewModel

@AndroidEntryPoint
class NewsDetailFragment(viewModel: NewsViewModel? = null)
    : BaseFragment<FragmentNewsDetailBinding, NewsViewModel>(
    R.layout.fragment_news_detail,
    NewsViewModel::class.java,
    viewModel
    ){
    private val args: NewsDetailFragmentArgs by navArgs()
    override fun getViewBinding() = FragmentNewsDetailBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.loadUrl(args.newsUrl)
    }
}