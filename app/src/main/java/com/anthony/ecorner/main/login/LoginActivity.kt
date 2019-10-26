package com.anthony.ecorner.main.login


import android.content.Intent
import android.os.Bundle
import com.anthony.ecorner.R
import com.anthony.ecorner.main.base.BaseActivity
import com.anthony.ecorner.main.registered.RegisteredActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()


    }


    private fun initView() {

        passwordEditText.setPasswordMode()

        registeredBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this,RegisteredActivity::class.java)
            startActivity(intent)
        }

    }

}
