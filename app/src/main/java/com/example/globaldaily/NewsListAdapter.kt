package com.example.globaldaily

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener: Home): RecyclerView.Adapter<NewsViewHolder>() {
    val item:ArrayList<News> = ArrayList()
    var i = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder = NewsViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentView = item[position]
        holder.titleView.text = currentView.title
        holder.authorView.text = currentView.publishedAt.substring(0,10)
        Glide.with(holder.itemView.context).load(currentView.urlToImage).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return item.size
    }
    fun update(updatedNews: ArrayList<News>){
        item.clear()
        item.addAll(updatedNews)
        notifyDataSetChanged()
    }

    fun updatePop(position:Int) {
        item.removeAt(position)
        notifyItemRemoved(position)
    }

}
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val titleView: TextView = itemView.findViewById(R.id.textView2)
    val imageView: ImageView = itemView.findViewById(R.id.imageView)
    val authorView:TextView = itemView.findViewById(R.id.textView)
}

