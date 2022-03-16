package com.kotlin.appbanhang.activity
import android.content.Intent
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
    var tongTienSp = 0
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
        btn_datmua.setOnClickListener {
            var intent = Intent(this,PayActivity::class.java)
            intent.putExtra("tongtien",tongTienSp)
            startActivity(intent)
        }
    }
//có sự thay đổi từ tongtiensp gọi lên biến toàn cục
    private fun updateTongTien(){
         tongTienSp  = 0
        listGioHang.forEach {
            tongTienSp += (it.sanPham.gia * it.soluong)
        }
        txt_tongtien.text = tongTienSp.toString()
    }
}