package com.example.kotlindemo.adaper

import android.graphics.Color
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.mvp.model.entity.Article
import com.example.kotlindemo.mvp.model.entity.NavBean
import com.google.android.flexbox.FlexboxLayout
import java.util.*

class NavAdapter(datas: MutableList<NavBean>) :
    BaseQuickAdapter<NavBean, BaseViewHolder>(R.layout.item_nav_desc, datas) {

    private val viewList = mutableListOf<TextView>()

    @ExperimentalStdlibApi
    override fun convert(holder: BaseViewHolder, item: NavBean) {
        holder.setText(R.id.tv_title, item.name)
        val flexboxLayout = holder.getView<FlexboxLayout>(R.id.fl_container)
        if (item.articles.size > viewList.size) {
            for (i in 1..item.articles.size - viewList.size) {
                val textView = createTextView()
                viewList.add(textView)
            }
        } else if (item.articles.size < viewList.size) {
            for (i in 1..viewList.size - item.articles.size) {
                viewList.removeLast()
            }
        } else {

        }
        val size = item.articles.size -1
        LogUtils.d("--viewList.size=${viewList.size},  item.articles.size=${item.articles.size}")
        for (i in 0..size) {
            viewList[i].text = item.articles[i].title
        }
//        flexboxLayout.removeAllViews()
        viewList.forEach {
            flexboxLayout.addView(it)
        }
    }

    private fun createTextView(): TextView {
        val textView = TextView(context)
        textView.setTextColor(randomColor())
        textView.setBackgroundColor(ColorUtils.getColor(R.color.Grey100))
        textView.setPadding(
            SizeUtils.dp2px(10f),
            SizeUtils.dp2px(6f),
            SizeUtils.dp2px(10f),
            SizeUtils.dp2px(6f)
        )
        val lp = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(
            SizeUtils.dp2px(6f),
            SizeUtils.dp2px(6f),
            SizeUtils.dp2px(6f),
            SizeUtils.dp2px(6f)
        )
        textView.layoutParams = lp
        return textView
    }

    /**
     * 获取随机rgb颜色值
     */
    fun randomColor(): Int {
        val random = Random()
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        var red = random.nextInt(190)
        var green = random.nextInt(190)
        var blue = random.nextInt(190)
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red, green, blue)
    }
}