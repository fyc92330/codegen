package org.chun.codegen.reocrd.hibernate;

import org.chun.codegen.reocrd.interfaces.IOutputParam;

public record HibernateOutputPathParam(
    String mainDir,
    String entity,
    String repository
) implements IOutputParam {
}
