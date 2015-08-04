# Simple Linkable Text

Simple way to create link text, such as @username or #hashtag in Android TextView and EditText

![](https://raw.githubusercontent.com/apradanas/simple-linkable-text/master/screenshots/slt_demo.gif)

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
// set rules

// find hashtags
Link linkHashtag = new Link(Pattern.compile("(#\\w+)"))
                	.setUnderlined(true)
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
                	.setClickListener(new Link.OnClickListener() {
                   		@Override
                    	public void onClick(String text) {
                       		// do something
                    	}
                	});

// match string "and""
Link linkAnd = new Link("and")
               		.setTextColor(Color.BLUE)
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


// add rules to View and build links

LinkableTextView textView = (LinkableTextView) findViewById(R.id.textView);
textView.setText("#test #LinkableTextView: detecting #hashtags and @username")
		.addLinks(links)
		.build();

LinkableEditText editText = (LinkableEditText) findViewById(R.id.editText);
editText.addLinks(links);
```

Features
--------

- Match single strings or regex pattern to set links
- Change the color of the linked text
- Set the underlined of the linked text
- Specify click actions of a specific word


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