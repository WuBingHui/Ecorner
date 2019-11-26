package com.anthony.ecorner.main.home.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.home.reponse.Product
import com.anthony.ecorner.extension.dp2px
import com.anthony.ecorner.extension.getWindowWidth
import com.anthony.ecorner.main.home.view.SearchActivity
import com.bumptech.glide.Glide

class SearchAdapter(private var context: Context) :
    RecyclerView.Adapter<SearchAdapter.CardViewHolder>() {

    private var data = listOf<Product>()
    private var setItemClick: SetItemClick? = null
    fun setData(data: List<Product>) {
        this.data = data
        notifyDataSetChanged()
    }

    interface SetItemClick {

        fun onClick(id:Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        val view = CardViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.view_class_big_item,
                parent,
                false
            ),
            context
        )

        return view
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {


            data[position].images?.let {
                Glide.with(context)
                    .load(it[0])
                    .placeholder(R.drawable.mobile)
                    .into( holder.classImg)
            }
            holder. descriptionLabel.text = data[position].name
            holder. priceLabel.text = String.format(context.getString(R.string.amount),data[position].rent_amount.toString())
            val iv = ((context as SearchActivity).getWindowWidth() - context.dp2px(20) * 5) / 3
            holder.classImg.layoutParams.width = iv
            holder.classImg.layoutParams.height = iv
            setItemClick?.let {itemClick->
                holder.itemView.setOnClickListener { itemClick.onClick(data[position].id) }
            }


    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class CardViewHolder internal constructor(itemView: View, context: Context) :
        RecyclerView.ViewHolder(itemView) {
        var classImg: ImageView = itemView.findViewById(R.id.classImg)
        var descriptionLabel: TextView = itemView.findViewById(R.id.descriptionLabel)
        var priceLabel: TextView = itemView.findViewById(R.id.priceLabel)


    }

    fun setOnItemClick(setItemClick: SetItemClick) {
        this.setItemClick = setItemClick
    }
}
