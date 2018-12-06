package com.liyang.housejob.service.house;

import org.junit.Test;

public class StringTest {
    @Test
    public void test(){
        String testString = "dasLi";
        String testStringNew =null;
        for (int i =0 ; i<testString.length() ; i++){
            char word = testString.charAt(i);
            if (word>='A' && word<='Z'){
                String wordNew = String.valueOf(word);
                testStringNew = testString.replaceAll(wordNew,"_"+wordNew.toLowerCase());
            }
        }

        System.out.println(testStringNew);
    }
}
