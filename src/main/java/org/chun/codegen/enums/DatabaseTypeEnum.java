package org.chun.codegen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.chun.codegen.enums.interfaces.ICustomerEnum;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum DatabaseTypeEnum implements ICustomerEnum {

  POSTGRES("org.postgresql.Driver"),
  MYSQL("com.microsoft.sqlserver.jdbc.SQLServerDriver"),
  MSSQL("com.mysql.jdbc.Driver");

  private final String driverClassName;

  public static DatabaseTypeEnum getEnum(String type) {
    return Arrays.stream(values())
        .filter(e -> e.name().equalsIgnoreCase(type))
        .findAny()
        .orElseThrow(() -> new EnumConstantNotPresentException(DatabaseTypeEnum.class, type));
  }

  @Override
  public Class<? extends Enum<?>> type() {
    return DatabaseTypeEnum.class;
  }

  public static String[] validInputs() {
    DatabaseTypeEnum[] values = DatabaseTypeEnum.values();
    String[] inputs = new String[values.length];
    for (int i = 0; i < values.length; i++) {
      inputs[i] = values[i].name();
    }
    return inputs;
  }
}
