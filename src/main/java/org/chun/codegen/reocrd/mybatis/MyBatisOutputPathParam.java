package org.chun.codegen.reocrd.mybatis;

import org.chun.codegen.reocrd.interfaces.IOutputParam;

public record MyBatisOutputPathParam(
    String mainDir,
    String daoPath,
    String voPath,
    String mapperPath
) implements IOutputParam {
}
