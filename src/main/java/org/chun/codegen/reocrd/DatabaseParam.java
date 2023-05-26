package org.chun.codegen.reocrd;

import org.chun.codegen.enums.DatabaseTypeEnum;

public record DatabaseParam(
    DatabaseTypeEnum databaseTypeEnum,
    String url,
    String username,
    String password
) {
}
