package org.chun.codegen.enums;

import lombok.AllArgsConstructor;
import org.chun.codegen.enums.interfaces.IPathEnum;

@AllArgsConstructor
public enum DataSourceEnum implements IPathEnum {

  DRIVER_CLASS_NAME("", "spring.datasource.driver_class_name"),
  NAME("", "spring.datasource.name"),
  URL("輸入資料庫路徑", "spring.datasource.url"),
  JDBC_URL("", "spring.datasource.jdbc_url"),
  USERNAME("輸入資料庫使用者名稱", "spring.datasource.username"),
  PASSWORD("輸入資料庫使用者密碼", "spring.datasource.password");

  private final String message;
  private final String paramTItle;

  @Override
  public String message() {
    return this.message;
  }

  @Override
  public String paramTitle() {
    return this.paramTItle;
  }

  @Override
  public Class<? extends Enum<?>> type() {
    return DataSourceEnum.class;
  }
}
