package com.example.marketpetproject.ui.utility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.marketpetproject.R
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

//Внутренний RecyclerView, через него происходит прокрутка изоображений. Экран "Основной", загрузка изоображения происходит с помощью Glide

class ImageAdapter(val listImages: List<String>?) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageProduct: ImageView

        init {
            imageProduct = itemView.findViewById(R.id.imageItemProduct)
            val shimmer = Shimmer.AlphaHighlightBuilder()
                //.setDuration(1800)
                .setBaseAlpha(0.7f)
                .setHighlightAlpha(0.6f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()
            val shimmerDrawable = ShimmerDrawable()
            shimmerDrawable.setShimmer(shimmer)
        }

        fun getShimmer(): ShimmerDrawable {
            val shimmer = Shimmer.AlphaHighlightBuilder()
                //.setDuration(1800)
                .setBaseAlpha(0.7f)
                .setHighlightAlpha(0.6f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()
            val shimmerDrawable = ShimmerDrawable()
            shimmerDrawable.setShimmer(shimmer)
            return shimmerDrawable
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.imageholder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        Glide.with(holder.imageProduct.context).load(listImages!![position])
            .placeholder(holder.getShimmer()).diskCacheStrategy(
            DiskCacheStrategy.ALL
        ).into(holder.imageProduct)
    }

    override fun getItemCount(): Int {
        return listImages!!.size
    }
}