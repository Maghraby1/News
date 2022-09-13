package com.maghraby.news.utils

import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.maghraby.news.R
import java.util.regex.Pattern

fun ImageView.setImage(image_url: String?=""){
    Glide.with(this.context)
        .load(image_url)
        .placeholder(R.drawable.ic_no_image)
        .into(this)
}
private val urlPattern: Pattern = Pattern.compile(
    "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
            + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
            + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
    Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
)

fun TextView.setTime(time:String){
    this.text = time.split("T")[1].split("+")[0]
}
fun TextView.setDate(date:String){
    this.text = date.split("T")[0]
}

fun TextView.clickableLink(longText:String=""){
    try {
        val str = SpannableString(longText)
        val matcher = urlPattern.matcher(longText)
        var matchStart: Int
        var matchEnd: Int

        while (matcher.find()){
            matchStart = matcher.start(1)
            matchEnd = matcher.end()

            var url = longText.substring(matchStart, matchEnd)
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "https://$url"

            val clickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    this@clickableLink .context.startActivity(intent)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.underlineColor=resources.getColor(R.color.blue,null)
                    ds.underlineThickness=1f
                    ds.color=resources.getColor(R.color.blue,null)
                    ds.isUnderlineText = false
                }
            }
            str.setSpan(clickableSpan, matchStart, matchEnd, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        }
        this.text = str
        this.movementMethod = LinkMovementMethod.getInstance()
    }catch (e: Exception){
        e.printStackTrace()
    }
}