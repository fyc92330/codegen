package org.chun.codegen.exception;

public class PropertyFileExistsException extends RuntimeException {

  public PropertyFileExistsException(String profile) {
    super(String.format("配置檔(%s)已經存在.", profile));
  }
}
