package com.anthony.ecorner.main.my_rent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.my_rent.response.Order
import com.bumptech.glide.Glide

class MyRentAdapter(private var context: Context) :
    RecyclerView.Adapter<MyRentAdapter.CardViewHolder>() {


    enum class Type(val value:String){
        OWNER("owner"),
        APPLICANT("Applicant")
    }

    private var data = listOf<Order>()
    private var type = Type.APPLICANT.value
    private var setItemClick: SetItemClick? = null
    fun setData(data: List<Order>,type:String) {
        this.type =type
        this.data = data
        notifyDataSetChanged()
    }

    interface SetItemClick {

        fun onClick(dto:Order)
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
                .load(data.product.images[0])
                .placeholder(R.drawable.mobile)
                .into(holder.commodityImageView)

            holder.commodityNameLabel.text = data.product.name
            holder.commodityAmountLabel.text =
                "$ ${data.product.rent_amount}/日    押金: ${data.product.deposit_amount}"
            setItemClick?.let { itemClick ->
                if(type == Type.APPLICANT.value){
                    holder.itemView.setOnClickListener { itemClick.onClick(data) }
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
}
