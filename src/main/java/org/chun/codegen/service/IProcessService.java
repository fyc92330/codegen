package org.chun.codegen.service;


import org.chun.codegen.reocrd.CodegenModuleCore;

import java.io.IOException;

/**
 * Operator: 輸入資料生成.properties
 * Producer: 生成物件
 */
public interface IProcessService {

  /** 執行邏輯 */
  void execute() throws Exception;

  /** 操作參數 */
  CodegenModuleCore inputParameter(String profileActive) throws IOException;

  /** 建立文件 */
  void generate(CodegenModuleCore core);

}
