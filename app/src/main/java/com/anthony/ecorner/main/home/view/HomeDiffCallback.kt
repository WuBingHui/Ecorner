package com.anthony.ecorner.main.home.view

import androidx.recyclerview.widget.DiffUtil
import com.anthony.ecorner.dto.home.reponse.Product

class HomeDiffCallback(
    private val oldList: List<Product>,
    private val newList: List<Product>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

}
