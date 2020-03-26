package com.anthony.ecorner.main.home.view.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.home.reponse.Product
import com.anthony.ecorner.extension.dp2px
import com.anthony.ecorner.extension.getWindowWidth
import com.anthony.ecorner.main.home.view.SearchActivity
import com.anthony.ecorner.main.home.view.SearchDiffCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.view_class_big_item.view.*

class SearchAdapter() :
    RecyclerView.Adapter<SearchAdapter.CardViewHolder>() {

    private var setItemClick: SetItemClick? = null

    private var isScrolling = false


    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    fun isScrolling(isScrolling: Boolean) {

        this.isScrolling = isScrolling

    }

    fun submitList(data: List<Product>) {

        differ.submitList(data)

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

       holder.bind(differ.currentList[position])

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class CardViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(item:Product) = with(item){

            if (isScrolling && !item.isLoaded) {
                Glide.with(itemView.context).pauseRequests()
            } else {
                Glide.with(itemView.context).resumeRequests()
            }

            item.images?.let {
                Glide.with(itemView.context)
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
                            item.isLoaded = true
                            return false;
                        }
                    })
                    .into(itemView.classImg)
            }

            itemView.descriptionLabel.text = item.name
            itemView.priceLabel.text = String.format(
                itemView.context.getString(R.string.amount),
                item.rent_amount.toString()
            )
            val iv =
                ((itemView.context as SearchActivity).getWindowWidth() - itemView.context.dp2px(
                    20
                ) * 5) / 3
            itemView.classImg.layoutParams.width = iv
            itemView.classImg.layoutParams.height = iv


            setItemClick?.let { itemClick ->
                itemView.setOnClickListener { itemClick.onClick(item.id) }
            }

        }

    }

    fun setOnItemClick(setItemClick: SetItemClick) {
        this.setItemClick = setItemClick
    }
}
