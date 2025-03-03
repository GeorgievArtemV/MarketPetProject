package com.example.marketpetproject.ui.utility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.marketpetproject.R
import com.example.marketpetproject.model.Goods
import me.relex.circleindicator.CircleIndicator3

// Экран "Основной". Адаптер для RecyclerView с внутренним ViewPager для возможности пролистывать картинки полученные из API

class CoreAdapter(val goodsList: List<Goods>, val click: onItemClick) :
    RecyclerView.Adapter<CoreAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewPager: ViewPager2
        val indicator: CircleIndicator3
        val price: TextView
        val title: TextView

        init {
            viewPager = itemView.findViewById(R.id.imageItemPager)
            indicator = itemView.findViewById(R.id.circleIndicatorImage)
            price = itemView.findViewById(R.id.priceItemProduct)
            title = itemView.findViewById(R.id.textTitleProduct)

            itemView.setOnClickListener {
                if (click != null) {
                    val pos = getAdapterPosition()
                    if (pos != RecyclerView.NO_POSITION) {
                        click.onItemClick(pos)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.price.text = goodsList[position].price.toString()
        holder.title.text = goodsList[position].title
        holder.viewPager.adapter = ImageAdapter(goodsList[position].images)
        holder.indicator.setViewPager(holder.viewPager)
    }

    override fun getItemCount(): Int {
        return goodsList.size
    }
}