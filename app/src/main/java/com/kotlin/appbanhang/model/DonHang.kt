package com.kotlin.appbanhang.model


class  DonHang(
    var sanPham: SanPham,
    var soluong : Int,
)

class GioHangControler() {

    fun addSanpham(sanpham : SanPham, soluong: Int = 1){

        // Nếu giỏ hàng trống thì thêm luôn
        if (manggiohang.isEmpty()){
            manggiohang.add(
                DonHang(
                    sanPham = sanpham,
                    soluong = 1
                ))
            return
        }
        manggiohang.forEach{
            // Nếu sản phẩm đã tồn tại thì thêm số lượng
            if(it.sanPham.id == sanpham.id){
                it.soluong += soluong
                return
            }
        }

        // Nếu sản phẩm chưa tồn tại thì thêm vào
        manggiohang.add(
            DonHang(
                sanPham = sanpham,
                soluong = soluong
            ))
        return
    }

    fun getMangGioHang() : ArrayList<DonHang> {
        return manggiohang
    }


    companion object{
        val manggiohang = arrayListOf<DonHang>()
    }
}
