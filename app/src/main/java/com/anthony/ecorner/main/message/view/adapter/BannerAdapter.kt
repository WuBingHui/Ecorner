package com.anthony.ecorner.main.message.view.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.anthony.ecorner.R
import com.bumptech.glide.Glide
import java.util.ArrayList


class BannerAdapter(private var context: Context) : RecyclerView.Adapter<BannerAdapter.CardViewHolder>() {

    private var imgList = mutableListOf<Drawable?>()

    fun setImages(imageList: MutableList<Drawable?>){
        this.imgList = imageList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        return CardViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.view_banner_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind()
        Glide.with(context)
            .load(imgList[position])
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.bannerImg)
    }

    override fun getItemCount(): Int {
        return imgList.size
    }


    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       lateinit var  bannerImg: ImageView
        fun bind(){
            bannerImg = itemView.findViewById(R.id.bannerImg)
        }
    }

}

