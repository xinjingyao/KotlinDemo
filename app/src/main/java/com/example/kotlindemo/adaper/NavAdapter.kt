package com.example.kotlindemo.adaper

import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.mvp.model.entity.NavBean
import com.example.kotlindemo.ui.activity.ContentActivity
import com.example.kotlindemo.util.SelectorUtils
import com.google.android.flexbox.FlexboxLayout
import java.util.*

class NavAdapter(datas: MutableList<NavBean>) :
    BaseQuickAdapter<NavBean, NavAdapter.NavViewHolder>(R.layout.item_nav_desc, datas), LoadMoreModule {


    @ExperimentalStdlibApi
    override fun convert(holder: NavViewHolder, item: NavBean) {
        holder.setText(R.id.tv_title, item.name)
        // 利用官方的flexboxlayout控件实现流布局
        val flexboxLayout = holder.getView<FlexboxLayout>(R.id.fl_container)
        if (item.articles.size > holder.viewList.size) {
            for (i in 1..item.articles.size - holder.viewList.size) {
                val textView = createTextView()
                holder.viewList.add(textView)
            }
        } else if (item.articles.size < holder.viewList.size) {
            for (i in 1..holder.viewList.size - item.articles.size) {
                holder.viewList.removeLast()
            }
        } else {
            // 数量相等，暂不处理
        }
        val size = item.articles.size - 1
        LogUtils.d("--viewList.size=${holder.viewList.size},  item.articles.size=${item.articles.size}")
        for (i in 0..size) {
            holder.viewList[i].text = item.articles[i].title
            holder.viewList[i].setOnClickListener {
                ContentActivity.start(
                    context,
                    item.articles[i].id,
                    item.articles[i].title,
                    item.articles[i].link
                )
            }
        }
        /*
         在每次addView之前先全部移除view，避免
         java.lang.IllegalStateException: The specified child already has a parent.
         You must call removeView() on the child's parent first.
         */
        flexboxLayout.removeAllViews()
        holder.viewList.forEach {
            flexboxLayout.addView(it)
        }
    }

    private fun createTextView(): TextView {
        val textView = TextView(context)
        textView.setTextColor(randomColor())
        textView.background = SelectorUtils.getSelector(
            ColorUtils.getColor(R.color.Grey100),
            ColorUtils.getColor(R.color.Blue_Grey),
            SizeUtils.dp2px(2f).toFloat()
        )
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
     * 自定义holder为了维护viewList
     */
    class NavViewHolder(view: View) : BaseViewHolder(view) {
        val viewList = mutableListOf<TextView>()
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