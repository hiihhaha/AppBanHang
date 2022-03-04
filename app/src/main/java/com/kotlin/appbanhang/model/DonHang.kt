package com.kotlin.appbanhang.model


class GioHangController() {

    companion object {
        val manggiohang = arrayListOf<DonHang>()

        fun addSanPham(sanPham: SanPham, soluong: Int = 1) {
            // Nếu giỏ hàng trống thì thêm luôn
            if (manggiohang.isEmpty()) {
                manggiohang.add(
                    DonHang(
                        sanPham = sanPham,
                        soluong = soluong
                    )
                )
                return
            }

            manggiohang.forEach {
                // Nếu sản phẩm đã tồn tại thì thêm số lượng
                if (it.sanPham.id == sanPham.id) {
                    it.soluong += soluong
                } else {
                    // Nếu sản phẩm chưa tồn tại thì thêm vào
                    manggiohang.add(
                        DonHang(
                            sanPham = sanPham,
                            soluong = soluong
                        )
                    )
                }
            }
        }


        fun deleteSanPham() {

        }
    }
}

class DonHang(
    var sanPham: SanPham,
    var soluong: Int
)