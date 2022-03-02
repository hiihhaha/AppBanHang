package com.kotlin.appbanhang.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.GravityCompat
import com.kotlin.appbanhang.R
import com.kotlin.appbanhang.model.SanPham
import kotlinx.android.synthetic.main.activity_chi_tiet_main.*
import kotlinx.android.synthetic.main.activity_main.*

class ChiTietMainActivity : AppCompatActivity() {

    var sanPham : SanPham? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chi_tiet_main)
        actionBar()



        // Lấy dữ liệu qua intent bằng key = "dienthoai"
        sanPham = intent.getSerializableExtra("dienthoai") as SanPham

    }



    private fun actionBar() {
        setSupportActionBar(toolbarmanhinhchinh)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarmanhinhchinh.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size)
        toolbarmanhinhchinh.setNavigationOnClickListener {
            drawelayout.openDrawer(GravityCompat.START)
        }
    }
}