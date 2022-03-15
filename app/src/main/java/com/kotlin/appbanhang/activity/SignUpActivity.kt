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
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    var apiBanHang = RetrofitCilent.getInstance(Utils.BaseUrl)?.create(ApiBanHang::class.java)
    var strEmail = ""
    var strPassword = ""
    var strRepass = ""
    var strNumberphone = ""
    var strUsername  = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initControll()



    }

    private fun initControll() {
        btn_dangky.setOnClickListener {
            // kiem tra o phia app thanh cong thi call api
            if (isValidateSuccess() == true){
                dangKy()
            }

        }

    }

    private fun isValidateSuccess() : Boolean {
        strEmail = email.text.toString().trim()
        strPassword = pass.text.toString().trim()
        strRepass = r_pass.text.toString().trim()
        strNumberphone = number.text.toString().trim()
        strUsername = username.text.toString().trim()

        when {
            (TextUtils.isEmpty(strEmail)) -> {
                Toast.makeText(
                    this,
                    "Bạn chưa nhập email",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            (TextUtils.isEmpty(strPassword)) ->{
                Toast.makeText(
                    this,
                    "Bạn chưa nhập mật khẩu",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            (TextUtils.isEmpty(strRepass)) ->{
                Toast.makeText(
                    this,
                    "Vui lòng nhập lại mật khẩu",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            (TextUtils.isEmpty(strNumberphone)) ->{
                Toast.makeText(
                    this,
                    "Bạn chưa nhập số điện thoại",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            (TextUtils.isEmpty(strUsername)) -> {
                Toast.makeText(
                    this,
                    "Bạn chưa nhập tên",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            (strPassword != strRepass) -> {
                Toast.makeText(
                    this,
                    "Mật khẩu không trùng",
                    Toast.LENGTH_SHORT).show()
                return false
            }
            else -> return true
        }
    }
    private fun dangKy(){
        apiBanHang?.dangKy(strEmail,strPassword,strUsername,strNumberphone )
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<UserResponse> {
                override fun onSuccess(userResponse : UserResponse) {
                    if (userResponse.success == true) {
                         userResponse.result?.getOrNull(0)?.let {
                             // Lưu giá trị user mà server trả về cho biến static user
                             Utils.user = it
                         }

                        Toast.makeText(this@SignUpActivity,"Đăng ký thành công",Toast.LENGTH_SHORT).show()
                        var intent = Intent(this@SignUpActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@SignUpActivity,userResponse.message,Toast.LENGTH_SHORT).show()
                    }

                }
                override fun onError(e: Throwable) {
                    Toast.makeText(this@SignUpActivity, e.message, Toast.LENGTH_SHORT).show()
                }
                override fun onSubscribe(d: Disposable) {
                }
            })
    }
}


