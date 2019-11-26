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
import com.bumptech.glide.Glide

class ElectricAdapter(private var context: Context) : RecyclerView.Adapter<ElectricAdapter.CardViewHolder>() {

    private var data = listOf<Product>()

    fun setData(data: List<Product>) {
        this.data = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        return CardViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.view_class_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind()
        val data = data[position]
        data.images?.let {
            Glide.with(context)
                .load(it[0])
                .placeholder(R.drawable.mobile)
                .into( holder.classImg)
        }
        holder. descriptionLabel.text =  data.name
        holder. priceLabel.text = String.format(context.getString(R.string.amount),data.rent_amount.toString())
        setItemClick?.let {itemClick->
            holder.itemView.setOnClickListener { itemClick.onClick(data.id) }
        }
    }

    override fun getItemCount(): Int {
        return  data.size
    }


    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var classImg: ImageView

        lateinit var descriptionLabel: TextView
        lateinit var priceLabel: TextView
        fun bind() {
            classImg = itemView.findViewById(R.id.classImg)

            descriptionLabel = itemView.findViewById(R.id.descriptionLabel)
            priceLabel = itemView.findViewById(R.id.priceLabel)
        }
    }
    private var setItemClick: SetItemClick? = null
    interface SetItemClick {
        fun onClick(id:Int)
    }
    fun setOnItemClick(setItemClick: SetItemClick) {
        this.setItemClick = setItemClick
    }
}