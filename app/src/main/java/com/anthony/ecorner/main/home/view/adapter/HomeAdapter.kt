package com.anthony.ecorner.main.home.view.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.home.reponse.CommodityDto
import com.anthony.ecorner.dto.home.reponse.Product
import com.anthony.ecorner.untils.ProductType
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class HomeAdapter(private val data: List<String>) :
    RecyclerView.Adapter<HomeAdapter.CardViewHolder>() {

    private var pool: RecyclerView.RecycledViewPool? = null

    private var commodity: CommodityDto? = null

    private val moreCallBack = PublishSubject.create<Pair<String, String>>()

    fun getMoreCallBack(): Observable<Pair<String, String>> = moreCallBack

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        return CardViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_class_group,
                parent,
                false
            )
        )
    }

    fun setData(commodity: CommodityDto?) {
        this.commodity = commodity
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val context = holder.itemView.context

        var name = ""
        var drawable: Drawable? = null
        var type = ""
        var productList: List<Product>? = null

        when (position) {
            0 -> {
                name = context.getString(R.string.child)
                drawable = ContextCompat.getDrawable(context, R.drawable.toddler)
                type = ProductType.CHILD.value
                productList = commodity?.categories?.child?.take(4)
            }
            1 -> {
                name = context.getString(R.string.travel)
                drawable = ContextCompat.getDrawable(context, R.drawable.world)
                type = ProductType.TRAVEL.value
                productList = commodity?.categories?.travel?.take(4)
            }
            2 -> {
                name = context.getString(R.string.hospital)
                drawable = ContextCompat.getDrawable(context, R.drawable.sign_hospital)
                type = ProductType.HOSPITAL.value
                productList = commodity?.categories?.hospital?.take(4)
            }
            3 -> {
                name = context.getString(R.string.electric)
                drawable = ContextCompat.getDrawable(context, R.drawable.mobile)
                type = ProductType.ELECTRIC.value
                productList = commodity?.categories?.electric?.take(4)
            }
            4 -> {
                name = context.getString(R.string.game)
                drawable = ContextCompat.getDrawable(context, R.drawable.games_control)
                type = ProductType.GAME.value
                productList = commodity?.categories?.game?.take(4)
            }
        }

        holder.classLabel.text = name

        holder.classLabel.setCompoundDrawablesWithIntrinsicBounds(
            drawable, null, null, null
        )

        holder.moreLabel.tag = Pair(name, type)

        holder.moreLabel.setOnClickListener(moreListener)

        productList?.let {

            holder.typeAdapter.setData(it)

        }

    }


    override fun getItemCount(): Int {
        return data.size
    }

    private val moreListener = View.OnClickListener {
        val pair = it.tag as Pair<String, String>
        moreCallBack.onNext(pair)
    }


    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val classLabel = itemView.findViewById<TextView>(R.id.classLabel)

        val moreLabel = itemView.findViewById<TextView>(R.id.moreLabel)

        val classRecyclerView = itemView.findViewById<RecyclerView>(R.id.classRecyclerView)

        val typeAdapter = TypeAdapter()


        init {

            val gridLayoutManager = GridLayoutManager(itemView.context, 2)

            gridLayoutManager.orientation = GridLayoutManager.VERTICAL

            gridLayoutManager.recycleChildrenOnDetach = true

            if(pool == null){
                pool = classRecyclerView.recycledViewPool
            }

            classRecyclerView.setRecycledViewPool(pool)

            classRecyclerView.layoutManager = gridLayoutManager

            classRecyclerView.adapter = typeAdapter
        }

    }

}