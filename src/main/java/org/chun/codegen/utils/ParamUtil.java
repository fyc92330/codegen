package org.chun.codegen.utils;

import org.chun.codegen.enums.ModuleTypeEnum;
import org.chun.codegen.enums.interfaces.IPathEnum;

import java.util.Arrays;

import static org.chun.codegen.constant.ValidParamConst.EQUALS_STR;

public class ParamUtil {

  public static String propertiesParam(IPathEnum pathEnum, String paramValue) {
    return propertiesParam(pathEnum.paramTitle(), paramValue);
  }

  public static String propertiesParam(String title, String paramValue) {
    return String.format("%s%s%s", title, EQUALS_STR, paramValue);
  }

  public static String[] inputParam(String param) {
    String[] emptyArray = new String[0];
    if (param == null || StringUtil.isNotContain(param, EQUALS_STR)) {
      return emptyArray;
    }
    String[] paramArray = param.split(EQUALS_STR);
    String title = paramArray[0], value = paramArray[1];
    ModuleTypeEnum moduleTypeEnum = ModuleTypeEnum.getEnum(title);
    boolean isValidParam = Arrays.stream(moduleTypeEnum.getMyBatisEnum().getEnumConstants())
        .anyMatch(e -> e.paramTitle().equals(title));
    return isValidParam ? paramArray : emptyArray;
  }
}
