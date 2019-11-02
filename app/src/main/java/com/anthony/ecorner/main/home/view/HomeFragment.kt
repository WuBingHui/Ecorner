package com.anthony.ecorner.main.home.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.anthony.ecorner.R
import com.anthony.ecorner.main.home.view.adapter.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_class_group.*
import kotlinx.android.synthetic.main.view_class_group.view.*
import kotlinx.android.synthetic.main.view_class_item.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {


    private lateinit var childRecyclerView: RecyclerView
    private lateinit var travelRecyclerView: RecyclerView
    private lateinit var hospitalRecyclerView: RecyclerView
    private lateinit var electricRecyclerView: RecyclerView
    private lateinit var gameRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initView(view)
        initRecyclerView()
        return view
    }

    private fun initView(view: View) {

        val childLabel =
            view.findViewById<View>(R.id.childType).findViewById<TextView>(R.id.classLabel)
        val travelLabel =
            view.findViewById<View>(R.id.travelType).findViewById<TextView>(R.id.classLabel)
        val hospitalLabel =
            view.findViewById<View>(R.id.hospitalType).findViewById<TextView>(R.id.classLabel)
        val electricLabel =
            view.findViewById<View>(R.id.electricType).findViewById<TextView>(R.id.classLabel)
        val gameLabel =
            view.findViewById<View>(R.id.gameType).findViewById<TextView>(R.id.classLabel)

        childLabel.text = getString(R.string.child)
        travelLabel.text = getString(R.string.travel)
        hospitalLabel.text = getString(R.string.hospital)
        electricLabel.text = getString(R.string.electric)
        gameLabel.text = getString(R.string.game)

        childLabel.setCompoundDrawablesWithIntrinsicBounds(
            resources.getDrawable(R.drawable.toddler),
            null,
            null,
            null
        )
        travelLabel.setCompoundDrawablesWithIntrinsicBounds(
            resources.getDrawable(R.drawable.world),
            null,
            null,
            null
        )
        hospitalLabel.setCompoundDrawablesWithIntrinsicBounds(
            resources.getDrawable(R.drawable.sign_hospital),
            null,
            null,
            null
        )
        electricLabel.setCompoundDrawablesWithIntrinsicBounds(
            resources.getDrawable(R.drawable.mobile),
            null,
            null,
            null
        )
        gameLabel.setCompoundDrawablesWithIntrinsicBounds(
            resources.getDrawable(R.drawable.games_control),
            null,
            null,
            null
        )

        val childMoreLabel =
            view.findViewById<View>(R.id.childType).findViewById<TextView>(R.id.moreLabel)
        val travelMoreLabel =
            view.findViewById<View>(R.id.travelType).findViewById<TextView>(R.id.moreLabel)
        val hospitalMoreLabel =
            view.findViewById<View>(R.id.hospitalType).findViewById<TextView>(R.id.moreLabel)
        val electricMoreLabel =
            view.findViewById<View>(R.id.electricType).findViewById<TextView>(R.id.moreLabel)
        val gameMoreLabel =
            view.findViewById<View>(R.id.gameType).findViewById<TextView>(R.id.moreLabel)

        childRecyclerView =
            view.findViewById<View>(R.id.childType).findViewById(R.id.classRecyclerView)
        travelRecyclerView =
            view.findViewById<View>(R.id.travelType).findViewById(R.id.classRecyclerView)
        hospitalRecyclerView =
            view.findViewById<View>(R.id.hospitalType).findViewById(R.id.classRecyclerView)
        electricRecyclerView =
            view.findViewById<View>(R.id.electricType).findViewById(R.id.classRecyclerView)
        gameRecyclerView =
            view.findViewById<View>(R.id.gameType).findViewById(R.id.classRecyclerView)
    }

    private fun initRecyclerView() {
        context?.let {
            val childLinearLayoutManager = LinearLayoutManager(it)
            val travelLinearLayoutManager = LinearLayoutManager(it)
            val hospitalLinearLayoutManager = LinearLayoutManager(it)
            val electricLinearLayoutManager = LinearLayoutManager(it)
            val gameLinearLayoutManager = LinearLayoutManager(it)
            childLinearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            travelLinearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            hospitalLinearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            electricLinearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            gameLinearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

            val childAdapter = ChildAdapter(it)
            val travelAdapter = TravelAdapter(it)
            val hospitalAdapter = HospitalAdapter(it)
            val electricAdapter = ElectricAdapter(it)
            val gameAdapter = GameAdapter(it)

            childRecyclerView.layoutManager = childLinearLayoutManager
            travelRecyclerView.layoutManager = travelLinearLayoutManager
            hospitalRecyclerView.layoutManager = hospitalLinearLayoutManager
            electricRecyclerView .layoutManager = electricLinearLayoutManager
            gameRecyclerView .layoutManager = gameLinearLayoutManager

            childRecyclerView.adapter = childAdapter
            travelRecyclerView.adapter = travelAdapter
            hospitalRecyclerView.adapter = hospitalAdapter
            electricRecyclerView .adapter = electricAdapter
            gameRecyclerView .adapter = gameAdapter
        }
    }

}
