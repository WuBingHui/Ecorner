package com.anthony.ecorner.main.login


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.koin.Properties
import com.anthony.ecorner.main.base.BaseActivity
import com.anthony.ecorner.main.login.viewModel.LoginViewModel
import com.anthony.ecorner.main.registered.RegisteredActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {
    private val viewModel by viewModel<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()

        initViewModel()
    }


    private fun initView() {

        passwordEditText.setPasswordMode()

        loginBtn.setOnClickListener { viewModel.postLogin(accountEditText.getText(),passwordEditText.getText()) }

        registeredBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, RegisteredActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initViewModel() {

        viewModel.onLogin.observe(this, androidx.lifecycle.Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {
                    Properties.setToken(dto.data?.user!!.username)
                    Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                }
                Status.Failed -> {
                    Toast.makeText(this, dto.data?.error, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

}
