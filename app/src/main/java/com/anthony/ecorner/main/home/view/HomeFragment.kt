package com.anthony.ecorner.main.home.view


import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.anthony.ecorner.R
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.main.commodity.view.CommodityActivity
import com.anthony.ecorner.main.home.view.adapter.*
import com.anthony.ecorner.main.home.view.viewModel.HomeViewModel
import com.csnt.android_sport.main.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import android.location.LocationManager
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */


class HomeFragment : BaseFragment(), EasyPermissions.PermissionCallbacks {


    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var childRecyclerView: RecyclerView
    private lateinit var travelRecyclerView: RecyclerView
    private lateinit var hospitalRecyclerView: RecyclerView
    private lateinit var electricRecyclerView: RecyclerView
    private lateinit var gameRecyclerView: RecyclerView

    private lateinit var childAdapter: ChildAdapter
    private lateinit var travelAdapter: TravelAdapter
    private lateinit var hospitalAdapter: HospitalAdapter
    private lateinit var electricAdapter: ElectricAdapter
    private lateinit var gameAdapter: GameAdapter


    companion object {
        const val TYPE = "TYPE"
        const val LOCATION_REQUEST_CODE = 1000
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initView(view)
        initRecyclerView()
        initViewModel()
        checkLocationPermission()
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

        childMoreLabel.setOnClickListener { openCommodityPage(getString(R.string.child)) }
        travelMoreLabel.setOnClickListener { openCommodityPage(getString(R.string.travel)) }
        hospitalMoreLabel.setOnClickListener { openCommodityPage(getString(R.string.hospital)) }
        electricMoreLabel.setOnClickListener { openCommodityPage(getString(R.string.electric)) }
        gameMoreLabel.setOnClickListener { openCommodityPage(getString(R.string.game)) }


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

            childAdapter = ChildAdapter(it)
            travelAdapter = TravelAdapter(it)
            hospitalAdapter = HospitalAdapter(it)
            electricAdapter = ElectricAdapter(it)
            gameAdapter = GameAdapter(it)

            childRecyclerView.layoutManager = childLinearLayoutManager
            travelRecyclerView.layoutManager = travelLinearLayoutManager
            hospitalRecyclerView.layoutManager = hospitalLinearLayoutManager
            electricRecyclerView.layoutManager = electricLinearLayoutManager
            gameRecyclerView.layoutManager = gameLinearLayoutManager

            childRecyclerView.adapter = childAdapter
            travelRecyclerView.adapter = travelAdapter
            hospitalRecyclerView.adapter = hospitalAdapter
            electricRecyclerView.adapter = electricAdapter
            gameRecyclerView.adapter = gameAdapter

            viewModel.getCommodity()

        }
    }


    private fun initViewModel() {
        viewModel.onCommodity.observe(this, Observer { dto ->
            context?.let { context ->
                when (dto.status) {
                    Status.SUCCESS -> {
                        dto.data?.let {
                            childAdapter.setData(it.child)
                            travelAdapter.setData(it.travel)
                            hospitalAdapter.setData(it.hospital)
                            electricAdapter.setData(it.electric)
                            gameAdapter.setData(it.game)
                        }
                    }
                    Status.FAILED -> {
                        Toast.makeText(context, dto.data?.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun openCommodityPage(type: String) {
        context?.let {
            val intent = Intent()
            intent.putExtra(TYPE, type)
            intent.setClass(it, CommodityActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(context, "您拒绝授權,並不再提醒", Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this).setTitle("打开應用程序設置修改應用程式權限").build().show()
        } else {
            Toast.makeText(context, "您不允許定位", Toast.LENGTH_SHORT).show()
            checkLocationPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {
        checkLocationPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            checkLocationPermission()
        }
    }

    private fun checkLocationPermission() {
        if (EasyPermissions.hasPermissions(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Log.e("aaaaaaa","我有近來")
            getLocation()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "應用需要訪問您的定位,您需要在彈跳視窗允許使用",
                LOCATION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private fun getLocation() {

        context?.let {
            val mLocationProviderClient = LocationServices.getFusedLocationProviderClient(it)
            val locationRequest = LocationRequest()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//         設定更新速度
            locationRequest.interval = 60000
            Log.i(
                " aaaaaaaaaaa",
                "我有近來"
            )
            mLocationProviderClient.requestLocationUpdates(
                locationRequest,
                object : LocationCallback() {
                    override fun onLocationResult(locationresult: LocationResult?) {
                        val geocoder = Geocoder(it, Locale.TAIWAN)
                         var addresses:List<Address> = geocoder.getFromLocation (locationresult?.lastLocation?.latitude!!, locationresult.lastLocation.longitude, 1)
                        var  address:Address = addresses[0]
                        cityLabel.text =  address.adminArea.toString()
                    }
                },
                null
            )
        }

    }


}
