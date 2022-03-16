package com.kotlin.appbanhang.adapter
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlin.appbanhang.R
import com.kotlin.appbanhang.model.DonHang
import com.kotlin.appbanhang.model.GioHangControler
import java.text.DecimalFormat

class GioHangAdapter(
    var context: Context,
    var listDonHang: ArrayList<DonHang>,
    var onItemDonHangChange: (DonHang) -> Unit
) : RecyclerView.Adapter<GioHangAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GioHangAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_giohang, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GioHangAdapter.ViewHolder, position: Int) {
        var donHang = listDonHang[position]
        var sanPham = donHang.sanPham
        Glide.with(context).load(sanPham.hinhanh).into(holder.img_giohang)
        holder.txttensp.text = sanPham.ten
        val formatter = DecimalFormat("#,###")
        val giaSP = formatter.format(sanPham.gia)
        holder.txtgiasp.text = "Giá : $giaSP Đ"
        holder.txtsoluong.text = donHang.soluong.toString()
        holder.txttongtien.text = (sanPham.gia * donHang.soluong).toString()
        holder.btntru.setOnClickListener {
            // nếu số lượng lớn hơn 1 thì mới được trừ
            if (donHang.soluong > 1) {
                donHang.soluong -= 1
                // cập nhật lại giao diện tính tiền
                holder.txtsoluong.text = donHang.soluong.toString()
                holder.txttongtien.text = (sanPham.gia * donHang.soluong).toString()
                //thông báo cho màn hình giỏ hàng biết đơn hàng có sự thay đổi
                onItemDonHangChange.invoke(donHang)
            } else {
                AlertDialog.Builder(context)
                    .setTitle("Thông Báo")
                    .setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng")
                    .setPositiveButton(
                        "Đồng ý"
                    ) { _, _ ->
                        // Click đồng ý thì xóa sản phẩm
                        GioHangControler.manggiohang.remove(donHang)
                        // Cập nhật giao diện cho list đơn hàng
                        notifyItemChanged(position)
                        //thông báo cho màn hình giỏ hàng biết đơn hàng có sự thay đổi
                        onItemDonHangChange.invoke(donHang)
                    }.setNegativeButton("Hủy bỏ") { dialog, _ ->
                        // Click hủy bỏ thì đóng dialog
                        dialog.dismiss()
                    }.show()
            }
        }
        holder.btncong.setOnClickListener {
            donHang.soluong += 1
            holder.txtsoluong.text = donHang.soluong.toString()
            holder.txttongtien.text = (sanPham.gia * donHang.soluong).toString()
            onItemDonHangChange.invoke(donHang)
        }
    }
    override fun getItemCount(): Int = listDonHang.size
    class ViewHolder(itemVew: View) : RecyclerView.ViewHolder(itemVew) {
        var btntru = itemVew.findViewById<ImageView>(R.id.img_item_tru)
        var btncong = itemVew.findViewById<ImageView>(R.id.img_item_cong)
        var img_giohang = itemVew.findViewById<ImageView>(R.id.img_item_giohang)
        var txttensp = itemVew.findViewById<TextView>(R.id.txt_item_tensp)
        var txtgiasp = itemVew.findViewById<TextView>(R.id.txt_item_gia)
        var txtsoluong = itemVew.findViewById<TextView>(R.id.txt_giohang_sl)
        var txttongtien = itemVew.findViewById<TextView>(R.id.txt_giohang_tonggiasp)
    }
}
