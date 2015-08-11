package com.apradanas.simplelinkabletextsample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apradanas.simplelinkabletext.Link;
import com.apradanas.simplelinkabletext.LinkableEditText;
import com.apradanas.simplelinkabletext.LinkableTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Link linkHashtag = new Link(Pattern.compile("(#\\w+)"))
                .setUnderlined(true)
                .setClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(String text) {
                        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                    }
                });

        Link linkUsername = new Link(Pattern.compile("(@\\w+)"))
                .setUnderlined(false)
                .setTextColor(Color.parseColor("#D00000"))
                .setClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(String text) {
                        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                    }
                });

        Link linkAnd = new Link("and")
                .setTextColor(Color.BLUE)
                .setClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(String text) {
                        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                    }
                });

        List<Link> links = new ArrayList<>();
        links.add(linkHashtag);
        links.add(linkUsername);
        links.add(linkAnd);

        final LinkableTextView textView = (LinkableTextView) findViewById(R.id.textView);
        final Button submitButton = (Button) findViewById(R.id.submitButton);
        final LinkableEditText editText = (LinkableEditText) findViewById(R.id.editText);

        textView.setText("#LinkableTextView: detecting #hashtags and @username")
                .addLinks(links)
                .build();


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(editText.getText().toString()).build();
            }
        });


        editText.addLinks(links);
        editText.setTextChangedListener(new LinkableEditText.OnTextChangedListener() {
            @Override
            public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                submitButton.setEnabled(s.length() > 0);
            }
        });


    }
}
