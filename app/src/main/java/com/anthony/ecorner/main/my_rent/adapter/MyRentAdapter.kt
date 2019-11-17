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
import com.anthony.ecorner.dto.my_rent.response.ProductX
import com.bumptech.glide.Glide

class MyRentAdapter(private var context: Context) :
    RecyclerView.Adapter<MyRentAdapter.CardViewHolder>() {


    enum class Type(val value:String){
        OWNER("owner"),
        APPLICANT("applicant"),
        UPLOAD("upload")
    }

    private var data = listOf<Any>()
    private var type = Type.APPLICANT.value
    private var setItemClick: SetItemClick? = null

    fun setData(data: List<Any>,type:String) {
        this.type =type
        this.data = data
        notifyDataSetChanged()
    }

    interface SetItemClick {

        fun onClick(dto:Any,type:String)
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

            if(type == Type.UPLOAD.value){
                val data = data[position] as ProductX
                Glide.with(context)
                        .load(data.images[0])
                        .placeholder(R.drawable.mobile)
                        .into(holder.commodityImageView)

                holder.commodityNameLabel.text = data.name
                holder.commodityAmountLabel.text =
                        "$ ${data.rent_amount}/日    押金: ${data.deposit_amount}"
                setItemClick?.let { itemClick ->
                    holder.itemView.setOnClickListener { itemClick.onClick(data,type) }
                }
            }else{
                val data = data[position] as Order
                Glide.with(context)
                        .load(data.product.images[0])
                        .placeholder(R.drawable.mobile)
                        .into(holder.commodityImageView)

                holder.commodityNameLabel.text = data.product.name
                holder.commodityAmountLabel.text =
                        "$ ${data.product.rent_amount}/日    押金: ${data.product.deposit_amount}"
                setItemClick?.let { itemClick ->
                    holder.itemView.setOnClickListener { itemClick.onClick(data,type) }
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
