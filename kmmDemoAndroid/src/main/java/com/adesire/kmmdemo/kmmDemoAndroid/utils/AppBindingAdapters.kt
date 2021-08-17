package com.adesire.kmmdemo.kmmDemoAndroid.utils

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.NumberFormat


@BindingAdapter("showMessageOnTextView")
fun TextView.showMessageOnTextView(message: String) {
    this.text = message
}

@BindingAdapter("viewVisibility")
fun View.updateViewVisibility(visibility: Int) {
    this.visibility = visibility
}

@BindingAdapter("buttonState")
fun Button.setButtonState(boolean: Boolean) {
    isEnabled = boolean
}

@BindingAdapter("showFormattedText")
fun TextView.showFormattedText(text: Long) {
    this.text = NumberFormat.getInstance().format(text)
}

@BindingAdapter("imgSrc")
fun ImageView.loadImageWithGlide(url: String?) {
    val image = if (!url.isNullOrEmpty())
        url
    else
        "https://cdn.pixabay.com/photo/2020/07/01/12/58/icon-5359553_960_720.png"
    Glide.with(this.context).load(image).into(this)
}
