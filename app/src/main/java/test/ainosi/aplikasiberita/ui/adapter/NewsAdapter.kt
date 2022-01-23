package test.ainosi.aplikasiberita.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import test.ainosi.aplikasiberita.R
import test.ainosi.aplikasiberita.databinding.RvItemNewsBinding
import test.ainosi.aplikasiberita.model.newslist.News
import test.ainosi.aplikasiberita.utility.Utility

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var items : MutableList<News> = arrayListOf()

    var onClick: ((News) -> Unit)? = null

    fun addItem(item: News){
        items.add(item)
    }

    fun resetItem(){
        items.clear()
    }

    fun updateItem(news: MutableList<News>){
        items = news
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = RvItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) = holder.bind(items[position], position)

    inner class NewsViewHolder(private val binding: RvItemNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: News, position: Int){
            if (position % 2 == 0){
                binding.llRoot.setBackgroundResource(R.color.gray)
            }else{
                binding.llRoot.setBackgroundResource(R.color.white)
            }
            binding.tvTitle.text = data.title
            binding.tvPublishDate.text = Utility.reformatDate(data.publishedDate!!, "yyyy-MM-dd", "dd MMM yyyy")
            binding.llRoot.setOnClickListener {
                onClick?.invoke(
                    data
                )
            }
            if (data.media!!.isNotEmpty()){
                Glide
                    .with(binding.ivNews.context)
                    .load(data.media!![0].mediaMetadata!![2].url)
                    .circleCrop()
                    .into(binding.ivNews)
            }
        }
    }


}