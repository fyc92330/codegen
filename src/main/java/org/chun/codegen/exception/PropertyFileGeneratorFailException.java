package org.chun.codegen.exception;

public class PropertyFileGeneratorFailException extends RuntimeException {

  public PropertyFileGeneratorFailException(String profile){
    super(String.format("配置檔(%s) 建立時失敗: ", profile));
  }
}
