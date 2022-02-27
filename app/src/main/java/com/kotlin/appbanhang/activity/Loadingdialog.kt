package com.kotlin.appbanhang.activity

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.kotlin.appbanhang.R

class Loadingdialog (
     var context: Context
        ){

    private lateinit var progressDialog : AlertDialog

    init {
        createProgressDialog()
    }

    // Hiển thị dialog
    fun showDialog(){
        progressDialog.show()
    }

    // Ẩn dialog
    fun hideDialog(){
        progressDialog.hide()
    }

    // Khởi tạo dialog
    private fun createProgressDialog(): AlertDialog {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_prorgress, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
        progressDialog = mBuilder.show()
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return progressDialog
    }
}