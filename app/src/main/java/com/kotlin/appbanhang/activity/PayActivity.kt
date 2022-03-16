package com.kotlin.appbanhang.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.kotlin.appbanhang.R
import com.kotlin.appbanhang.model.SanPham
import kotlinx.android.synthetic.main.activity_gio_hang.*
import kotlinx.android.synthetic.main.activity_pay.*
import java.text.DecimalFormat

class PayActivity : AppCompatActivity() {
    var diachi = ""
    var tongtien = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)
        tongtien = intent.getIntExtra("tongtien",0)
        initControl()
        initView()

    }

    private fun initView() {
        val formatter = DecimalFormat("#,###")
        val giaSP = formatter.format(tongtien)
        txt_tongtienthanhtoan.text = "$giaSP Đ"

    }

    private fun initControl (){
        img_back3.setOnClickListener{onBackPressed()}
        btn_thanhtoan.setOnClickListener {
            if (isValidateSuccess()){


            }
        }
    }




    private fun isValidateSuccess() : Boolean {
        var diachi = edt_diachi.text.toString().trim()
        if (TextUtils.isEmpty(diachi)) {
            Toast.makeText(this,"Bạn chưa nhập địa chỉ",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}