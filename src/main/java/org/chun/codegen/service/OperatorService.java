package org.chun.codegen.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.codegen.constant.GlobalFormatConst;
import org.chun.codegen.enums.DataSourceEnum;
import org.chun.codegen.enums.DatabaseTypeEnum;
import org.chun.codegen.enums.ObjectRelationTypeEnum;
import org.chun.codegen.enums.mybatis.MyBatisOutputPathEnum;
import org.chun.codegen.enums.mybatis.MyBatisPackagePathEnum;
import org.chun.codegen.enums.mybatis.MyBatisTemplatePathEnum;
import org.chun.codegen.exception.PropertyFileNotExistsException;
import org.chun.codegen.reocrd.CodegenModuleCore;
import org.chun.codegen.reocrd.DatabaseParam;
import org.chun.codegen.reocrd.mybatis.MyBatisOutputPathParam;
import org.chun.codegen.reocrd.mybatis.MyBatisPackagePathParam;
import org.chun.codegen.reocrd.mybatis.MyBatisTemplatePathParam;
import org.chun.codegen.utils.ParamUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.chun.codegen.constant.SystemParamConst.PROPERTIES_ORM_TYPE_TITLE;
import static org.chun.codegen.utils.ScannerUtil.input;

/**
 * Java物件生產邏輯
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OperatorService extends AbstractProcessService {

  @Override
  public synchronized void execute() throws Exception {
    final String profileActive = input(" >>> 建立模式,輸入配置名稱: ");
    propertiesResource = new ClassPathResource(String.format(GlobalFormatConst.PROPERTIES_NAME_FORMAT, profileActive));
    if (propertiesResource.exists()) {
      this.main(profileActive);
    } else {
      throw new PropertyFileNotExistsException(profileActive);
    }
  }

  @Override
  public CodegenModuleCore inputParameter(String profileActive) throws IOException {
    // 先取得properties的參數
    Map<String, String> parameterMap = this.propertiesParamMap();

    // 參數回填
    ObjectRelationTypeEnum relationTypeEnum = ObjectRelationTypeEnum.getEnum(parameterMap.get(PROPERTIES_ORM_TYPE_TITLE));

    DatabaseParam databaseParam = new DatabaseParam(
        DatabaseTypeEnum.getEnum(parameterMap.get(DataSourceEnum.DRIVER_CLASS_NAME.paramTitle())),
        parameterMap.get(DataSourceEnum.URL.paramTitle()),
        parameterMap.get(DataSourceEnum.USERNAME.paramTitle()),
        parameterMap.get(DataSourceEnum.PASSWORD.paramTitle())
    );


    return switch (relationTypeEnum) {
      case MYBATIS -> this.myBatisPropertiesParams(parameterMap, profileActive, databaseParam, relationTypeEnum);
      case HIBERNATE -> null;
    };
  }

  private CodegenModuleCore myBatisPropertiesParams(Map<String, String> parameterMap, String profile, DatabaseParam databaseParam, ObjectRelationTypeEnum relationTypeEnum) {
    MyBatisPackagePathParam myBatisPackagePathParam = new MyBatisPackagePathParam(
        parameterMap.get(MyBatisPackagePathEnum.PACKAGE_DAO_BASE.paramTitle()),
        parameterMap.get(MyBatisPackagePathEnum.PACKAGE_DAO.paramTitle()),
        parameterMap.get(MyBatisPackagePathEnum.PACKAGE_VO_BASE.paramTitle()),
        parameterMap.get(MyBatisPackagePathEnum.PACKAGE_VO.paramTitle()),
        parameterMap.get(MyBatisPackagePathEnum.PACKAGE_MAPPER_BASE.paramTitle()),
        parameterMap.get(MyBatisPackagePathEnum.PACKAGE_MAPPER.paramTitle())
    );

    MyBatisTemplatePathParam myBatisTemplatePathParam = new MyBatisTemplatePathParam(
        parameterMap.get(MyBatisTemplatePathEnum.TEMPLATE_DAO_BASE.paramTitle()),
        parameterMap.get(MyBatisTemplatePathEnum.TEMPLATE_DAO.paramTitle()),
        parameterMap.get(MyBatisTemplatePathEnum.TEMPLATE_VO_BASE.paramTitle()),
        parameterMap.get(MyBatisTemplatePathEnum.TEMPLATE_VO.paramTitle()),
        parameterMap.get(MyBatisTemplatePathEnum.TEMPLATE_MAPPER_BASE.paramTitle()),
        parameterMap.get(MyBatisTemplatePathEnum.TEMPLATE_MAPPER.paramTitle())
    );

    MyBatisOutputPathParam myBatisOutputPathParam = new MyBatisOutputPathParam(
        parameterMap.get(MyBatisOutputPathEnum.OUTPUT_MAIN_DIR.paramTitle()),
        parameterMap.get(MyBatisOutputPathEnum.OUTPUT_DAO_PATH.paramTitle()),
        parameterMap.get(MyBatisOutputPathEnum.OUTPUT_VO_PATH.paramTitle()),
        parameterMap.get(MyBatisOutputPathEnum.OUTPUT_MAPPER_PATH.paramTitle())
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

  @Override
  public void generate(CodegenModuleCore core) {

  }

  private Map<String, String> propertiesParamMap() throws IOException {
    Map<String, String> resultMap = new HashMap<>();
    File propertiesFile = propertiesResource.getFile();
    BufferedReader reader = new BufferedReader(new FileReader(propertiesFile));
    try (reader) {
      String tempStr;
      do {
        tempStr = reader.readLine();
        String[] paramArray = ParamUtil.inputParam(tempStr);
        if (paramArray.length != 0) {
          resultMap.put(paramArray[0], paramArray[1]);
        }
      } while (tempStr != null);
    }
    return resultMap;
  }
}
