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
import com.kotlin.appbanhang.model.LoaiSp

class LoaiSpAdapter(
    var context : Context,
    var listSanPham : ArrayList<LoaiSp>,
    var onCLickItem : (item : LoaiSp) -> Unit
) : RecyclerView.Adapter<LoaiSpAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_sanpham,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Khởi tạo sản phẩm ở vị trí (position) hiện tại
        val sanPham = listSanPham[position]

        // Hiển thị ảnh sản phẩm lên imageview
        Glide.with(context).load(sanPham.hinhanh).into(holder.imgItem)

        // Hiển thị tên sản phẩm lên text view
        holder.txtTensp.text = sanPham.name

        // Bắt sự kiện click item
        holder.itemView.setOnClickListener {
            onCLickItem.invoke(sanPham)
        }
    }

    override fun getItemCount(): Int = listSanPham.size


    class MyViewHolder (itemVew : View) : RecyclerView.ViewHolder(itemVew){
        var imgItem = itemVew.findViewById<ImageView>(R.id.img_item)
        var txtTensp = itemVew.findViewById<TextView>(R.id.item_tensp)

    }

}