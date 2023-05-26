package org.chun.codegen.reocrd;

import org.chun.codegen.enums.DatabaseTypeEnum;
import org.chun.codegen.enums.ObjectRelationTypeEnum;
import org.chun.codegen.reocrd.interfaces.IOutputParam;
import org.chun.codegen.reocrd.interfaces.IPackageParam;
import org.chun.codegen.reocrd.interfaces.ITemplateParam;
import org.chun.codegen.reocrd.mybatis.MyBatisOutputPathParam;
import org.chun.codegen.reocrd.mybatis.MyBatisPackagePathParam;
import org.chun.codegen.reocrd.mybatis.MyBatisTemplatePathParam;

import static org.chun.codegen.constant.SystemParamConst.DOWNLOADS_FOLDER_PATH;

public record CodegenModuleCore(
    String profile,
    ObjectRelationTypeEnum relationTypeEnum,
    DatabaseParam databaseParam,
    IPackageParam packageParam,
    ITemplateParam templateParam,
    IOutputParam outputParam
) {
  public boolean diff(String profileActive) {
    return !profile.equals(profileActive);
  }

  public static CodegenModuleCore defaultModule() {
    return new CodegenModuleCore(
        "default",
        ObjectRelationTypeEnum.MYBATIS,
        new DatabaseParam(
            DatabaseTypeEnum.POSTGRES,
            "jdbc:postgresql://localhost:5432/postgres",
            "postgres",
            "postgres"
        ),
        new MyBatisPackagePathParam(
            "org.chun.demo.dao.base",
            "org.chun.demo.dao",
            "org.chun.demo.vo.base",
            "org.chun.demo.vo",
            "mybatis.mapper.base",
            "mybatis.mapper"
        ),
        new MyBatisTemplatePathParam(
            "baseDao.ftl",
            "extendDao.ftl",
            "aseVo.ftl",
            "extendVo.ftl",
            "baseMapper.ftl",
            "extendMapper.ftl"
        ),
        new MyBatisOutputPathParam(
            DOWNLOADS_FOLDER_PATH,
            "/dao",
            "/vo",
            "/mapper"
        )
    );
  }

}
