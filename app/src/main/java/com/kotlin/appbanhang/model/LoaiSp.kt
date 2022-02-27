package com.kotlin.appbanhang.model

class LoaiSpResponse (
    var success : Boolean?,
    var message : String?,
    var result : List<LoaiSp>?
)


class LoaiSp (
    var id : Int,
    var name : String,
    var hinhanh : String,
        ) {
}