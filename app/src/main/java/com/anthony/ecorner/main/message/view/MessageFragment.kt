package com.anthony.ecorner.main.message.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.message.response.MessageDto
import com.anthony.ecorner.main.message.view.adapter.MessageAdapter
import com.csnt.android_sport.main.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_message.*


/**
 * A simple [Fragment] subclass.
 */
class MessageFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_message, container, false)
        initView(view)
        return view
    }


    private fun initView(view: View) {

        context?.let {
            val data = ArrayList<MessageDto>()
            data.add(
                MessageDto(
                    "出租通知",
                    arrayListOf("你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。")
                )
            )
            data.add(
                MessageDto(
                    "系統通知",
                    arrayListOf("你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。")
                )
            )
            data.add(
                MessageDto(
                    "出租通知",
                    arrayListOf("你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。")
                )
            )
            data.add(
                MessageDto(
                    "系統通知",
                    arrayListOf("你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。你的出租物已經組出囉。")
                )
            )
            val messageListView = view.findViewById<ExpandableListView>(R.id.messageListView)
            val messageAdapter = MessageAdapter(it, data)
            messageListView.setAdapter(messageAdapter)
            messageAdapter.setData(data)
        }

    }

}
