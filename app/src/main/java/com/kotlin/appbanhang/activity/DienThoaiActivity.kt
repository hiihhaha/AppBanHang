package com.kotlin.appbanhang.activity

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
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_dien_thoai.*

class DienThoaiActivity : AppCompatActivity() {
    var apiBanHang = RetrofitCilent.getInstance(Utils.BaseUrl)?.create(ApiBanHang ::class.java)
    var adapterDt : DienThoaiAdapter?=null
    var listDt = ArrayList<SanPham>()

    lateinit var dialog : Loadingdialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dien_thoai)
        dialog = Loadingdialog(this)

        setUpRecyclerviewSpMoi()
        dialog.showDialog()
        getSpDienThoai()
    }

    private fun setUpRecyclerviewSpMoi () {
        adapterDt = DienThoaiAdapter(this,listDt)

        rcv_dienthoai.layoutManager = LinearLayoutManager(this)

        rcv_dienthoai.adapter = adapterDt
    }

    private fun getSpDienThoai(){ apiBanHang?.getChiTiet(1)
        //chạy luồng dữ liệu lớn vào đây
        ?.subscribeOn(Schedulers.io())
        // dữ liệu nhẹ thì chạy vào UI Thread
        ?.observeOn(AndroidSchedulers.mainThread())
        ?.subscribe( object : Observer<SanPhamResponse> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(sanPhamResponse: SanPhamResponse) {
                dialog.hideDialog() //  gọi thành công/thất bại sẽ ẩn đi
                sanPhamResponse.result?.toMutableList()?.let {
                    listDt.addAll(it)
                    adapterDt?.notifyDataSetChanged()
                }

            }

            override fun onError(e: Throwable) {
                dialog.hideDialog()
                println(e.message)
                Toast.makeText(this@DienThoaiActivity,e.message,Toast.LENGTH_SHORT).show()

            }

            override fun onComplete() {

            }


        })

    }
}