package org.chun.codegen.exception;

public class FileNotExistsException extends RuntimeException {
  private static final String errorMsg = "缺少必要的檔案: ";
  FileNotExistsException(String fileName){
    super(errorMsg + fileName);
  }
}
