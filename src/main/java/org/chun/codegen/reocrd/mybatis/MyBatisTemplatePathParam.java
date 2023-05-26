package org.chun.codegen.reocrd.mybatis;

import org.chun.codegen.reocrd.interfaces.ITemplateParam;

public record MyBatisTemplatePathParam(
    String daoBasePath,
    String daoPath,
    String voBasePath,
    String voPath,
    String mapperBasePath,
    String mapperPath
) implements ITemplateParam {
}
