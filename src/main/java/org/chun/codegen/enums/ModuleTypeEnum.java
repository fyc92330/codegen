package org.chun.codegen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.chun.codegen.enums.interfaces.IPathEnum;
import org.chun.codegen.enums.mybatis.MyBatisOutputPathEnum;
import org.chun.codegen.enums.mybatis.MyBatisPackagePathEnum;
import org.chun.codegen.enums.mybatis.MyBatisTemplatePathEnum;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ModuleTypeEnum {

  //  DATABASE_CONNECTION,
  PACKAGE_PROPERTIES("package.", MyBatisPackagePathEnum.class),
  TEMPLATE_PROPERTIES("template.", MyBatisTemplatePathEnum.class),
  OUTPUT_PROPERTIES("output.", MyBatisOutputPathEnum.class);

  private final String prefix;
  private final Class<? extends IPathEnum> myBatisEnum;

  public static ModuleTypeEnum getEnum(String param){
    return Arrays.stream(values())
        .filter(e -> param.startsWith(e.prefix))
        .findAny()
        .orElseThrow(() -> new EnumConstantNotPresentException(ModuleTypeEnum.class, param));
  }

}
