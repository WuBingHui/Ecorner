package com.anthony.ecorner.main.home.view.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.home.reponse.Child

class ChildAdapter(private var context: Context) : RecyclerView.Adapter<ChildAdapter.CardViewHolder>() {

    private var data = listOf<Child>()

    fun setData(data: List<Child>) {
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

    }

    override fun getItemCount(): Int {
        return 5
    }


    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var classImg: ImageView
        lateinit var collectImg: ImageView
        lateinit var descriptionLabel:TextView
        lateinit var priceLabel:TextView
        fun bind() {
            classImg = itemView.findViewById(R.id.classImg)
            collectImg = itemView.findViewById(R.id.collectImg)
            descriptionLabel = itemView.findViewById(R.id.descriptionLabel)
            priceLabel = itemView.findViewById(R.id.priceLabel)
        }
    }
}