/**
一直以来总是被browser和server之间各种编码所困惑，尤其在多语言支持上非常头大。
今天参考网络上一些Base64编码传送的思路，写了JavaScript的Base64编解码的过程，
主要目的是，当需要把一个多语言的字符串传到server时，对字符串进行Base64编码，
反过来当从server得到字符串时，进行Base64解码，同时希望Base64编码后的东西，
不会被browser或server的各种编码方法，如 javascript:escape()改变。

思路：
1. JavaScript 的String都是Unicode的，也就是UCS-2
2. 为了保证Base64后的码较短，UCS-2先转换成UTF-8编码
3. 选择不会被javascript:escape()编码的Base64字符集

注：以下代码来自网上
*/
var encrypt = {
	keyStr : "Aa9Bb8Cc7Dd6Ee5Ff4Gg3Hh2Ii1Jj0Kk_Ll@Mm$NnOoPpQqRrSsTtUuVvWwXxYyZz",
	
	encode: function(s){
	    if(!s || s.length == 0) 
	    	return s;
	    	
	    var d = "";
	    var b = this.ucs2_utf8(s);
	    var b0, b1, b2, b3;
	    var len = b.length;
	    var i = 0;
	    
	    while(i < len){
	       var tmp = b[i++];
	       b0 = (tmp & 0xfc) >> 2;
	       b1 = (tmp & 0x03) << 4;
	       if(i < len){
	          tmp = b[i++];
	          b1 |= (tmp & 0xf0) >> 4;
	          b2 = (tmp & 0x0f) << 2;
	          if(i< len){
	             tmp = b[i++];
	             b2 |= (tmp & 0xc0) >> 6;
	             b3 = tmp & 0x3f;
	          }else{
	             b3 = 64; // 1 byte "-" is supplement
	
	          }
	       }else{
	          b2 = b3 = 64; // 2 bytes "-" are supplement
	       }
	
	       d += this.keyStr.charAt(b0);
	       d += this.keyStr.charAt(b1);
	       d += this.keyStr.charAt(b2);
	       d += this.keyStr.charAt(b3);
	    }
	    return d;
	},
	
	/**
	 * Decode a Base64 string to a string follow Base64 regular.
	 * @param s, a Base64 string
	 * @return a normal string
	 */
	decode: function(s){
	    if(!s)
	    	return null;
	    	
	    var len = s.length;
	    var b = [];
	    var i=0, j=0, e=0, c, tmp;
	    
	    while(i < len){
	       c = this.keyStr.indexOf(s.charAt(i++));
	       tmp = c << 18;
	       c = this.keyStr.indexOf(s.charAt(i++));
	       tmp |= c << 12;
	       c = this.keyStr.indexOf(s.charAt(i++));
	       if(c < 64){
	          tmp |= c << 6;
	          c = this.keyStr.indexOf(s.charAt(i++));
	          if(c < 64){
	             tmp |= c;
	          }else{
	             e = 1;
	          }
	       }else{
	          e = 2;
	          i++;
	       }
	
	       b[j+2] = tmp & 0xff;
	       tmp >>= 8;
	       b[j+1] = tmp & 0xff;
	       tmp >>= 8;
	       b[j+0] = tmp & 0xff;
	       j += 3;
	    }
	    
	    b.splice(b.length-e, e);
	    return this.utf8_ucs2(b);
	},
	
	
	/**
	 * Encodes a ucs2 string to a utf8 integer array.
	 * @param s, a string
	 * @return an integer array
	 */
	ucs2_utf8: function(s){
	    if(!s || s == "")
	    	return null;
	    	
	    var d = [];
	    var c = 0, i = 0, j = 0;
	    var len = s.length;
	    while(i < len){
	       c = s.charCodeAt(i++);
	       if(c <= 0x7f){// 1 byte
	          d[j++] = c;
	       }else if((c >= 0x80) && (c <= 0x7ff)){// 2 bytes
	          d[j++] = ((c >> 6) & 0x1f) | 0xc0;
	          d[j++] = (c & 0x3f) | 0x80;
	       }else{// 3 bytes
	          d[j++] = (c >> 12) | 0xe0;
	          d[j++] = ((c >> 6) & 0x3f) | 0x80;
	          d[j++] = (c & 0x3f) | 0x80;
	       }
	    }
	    return d;
	},
	
	/**
	 * Encodes a utf8 integer array to a ucs2 string.
	 * @param s, an integer array
	 * @return a string
	 */
	utf8_ucs2: function(s){
	    if(!s)
	    	return null;
	    	
	    var len = s.length;
	    if(len == 0)
	    	return "";
	
	    var d = "";
	    var c = 0, i = 0, tmp = 0;
	    while(i < len){
	       c = s[i++];
	       if((c & 0xe0) == 0xe0){// 3 bytes
	          tmp = (c & 0x0f) << 12;
	          c = s[i++];
	          tmp |= ((c & 0x3f) << 6);
	          c = s[i++];
	          tmp |= (c & 0x3f);
	       }else if((c & 0xc0) == 0xc0){// 2 bytes
	          tmp = (c & 0x1f) << 6;
	          c = s[i++];
	          tmp |= (c & 0x3f);
	       }else{// 1 byte
	          tmp = c;
	       }
	       d += String.fromCharCode(tmp);
	    }
	    
	    return d;
	}
}