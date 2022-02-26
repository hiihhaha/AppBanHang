package com.kotlin.appbanhang.model

class SanPhamResponse (
    var success : Boolean?,
    var message : String?,
    var result : List<SanPham>?
)

class SanPham(
    var id : Int,
    var ten : String,
    var gia : Int,
    var hinhanh : String,
    var mota : String,
    var loai : Int
)