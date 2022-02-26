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


class DienThoaiAdapter(

    var context : Context,
    var listDienThoai : ArrayList<SanPham>,

    ) : RecyclerView.Adapter<DienThoaiAdapter.MyViewHolder> () {


    class MyViewHolder (itemVew : View) : RecyclerView.ViewHolder(itemVew) {
        var  txtten = itemVew.findViewById<TextView>(R.id.txtdt_itemten)
        var  txtgia = itemVew.findViewById<TextView>(R.id.txtdt_itemgia)
        var  txtmota = itemVew.findViewById<TextView>(R.id.txtdt_itemmota)
        var  imghinhanh = itemVew.findViewById<ImageView>(R.id.img_itemdt)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_dienthoai,parent,false)
        return DienThoaiAdapter.MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var dienThoai = listDienThoai[position]
        holder.txtten.text = dienThoai.ten
        val formatter = DecimalFormat("#,###")
        val giaSP = formatter.format(dienThoai.gia)

        holder.txtgia.text = "Giá : $giaSP đ"
        holder.txtmota.text = dienThoai.mota
        Glide.with(context).load(dienThoai.hinhanh).into(holder.imghinhanh)
    }

    override fun getItemCount(): Int= listDienThoai.size


}
