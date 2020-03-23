package com.anthony.ecorner.main.home.view.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.home.reponse.Product
import com.anthony.ecorner.extension.dp2px
import com.anthony.ecorner.extension.getWindowWidth
import com.anthony.ecorner.main.home.view.SearchActivity
import com.anthony.ecorner.main.home.view.SearchDiffCallback
import com.anthony.ecorner.main.home.view.TypeDiffCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class SearchAdapter() :
    RecyclerView.Adapter<SearchAdapter.CardViewHolder>() {

    private var data = mutableListOf<Product>()

    private var setItemClick: SetItemClick? = null

    private var isScrolling = false

    fun isScrolling(isScrolling: Boolean) {

        this.isScrolling = isScrolling

    }

    fun setData(data: List<Product>) {

        val diffCallback = SearchDiffCallback(this.data, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.data.clear()
        this.data.addAll(data)

        diffResult.dispatchUpdatesTo(this)

    }


    interface SetItemClick {

        fun onClick(id: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        val view = CardViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_class_big_item,
                parent,
                false
            )
        )

        return view
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        if (isScrolling && !data[position].isLoaded ) {
            Glide.with(holder.itemView.context).pauseRequests()
        } else {
            Glide.with(holder.itemView.context).resumeRequests()
        }

        data[position].images?.let {
            Glide.with(holder.itemView.context)
                .load(it[0])
                .placeholder(R.drawable.mobile)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false;
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        data[position].isLoaded = true
                        return false;
                    }
                })
                .into(holder.classImg)
        }

        holder.descriptionLabel.text = data[position].name
        holder.priceLabel.text = String.format(
            holder.itemView.context.getString(R.string.amount),
            data[position].rent_amount.toString()
        )
        val iv =
            ((holder.itemView.context as SearchActivity).getWindowWidth() - holder.itemView.context.dp2px(
                20
            ) * 5) / 3
        holder.classImg.layoutParams.width = iv
        holder.classImg.layoutParams.height = iv


        setItemClick?.let { itemClick ->
            holder.itemView.setOnClickListener { itemClick.onClick(data[position].id) }
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class CardViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var classImg: ImageView = itemView.findViewById(R.id.classImg)
        var descriptionLabel: TextView = itemView.findViewById(R.id.descriptionLabel)
        var priceLabel: TextView = itemView.findViewById(R.id.priceLabel)


    }

    fun setOnItemClick(setItemClick: SetItemClick) {
        this.setItemClick = setItemClick
    }
}
