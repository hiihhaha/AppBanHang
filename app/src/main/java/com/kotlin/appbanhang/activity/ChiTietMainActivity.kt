package com.kotlin.appbanhang.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.kotlin.appbanhang.R
import com.kotlin.appbanhang.model.SanPham
import kotlinx.android.synthetic.main.activity_chi_tiet_main.*
import java.text.DecimalFormat

class ChiTietMainActivity : AppCompatActivity() {

    var sanPham : SanPham? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chi_tiet_main)
        // Lấy dữ liệu qua intent bằng key = "dienthoai"
        sanPham = intent.getSerializableExtra("dienthoai") as SanPham

        // Setup Spinner
        setupSpinner()

        // Check sản phẩm k null thì hiển thị lên màn hình
        sanPham?.let {
            initData(it)
        }

        btn_them.setOnClickListener {

        }
    }

    private fun setupSpinner() {
        // Tạo danh sách số lượng
        val listSoluong = arrayListOf<Int>()

        // Add số lượng
        for (i in 0..10){listSoluong.add(i)}

        // Tạo adapter
        val adapterSpinner = ArrayAdapter(this,android.R.layout.simple_spinner_item,listSoluong)

        // truyền adapter vào spinner
        spinner.adapter = adapterSpinner
    }

    private fun initData(sanPham: SanPham) {
        tv_tensp.text = sanPham.ten
        tv_motachitiet.text = sanPham.mota
        Glide.with(this).load(sanPham.hinhanh).into(img_chitiet)
        val formatter = DecimalFormat("#,###")
        val giaSP = formatter.format(sanPham.gia)
        tv_gia.text = "Giá : $giaSP đ"
    }


}