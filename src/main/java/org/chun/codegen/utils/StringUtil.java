package org.chun.codegen.utils;

public class StringUtil {

  public static boolean isNotContain(String t, CharSequence s){
    return t != null && !t.contains(s);
  }
}
