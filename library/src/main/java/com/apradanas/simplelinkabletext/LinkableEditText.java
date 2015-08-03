package com.apradanas.simplelinkabletext;

import android.content.Context;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by @apradanas
 */
public class LinkableEditText extends EditText implements TextWatcher {

    private List<Link> mLinks = new ArrayList<>();
    private List<Link> mFoundLinks = new ArrayList<>();

    private String mText;
    private Editable editable;

    public LinkableEditText(Context context) {
        super(context);

        addTextChangedListener(this);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public LinkableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        addTextChangedListener(this);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public LinkableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        addTextChangedListener(this);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public LinkableEditText addLink(Link link) {
        mLinks.add(link);
        mFoundLinks.add(link);

        return this;
    }

    public LinkableEditText addLinks(List<Link> links) {
        mLinks.addAll(links);
        mFoundLinks.addAll(links);

        return this;
    }

    private void addLinkToSpan(Link link) {
        Pattern pattern = Pattern.compile(Pattern.quote(link.getText()));
        Matcher matcher = pattern.matcher(mText);

        while (matcher.find()) {

            int start = matcher.start();

            if (start >= 0) {
                int end = start + link.getText().length();

                applyLink(link, new Range(start, end));
            }
        }
    }

    private void removePreviousSpans() {
        ClickableSpan[] toRemoveSpans = editable.getSpans(0, editable.length(), ClickableSpan.class);
        for(int i = 0; i < toRemoveSpans.length; i++) {
            editable.removeSpan(toRemoveSpans[i]);
        }
    }

    private void applyLink(final Link link, final Range range) {
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                EditText et = (EditText) widget;
                Spanned s = et.getText();

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

        editable.setSpan(span, range.start, range.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void convertPatternsToLinks() {
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

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mText = s.toString();
        editable = s;

        convertPatternsToLinks();

        removePreviousSpans();

        for (Link link : mFoundLinks) {
            addLinkToSpan(link);
        }
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
