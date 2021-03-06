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
import kotlinx.android.synthetic.main.activity_sign_up.*

class LoginActivity : AppCompatActivity() {
    var apiBanHang = RetrofitCilent.getInstance(Utils.BaseUrl)?.create(ApiBanHang::class.java)
    var email = ""
    var password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initControl()
    }

    private fun initControl() {
        btn_login.setOnClickListener {
            if (isValidateSuccess()) {
                dangnhap()
            }
        }

        txt_dangky.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        txt_rspass.setOnClickListener {
            startActivity(Intent(this,ResetPasswordActivity::class.java))
            finish()
        }
    }
    private fun isValidateSuccess(): Boolean {
        email = login_email.text.toString().trim()
        password = login_password.text.toString().trim()
        when {
            (TextUtils.isEmpty(email)) -> {
                Toast.makeText(
                    this,
                    "Bạn chưa nhập email",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            (TextUtils.isEmpty(password)) -> {
                Toast.makeText(
                    this,
                    "Bạn chưa nhập mật khẩu",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
        }
        return true
    }
    private fun dangnhap() {
        apiBanHang?.login(email, password)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<UserResponse> {
                override fun onSuccess(userResponse: UserResponse) {
                    if (userResponse.success == true) {
                        userResponse.result?.getOrNull(0)?.let {
                            Utils.user = it
                        }
                        Toast.makeText(
                            this@LoginActivity,
                            "Đăng nhập thành công",
                            Toast.LENGTH_SHORT
                        ).show()
                        var intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            userResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
                }

                override fun onSubscribe(d: Disposable) {
                }
            })
    }
}