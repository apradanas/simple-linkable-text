package com.apradanas.simplelinkabletext;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by @apradanas
 */
public class LinkableTextView extends TextView {

    private List<Link> mLinks = new ArrayList<>();

    private LinkModifier mLinkModifier;

    public LinkableTextView(Context context) {
        super(context);

        init();
    }

    public LinkableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public LinkableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        setMovementMethod(LinkMovementMethod.getInstance());

        mLinkModifier = new LinkModifier(LinkModifier.ViewType.TEXT_VIEW);
    }

    public LinkableTextView setText(String text) {
        super.setText(text);
        mLinkModifier.setText(text);
        return this;
    }

    public LinkableTextView addLink(Link link) {
        mLinks.add(link);

        mLinkModifier.setLinks(mLinks);

        return this;
    }

    public LinkableTextView addLinks(List<Link> links) {
        mLinks.addAll(links);

        mLinkModifier.setLinks(mLinks);

        return this;
    }

    public List<Link> getFoundLinks() {
        return mLinkModifier.getFoundLinks();
    }

    public LinkableTextView build() {
        mLinkModifier.build();
        if (mLinkModifier.getSpannable()!=null){
            setText(mLinkModifier.getSpannable());
        }else{
            setText(mLinkModifier.getText());
        }
        return this;
    }
}
