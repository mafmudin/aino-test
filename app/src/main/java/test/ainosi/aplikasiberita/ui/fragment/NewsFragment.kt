package test.ainosi.aplikasiberita.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import test.ainosi.aplikasiberita.R
import test.ainosi.aplikasiberita.base.BaseFragment
import test.ainosi.aplikasiberita.data.EnumStatus
import test.ainosi.aplikasiberita.databinding.FragmentNewsListBinding
import test.ainosi.aplikasiberita.ui.adapter.NewsAdapter
import test.ainosi.aplikasiberita.viewmodel.NewsViewModel
import timber.log.Timber
import udinsi.dev.progress_svg.ProgressSvg

@AndroidEntryPoint
class NewsFragment(viewModel: NewsViewModel? = null)
    : BaseFragment<FragmentNewsListBinding, NewsViewModel>(
    R.layout.fragment_news_list,
    NewsViewModel::class.java,
    viewModel
    ){
    private lateinit var progressSvg: ProgressSvg
    lateinit var newsAdapter: NewsAdapter

    override fun getViewBinding() = FragmentNewsListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView(){
        progressSvg = ProgressSvg(context)
        progressSvg.setMessage(getString(R.string.sedang_memuat))

        val gridCount = resources.getInteger(R.integer.grid_column)

        Timber.e("GRID %s", gridCount)

        newsAdapter = NewsAdapter()

        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = GridLayoutManager(context, gridCount)
        }

        newsAdapter.onClick = {news ->
            findNavController().navigate(NewsFragmentDirections.actionNewsDetail(
                news.url!!
            ))
        }

        binding.rlNews.setOnRefreshListener {
            viewModel.internetCheck(requireContext())
            viewModel.getNewsTry("1")
        }
    }

    private fun observeData(){
        viewModel.getNewsTry("1").observe(viewLifecycleOwner, {
            when(it.status){
                EnumStatus.SUCCESS->{
                    progressSvg.dissmis()
                    newsAdapter.items = it.data!!
                }
                EnumStatus.ERROR->{
                    progressSvg.dissmis()
                }
                EnumStatus.LOADING->{
                    progressSvg.show()
                }
            }
        })
    }
}