package org.chun.codegen.utils;

import org.chun.codegen.enums.interfaces.IPathEnum;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Scanner;

@Service
public class ScannerUtil {
  private static final Scanner scanner = new Scanner(System.in);
  private static final String INVALID_VALUE_MESSAGE = "輸入值無效, 請重新輸入! ";

  /**
   * 按照預先設定的文字敘述輸入文字
   *
   * @param pathEnum
   * @return
   */
  public static String input(IPathEnum pathEnum) {
    return input(pathEnum.message());
  }

  /**
   * 按照預先設定的文字敘述輸入文字, 若無輸入則以預設值代替
   *
   * @param pathEnum
   * @return
   */
  public static String input(IPathEnum pathEnum, String defaultPath) {
    String input = input(pathEnum.message());
    return input.isBlank() ? defaultPath : input;
  }

  /**
   * 按照當下設定的文字敘述輸入文字
   *
   * @param message
   * @return
   */
  public static String input(String message) {
    print(message);
    return scanner.nextLine();
  }


  public static String input(String message, String... validInputs) {
    print(message);
    String result = scanner.nextLine();
    return Arrays.stream(validInputs)
        .noneMatch(input -> input.equalsIgnoreCase(result))
        ? secondInput(message, validInputs)
        : result;
  }

  private static String secondInput(String message, String... validInputs){
    print(INVALID_VALUE_MESSAGE);
    return input(message, validInputs);
  }

  private static void print(String text) {
    System.out.println(text);
  }
}
