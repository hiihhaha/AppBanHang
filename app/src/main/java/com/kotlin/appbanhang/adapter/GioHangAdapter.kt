package com.kotlin.appbanhang.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlin.appbanhang.R
import com.kotlin.appbanhang.model.SanPham
import java.text.DecimalFormat

class GioHangAdapter(
    var context: Context,
    var lisGioHang : ArrayList<SanPham>
) : RecyclerView.Adapter<GioHangAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GioHangAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_giohang,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GioHangAdapter.ViewHolder, position: Int) {
        var sanPham = lisGioHang [position]
        Glide.with(context).load(sanPham.hinhanh).into(holder.img_giohang)
        holder.txttensp.text = sanPham.ten
        val formatter = DecimalFormat("#,###")
        val giaSP = formatter.format(sanPham.gia)
        holder.txtgiasp.text = "Giá : $giaSP Đ"
        holder.txtsoluong.text = sanPham.soluong.toString()
        holder.txttongtien.text = sanPham.tongtien.toString()

    }

    override fun getItemCount(): Int = lisGioHang.size

    class ViewHolder (itemVew : View) : RecyclerView.ViewHolder(itemVew){
        var  img_giohang = itemVew.findViewById<ImageView>(R.id.img_item_giohang)
        var txttensp = itemVew.findViewById<TextView>(R.id.txt_item_tensp)
        var txtgiasp = itemVew.findViewById<TextView>(R.id.txt_item_gia)
        var txtsoluong = itemVew.findViewById<TextView>(R.id.txt_giohang_sl)
        var txttongtien = itemVew.findViewById<TextView>(R.id.txt_giohang_tonggiasp)
    }
}
