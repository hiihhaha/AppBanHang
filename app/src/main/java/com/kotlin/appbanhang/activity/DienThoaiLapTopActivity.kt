package com.kotlin.appbanhang.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.appbanhang.R
import com.kotlin.appbanhang.adapter.DienThoaiAdapter
import com.kotlin.appbanhang.model.SanPham
import com.kotlin.appbanhang.model.SanPhamResponse
import com.kotlin.appbanhang.retrofit.ApiBanHang
import com.kotlin.appbanhang.retrofit.RetrofitCilent
import com.kotlin.appbanhang.utils.Utils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_dien_thoai_lap_top.*
import kotlinx.android.synthetic.main.activity_dien_thoai_lap_top.*

class DienThoaiLapTopActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context, loai: Int = 1): Intent {
            val intent = Intent(context, DienThoaiLapTopActivity::class.java).apply {
                putExtra("loai", loai)
            }
            return intent
        }
    }

    var apiBanHang = RetrofitCilent.getInstance(Utils.BaseUrl)?.create(ApiBanHang::class.java)
    var adapterDt: DienThoaiAdapter? = null
    var listDt = ArrayList<SanPham>()
    var loai = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dien_thoai_lap_top)
        toolbarmanhinhdienthoai.setOnClickListener{onBackPressed()}
        loai = intent.getIntExtra("loai", 1)
        setUpRecyclerviewSpMoi()
        getSpDienThoai()
    }

    private fun setUpRecyclerviewSpMoi() {
        adapterDt = DienThoaiAdapter(this, listDt, ::onItemClick)

        rcv_dienthoai.layoutManager = LinearLayoutManager(this)

        rcv_dienthoai.adapter = adapterDt
    }

    private fun getSpDienThoai() {
        apiBanHang?.getChiTiet(loai)
            //chạy luồng dữ liệu lớn vào đây
            ?.subscribeOn(Schedulers.io())
            // dữ liệu nhẹ thì chạy vào UI Thread
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<SanPhamResponse> {
                override fun onSuccess(sanPhamResponse: SanPhamResponse) {
                    sanPhamResponse.result?.toMutableList()?.let {
                        listDt.addAll(it)
                        adapterDt?.notifyDataSetChanged()
                    }
                }
                override fun onError(e: Throwable) {
                    println(e.message)
                    Toast.makeText(this@DienThoaiLapTopActivity, e.message, Toast.LENGTH_SHORT)
                        .show()

                }

                override fun onSubscribe(d: Disposable) {}
            })
    }

    /**
     *  Call back : Click vào 1 sản phẩm trong danh sách điện thoại
     * */
    private fun onItemClick(dienThoai: SanPham) {
        var intent = Intent(this, ChiTietMainActivity::class.java)
        // Truyền dữ liệu qua intent dưới dạng key - value
        // VD : key = "dienthoai" ( Màn nào nhận dữ liệu phải lấy ra theo key này )
        //      value = dienThoai ( Những object phải extend Serializable mới truyền qua thằng intent được )
        intent.putExtra("dienthoai", dienThoai)
        startActivity(intent)
    }
}