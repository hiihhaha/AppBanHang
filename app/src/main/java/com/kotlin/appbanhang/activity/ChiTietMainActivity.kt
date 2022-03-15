package com.kotlin.appbanhang.activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.kotlin.appbanhang.R
import com.kotlin.appbanhang.model.GioHangControler
import com.kotlin.appbanhang.model.SanPham
import kotlinx.android.synthetic.main.activity_chi_tiet_main.*
import java.text.DecimalFormat

class ChiTietMainActivity : AppCompatActivity() {
    var gioHangControler = GioHangControler()
    var sanPham: SanPham? = null
    var soLuong = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chi_tiet_main)
        // Lấy dữ liệu qua intent bằng key = "dienthoai"
        sanPham = intent.getSerializableExtra("dienthoai") as SanPham
        initControl()
        // Setup Spinner
        setupSpinner()

        // Check sản phẩm k null thì hiển thị lên màn hình
        sanPham?.let {
            showSanPham(it)
        }

        // Lần đâu vào màn hình kiểm tra giỏ hàng và hiển thị
        updateUIGioHang()
    }

    private fun initControl() {
        img_cart.setOnClickListener {
            var intent = Intent(this,GioHangActivity::class.java)
            startActivity(intent)
        }
        img_back.setOnClickListener { onBackPressed() }
        btn_them.setOnClickListener {
            sanPham?.let { gioHangControler.addSanpham(it, soLuong) }
            // Cập nhật lại hiển thị giỏ hàng
            updateUIGioHang()
        }
    }

    private fun setupSpinner() {
        // Tạo danh sách số lượng
        val listSoluong = arrayListOf<Int>()

        // Add số lượng
        for (i in 1..10) {
            listSoluong.add(i)
        }

        // Tạo adapter
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, listSoluong)

        // truyền adapter vào spinner
        spinner.adapter = adapterSpinner

        // Chọn giá trị mặc định ban đầu ở vị trí đầu tiên
        spinner.setSelection(0)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Khi chọn 1 item trong danh sách số lượng nó sẽ vào đây
                // position là vị trí của item được chọn

                soLuong = listSoluong[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Khi k chọn gì nó sẽ vào đây
            }
        }
    }

    // Hiển thị sản phẩm
    private fun showSanPham(sanPham: SanPham) {
        tv_tensp.text = sanPham.ten
        tv_motachitiet.text = sanPham.mota
        Glide.with(this).load(sanPham.hinhanh).into(img_chitiet)
        val formatter = DecimalFormat("#,###")
        val giaSP = formatter.format(sanPham.gia)
        tv_gia.text = "Giá : $giaSP Đ"
    }

    // Hiển thị giỏ hàng
    private fun updateUIGioHang() {
        var soLuong = 0
        GioHangControler.manggiohang.forEach {
            // Cộng lại tổng số lượng trong giỏ hàng
            soLuong += it.soluong
        }
        // Kiểm tra nếu số lượng lớn hơn 0 thì hiển thị k thì ẩn đi
        txt_soluong.isVisible = soLuong > 0
        // Hiển thị số lên giỏ hàng
        txt_soluong.text = soLuong.toString()
    }
}