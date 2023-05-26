package org.chun.codegen.enums;

import lombok.AllArgsConstructor;

/**
 * 程式執行角色
 */
@AllArgsConstructor
public enum ExecuteType {
  PRODUCER(new String[]{}),
  OPERATOR(new String[]{});

  private final String[] responds;
}
