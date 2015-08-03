package com.apradanas.simplelinkabletext;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by @apradanas
 */
public class LinkableTextView extends TextView {

    private List<Link> mLinks = new ArrayList<>();

    private String mText;
    private SpannableString spannable;

    public LinkableTextView(Context context) {
        super(context);

        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public LinkableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public LinkableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public LinkableTextView setText(String text) {
        this.mText = text;

        return this;
    }

    public LinkableTextView addLink(Link link) {
        mLinks.add(link);

        return this;
    }

    public LinkableTextView addLinks(List<Link> links) {
        mLinks.addAll(links);

        return this;
    }

    private void addLinkToSpan(Link link) {
        if (spannable == null) {
            spannable = SpannableString.valueOf(mText);
        }

        addLinkToSpan(spannable, link);
    }

    private void addLinkToSpan(Spannable s, Link link) {
        Pattern pattern = Pattern.compile(Pattern.quote(link.getText()));
        Matcher matcher = pattern.matcher(mText);

        while (matcher.find()) {

            int start = matcher.start();

            if (start >= 0) {
                int end = start + link.getText().length();

                applyLink(link, new Range(start, end), s);
            }
        }
    }

    private void applyLink(final Link link, final Range range, Spannable text) {
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                TextView tv = (TextView) widget;
                Spanned s = (Spanned) tv.getText();

                if(link.getClickListener() != null) {
                    link.getClickListener().onClick(s.subSequence(range.start, range.end).toString());
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                if(link.getTextColor() != 0) {
                    ds.setColor(link.getTextColor());
                }
                ds.setUnderlineText(link.isUnderlined());
            }
        };


        text.setSpan(span, range.start, range.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void convertPatternsToLinks() {
        int size = mLinks.size();
        int i = 0;
        while (i < size) {
            if (mLinks.get(i).getPattern() != null) {
                addLinksFromPattern(mLinks.get(i));

                mLinks.remove(i);
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
            mLinks.add(link);
        }
    }

    public LinkableTextView build() {
        convertPatternsToLinks();

        for (Link link : mLinks) {
            addLinkToSpan(link);
        }

        setText(spannable);

        return this;
    }

    private static class Range {
        public int start;
        public int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
