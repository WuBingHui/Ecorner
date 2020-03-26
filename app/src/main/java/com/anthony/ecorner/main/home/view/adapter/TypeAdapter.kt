package com.anthony.ecorner.main.home.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.home.reponse.Product
import com.anthony.ecorner.main.commodity.view.CommodityDetailActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_class_item.view.*

class TypeAdapter() : RecyclerView.Adapter<TypeAdapter.CardViewHolder>() {

   private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(data: List<Product>) {

        differ.submitList(data)

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

        holder.bind(differ.currentList[position])

    }

    private val itemViewListener = View.OnClickListener {

        val pair = it.tag as Pair<Context, Int>

        goCommodityDetailPage(pair.first, pair.second)

    }

    private fun goCommodityDetailPage(context: Context, id: Int) {

        val intent = Intent()
        intent.putExtra("ID", id)
        intent.setClass(context, CommodityDetailActivity::class.java)
        context.startActivity(intent)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Product) = with(itemView) {

            item.images?.let {
                Glide.with(itemView.context)
                    .load(it[0])
                    .placeholder(R.drawable.mobile)
                    .into(itemView.classImg)
            }

            itemView.descriptionLabel.text = item.name
            itemView.priceLabel.text = String.format(
                itemView.context.getString(R.string.amount),
                item.rent_amount.toString()
            )

            itemView.tag = Pair(itemView.context, item.id)

            itemView.setOnClickListener(itemViewListener)

        }

    }

}