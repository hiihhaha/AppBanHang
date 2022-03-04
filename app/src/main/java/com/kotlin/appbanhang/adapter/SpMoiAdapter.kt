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

class SpMoiAdapter(
    var context : Context,
    var listSanPham : ArrayList<SanPham>,
    var setOnclickItem : (item : SanPham) -> Unit
) :RecyclerView.Adapter<SpMoiAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpMoiAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_sp_moi,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpMoiAdapter.ViewHolder, position: Int) {
        var sanPham = listSanPham [position]
        Glide.with(context).load(sanPham.hinhanh).into(holder.imghinhanh)
        holder.txtten.text = sanPham.ten

        // format thành giá tiền vd (1000000 -> 1.000.000đ)
        val formatter = DecimalFormat("#,###")
        val giaSP = formatter.format(sanPham.gia)

        holder.txtgia.text = "Giá : $giaSP đ"
        holder.itemView.setOnClickListener {
            setOnclickItem.invoke(sanPham)
        }
    }

    override fun getItemCount(): Int = listSanPham.size

    class ViewHolder (itemVew : View) : RecyclerView.ViewHolder(itemVew){
        var txtten = itemVew.findViewById<TextView>(R.id.item_ten)
        var txtgia = itemVew.findViewById<TextView>(R.id.item_gia)
        var imghinhanh = itemVew.findViewById<ImageView>(R.id.itemsp_img)


    }

}