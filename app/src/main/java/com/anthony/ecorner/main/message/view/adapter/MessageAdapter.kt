package com.anthony.ecorner.main.message.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.message.response.MessageDto

class MessageAdapter(private val context: Context,data:ArrayList<MessageDto> ) : BaseExpandableListAdapter(){

    var data = mutableListOf<MessageDto>()

    init {
        this.data = data
    }

    fun setData(data:ArrayList<MessageDto>){
        this.data = data
        notifyDataSetChanged()
    }

    override fun getGroup(p0: Int): Any {
        return data[p0]
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return data[p0].message[p1]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return  p1.toLong()
    }

    override fun getGroupCount(): Int {
            return data.size
    }
    override fun getChildrenCount(p0: Int): Int {
        return data[p0].message.size
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
      val view =  LayoutInflater.from(context).inflate(R.layout.view_message_parent_item, null)
        val messageTitleLabel = view.findViewById<TextView>(R.id.messageTitleLabel)
        messageTitleLabel.text = data[p0].title
        return view
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        val view =  LayoutInflater.from(context).inflate(R.layout.view_message_child_item, null)
        val messageLabel = view.findViewById<TextView>(R.id.messageLabel)
        messageLabel.text = data[p0].message[p1]
        return view
    }


}