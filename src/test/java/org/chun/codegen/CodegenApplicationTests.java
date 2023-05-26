package org.chun.codegen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class CodegenApplicationTests {

  @Test
  void contextLoads() {
    String input = "jdbc:postgresql://localhost:5432/postgres?stringtype=unspecified&autosave=always";
    Pattern pattern = Pattern.compile("(?<=\\/\\/)[^\\/]+\\/(\\w+)");


    System.out.println(pattern.matcher(input).group());
    Assertions.assertTrue(pattern.matcher(input).find());
  }

  @Test
  void test() {
    int[] a = new int[]{1,3,2};
    List<Integer> aa = Arrays.stream(a).boxed().collect(Collectors.toList());


    System.out.println(true || false);
    System.out.println(true && false);



    String s = "knnqo";
    for (int i = 0; i < s.length() - 1; i++) {
      for (int j = i + 1; j < s.length(); j++) {
        // 將兩個字母交換位置
        char[] chars = s.toCharArray();
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;

        // 印出新字串
        System.out.println(new String(chars));
      }
    }
  }
}
