package com.example.marketpetproject.ui.utility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.marketpetproject.R
import com.example.marketpetproject.model.GoodsCart
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.Shimmer.AlphaHighlightBuilder
import com.facebook.shimmer.ShimmerDrawable

// Экран "Заказы". Адаптер для RecyclerView загрузка изоображения происходит с помощью Glide

class ProfileAdapter(val listProfile: List<GoodsCart>) :
    RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageProfile: ImageView
        val price: TextView

        init {
            imageProfile = itemView.findViewById(R.id.imageProfile)
            price = itemView.findViewById(R.id.price)
            val shimmer = AlphaHighlightBuilder()
                .setDuration(1800)
                .setBaseAlpha(0.7f)
                .setHighlightAlpha(0.6f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()
            val shimmerDrawable = ShimmerDrawable()
            shimmerDrawable.setShimmer(shimmer)
        }

        fun getShimmer(): ShimmerDrawable {
            val shimmer = AlphaHighlightBuilder()
                .setDuration(1800)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.imageProfile.context).load(listProfile.get(position).images)
            .placeholder(holder.getShimmer()).diskCacheStrategy(
            DiskCacheStrategy.ALL
        ).into(holder.imageProfile)
        holder.price.text = listProfile[position].price.toString()
    }

    override fun getItemCount(): Int {
        return listProfile.size
    }
}