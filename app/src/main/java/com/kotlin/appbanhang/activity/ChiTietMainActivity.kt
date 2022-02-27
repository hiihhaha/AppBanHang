package com.kotlin.appbanhang.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kotlin.appbanhang.R
import com.kotlin.appbanhang.model.SanPham

class ChiTietMainActivity : AppCompatActivity() {

    var sanPham : SanPham? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chi_tiet_main)

        // Lấy dữ liệu qua intent bằng key = "dienthoai"
        sanPham = intent.getSerializableExtra("dienthoai") as SanPham





    }
}