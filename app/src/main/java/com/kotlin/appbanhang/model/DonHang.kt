package com.kotlin.appbanhang.model


class GioHangController() {

    companion object {
        val manggiohang = arrayListOf<DonHang>()

        fun addSanPham(sanPham: SanPham) {
            // Nếu giỏ hàng trống thì thêm luôn
            if (manggiohang.isEmpty()) {
                DonHang(
                    sanPham = sanPham,
                    soluong = 1
                )
                return
            }

            manggiohang.forEach {
                // Nếu sản phẩm đã tồn tại thì thêm số lượng
                if (it.sanPham.id == sanPham.id) {
                    it.soluong += 1
                } else {
                    // Nếu sản phẩm chưa tồn tại thì thêm vào
                    DonHang(
                        sanPham = sanPham,
                        soluong = 1
                    )
                }
            }
        }


        fun deleteSanPham(){

        }
    }
}

class DonHang(
    var sanPham: SanPham,
    var soluong: Int
)