package com.anthony.ecorner.main.message.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.dto.message.response.Apply
import com.anthony.ecorner.dto.message.response.MessageDto
import com.anthony.ecorner.main.home.view.viewModel.HomeViewModel
import com.anthony.ecorner.main.message.view.adapter.MessageAdapter
import com.anthony.ecorner.main.message.view.viewModel.MessageViewModel
import com.anthony.ecorner.widget.CustomLoadingDialog
import com.csnt.android_sport.main.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_message.*
import okhttp3.internal.lockAndWaitNanos
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class MessageFragment : BaseFragment() {

    private val viewModel by viewModel<MessageViewModel>()
    private val loadingFragment = CustomLoadingDialog.newInstance()
    private var  messageAdapter:MessageAdapter? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initViewModel()

    }

    override fun getData() {
        fragmentManager?.let {
            loadingFragment.show(it, loadingFragment.tag)
        }
        viewModel.getNotify()
    }

    private fun initView(view: View) {

        context?.let {context->

            messageAdapter = MessageAdapter(context)
            messageListView.setAdapter(messageAdapter)
        }

    }

    private fun initViewModel() {

        viewModel.onNotify.observe(this, Observer { dto ->

            when (dto.status) {
                Status.SUCCESS -> {
                    dto.data?.let {
                        context?.let {context->

                            messageAdapter?.setData(it.apply.toMutableList())
                        }
                    }
                }
                Status.FAILED -> {
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                }
            }
            loadingFragment.dismiss()
        })

    }

}
