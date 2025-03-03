package com.example.marketpetproject.ui.utility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.marketpetproject.R
import com.example.marketpetproject.model.GoodsCart
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

//Адаптер для RecyclerView. Экран "Корзина", загрузка изоображения происходит с помощью Glide
class CartAdapter(val cartList:List<GoodsCart>, val click:onItemClick): RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    inner class  ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView
        val price: TextView
        val title: TextView
        val priceWithcout: TextView
        val buttonDelete:ImageButton

        init {
            buttonDelete = itemView.findViewById(R.id.deleteCart)
            image = itemView.findViewById(R.id.imageCart)
            price = itemView.findViewById(R.id.cartPrice)
            title = itemView.findViewById(R.id.titleCart)
            priceWithcout = itemView.findViewById(R.id.cartPriceWithoutDiscount)
            buttonDelete.setOnClickListener {
                if (click != null) {
                    val pos = getAdapterPosition()
                    if (pos != RecyclerView.NO_POSITION) {
                        click.onItemClick(pos)
                    }
                }
            }
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.priceWithcout.text = String.format("%.2f",cartList[position].price!! * (100+cartList[position].discountPercentage!!)/100)
        holder.price.text = cartList[position].price.toString()
        holder.title.text = cartList[position].title
        Glide.with(holder.image.context).load(cartList[position].images).placeholder(holder.getShimmer()).diskCacheStrategy(
            DiskCacheStrategy.ALL).into(holder.image)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}