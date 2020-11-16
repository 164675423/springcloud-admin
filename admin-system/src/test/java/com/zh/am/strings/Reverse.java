package com.zh.am.strings;

public class Reverse {
  public static void main(String[] args) {
    String a = "1 2 3 4 5 6 7 8 9";
    StringBuilder builder = new StringBuilder();
    reverse(builder, a);
    System.out.println(builder.toString());
    System.out.println(reverseString(a));
    System.out.println(reverse2(a));
  }

  public static void reverse(StringBuilder dest, String orig) {
    if (orig.length() > 1) {
      String substring = orig.substring(orig.length() - 1);
      dest.append(substring);
      reverse(dest, orig.substring(0, orig.length() - 1));
    } else {
      dest.append(orig);
    }
  }

  public static String reverse2(String orig) {
    return orig.length() > 1
        ? orig.substring(orig.length() - 1) + reverse2(orig.substring(0, orig.length() - 1))
        : orig;
  }

  private static String reverseString(String str) {
    return str.isEmpty()
        ? str
        : reverseString(str.substring(1)) + str.charAt(0);
  }
}
