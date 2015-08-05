package com.apradanas.simplelinkabletext;

import android.support.annotation.NonNull;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.apradanas.simplelinkabletext.util.Range;

/**
 * Created by @apradanas
 */
public class ClickableLinkSpan extends ClickableSpan {

    private Link mLink;
    private Range mRange;

    public ClickableLinkSpan(Link link, Range range) {
        this.mLink = link;
        this.mRange = range;
    }

    @Override
    public void onClick(View widget) {
        TextView tv = (TextView) widget;
        Spanned s = (Spanned) tv.getText();

        if(mLink.getClickListener() != null) {
            mLink.getClickListener().onClick(s.subSequence(mRange.start, mRange.end).toString());
        }
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        if(mLink.getTextColor() != 0) {
            ds.setColor(mLink.getTextColor());
        }
        ds.setUnderlineText(mLink.isUnderlined());
    }
}
