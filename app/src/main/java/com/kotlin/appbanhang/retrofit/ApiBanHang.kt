package com.kotlin.appbanhang.retrofit

import com.kotlin.appbanhang.model.LoaiSpResponse
import com.kotlin.appbanhang.model.SanPhamResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiBanHang {
    @GET("getloaisp.php")
    // lấy danh sách sản phẩm ở link về
    fun getDanhSachLoaiSP(): Observable<LoaiSpResponse>


    @GET("getLoaiSpMoi.php")
    fun getDanhSachSpMoi(): Observable<SanPhamResponse>

    @GET("getchitiet.php/{loai}")
    fun getChiTiet(
        @Path("loai") loai: Int
    ): Observable<SanPhamResponse>


}