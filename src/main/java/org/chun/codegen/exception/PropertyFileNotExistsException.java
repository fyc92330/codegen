package org.chun.codegen.exception;

public class PropertyFileNotExistsException extends FileNotExistsException {
  public PropertyFileNotExistsException(String profile) {
    super(profile);
  }
}
