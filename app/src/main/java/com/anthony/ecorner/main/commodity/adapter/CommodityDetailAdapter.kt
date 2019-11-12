package com.anthony.ecorner.main.commodity.adapter
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.anthony.ecorner.R
import com.bumptech.glide.Glide


class CommodityDetailAdapter(private val context:Context): RecyclerView.Adapter<CommodityDetailAdapter.ViewHolder>() {


    private var img:Array<Drawable?> = arrayOf()
    private var setItemClick:SetItemClick? = null

    fun setData(arrayImg: Array<Drawable?>){
        this.img=arrayImg
        notifyDataSetChanged()
    }
    interface SetItemClick{

        fun onClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_banner_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return img.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(img[position])
            .into(holder.bannerImg)
        setItemClick?.let {
            holder.itemView.setOnClickListener { it }
        }
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
         var bannerImg: ImageView = itemView.findViewById(R.id.bannerImg)

    }

    fun setOnItemClick(setItemClick:SetItemClick){
        this.setItemClick = setItemClick
    }
}