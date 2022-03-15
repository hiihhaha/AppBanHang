package com.kotlin.appbanhang.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.kotlin.appbanhang.R
import com.kotlin.appbanhang.model.UserResponse
import com.kotlin.appbanhang.retrofit.ApiBanHang
import com.kotlin.appbanhang.retrofit.RetrofitCilent
import com.kotlin.appbanhang.utils.Utils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {
    var apiBanHang = RetrofitCilent.getInstance(Utils.BaseUrl)?.create(ApiBanHang::class.java)
    var email = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        initControll()


    }

    private fun initControll() {
        btn_rspassword.setOnClickListener {
            if (isValidateSuccess()) {
                resetpass()
                // ????????????????????????
                progressbar.visibility

            }
        }
    }

    private fun isValidateSuccess(): Boolean {
        email = edt_email.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(
                this,
                "Bạn chưa nhập email",
                Toast.LENGTH_SHORT)
            return false
        }

        return true
    }

    private fun resetpass() {
        apiBanHang?.resetpass(email)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<UserResponse> {
                override fun onSuccess(userResponse: UserResponse) {
                    if (userResponse.success == true) {
                        userResponse.result?.getOrNull(0)?.let {
                            Utils.user = it

                        }
                        Toast.makeText(
                            this@ResetPasswordActivity,
                            "reset pass thành công",
                            Toast.LENGTH_SHORT
                        ).show()
                        var intent = Intent(this@ResetPasswordActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@ResetPasswordActivity,
                            userResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@ResetPasswordActivity, e.message, Toast.LENGTH_SHORT).show()
                }

                override fun onSubscribe(d: Disposable) {
                }
            })


    }
}