package com.kotlin.appbanhang.retrofit

import com.kotlin.appbanhang.model.LoaiSpResponse
import com.kotlin.appbanhang.model.SanPhamResponse
import com.kotlin.appbanhang.model.UserResponse
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
    @FormUrlEncoded
    @POST("dangky.php")
    fun dangKy(
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("username") username : String,
        @Field("numberphone") numberphone : String
    ) : Single<UserResponse>

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String,
    ) : Single<UserResponse>
    @FormUrlEncoded
    @POST("resetpassword.php")
    fun resetpass(
        @Field("email") email : String,
    ) : Single<UserResponse>


}