package test.ainosi.aplikasiberita.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import test.ainosi.aplikasiberita.R
import test.ainosi.aplikasiberita.base.BaseFragment
import test.ainosi.aplikasiberita.data.EnumStatus
import test.ainosi.aplikasiberita.databinding.FragmentNewsListBinding
import test.ainosi.aplikasiberita.ui.adapter.ArticleAdapter
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
    lateinit var articleAdapter: ArticleAdapter
    var page = 0
    var isloadMore = false

    override fun getViewBinding() = FragmentNewsListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()

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
        articleAdapter = ArticleAdapter()

        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = GridLayoutManager(context, gridCount)
        }

        binding.rvArticle.apply {
            adapter = articleAdapter
            layoutManager = GridLayoutManager(context, gridCount)
        }

    }

    private fun initListener(){
        newsAdapter.onClick = {news ->
            findNavController().navigate(NewsFragmentDirections.actionNewsDetail(
                news.url!!
            ))
        }

        articleAdapter.onClick = {news ->
            findNavController().navigate(NewsFragmentDirections.actionNewsDetail(
                news.webUrl!!
            ))
        }

        binding.rlNews.setOnRefreshListener {
            isloadMore = false
            page = 0
            binding.etSearch.setText("")
            viewModel.internetCheck(requireContext())
            viewModel.getNewsTry("1")
        }

        var job: Job? = null
        binding.etSearch.addTextChangedListener {
            job?.cancel()

            job = lifecycleScope.launch {
                delay(1000)
                it.let {
                    if (it.toString().isNotEmpty()){
                        isloadMore = false
                        page = 0
                        viewModel.searchNews(it.toString(), page)
                        binding.rvNews.visibility = View.GONE
                        binding.rvArticle.visibility = View.VISIBLE
                    }else{
                        binding.rvNews.visibility = View.VISIBLE
                        binding.rvArticle.visibility = View.GONE
                    }
                }
            }
        }

        binding.rvArticle.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val gridLayoutManager = binding.rvArticle.layoutManager as GridLayoutManager
                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == articleAdapter.itemCount - 1){
                    page++
                    isloadMore = true
                    viewModel.searchNews(binding.etSearch.text.toString(), page)
                }
            }
        })
    }

    private fun observeData(){
        viewModel.result.observe(viewLifecycleOwner, {
            when(it.status){
                EnumStatus.SUCCESS->{
                    binding.rlNews.isRefreshing = false
                    progressSvg.dissmis()
                    newsAdapter.updateItem(it.data!!.news)
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

        viewModel.resultSearch.observe(viewLifecycleOwner, {
            when(it.status){
                EnumStatus.SUCCESS->{
                    progressSvg.dissmis()
                    if (isloadMore){
                        articleAdapter.addAllItem(it.data!!.response!!.docs)
                    }else{
                        articleAdapter.updateItem(it.data!!.response!!.docs)
                    }
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

    override fun onPause() {
        super.onPause()
        binding.etSearch.setText("")
        page = 0
    }
}