package org.chun.codegen.enums.mybatis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.chun.codegen.enums.interfaces.IOutputPathEnum;

import static org.chun.codegen.constant.SystemParamConst.DOWNLOADS_FOLDER_PATH;

@AllArgsConstructor
public enum MyBatisOutputPathEnum implements IOutputPathEnum {

  OUTPUT_MAIN_DIR("輸入檔案輸出專案資料夾路徑: \n", "output.main_dir", DOWNLOADS_FOLDER_PATH),
  OUTPUT_DAO_PATH("輸入 DAO 輸出資料夾路徑: \n", "output.dao_path", "/dao"),
  OUTPUT_VO_PATH("輸入 VO 輸出資料夾路徑: \n", "output.vo_path", "/vo"),
  OUTPUT_MAPPER_PATH("輸入 MAPPER 輸出資料夾路徑: \n", "output.mapper_path", "/mapper");

  private final String message;
  private final String paramTitle;
  @Getter
  private final String defaultPath;

  @Override
  public String message() {
    return this.message;
  }

  @Override
  public String paramTitle() {
    return this.paramTitle;
  }

  @Override
  public Class<? extends Enum<?>> type() {
    return MyBatisOutputPathEnum.class;
  }

}
