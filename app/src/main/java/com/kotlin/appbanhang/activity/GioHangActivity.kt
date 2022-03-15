package com.kotlin.appbanhang.activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.appbanhang.R
import com.kotlin.appbanhang.adapter.GioHangAdapter
import com.kotlin.appbanhang.model.DonHang
import com.kotlin.appbanhang.model.GioHangControler
import kotlinx.android.synthetic.main.activity_gio_hang.*

class GioHangActivity : AppCompatActivity() {
    var adapterGioHang :GioHangAdapter? = null
    var listGioHang = ArrayList<DonHang>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gio_hang)
        initControl ()
        setupRecyclerviewGioHang()
        updateTongTien()
    }

    private fun setupRecyclerviewGioHang() {
        listGioHang = GioHangControler.manggiohang
       adapterGioHang = GioHangAdapter(this,listGioHang,::onItemDonHangChange)
        rcv_giohang.adapter = adapterGioHang
        rcv_giohang.layoutManager = LinearLayoutManager(this)


    }
    private fun onItemDonHangChange (donHang: DonHang) {
        updateTongTien()
    }
    private fun initControl (){
        img_back2.setOnClickListener{onBackPressed()}
    }

    private fun updateTongTien(){
        var tongTien = 0
        listGioHang.forEach {
            tongTien += (it.sanPham.gia * it.soluong)
        }
        txt_tongtien.text = tongTien.toString()
    }
}