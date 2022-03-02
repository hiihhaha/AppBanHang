package com.kotlin.appbanhang.retrofit

import com.kotlin.appbanhang.model.LoaiSpResponse
import com.kotlin.appbanhang.model.SanPhamResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ApiBanHang {
    @GET("getloaisp.php")
    // lấy danh sách sản phẩm ở link về
    fun getDanhSachLoaiSP(): Single<LoaiSpResponse>


    @GET("getLoaiSpMoi.php")
    fun getDanhSachSpMoi(): Single<SanPhamResponse>

    @GET("getchitiet.php")
    fun getChiTiet(
        @Query("loai") loai: Int
    ): Single<SanPhamResponse>


}