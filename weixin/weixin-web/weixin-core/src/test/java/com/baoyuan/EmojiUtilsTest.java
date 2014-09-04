package com.baoyuan;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.baoyuan.weixin.EmojiParser;
import com.baoyuan.weixin.EmojiUtils;

public class EmojiUtilsTest {

	@Test
	@Ignore
    public void fileterEmoji() {
        String s = "<body>😄213这是一个有各种内容的消息,  Hia Hia Hia !!!! xxxx@@@...*)!" +
                "(@*$&@(&#!)@*)!&$!)@^%@(!&#. 😄👩👨], ";
        String c = EmojiUtils.filterEmoji(s);
        assertFalse(s.equals(c));
        String expected = "<body>213这是一个有各种内容的消息,  Hia Hia Hia !!!! xxxx@@@...*)" +
                "!(@*$&@(&#!)@*)!&$!)@^%@(!&#. ], ";
        assertEquals(expected, c);
//        assertSame(c, expected);
        assertSame(expected, "<body>213这是一个有各种内容的消息,  Hia Hia Hia !!!! xxxx@@@...*)" +
                "!(@*$&@(&#!)@*)!&$!)@^%@(!&#. ], ");
        assertSame(c, EmojiUtils.filterEmoji(c));
    }
	
	@Test
	@Ignore
    public void parserEmoji() {
		 String s = ":u7a7a:[1f233]<body>😄213这是一个有各种内容的消息,  Hia Hia Hia !!!! xxxx@@@...*)!" +
	                "(@*$&@(&#!)@*)!&$!)@^%@(!&#. 😄👩👨], ";
		 
		 System.out.println(EmojiParser.demojizedText(s));
	}
}
