package com.apradanas.simplelinkabletext;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by @apradanas
 */
public class LinkableEditText extends EditText implements TextWatcher {

    private List<Link> mLinks = new ArrayList<>();

    private LinkModifier mLinkModifier;
    private OnTextChangedListener mOnTextChangedListener;

    public LinkableEditText(Context context) {
        super(context);

        init();
    }

    public LinkableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public LinkableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        addTextChangedListener(this);
        setMovementMethod(LinkMovementMethod.getInstance());

        mLinkModifier = new LinkModifier(LinkModifier.ViewType.EDIT_TEXT);
    }

    public LinkableEditText addLink(Link link) {
        mLinks.add(link);

        mLinkModifier.setLinks(mLinks);

        return this;
    }

    public LinkableEditText addLinks(List<Link> links) {
        mLinks.addAll(links);

        mLinkModifier.setLinks(mLinks);

        return this;
    }

    public List<Link> getFoundLinks() {
        return mLinkModifier.getFoundLinks();
    }

    public LinkableEditText setTextChangedListener(OnTextChangedListener listener) {
        mOnTextChangedListener = listener;
        return this;
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        if(mOnTextChangedListener != null) {
            mOnTextChangedListener.onTextChanged(text, start, lengthBefore, lengthAfter);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if(mOnTextChangedListener != null) {
            mOnTextChangedListener.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        mLinkModifier.setSpannable(s);

        mLinkModifier.build();

        if(mOnTextChangedListener != null) {
            mOnTextChangedListener.afterTextChanged(s);
        }
    }

    public interface OnTextChangedListener {
        void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter);
        void beforeTextChanged(CharSequence s, int start, int count, int after);
        void afterTextChanged(Editable s);
    }
}
