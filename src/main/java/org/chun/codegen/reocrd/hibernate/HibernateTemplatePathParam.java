package org.chun.codegen.reocrd.hibernate;

import org.chun.codegen.reocrd.interfaces.ITemplateParam;

public record HibernateTemplatePathParam(
    String entity,
    String repository
) implements ITemplateParam {
}
