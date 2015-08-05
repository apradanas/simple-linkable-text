package com.apradanas.simplelinkabletext;

import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;

import com.apradanas.simplelinkabletext.util.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by @apradanas
 */
public class LinkModifier {

    public enum ViewType {
        TEXT_VIEW, EDIT_TEXT
    }

    private List<Link> mLinks = new ArrayList<>();
    private List<Link> mFoundLinks = new ArrayList<>();

    private String mText;
    private SpannableString mSpannable;
    private Editable mEditable;

    private ViewType mViewType;

    public LinkModifier(ViewType viewType) {
        this.mViewType = viewType;
    }

    public List<Link> getLinks() {
        return mLinks;
    }

    public void setLinks(List<Link> mLinks) {
        this.mLinks = mLinks;
    }

    public List<Link> getFoundLinks() {
        return mFoundLinks;
    }

    public void setFoundLinks(List<Link> mFoundLinks) {
        this.mFoundLinks = mFoundLinks;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public SpannableString getSpannable() {
        return mSpannable;
    }

    public void setSpannable(SpannableString mSpannable) {
        this.mSpannable = mSpannable;
    }

    public Editable getEditable() {
        return mEditable;
    }

    public void setEditable(Editable mEditable) {
        this.mEditable = mEditable;
    }

    public void addLinkToSpan(Link link) {
        if (mSpannable == null) {
            mSpannable = SpannableString.valueOf(mText);
        }

        addLinkToSpan(mSpannable, link);
    }

    private void addLinkToSpan(Spannable s, Link link) {
        Pattern pattern = Pattern.compile(Pattern.quote(link.getText()));
        Matcher matcher = pattern.matcher(mText);

        while (matcher.find()) {

            int start = matcher.start();

            if (start >= 0) {
                int end = start + link.getText().length();

                switch (mViewType) {
                    case TEXT_VIEW:
                        applyLink(link, new Range(start, end), s);
                        break;
                    case EDIT_TEXT:
                        applyLink(link, new Range(start, end));
                        break;
                }
            }
        }
    }

    private void applyLink(final Link link, final Range range) {
        ClickableLinkSpan linkSpan = new ClickableLinkSpan(link, range);
        mEditable.setSpan(linkSpan, range.start, range.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void applyLink(final Link link, final Range range, Spannable text) {
        ClickableLinkSpan linkSpan = new ClickableLinkSpan(link, range);
        text.setSpan(linkSpan, range.start, range.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public void removePreviousSpans() {
        ClickableSpan[] toRemoveSpans = mEditable.getSpans(0, mEditable.length(), ClickableSpan.class);
        for(ClickableSpan toRemoveSpan : toRemoveSpans) {
            mEditable.removeSpan(toRemoveSpan);
        }
    }

    public void convertPatternsToLinks() {
        mFoundLinks.clear();
        mFoundLinks.addAll(mLinks);

        int size = mFoundLinks.size();
        int i = 0;
        while (i < size) {
            if (mFoundLinks.get(i).getPattern() != null) {
                addLinksFromPattern(mFoundLinks.get(i));

                mFoundLinks.remove(i);
                size--;
            } else {
                i++;
            }
        }
    }

    private void addLinksFromPattern(Link linkWithPattern) {
        Pattern pattern = linkWithPattern.getPattern();
        Matcher m = pattern.matcher(mText);

        while (m.find()) {
            Link link = new Link(linkWithPattern).setText(m.group());
            mFoundLinks.add(link);
        }
    }

    public void build() {
        if(mViewType == ViewType.EDIT_TEXT) {
            removePreviousSpans();
        }

        convertPatternsToLinks();

        for (Link link : mFoundLinks) {
            addLinkToSpan(link);
        }
    }
}
