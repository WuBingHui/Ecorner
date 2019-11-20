package com.anthony.ecorner.main.personal.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.anthony.ecorner.R
import com.anthony.ecorner.dto.Status
import com.anthony.ecorner.dto.personal.resquest.ProfileBo
import com.anthony.ecorner.dto.personal.resquest.UpdateProfileBo
import com.anthony.ecorner.koin.Properties
import com.anthony.ecorner.main.base.BaseActivity
import com.anthony.ecorner.main.login.view.LoginActivity
import com.anthony.ecorner.main.personal.view.viewModel.PersonalViewModel
import com.anthony.ecorner.widget.CustomLoadingDialog
import kotlinx.android.synthetic.main.activity_personal_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonalInfoActivity : BaseActivity() {
    private val viewmodel by viewModel<PersonalViewModel>()
    private val loadingDialog = CustomLoadingDialog.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)

        initView()
        initViewModel()
        loadingDialog.show(supportFragmentManager, loadingDialog.tag)
        viewmodel.postProfile(ProfileBo(Properties.getId()))
    }

    private fun initView() {

        saveBtn.setOnClickListener {
            loadingDialog.show(supportFragmentManager, loadingDialog.tag)
            viewmodel.postUpdateProfile(
                UpdateProfileBo(
                    addressEditText.text.toString(),
                    bankCodeSpinner.selectedItem.toString(),
                    bankAccountEditText.text.toString(),
                    nickNameEditText.text.toString()
                )
            )
        }
    }


    private fun initViewModel() {
        viewmodel.onProfile.observe(this, Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {
                    dto.data?.user?.let {
                        accountEditText.setText(it.username)
                        nickNameEditText.setText(it.name)
                        phoneEditText.setText(it.phone_number)
                        addressEditText.setText(it.address)
                        bankCodeLabel.text = it.bank_code
                        bankAccountEditText.setText(it.bank_number)
                    }
                }
                Status.FAILED -> {
                    Toast.makeText(this, dto.data?.error, Toast.LENGTH_SHORT).show()
                }
            }
            loadingDialog.dismiss()
        })

        viewmodel.onUpdateProfile.observe(this, Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show()
                    Properties.setName(nickNameEditText.text.toString())
                    viewmodel.postProfile(ProfileBo(Properties.getId()))
                }
                Status.FAILED -> {
                    Toast.makeText(this, dto.data?.error, Toast.LENGTH_SHORT).show()
                    loadingDialog.dismiss()
                }
            }
        })

    }

}
