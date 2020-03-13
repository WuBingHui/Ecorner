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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.anthony.ecorner.dto.home.request.SearchBo
import com.anthony.ecorner.main.commodity.adapter.CommodityAdapter
import com.anthony.ecorner.main.commodity.view.CommodityDetailActivity
import com.anthony.ecorner.main.message.view.viewModel.MessageViewModel
import com.anthony.ecorner.untils.ProductType
import com.anthony.ecorner.widget.CustomLoadingDialog
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
    private lateinit var childRecyclerView: RecyclerView
    private lateinit var travelRecyclerView: RecyclerView
    private lateinit var hospitalRecyclerView: RecyclerView
    private lateinit var electricRecyclerView: RecyclerView
    private lateinit var gameRecyclerView: RecyclerView
    private lateinit var  cityLabel:TextView

    private lateinit var childAdapter: ChildAdapter
    private lateinit var travelAdapter: TravelAdapter
    private lateinit var hospitalAdapter: HospitalAdapter
    private lateinit var electricAdapter: ElectricAdapter
    private lateinit var gameAdapter: GameAdapter


    companion object {
        const val TYPE_NAME = "TYPE_NAME"
        const val TYPE = "TYPE"
        const val LOCATION_REQUEST_CODE = 1000

        const val KEYWORD = "KEYWORD"
        const val CATEGORY = "CATEGORY"

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
        checkLocationPermission()
    }


    private fun initView(view: View) {
             cityLabel = view.findViewById(R.id.cityLabel)
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

        childMoreLabel.setOnClickListener {
            openCommodityPage(
                getString(R.string.child),
                ProductType.CHILD.value
            )
        }
        travelMoreLabel.setOnClickListener {
            openCommodityPage(
                getString(R.string.travel),
                ProductType.TRAVEL.value
            )
        }
        hospitalMoreLabel.setOnClickListener {
            openCommodityPage(
                getString(R.string.hospital),
                ProductType.HOSPITAL.value
            )
        }
        electricMoreLabel.setOnClickListener {
            openCommodityPage(
                getString(R.string.electric),
                ProductType.ELECTRIC.value
            )
        }
        gameMoreLabel.setOnClickListener {
            openCommodityPage(
                getString(R.string.game),
                ProductType.GAME.value
            )
        }

        searchLabel.setOnClickListener {
            context?.let {
                val intent = Intent()
                intent.putExtra(KEYWORD, searchEditText.text.toString())
                intent.putExtra(CATEGORY, "all")
                intent.setClass(it, SearchActivity::class.java)
                startActivity(intent)
            }
        }

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
            val childLinearLayoutManager = GridLayoutManager(it,2)
            val travelLinearLayoutManager = GridLayoutManager(it,2)
            val hospitalLinearLayoutManager = GridLayoutManager(it,2)
            val electricLinearLayoutManager = GridLayoutManager(it,2)
            val gameLinearLayoutManager = GridLayoutManager(it,2)
            childLinearLayoutManager.orientation = GridLayoutManager.VERTICAL
            travelLinearLayoutManager.orientation = GridLayoutManager.VERTICAL
            hospitalLinearLayoutManager.orientation = GridLayoutManager.VERTICAL
            electricLinearLayoutManager.orientation = GridLayoutManager.VERTICAL
            gameLinearLayoutManager.orientation = GridLayoutManager.VERTICAL
            childAdapter = ChildAdapter()
            travelAdapter = TravelAdapter()
            hospitalAdapter = HospitalAdapter()
            electricAdapter = ElectricAdapter()
            gameAdapter = GameAdapter()

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

            childAdapter.setOnItemClick(object : ChildAdapter.SetItemClick {
                override fun onClick(id: Int) {
                    goCommodityDetailPage(id)
                }
            })
            travelAdapter.setOnItemClick(object : TravelAdapter.SetItemClick {
                override fun onClick(id: Int) {
                    goCommodityDetailPage(id)
                }
            })
            hospitalAdapter.setOnItemClick(object : HospitalAdapter.SetItemClick {
                override fun onClick(id: Int) {
                    goCommodityDetailPage(id)
                }
            })
            electricAdapter.setOnItemClick(object : ElectricAdapter.SetItemClick {
                override fun onClick(id: Int) {
                    goCommodityDetailPage(id)
                }
            })
            gameAdapter.setOnItemClick(object : GameAdapter.SetItemClick {
                override fun onClick(id: Int) {
                    goCommodityDetailPage(id)
                }
            })


        }
    }


    private fun initViewModel() {
        viewModel.onCommodity.observe(this, Observer { dto ->
            context?.let { context ->
                when (dto.status) {
                    Status.SUCCESS -> {
                        dto.data?.let {
                            it.categories.child?.let {
                                childAdapter.setData(it.take(4))
                            }
                            it.categories.travel?.let {
                                travelAdapter.setData(it.take(4))
                            }
                            it.categories.hospital?.let {
                                hospitalAdapter.setData(it.take(4))
                            }
                            it.categories.electric?.let {
                                electricAdapter.setData(it.take(4))
                            }
                            it.categories.game?.let {
                                gameAdapter.setData(it.take(4))
                            }
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
            Log.e("aaaaaaa", "我有近來")
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

    private fun goCommodityDetailPage(id: Int) {
        context?.let {
            val intent = Intent()
            intent.putExtra("ID", id)
            intent.setClass(it, CommodityDetailActivity::class.java)
            startActivity(intent)
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
