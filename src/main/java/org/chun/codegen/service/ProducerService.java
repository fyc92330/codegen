package org.chun.codegen.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.chun.codegen.constant.GlobalFormatConst;
import org.chun.codegen.enums.DataSourceEnum;
import org.chun.codegen.enums.DatabaseTypeEnum;
import org.chun.codegen.enums.ObjectRelationTypeEnum;
import org.chun.codegen.enums.hibernate.HibernateOutputPathEnum;
import org.chun.codegen.enums.hibernate.HibernatePackagePathEnum;
import org.chun.codegen.enums.hibernate.HibernateTemplatePathEnum;
import org.chun.codegen.enums.mybatis.MyBatisOutputPathEnum;
import org.chun.codegen.enums.mybatis.MyBatisPackagePathEnum;
import org.chun.codegen.enums.mybatis.MyBatisTemplatePathEnum;
import org.chun.codegen.exception.PropertyFileExistsException;
import org.chun.codegen.reocrd.CodegenModuleCore;
import org.chun.codegen.reocrd.DatabaseParam;
import org.chun.codegen.reocrd.hibernate.HibernateOutputPathParam;
import org.chun.codegen.reocrd.hibernate.HibernatePackagePathParam;
import org.chun.codegen.reocrd.hibernate.HibernateTemplatePathParam;
import org.chun.codegen.reocrd.mybatis.MyBatisOutputPathParam;
import org.chun.codegen.reocrd.mybatis.MyBatisPackagePathParam;
import org.chun.codegen.reocrd.mybatis.MyBatisTemplatePathParam;
import org.chun.codegen.utils.FileUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Pattern;

import static org.chun.codegen.constant.GlobalFormatConst.PROPERTIES_TITLE_FORMAT;
import static org.chun.codegen.constant.SystemParamConst.PROPERTIES_ORM_TYPE_TITLE;
import static org.chun.codegen.utils.ParamUtil.propertiesParam;
import static org.chun.codegen.utils.ScannerUtil.input;

