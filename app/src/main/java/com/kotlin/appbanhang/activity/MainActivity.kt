package com.kotlin.appbanhang.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.kotlin.appbanhang.R
import kotlinx.android.synthetic.main.activity_main.*
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.appbanhang.adapter.LoaiSpAdapter
import com.kotlin.appbanhang.adapter.SpMoiAdapter
import com.kotlin.appbanhang.model.LoaiSp
import com.kotlin.appbanhang.model.LoaiSpResponse
import com.kotlin.appbanhang.model.SanPham
import com.kotlin.appbanhang.model.SanPhamResponse
import com.kotlin.appbanhang.retrofit.ApiBanHang
import com.kotlin.appbanhang.retrofit.RetrofitCilent
import com.kotlin.appbanhang.utils.Utils.Companion.BaseUrl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_dien_thoai_lap_top.*


class MainActivity : AppCompatActivity() {
    var adapterSpMoi: SpMoiAdapter? = null
    var listSpMoi = ArrayList<SanPham>()
    var listSanPham = ArrayList<LoaiSp>()
    var adapterSanPham: LoaiSpAdapter? = null
    var apiBanHang = RetrofitCilent.getInstance(BaseUrl)?.create(ApiBanHang::class.java)

    companion object {
        const val TRANG_CHU = 0
        const val DIEN_THOAI = 1
        const val LAP_TOP = 2
        const val LIEN_HE = 3
        const val THONG_TIN = 4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionBar()
        actionViewFlipper()
        setUpRecyclerview()
        setUpRecyclerviewSpMoi()
        getLoaiSpMoi()
        getDanhSachLoaiSP()
    }

    //l???y s???n ph???m t??? sv v???
    private fun getDanhSachLoaiSP() {
        apiBanHang?.getDanhSachLoaiSP()
            //ch???y lu???ng d??? li???u l???n v??o ????y
            ?.subscribeOn(Schedulers.io())
            // d??? li???u nh??? th?? ch???y v??o Main Thread
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<LoaiSpResponse> {
                override fun onSuccess(loaiSPResponse: LoaiSpResponse) {
                    // Ki???m tra danh s??ch s???n ph???m tr??? v??? n???u k null th?? add v??o danh s??ch
                    loaiSPResponse.result?.toMutableList()?.let {
                        // Add v??o danh s??ch s???n ph???m
                        listSanPham.addAll(it)
                        // C???p nh???t l???i giao di???n recycleview sau khi th??m s???n ph???m
                        adapterSanPham?.notifyDataSetChanged()
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }

                override fun onSubscribe(d: Disposable) {
                }
            }
            )
    }

    private fun getLoaiSpMoi() {
        apiBanHang?.getDanhSachSpMoi()
            //ch???y lu???ng d??? li???u l???n v??o ????y
            ?.subscribeOn(Schedulers.io())
            // d??? li???u nh??? th?? ch???y v??o UI Thread
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<SanPhamResponse> {
                override fun onSuccess(sanPhamResponse: SanPhamResponse) {
                    sanPhamResponse.result?.toMutableList()?.let {
                        listSpMoi.addAll(it)
                        adapterSpMoi?.notifyDataSetChanged()
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }

                override fun onSubscribe(d: Disposable) {
                }
            })
    }


    private fun actionBar() {
        setSupportActionBar(toolbarmanhinhchinh)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarmanhinhchinh.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size)
        toolbarmanhinhchinh.setNavigationOnClickListener {
            drawelayout.openDrawer(GravityCompat.START)
        }
    }

    private fun actionViewFlipper() {
        var mangquangcao = ArrayList<String>()
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png")
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png")
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg")
        for (i in 0 until mangquangcao.size) {
            var imageView = ImageView(this)
            Glide.with(applicationContext).load(mangquangcao[i]).into(imageView)
            imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            viewflipper.addView(imageView)
        }
        viewflipper.flipInterval = 5000
        viewflipper.isAutoStart = true
        var slide_in = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        var slide_out = AnimationUtils.loadAnimation(this, R.anim.slide_out_right)
        viewflipper.inAnimation = slide_in
        viewflipper.outAnimation = slide_out

    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    /**
     *  Set up danh s??ch s???n ph???m
     * */
    private fun setUpRecyclerview() {
        // Kh???i t???o adapter s???n ph???m
        adapterSanPham = LoaiSpAdapter(applicationContext, listSanPham, ::onClickLoaiSP)

        // Set layout manager cho recycleview
        lv_manhinhchinh.layoutManager = LinearLayoutManager(this)

        // Set adapter s???n ph???m cho recycle view
        lv_manhinhchinh.adapter = adapterSanPham

    }

    private fun setUpRecyclerviewSpMoi() {
        adapterSpMoi = SpMoiAdapter(applicationContext, listSpMoi, ::onItemClick)

        rcv_sp_moi.layoutManager = GridLayoutManager(this, 2)

        rcv_sp_moi.adapter = adapterSpMoi
    }

    /**
     *  Click item loai sp
     * */
    private fun onClickLoaiSP(loaiSp: LoaiSp) {
        Toast.makeText(this, loaiSp.name, Toast.LENGTH_SHORT).show()
        when (loaiSp.id) {
            DIEN_THOAI -> {
                val intent = DienThoaiLapTopActivity.getIntent(this, 1)
                startActivity(intent)
            }
            LAP_TOP -> {
                val intent = DienThoaiLapTopActivity.getIntent(this, 2)
                startActivity(intent)
            }
            TRANG_CHU -> {
                drawelayout.close()
            }

        }
    }

    private fun onItemClick(sanPham: SanPham) {
        Toast.makeText(this, sanPham.ten, Toast.LENGTH_SHORT).show()
        var intent = Intent(this, ChiTietMainActivity::class.java)

        intent.putExtra("dienthoai", sanPham)
        startActivity(intent)
    }

}