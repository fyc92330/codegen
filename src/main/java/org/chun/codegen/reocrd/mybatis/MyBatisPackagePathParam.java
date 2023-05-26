package org.chun.codegen.reocrd.mybatis;

import org.chun.codegen.reocrd.interfaces.IPackageParam;

public record MyBatisPackagePathParam(
    String daoBasePath,
    String daoPath,
    String voBasePath,
    String voPath,
    String mapperBasePath,
    String mapperPath
) implements IPackageParam {
}
