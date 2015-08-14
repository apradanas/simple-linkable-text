package com.apradanas.simplelinkabletext;

import java.util.regex.Pattern;

/**
 * Created by @apradanas
 */
public class Link {

    public enum TextStyle {
        NORMAL, BOLD, ITALIC, BOLD_ITALIC
    }

    private String mText;
    private Pattern mPattern;
    private int mTextColor = 0;
    private TextStyle mTextStyle = TextStyle.NORMAL;
    private boolean mUnderlined = true;

    private OnClickListener mClickListener;

    public Link(Link link) {
        this.mText = link.getText();
        this.mPattern = link.getPattern();
        this.mClickListener = link.getClickListener();
        this.mTextColor = link.getTextColor();
        this.mTextStyle = link.getTextStyle();
        this.mUnderlined = link.isUnderlined();
    }

    public Link(String text) {
        this.mText = text;
        this.mPattern = null;
    }

    public Link(Pattern pattern) {
        this.mPattern = pattern;
        this.mText = null;
    }

    public String getText() {
        return mText;
    }

    public Link setText(String text) {
        this.mText = text;
        return this;
    }

    public Pattern getPattern() {
        return mPattern;
    }

    public Link setPattern(Pattern pattern) {
        this.mPattern = pattern;
        return this;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public Link setTextColor(int textColor) {
        this.mTextColor = textColor;
        return this;
    }

    public TextStyle getTextStyle() {
        return mTextStyle;
    }

    public Link setTextStyle(TextStyle textStyle) {
        this.mTextStyle = textStyle;
        return this;
    }

    public boolean isUnderlined() {
        return mUnderlined;
    }

    public Link setUnderlined(boolean underlined) {
        this.mUnderlined = underlined;
        return this;
    }

    public OnClickListener getClickListener() {
        return mClickListener;
    }

    public Link setClickListener(OnClickListener clickListener) {
        this.mClickListener = clickListener;
        return this;
    }

    public interface OnClickListener {
        void onClick(String text);
    }
}
