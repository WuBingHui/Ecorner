package com.anthony.ecorner.main.home.view.adapter

import android.content.Context
import android.content.Intent
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
import com.anthony.ecorner.main.commodity.view.CommodityDetailActivity
import com.anthony.ecorner.main.home.view.HomeDiffCallback
import com.bumptech.glide.Glide
import io.reactivex.subjects.BehaviorSubject

class TypeAdapter() : RecyclerView.Adapter<TypeAdapter.CardViewHolder>() {

    private var data = mutableListOf<Product>()

    fun setData(data: List<Product>) {

        val diffCallback = HomeDiffCallback(this.data, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.data.clear()
        this.data.addAll(data)


        diffResult.dispatchUpdatesTo(this)

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        return CardViewHolder(
            LayoutInflater.from(parent.context).inflate(
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
            Glide.with(holder.itemView.context)
                .load(it[0])
                .placeholder(R.drawable.mobile)
                .into(holder.classImg)
        }
        holder.descriptionLabel.text = data.name
        holder.priceLabel.text = String.format(
            holder.itemView.context.getString(R.string.amount),
            data.rent_amount.toString()
        )

        holder.itemView.tag = Pair(holder.itemView.context, data.id)

        holder.itemView.setOnClickListener(itemViewListener)

    }

    private val itemViewListener = View.OnClickListener {

        val pair = it.tag as Pair<Context,Int>

        goCommodityDetailPage(pair.first,pair.second)

    }

    private fun goCommodityDetailPage(context: Context, id: Int) {

        val intent = Intent()
        intent.putExtra("ID", id)
        intent.setClass(context, CommodityDetailActivity::class.java)
        context.startActivity(intent)

    }

    override fun getItemCount(): Int {
        return data.size
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

}