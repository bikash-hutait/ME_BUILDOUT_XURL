package com.crio.shorturl;

import java.security.SecureRandom;
import java.util.HashMap;

class XUrlImpl implements XUrl {
    HashMap<String, String> storeUrlLongToshort = new HashMap<>();
    HashMap<String, String> storeUrlshortToLong = new HashMap<>();
    HashMap<String, Integer> mapCount = new HashMap<>();

    @Override
    public String registerNewUrl(String longUrl) {
    String shortUrl="http://short.url/";
    String str="";
    String shortConvertedUrl="";
    // range – alphanumeric (0-9, a-z, A-Z)
    final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; 
    SecureRandom random = new SecureRandom();
    StringBuilder sb = new StringBuilder();

    if(storeUrlLongToshort.containsKey(longUrl)==false){
        for (int i = 0; i < 9; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        str= sb.toString();
        shortConvertedUrl=shortUrl+ str;
        storeUrlLongToshort.put(longUrl,shortConvertedUrl);
        storeUrlshortToLong.put(shortConvertedUrl,longUrl);

    } 
    else {
        shortConvertedUrl=storeUrlLongToshort.get(longUrl);
       }

    return shortConvertedUrl;
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        String str=null;
        if(storeUrlLongToshort.containsValue(shortUrl) == false){
            storeUrlLongToshort.put(longUrl, shortUrl);
            storeUrlshortToLong.put(shortUrl, longUrl);
            str=shortUrl;
        }

        return str;
    }
  
    @Override
    public String getUrl(String shortUrl){
    String longUrl="";  
    if(storeUrlshortToLong.get(shortUrl)==null){
        longUrl=null;
    }  
    else {
        longUrl=storeUrlshortToLong.get(shortUrl);
        mapCount.put(longUrl, mapCount.getOrDefault(longUrl, 0) +1);  
    }
    return longUrl;
    }

    @Override
    public Integer getHitCount(String longUrl){
        return mapCount.getOrDefault(longUrl, 0);
    }

    @Override
    public String delete(String longUrl){
    String delItem=storeUrlLongToshort.get(longUrl);
    storeUrlLongToshort.remove(longUrl);
    storeUrlshortToLong.remove(delItem);
    return longUrl;
    }
  }
  