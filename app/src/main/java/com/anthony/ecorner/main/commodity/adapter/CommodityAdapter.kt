package com.anthony.ecorner.main.commodity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.home.reponse.Child
import com.anthony.ecorner.extension.dp2px
import com.anthony.ecorner.extension.getWindowWidth
import com.anthony.ecorner.main.commodity.view.CommodityActivity
import com.anthony.ecorner.main.commodity.view.CommodityActivity.Companion.commodityActivity
import com.anthony.ecorner.main.main.view.MainActivity.Companion.activity

class CommodityAdapter(private var context: Context) :
    RecyclerView.Adapter<CommodityAdapter.CardViewHolder>() {

    private var data = listOf<Child>()
    private var setItemClick: SetItemClick? = null
    fun setData(data: List<Child>) {
        this.data = data
        notifyDataSetChanged()
    }

    interface SetItemClick {

        fun onClick()
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

        val iv = ((context as CommodityActivity).getWindowWidth() - context.dp2px(20) * 5) / 3
        holder.classImg.layoutParams.width = iv
        holder.classImg.layoutParams.height = iv
        setItemClick?.let {itemClick->
            holder.itemView.setOnClickListener { itemClick.onClick() }
        }
    }

    override fun getItemCount(): Int {
        return 20
    }


    inner class CardViewHolder internal constructor(itemView: View, context: Context) :
        RecyclerView.ViewHolder(itemView) {
        var classImg: ImageView = itemView.findViewById(R.id.classImg)
        var collectImg: ImageView = itemView.findViewById(R.id.collectImg)
        var descriptionLabel: TextView = itemView.findViewById(R.id.descriptionLabel)
        var priceLabel: TextView = itemView.findViewById(R.id.priceLabel)


    }

    fun setOnItemClick(setItemClick: SetItemClick) {
        this.setItemClick = setItemClick
    }
}
