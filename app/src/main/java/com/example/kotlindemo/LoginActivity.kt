package com.example.kotlindemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        fun launch(context: Context) {
            context?.startActivity(Intent(context, LoginActivity::class.java))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (et_account.text.isEmpty()) {
            Toast.makeText(this, "账号为空", Toast.LENGTH_SHORT).show()
            return
        }
        if (et_pwd.text.isEmpty()) {
            Toast.makeText(this, "密码为空", Toast.LENGTH_SHORT).show()
            return
        }
        Toast.makeText(this, "账号${et_account.text}\n密码${et_pwd.text}", Toast.LENGTH_SHORT).show()
    }


}