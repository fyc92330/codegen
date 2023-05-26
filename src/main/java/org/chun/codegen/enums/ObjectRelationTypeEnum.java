package org.chun.codegen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.chun.codegen.enums.hibernate.HibernateOutputPathEnum;
import org.chun.codegen.enums.hibernate.HibernateTemplatePathEnum;
import org.chun.codegen.enums.interfaces.ICustomerEnum;
import org.chun.codegen.enums.mybatis.MyBatisOutputPathEnum;
import org.chun.codegen.enums.mybatis.MyBatisPackagePathEnum;
import org.chun.codegen.enums.mybatis.MyBatisTemplatePathEnum;

import java.util.Arrays;

/**
 * Producer模式下產生的ORM物件
 */
@Getter
@AllArgsConstructor
public enum ObjectRelationTypeEnum implements ICustomerEnum {
  MYBATIS(new Class[]{
      MyBatisPackagePathEnum.class,
      MyBatisTemplatePathEnum.class,
      MyBatisOutputPathEnum.class,
      DataSourceEnum.class}),
  HIBERNATE(new Class[]{
      HibernateOutputPathEnum.class,
      HibernateTemplatePathEnum.class,
      DataSourceEnum.class});

  private final Class<?>[] enumsArray;

  public static ObjectRelationTypeEnum getEnum(String type) {
    return Arrays.stream(values())
        .filter(e -> e.name().equalsIgnoreCase(type))
        .findAny()
        .orElseThrow(() -> new EnumConstantNotPresentException(ObjectRelationTypeEnum.class, type));
  }

  @Override
  public Class<? extends Enum<?>> type() {
    return ObjectRelationTypeEnum.class;
  }

  public static String[] validInputs() {
    ObjectRelationTypeEnum[] values = ObjectRelationTypeEnum.values();
    String[] inputs = new String[values.length];
    for (int i = 0; i < values.length; i++) {
      inputs[i] = values[i].name();
    }
    return inputs;
  }
}
