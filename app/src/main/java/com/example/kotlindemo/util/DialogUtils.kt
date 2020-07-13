package com.example.kotlindemo.util

import android.app.AlertDialog
import android.app.Dialog
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.StringUtils
import com.example.kotlindemo.R


object DialogUtils {

    private val dialogs = mutableListOf<Dialog>()

    interface OnClickConfirmListener {
        fun onClick();
    }
    interface OnClickCancelListener {
        fun onClick();
    }

    fun showLogoutDialog(onClickConfirmListener: OnClickConfirmListener){
        val dialog = AlertDialog.Builder(ActivityUtils.getTopActivity())
            .setTitle(StringUtils.getString(R.string.confirm_logout))
            .setPositiveButton(
                StringUtils.getString(R.string.confirm)
            ) { dialog, which ->
                dialog.dismiss()
                onClickConfirmListener.onClick()
                // 处理逻辑
            }
            .setNegativeButton(
                StringUtils.getString(R.string.cancel)
            ) { dialog, which ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
        dialogs.add(dialog)
    }

    fun dismissAll() {
        dialogs.forEach {
            if (it.isShowing)
                it.dismiss()
        }
    }
}