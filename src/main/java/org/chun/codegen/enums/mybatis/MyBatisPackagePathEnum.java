package org.chun.codegen.enums.mybatis;

import lombok.AllArgsConstructor;
import org.chun.codegen.enums.interfaces.IPackagePathEnum;

@AllArgsConstructor
public enum MyBatisPackagePathEnum implements IPackagePathEnum {
  PACKAGE_DAO_BASE("輸入專案內 BASE DAO 資料夾路徑(ex com.chun.demo.dao.base): \n", "package.dao_base"),
  PACKAGE_DAO("輸入專案內 DAO 資料夾路徑(ex com.chun.demo.dao): \n", "package.dao_extend"),
  PACKAGE_VO_BASE("輸入專案內 BASE VO 資料夾路徑(ex com.chun.demo.vo.base): \n", "package.vo_base"),
  PACKAGE_VO("輸入專案內 VO 資料夾路徑(ex com.chun.demo.vo.): \n", "package.vo_extend"),
  PACKAGE_MAPPER_BASE("輸入專案內 BASE MAPPER 資料夾路徑(ex mybatis.mapper.base): \n", "package.mapper_base"),
  PACKAGE_MAPPER("輸入專案內 MAPPER 資料夾路徑(ex mybatis.mapper): \n", "package.mapper_extend");

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
    return MyBatisPackagePathEnum.class;
  }

}
