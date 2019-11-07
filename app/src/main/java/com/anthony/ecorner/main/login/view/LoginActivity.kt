package com.anthony.ecorner.main.login.view


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.dto.login.request.LoginBo
import com.anthony.ecorner.extension.isEmailFormat
import com.anthony.ecorner.koin.Properties
import com.anthony.ecorner.main.main.view.MainActivity
import com.anthony.ecorner.main.base.BaseActivity
import com.anthony.ecorner.main.login.viewModel.LoginViewModel
import com.anthony.ecorner.main.registered.view.RegisteredActivity
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

        accountEditText.setEmailMode()
        passwordEditText.setPasswordMode()

        loginBtn.setOnClickListener {
            if (checkAccountEmpty() && checkAccountFormat() && checkPasswordEmpty()) {
                viewModel.postLogin(LoginBo(accountEditText.getText(), passwordEditText.getText()))
            }
        }

        registeredBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, RegisteredActivity::class.java)
            startActivity(intent)
        }

    }

    private fun checkAccountEmpty(): Boolean {
        if (accountEditText.getText() == "") {
            Toast.makeText(this, getString(R.string.email_empty), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun checkAccountFormat(): Boolean {
        if (!accountEditText.getText().isEmailFormat()) {
            Toast.makeText(this, getString(R.string.email_format_error), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun checkPasswordEmpty(): Boolean {
        if (passwordEditText.getText() == "") {
            Toast.makeText(this, getString(R.string.password_empty), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun initViewModel() {

        viewModel.onLogin.observe(this, Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {
                    Properties.setToken(dto.data?.user!!.id)
                    Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent()
                    intent.setClass(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                Status.FAILED -> {
                    Toast.makeText(this, dto.data?.error, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

}
