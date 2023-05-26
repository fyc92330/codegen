package org.chun.codegen.reocrd.hibernate;

import org.chun.codegen.reocrd.interfaces.IPackageParam;

public record HibernatePackagePathParam(
    String entity,
    String repository
) implements IPackageParam {
}
