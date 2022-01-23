package test.ainosi.aplikasiberita.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import test.ainosi.aplikasiberita.R
import test.ainosi.aplikasiberita.databinding.RvItemNewsBinding
import test.ainosi.aplikasiberita.model.searchnews.Doc
import test.ainosi.aplikasiberita.utility.Utility

class ArticleAdapter: RecyclerView.Adapter<ArticleAdapter.NewsViewHolder>() {

    var items : MutableList<Doc> = arrayListOf()

    var onClick: ((Doc) -> Unit)? = null

    fun addItem(item: Doc){
        items.add(item)
    }

    fun addAllItem(item: MutableList<Doc>){
        items.addAll(item)
        notifyDataSetChanged()
    }

    fun resetItem(){
        items.clear()
    }

    fun updateItem(news: MutableList<Doc>){
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
        fun bind(data: Doc, position: Int){
            if (position % 2 == 0){
                binding.llRoot.setBackgroundResource(R.color.gray)
            }else{
                binding.llRoot.setBackgroundResource(R.color.white)
            }
            binding.tvTitle.text = data.abstract
            binding.tvPublishDate.text = Utility.reformatDate(data.pubDate!!, "yyyy-MM-dd", "dd MMM yyyy")
            binding.llRoot.setOnClickListener {
                onClick?.invoke(
                    data
                )
            }
            binding.ivNews.visibility = View.GONE
        }
    }


}