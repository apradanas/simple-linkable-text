# Simple Linkable Text

 [ ![Download](https://api.bintray.com/packages/apradanas/maven/simple-linkable-text/images/download.svg) ](https://bintray.com/apradanas/maven/simple-linkable-text/_latestVersion)
 [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Simple%20Linkable%20Text-green.svg?style=flat)](https://android-arsenal.com/details/1/2242)
 ![](https://img.shields.io/badge/platform-android-green.svg)

Simple way to create link text, such as @username or #hashtag, in Android TextView and EditText

![](https://raw.githubusercontent.com/apradanas/simple-linkable-text/master/screenshots/slt_demo.gif)

Installation
------------
##### Gradle

Add dependency

```
compile 'com.apradanas.simplelinkabletext:library:1.0.3@aar'
```


Features
--------

- Match single strings or regex pattern to set links
- Change the color of the linked text
- Set the style of the linked text: **BOLD**, *ITALIC*, or ***BOLD_ITALIC***
- Set the underlined of the linked text
- Specify click actions of a specific word
- OnTextChangedListener listener for LinkableEditText

Usage
-----
### In your XML layout

##### LinkableTextView

```
<com.apradanas.simplelinkabletext.LinkableTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```

##### LinkableEditText

```
<com.apradanas.simplelinkabletext.LinkableEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```

### In your Activity / Fragment

```
/*
**	define rules
*/

// find hashtags
Link linkHashtag = new Link(Pattern.compile("(#\\w+)"))
                	.setUnderlined(true)
                	.setTextStyle(TextStyle.ITALIC)
                	.setClickListener(new Link.OnClickListener() {
                    	@Override
                    	public void onClick(String text) {
                       		// do something
                    	}
                	});

// find username
Link linkUsername = new Link(Pattern.compile("(@\\w+)"))
                	.setUnderlined(false)
                	.setTextColor(Color.parseColor("#D00000"))
                	.setTextStyle(TextStyle.BOLD)
                	.setClickListener(new Link.OnClickListener() {
                   		@Override
                    	public void onClick(String text) {
                       		// do something
                    	}
                	});

// match string "string"
Link linkAnd = new Link("string")
               		.setTextColor(Color.BLUE)
               		.setTextStyle(TextStyle.BOLD_ITALIC)
                	.setClickListener(new Link.OnClickListener() {
                   		@Override
                    	public void onClick(String text) {
                       		// do something
                    	}
                	});

List<Link> links = new ArrayList<>();
links.add(linkHashtag);
links.add(linkUsername);
links.add(linkAnd);

/*
**	add rules to LinkableTextView
**	then build()
*/
LinkableTextView textView = (LinkableTextView) findViewById(R.id.textView);
textView.setText("#test #LinkableTextView: detecting #hashtags and @username")
		.addLinks(links)
		.build();

/*
**	add rules to LinkableEditText
**	no need to build()
*/
LinkableEditText editText = (LinkableEditText) findViewById(R.id.editText);
editText.addLinks(links);
```


License
-------

    Copyright 2015 Aditya Pradana Sugiarto

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.