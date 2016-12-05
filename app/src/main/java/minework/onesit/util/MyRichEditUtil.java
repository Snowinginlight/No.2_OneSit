package minework.onesit.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Parcel;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.QuoteSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.Button;

import minework.onesit.activity.MyApplication;

/**
 * Created by 无知 on 2016/12/1.
 */
public class MyRichEditUtil {
    //粗体
    public static SpannableStringBuilder BoldSpan(CharSequence sub, int start, int end, int Flag) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sub);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, sub.length(), Flag);
        return ssb;
    }

    //斜体
    public static SpannableStringBuilder ItalicSpan(CharSequence sub, int start, int end, int Flag) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sub);
        ssb.setSpan(new StyleSpan(Typeface.ITALIC), 0, sub.length(), Flag);
        return ssb;
    }

    //下划线
    public static SpannableStringBuilder UnderlineSpan(CharSequence sub, int start, int end, int Flag) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sub);
        ssb.setSpan(new UnderlineSpan(), 0, sub.length(), Flag);
        return ssb;
    }

    //字体
    public static SpannableStringBuilder TypefaceSpan(CharSequence sub, int start, int end, int Flag) {
        String[] str = {"serif", "monospace", "sans-serif"};
        int random = (int) (Math.random() * 3);
        SpannableStringBuilder ssb = new SpannableStringBuilder(sub);
        ssb.setSpan(new TypefaceSpan(str[random]), 0, sub.length(), Flag);
        return ssb;
    }

    //上标
    public static SpannableStringBuilder SuperscriptSpan(CharSequence sub, int start, int end, int Flag) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sub);
        Parcel parcel = Parcel.obtain();
        parcel.writeString(sub.toString());
        ssb.setSpan(new SuperscriptSpan(parcel), 0, sub.length(), Flag);
        parcel.recycle();
        return ssb;
    }

    //下标
    public static SpannableStringBuilder SubscriptSpan(CharSequence sub, int start, int end, int Flag) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sub);
        Parcel parcel = Parcel.obtain();
        parcel.writeString(sub.toString());
        ssb.setSpan(new SubscriptSpan(parcel), 0, sub.length(), Flag);
        parcel.recycle();
        return ssb;
    }

    //改变字号
    public static SpannableStringBuilder ChangeSizeSpan(Button button1, Button button2, CharSequence sub, int start, int end, int size, int Flag) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sub);
        ssb.setSpan(new AbsoluteSizeSpan(size, false), 0, sub.length(), Flag);
        button1.setText(String.valueOf(size));
        button2.setText(String.valueOf(size));
        return ssb;
    }

    //删除线
    public static SpannableStringBuilder StrikethroughSpan(CharSequence sub, int start, int end, int Flag) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sub);
        ssb.setSpan(new StrikethroughSpan(), 0, sub.length(), Flag);
        return ssb;
    }

    //引用
    public static SpannableStringBuilder QuoteSpan(CharSequence sub, int start, int end, int Flag) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sub);
        ssb.setSpan(new QuoteSpan(Color.parseColor("#5d9960")), 0, sub.length(), Flag);
        return ssb;
    }

    //字体颜色
    public static SpannableStringBuilder ForegroundColorSpan(Button button, CharSequence sub, int start, int end, int Flag) {
        int[] color = {Color.parseColor("#9bb7b0"), Color.parseColor("#8b8d70"), Color.parseColor("#ebbd70"), Color.parseColor("#dca09c"), Color.parseColor("#b89f87"), Color.parseColor("#7f7788"), Color.parseColor("#2f3135"), Color.parseColor("#5d9960")};
        int random = (int) (Math.random() * 8);
        int c = color[random];
        SpannableStringBuilder ssb = new SpannableStringBuilder(sub);
        ssb.setSpan(new ForegroundColorSpan(c), 0, sub.length(), Flag);
        button.setTextColor(c);
        return ssb;
    }

    //字体背景颜色
    public static SpannableStringBuilder BackgroundColorSpan(Button button, CharSequence sub, int start, int end, int Flag) {
        int[] color = {Color.parseColor("#9bb7b0"), Color.parseColor("#8b8d70"), Color.parseColor("#ebbd70"), Color.parseColor("#dca09c"), Color.parseColor("#b89f87"), Color.parseColor("#7f7788"), Color.parseColor("#2f3135"), Color.WHITE, Color.parseColor("#eff8ed")};
        int random = (int) (Math.random() * 9);
        int c = color[random];
        SpannableStringBuilder ssb = new SpannableStringBuilder(sub);
        ssb.setSpan(new BackgroundColorSpan(c), 0, sub.length(), Flag);
        button.setBackgroundColor(c);
        return ssb;
    }

    //首行缩进
    public static SpannableStringBuilder AddIndentSpan(CharSequence sub, int start, int end) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sub);
        ssb.setSpan(new LeadingMarginSpan.Standard(30), 0, sub.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    //首行退进
    public static SpannableStringBuilder MinusIndentSpan(CharSequence sub, int start, int end) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sub);
        ssb.setSpan(new LeadingMarginSpan.Standard(-30), 0, sub.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    //段点
    public static SpannableStringBuilder BulletSpan(CharSequence sub, int start, int end) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sub);
        ssb.setSpan(new BulletSpan(30, Color.parseColor("#5e9a61")), 0, sub.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    //链接
    public static SpannableStringBuilder LinkSpan(CharSequence sub, int start, int end, String link, int Flag) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sub);
        ssb.setSpan(new URLSpan(link), 0, sub.length(), Flag);
        return ssb;
    }

    //图片
    public static SpannableStringBuilder InsertImageSpan(CharSequence sub, Bitmap bmp) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sub);
        ssb.setSpan(new ImageSpan(MyApplication.getInstance(), bmp, ImageSpan.ALIGN_BASELINE), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

}
