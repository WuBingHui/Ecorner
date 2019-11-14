package com.anthony.ecorner.main.registered.view

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.anthony.ecorner.R
import com.anthony.ecorner.databinding.ActivityRegisteredBinding
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.dto.registered.request.RegisteredBo
import com.anthony.ecorner.extension.isEmailFormat
import com.anthony.ecorner.extension.isTaiwanPhone
import com.anthony.ecorner.main.base.BaseActivity
import com.anthony.ecorner.main.registered.viewModel.RegisteredViewModel
import com.anthony.ecorner.widget.CustomLoadingDialog
import kotlinx.android.synthetic.main.activity_registered.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisteredActivity : BaseActivity() {

    private val viewModel by viewModel<RegisteredViewModel>()
    private val loadingDialog = CustomLoadingDialog.newInstance()
     var activityRegisteredBinding: ActivityRegisteredBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityRegisteredBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_registered)

        initView()

        initViewModel()
    }


    private fun initView() {

        activityRegisteredBinding?.registered = this

        accountEditText.setEmailMode()
        passwordEditText.setPasswordMode()
        confirmPasswordEditText.setPasswordMode()
        phoneEditText.setPhoneMode()

    }

    private fun initViewModel() {

        viewModel.onRegistered.observe(this, Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {
                    Toast.makeText(this, getString(R.string.registered_success), Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
                Status.FAILED -> {
                    Toast.makeText(this, dto.data?.error, Toast.LENGTH_SHORT).show()
                }
            }
            loadingDialog.dismiss()
        })

    }

    fun checkRegistered() {
        if (checkEmailEmpty() &&
            checkEmailFormat() &&
            checkPasswordEmpty() &&
            checkConfirmPasswordEmpty() &&
            checkPasswordAndConfirmPassword() &&
            checkPhoneEmpty() &&
            checkPhoneFormat() &&
            checkAddress()
        ) {
            loadingDialog.show(supportFragmentManager,loadingDialog.tag)
            viewModel.postRegistered(RegisteredBo(accountEditText.getText(),passwordEditText.getText(),nameEditText.getText(),phoneEditText.getText(),addressEditText.getText()))

        }
    }

    private fun checkEmailEmpty(): Boolean {
        if (accountEditText.getText() == "") {
            Toast.makeText(this, getString(R.string.email_empty), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun checkEmailFormat(): Boolean {
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

    private fun checkConfirmPasswordEmpty(): Boolean {
        if (confirmPasswordEditText.getText() == "") {
            Toast.makeText(this, getString(R.string.confirm_password_empty), Toast.LENGTH_SHORT)
                .show()
            return false
        }
        return true
    }

    private fun checkPasswordAndConfirmPassword(): Boolean {
        if (passwordEditText.getText() != confirmPasswordEditText.getText()) {
            Toast.makeText(this, getString(R.string.password_not_same), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun checkPhoneEmpty(): Boolean {
        if (phoneEditText.getText() == "") {
            Toast.makeText(this, getString(R.string.phone_empty), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun checkPhoneFormat(): Boolean {
        if (!phoneEditText.getText().isTaiwanPhone()) {
            Toast.makeText(this, getString(R.string.phone_format_error), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun checkAddress(): Boolean {
        if (addressEditText.getText() == "") {
            Toast.makeText(this, getString(R.string.address_empty), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


}
