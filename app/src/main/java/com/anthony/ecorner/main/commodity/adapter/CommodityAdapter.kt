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

    fun setData(data: List<Child>) {
        this.data = data
        notifyDataSetChanged()
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
        holder.bind()

    }

    override fun getItemCount(): Int {
        return 20
    }


    class CardViewHolder(itemView: View,private val context: Context) : RecyclerView.ViewHolder(itemView) {
        lateinit var classImg: ImageView
        lateinit var collectImg: ImageView
        lateinit var descriptionLabel: TextView
        lateinit var priceLabel: TextView
        fun bind() {
            classImg = itemView.findViewById(R.id.classImg)
            collectImg = itemView.findViewById(R.id.collectImg)
            descriptionLabel = itemView.findViewById(R.id.descriptionLabel)
            priceLabel = itemView.findViewById(R.id.priceLabel)
            val iv = ((context as CommodityActivity).getWindowWidth() - context.dp2px(20) * 5) / 3
            classImg.layoutParams.width = iv
            classImg.layoutParams.height = iv
        }
    }
}