/**
 * .properties 生產邏輯
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProducerService extends AbstractProcessService {

  private static final String PROPERTIES_SPRING_TITLE = String.format(PROPERTIES_TITLE_FORMAT, "spring");
  private static final String PROPERTIES_PACKAGE_TITLE = String.format(PROPERTIES_TITLE_FORMAT, "package");
  private static final String PROPERTIES_TEMPLATE_TITLE = String.format(PROPERTIES_TITLE_FORMAT, "template");
  private static final String PROPERTIES_OUTPUT_TITLE = String.format(PROPERTIES_TITLE_FORMAT, "output");
  private static final String PROPERTIES_DATASOURCE_TITLE = String.format(PROPERTIES_TITLE_FORMAT, "datasource");
  private static final String PROPERTIES_SPRING_PARAM_FORMAT = "spring.profiles.active=%s";
  private static final Pattern DATABASE_NAME_PATTERN = Pattern.compile("(?<=\\/\\/)[^\\/]+\\/(\\w+)");
  private StringBuilder builder;


  @Override
  public synchronized void execute() throws Exception {
    String inputProfileActive = input(" >>> 生產模式,輸入配置名稱: ");
    propertiesResource = new ClassPathResource(String.format(GlobalFormatConst.PROPERTIES_NAME_FORMAT, inputProfileActive));
    if (propertiesResource.exists()) {
      throw new PropertyFileExistsException(inputProfileActive);
    }
    this.main(inputProfileActive);
  }

  @Override
  @SneakyThrows
  public CodegenModuleCore inputParameter(String profileActive) {
    ObjectRelationTypeEnum relationTypeEnum = ObjectRelationTypeEnum.getEnum(input("輸入ORM框架 (1)MyBatis (2)Hibernate: ", ObjectRelationTypeEnum.validInputs()));

    DatabaseParam databaseParam = new DatabaseParam(
        DatabaseTypeEnum.getEnum(input("輸入資料庫類型 (1)postgres (2)mssql (3)mysql: ", DatabaseTypeEnum.validInputs())),
        input(DataSourceEnum.URL),
        input(DataSourceEnum.USERNAME),
        input(DataSourceEnum.PASSWORD)
    );

    return switch (relationTypeEnum) {
      case MYBATIS -> this.myBatisPropertiesParams(profileActive, databaseParam, relationTypeEnum);
      case HIBERNATE -> this.hibernatePropertiesParams(profileActive, databaseParam, relationTypeEnum);
    };
  }

  private CodegenModuleCore myBatisPropertiesParams(String profile, DatabaseParam databaseParam, ObjectRelationTypeEnum relationTypeEnum) throws IOException {
    MyBatisOutputPathParam myBatisOutputPathParam = new MyBatisOutputPathParam(
        input(MyBatisOutputPathEnum.OUTPUT_MAIN_DIR, MyBatisOutputPathEnum.OUTPUT_MAIN_DIR.getDefaultPath()),
        input(MyBatisOutputPathEnum.OUTPUT_DAO_PATH, MyBatisOutputPathEnum.OUTPUT_DAO_PATH.getDefaultPath()),
        input(MyBatisOutputPathEnum.OUTPUT_VO_PATH, MyBatisOutputPathEnum.OUTPUT_VO_PATH.getDefaultPath()),
        input(MyBatisOutputPathEnum.OUTPUT_MAPPER_PATH, MyBatisOutputPathEnum.OUTPUT_MAPPER_PATH.getDefaultPath())
    );

    MyBatisPackagePathParam myBatisPackagePathParam = new MyBatisPackagePathParam(
        input(MyBatisPackagePathEnum.PACKAGE_DAO_BASE),
        input(MyBatisPackagePathEnum.PACKAGE_DAO),
        input(MyBatisPackagePathEnum.PACKAGE_VO_BASE),
        input(MyBatisPackagePathEnum.PACKAGE_VO),
        input(MyBatisPackagePathEnum.PACKAGE_MAPPER_BASE),
        input(MyBatisPackagePathEnum.PACKAGE_MAPPER)
    );

    MyBatisTemplatePathParam myBatisTemplatePathParam = new MyBatisTemplatePathParam(
        MyBatisTemplatePathEnum.TEMPLATE_DAO_BASE.getTemplateFileName(),
        MyBatisTemplatePathEnum.TEMPLATE_DAO.getTemplateFileName(),
        MyBatisTemplatePathEnum.TEMPLATE_VO_BASE.getTemplateFileName(),
        MyBatisTemplatePathEnum.TEMPLATE_VO.getTemplateFileName(),
        MyBatisTemplatePathEnum.TEMPLATE_MAPPER_BASE.getTemplateFileName(),
        MyBatisTemplatePathEnum.TEMPLATE_MAPPER.getTemplateFileName()
    );

    return new CodegenModuleCore(
        profile,
        relationTypeEnum,
        databaseParam,
        myBatisPackagePathParam,
        myBatisTemplatePathParam,
        myBatisOutputPathParam
    );
  }

  private CodegenModuleCore hibernatePropertiesParams(String profile, DatabaseParam databaseParam, ObjectRelationTypeEnum relationTypeEnum) throws IOException {
    HibernateOutputPathParam hibernateOutputPathParam = new HibernateOutputPathParam(
        input(HibernateOutputPathEnum.OUTPUT_MAIN_DIR, HibernateOutputPathEnum.OUTPUT_MAIN_DIR.getDefaultPath()),
        input(HibernateOutputPathEnum.OUTPUT_ENTITY, HibernateOutputPathEnum.OUTPUT_ENTITY.getDefaultPath()),
        input(HibernateOutputPathEnum.OUTPUT_REPOSITORY, HibernateOutputPathEnum.OUTPUT_REPOSITORY.getDefaultPath())
    );

    HibernatePackagePathParam hibernatePackagePathParam = new HibernatePackagePathParam(
        input(HibernatePackagePathEnum.PACKAGE_ENTITY),
        input(HibernatePackagePathEnum.PACKAGE_REPOSITORY)
    );

    HibernateTemplatePathParam hibernateTemplatePathParam = new HibernateTemplatePathParam(
        HibernateTemplatePathEnum.TEMPLATE_ENTITY.getTemplateFileName(),
        HibernateTemplatePathEnum.TEMPLATE_ENTITY.getTemplateFileName()
    );

    return new CodegenModuleCore(
        profile,
        relationTypeEnum,
        databaseParam,
        hibernatePackagePathParam,
        hibernateTemplatePathParam,
        hibernateOutputPathParam
    );
  }

  @Override
  public void generate(CodegenModuleCore core) {
    builder = new StringBuilder();
    // spring
    final String profile = core.profile();
    final ObjectRelationTypeEnum relationTypeEnum = core.relationTypeEnum();
    this.appendContent(PROPERTIES_SPRING_TITLE);
    this.appendContent(String.format(PROPERTIES_SPRING_PARAM_FORMAT, profile));
    this.appendContent(propertiesParam(PROPERTIES_ORM_TYPE_TITLE, relationTypeEnum.name()));

    // datasource
    DatabaseParam databaseParam = core.databaseParam();
    DatabaseTypeEnum databaseTypeEnum = databaseParam.databaseTypeEnum();
    final String url = databaseParam.url();
    this.appendContent(PROPERTIES_DATASOURCE_TITLE);
    this.appendContent(propertiesParam(DataSourceEnum.DRIVER_CLASS_NAME, databaseTypeEnum.getDriverClassName()));
    this.appendContent(propertiesParam(DataSourceEnum.URL, url));
    this.appendContent(propertiesParam(DataSourceEnum.JDBC_URL, url));
    this.appendContent(propertiesParam(DataSourceEnum.USERNAME, databaseParam.username()));
    this.appendContent(propertiesParam(DataSourceEnum.PASSWORD, databaseParam.password()));
    this.appendContent(propertiesParam(DataSourceEnum.NAME, StringUtils.firstNonBlank(DATABASE_NAME_PATTERN.matcher(url).group(1), "none")));

    switch (relationTypeEnum) {
      case MYBATIS -> this.setMyBatisParams(core);
      case HIBERNATE -> this.setHibernateParams(core);
    }

    // write onto file
    FileUtil.writeByResource(propertiesResource, builder.toString());
  }

  private void setMyBatisParams(CodegenModuleCore core) {
    // package
    MyBatisPackagePathParam myBatisPackagePathParam = (MyBatisPackagePathParam) core.packageParam();
    this.appendContent(PROPERTIES_PACKAGE_TITLE);
    this.appendContent(propertiesParam(MyBatisPackagePathEnum.PACKAGE_DAO_BASE, myBatisPackagePathParam.daoBasePath()));
    this.appendContent(propertiesParam(MyBatisPackagePathEnum.PACKAGE_DAO, myBatisPackagePathParam.daoPath()));
    this.appendContent(propertiesParam(MyBatisPackagePathEnum.PACKAGE_VO_BASE, myBatisPackagePathParam.voBasePath()));
    this.appendContent(propertiesParam(MyBatisPackagePathEnum.PACKAGE_VO, myBatisPackagePathParam.voPath()));
    this.appendContent(propertiesParam(MyBatisPackagePathEnum.PACKAGE_MAPPER_BASE, myBatisPackagePathParam.mapperBasePath()));
    this.appendContent(propertiesParam(MyBatisPackagePathEnum.PACKAGE_MAPPER, myBatisPackagePathParam.mapperPath()));

    // template
    MyBatisTemplatePathParam myBatisTemplatePathParam = (MyBatisTemplatePathParam) core.templateParam();
    this.appendContent(PROPERTIES_TEMPLATE_TITLE);
    this.appendContent(propertiesParam(MyBatisTemplatePathEnum.TEMPLATE_DAO_BASE, myBatisTemplatePathParam.daoBasePath()));
    this.appendContent(propertiesParam(MyBatisTemplatePathEnum.TEMPLATE_DAO, myBatisTemplatePathParam.daoPath()));
    this.appendContent(propertiesParam(MyBatisTemplatePathEnum.TEMPLATE_VO_BASE, myBatisTemplatePathParam.voBasePath()));
    this.appendContent(propertiesParam(MyBatisTemplatePathEnum.TEMPLATE_VO, myBatisTemplatePathParam.voPath()));
    this.appendContent(propertiesParam(MyBatisTemplatePathEnum.TEMPLATE_MAPPER_BASE, myBatisTemplatePathParam.mapperBasePath()));
    this.appendContent(propertiesParam(MyBatisTemplatePathEnum.TEMPLATE_MAPPER, myBatisTemplatePathParam.mapperPath()));

    // output
    MyBatisOutputPathParam myBatisOutputPathParam = (MyBatisOutputPathParam) core.outputParam();
    this.appendContent(PROPERTIES_OUTPUT_TITLE);
    this.appendContent(propertiesParam(MyBatisOutputPathEnum.OUTPUT_MAIN_DIR, myBatisOutputPathParam.mainDir()));
    this.appendContent(propertiesParam(MyBatisOutputPathEnum.OUTPUT_DAO_PATH, myBatisOutputPathParam.daoPath()));
    this.appendContent(propertiesParam(MyBatisOutputPathEnum.OUTPUT_VO_PATH, myBatisOutputPathParam.voPath()));
    this.appendContent(propertiesParam(MyBatisOutputPathEnum.OUTPUT_MAPPER_PATH, myBatisOutputPathParam.mapperPath()));
  }

  private void setHibernateParams(CodegenModuleCore core) {
    HibernatePackagePathParam hibernatePackagePathParam = (HibernatePackagePathParam) core.packageParam();
    this.appendContent(PROPERTIES_PACKAGE_TITLE);
    this.appendContent(propertiesParam(HibernatePackagePathEnum.PACKAGE_ENTITY, hibernatePackagePathParam.entity()));
    this.appendContent(propertiesParam(HibernatePackagePathEnum.PACKAGE_REPOSITORY, hibernatePackagePathParam.repository()));

    HibernateTemplatePathParam hibernateTemplatePathParam = (HibernateTemplatePathParam) core.templateParam();
    this.appendContent(PROPERTIES_TEMPLATE_TITLE);
    this.appendContent(propertiesParam(HibernateTemplatePathEnum.TEMPLATE_ENTITY, hibernateTemplatePathParam.entity()));
    this.appendContent(propertiesParam(HibernateTemplatePathEnum.TEMPLATE_REPOSITORY, hibernateTemplatePathParam.repository()));

    HibernateOutputPathParam hibernateOutputPathParam = (HibernateOutputPathParam) core.outputParam();
    this.appendContent(PROPERTIES_OUTPUT_TITLE);
    this.appendContent(propertiesParam(HibernateOutputPathEnum.OUTPUT_MAIN_DIR, hibernateOutputPathParam.mainDir()));
    this.appendContent(propertiesParam(HibernateOutputPathEnum.OUTPUT_ENTITY, hibernateOutputPathParam.entity()));
    this.appendContent(propertiesParam(HibernateOutputPathEnum.OUTPUT_REPOSITORY, hibernateOutputPathParam.repository()));
  }

  private void appendContent(String param) {
    builder.append(param).append("\n");
  }
}
