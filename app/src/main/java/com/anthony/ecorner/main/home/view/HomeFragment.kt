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
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.anthony.ecorner.main.commodity.view.CommodityDetailActivity
import com.anthony.ecorner.widget.CustomLoadingDialog
import com.csnt.android_sport.extension.addTo
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

    private val loadingDialog = CustomLoadingDialog.newInstance()
    private val viewModel by viewModel<HomeViewModel>()

    private lateinit var cityLabel: TextView

    private lateinit var homeAdapter: HomeAdapter


    companion object {

        private const val TYPE_NAME = "TYPE_NAME"
        private const val TYPE = "TYPE"
        private const val LOCATION_REQUEST_CODE = 1000

        private const val KEYWORD = "KEYWORD"
        private const val CATEGORY = "CATEGORY"

        const val CHILD = "child"
        const val TRAVEL = "travel"
        const val HOSPITAL = "hospital"
        const val ELECTRIC = "electric"
        const val GAME = "game"

    }

    enum class CityType(val value: String) {
        NEW_CITY_TAIPEI("新北市"),
        HUALIEN("花蓮縣"),
        KAOHSUNG("高雄市"),
        JILONG("基隆市"),
        TAOYUAN("桃園市"),
        TAIZHONG("台中市"),
        YUNLIN("雲林縣"),
        CHIAYI_XIAN("嘉義縣"),
        CHIAYI("嘉義市"),
        TAINAN("台南市"),
        PINTUNG("屏東縣"),
        TAIPEI("台北市"),
        NANTOU("南投縣"),
        ZANGHUA("彰化縣"),
        YILAN("宜蘭縣"),
        TIATONG("台東縣"),
        MIAOLI("苗栗縣"),
        XINZHU("新竹市"),
        XINZHU_XIAN("新竹縣"),
        JINMEN("金門縣"),
        LIANJIANG("連江縣"),
        PENGHU("澎湖縣")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onResume() {
        super.onResume()
        fragmentManager?.let {
            loadingDialog.show(it, loadingDialog.tag)
            viewModel.getCommodity()
        }
    }

    override fun getData() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initRecyclerView()
        initViewModel()
        initCallBack()
        checkLocationPermission()
    }


    private fun initView(view: View) {
        cityLabel = view.findViewById(R.id.cityLabel)

        searchLabel.setOnClickListener {
            context?.let {
                val intent = Intent()
                intent.putExtra(KEYWORD, searchEditText.text.toString())
                intent.putExtra(CATEGORY, "all")
                intent.setClass(it, SearchActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun initRecyclerView() {
        context?.let {

            val linearLayoutManager = LinearLayoutManager(it)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            homeAdapter = HomeAdapter(listOf(CHILD, TRAVEL, HOSPITAL, ELECTRIC, GAME))
            homeRecyclerView.setHasFixedSize(true)
            homeRecyclerView.layoutManager = linearLayoutManager
            homeRecyclerView.adapter = homeAdapter

        }
    }

    private fun initCallBack(){

        homeAdapter.getMoreCallBack().subscribe{ pair ->

            openCommodityPage(pair.first,pair.second)

        }.addTo(compositeDisposable)


    }


    private fun initViewModel() {
        viewModel.onCommodity.observe(this, Observer { dto ->
            context?.let { context ->
                when (dto.status) {
                    Status.SUCCESS -> {
                        dto.data?.let {

                            homeAdapter.setData(it)

                        }
                    }
                    Status.FAILED -> {
                        Toast.makeText(context, dto.data?.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            loadingDialog.dismiss()
        })

    }

    private fun openCommodityPage(typeName: String, type: String) {
        context?.let {
            val intent = Intent()
            intent.putExtra(TYPE_NAME, typeName)
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
            mLocationProviderClient.requestLocationUpdates(
                locationRequest,
                object : LocationCallback() {
                    override fun onLocationResult(locationresult: LocationResult?) {
                        val geocoder = Geocoder(it, Locale.TAIWAN)
                        val addresses: List<Address> = geocoder.getFromLocation(
                            locationresult?.lastLocation?.latitude!!,
                            locationresult.lastLocation.longitude,
                            1
                        )
                        if (addresses.isNotEmpty()) {
                            val address: Address = addresses[0]
                            address.adminArea?.let {
                                cityLabel.text = it
                            } ?: run {
                                cityLabel.text = address.subAdminArea
                            }
                            setCityView()
                        } else {
                            cityLabel.text = "無法定位"
                        }
                    }
                },
                null
            )
        }

    }



    private fun setCityView() {
        val city = cityLabel.text
        if (!city.isNullOrEmpty()) {
            context?.let {
                var bg = ContextCompat.getDrawable(it, R.drawable.taipei)
                when (city) {
                    CityType.NEW_CITY_TAIPEI.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.xinbei)
                    }
                    CityType.HUALIEN.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.hualien)
                    }
                    CityType.KAOHSUNG.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.kaohsung)
                    }
                    CityType.JILONG.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.jilong)
                    }
                    CityType.TAOYUAN.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.taoyuan)
                    }
                    CityType.TAIZHONG.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.taizhong)
                    }
                    CityType.YUNLIN.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.yunlin)
                    }
                    CityType.CHIAYI_XIAN.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.chiayi_xian)
                    }
                    CityType.CHIAYI.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.chiayi)
                    }
                    CityType.TAINAN.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.tainan)
                    }
                    CityType.PINTUNG.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.pintung)
                    }
                    CityType.TAIPEI.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.taipei)
                    }
                    CityType.NANTOU.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.nantou)
                    }
                    CityType.ZANGHUA.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.zanghua)
                    }
                    CityType.YILAN.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.yilan)
                    }
                    CityType.TIATONG.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.tiatong)
                    }
                    CityType.MIAOLI.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.miaoli)
                    }
                    CityType.XINZHU.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.xinzhu)
                    }
                    CityType.XINZHU_XIAN.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.xinzhu)
                    }
                    CityType.JINMEN.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.jinmen)
                    }
                    CityType.LIANJIANG.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.lianjiang)
                    }
                    CityType.PENGHU.value -> {
                        bg = ContextCompat.getDrawable(it, R.drawable.penghu)
                    }
                }
                homeImg.background = bg
            }
        }

    }

}
