package test.ainosi.aplikasiberita.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
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

        viewModel.internetCheck(requireContext())
        viewModel.getNewsTry("1")

        observeData()
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
        viewModel.result.observe(viewLifecycleOwner, {
            when(it.status){
                EnumStatus.SUCCESS->{
                    binding.rlNews.isRefreshing = false
                    progressSvg.dissmis()
                    newsAdapter.updateItem(it.data!!)
                }
                EnumStatus.ERROR->{
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    binding.rlNews.isRefreshing = false
                    progressSvg.dissmis()
                }
                EnumStatus.LOADING->{
                    progressSvg.show()
                }
            }
        })
    }
}