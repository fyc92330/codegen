package org.chun.codegen.enums.hibernate;

import lombok.AllArgsConstructor;
import org.chun.codegen.enums.interfaces.IPackagePathEnum;

@AllArgsConstructor
public enum HibernatePackagePathEnum implements IPackagePathEnum {

  PACKAGE_ENTITY("輸入專案內 ENTITY 資料夾路徑(ex com.chun.demo.entity): \n", ""),
  PACKAGE_REPOSITORY("輸入專案內 REPOSITORY 資料夾路徑(ex com.chun.demo.repository): \n", "");

  private final String message;
  private final String paramTitle;

  @Override
  public String message() {
    return this.message;
  }

  @Override
  public String paramTitle() {
    return this.paramTitle;
  }

  @Override
  public Class<? extends Enum<?>> type() {
    return null;
  }
}
