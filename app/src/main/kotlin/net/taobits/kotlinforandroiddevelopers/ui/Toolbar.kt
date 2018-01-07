package net.taobits.kotlinforandroiddevelopers.ui

import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import net.taobits.kotlinforandroiddevelopers.R
import org.jetbrains.anko.toast
import org.jetbrains.anko.ctx


interface ToolbarManager {
    val toolbar: Toolbar

    var toolbarTitle: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }

    fun initToolbar() {
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_settings -> App.instance.toast("Settings")
                else -> App.instance.toast("Unkown option")
            }
            true
        }
    }

    fun enableHomeAsUp(up: () -> Unit) {
        toolbar.navigationIcon = createUpDrawable()
        toolbar.setNavigationOnClickListener { up() }
    }

    fun createUpDrawable() = with (DrawerArrowDrawable(toolbar.context)) {
        progress = 1f
        this
    }

    fun attachToScroll(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0) toolbar.slideExit() else toolbar.slideEnter()
            }
        })
    }
}

private fun Toolbar.slideExit() {
    if (translationY == 0f) animate().translationY(-height.toFloat())
}

private fun Toolbar.slideEnter() {
    if (translationY < 0f) animate().translationY(0f)
}
