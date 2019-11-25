package com.anthony.ecorner.main.personal.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.my_rent.response.ProductX
import com.anthony.ecorner.dto.personal.response.Data
import com.anthony.ecorner.dto.personal.response.MyCollectDto
import com.bumptech.glide.Glide

class MyCollectAdapter(private var context: Context) :
    RecyclerView.Adapter<MyCollectAdapter.CardViewHolder>() {

    private var data = listOf<Data>()
    private var setItemClick: SetItemClick? = null
    private var setItemLongClick: SetItemLongClick? = null


    fun setData(data: List<Data>) {
        this.data = data
        notifyDataSetChanged()
    }

    interface SetItemClick {

        fun onClick(id: Int)
    }

    interface SetItemLongClick {

        fun onClick(id: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        val view = CardViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.view_my_rent_item,
                parent,
                false
            ),
            context
        )

        return view
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        context?.let { context ->

            val data = data[position]
            Glide.with(context)
                .load(data.product_info.images[0])
                .placeholder(R.drawable.mobile)
                .into(holder.commodityImageView)

            holder.commodityNameLabel.text = data.product_info.name
            holder.commodityAmountLabel.text =
                "$ ${data.product_info.rent_amount}/日    押金: ${data.product_info.deposit_amount}"
            setItemClick?.let { itemClick ->
                holder.itemView.setOnClickListener { itemClick.onClick(data.product_info.id) }
            }
            setItemLongClick?.let { itemClick ->
                holder.itemView.setOnLongClickListener {
                    itemClick.onClick(data.product_info.id)
                    false
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class CardViewHolder internal constructor(itemView: View, context: Context) :
        RecyclerView.ViewHolder(itemView) {
        var commodityImageView: ImageView = itemView.findViewById(R.id.commodityImageView)
        var commodityNameLabel: TextView = itemView.findViewById(R.id.commodityNameLabel)
        var commodityAmountLabel: TextView = itemView.findViewById(R.id.commodityAmountLabel)
    }

    fun setOnItemClick(setItemClick: SetItemClick) {
        this.setItemClick = setItemClick
    }

    fun setOnItemLongClick(setItemLongClick: SetItemLongClick) {
        this.setItemLongClick = setItemLongClick
    }
}
