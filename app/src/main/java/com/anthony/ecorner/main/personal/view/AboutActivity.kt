package com.anthony.ecorner.main.personal.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anthony.ecorner.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        initView()

    }

    private fun initView(){



        aboutLabel.text ="Ecorner指向的意思是\nE economic(經濟)\nC corner(轉角)\n想法是從希望轉個彎就能借到想要的物品，像是與街坊鄰居借東西一樣的概念。加工製造成商品，因產品不斷推成出新，消費者追求新奇感，許多商品還可以使用，卻被丟棄，因此造成資源尚未被最大利利用，即使仍屬「堪用」，不少民眾為了嘗鮮，也願意花錢換新機。然而，這些用品擺在家裡，到底一天使用時間有多長？閒置的時間又有多長？工業製程和人們的生活方式不斷的消耗著有限的資源，以此製造商品。利用租賃網，使出租人以及租賃人能夠達到相互的利益，進而使得商品在使用過後仍能被予以再利用，進而減少源頭對原物料的使用。"


    }
}
