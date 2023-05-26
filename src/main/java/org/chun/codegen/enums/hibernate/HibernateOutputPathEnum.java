package org.chun.codegen.enums.hibernate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.chun.codegen.enums.interfaces.IOutputPathEnum;

import static org.chun.codegen.constant.SystemParamConst.DOWNLOADS_FOLDER_PATH;

@AllArgsConstructor
public enum HibernateOutputPathEnum implements IOutputPathEnum {

  OUTPUT_MAIN_DIR("輸入檔案輸出專案資料夾路徑: \n", "output.main_dir", DOWNLOADS_FOLDER_PATH),
  OUTPUT_ENTITY("輸入 ENTITY 輸出資料夾路徑: \n", "output.entity_path", "/entity"),
  OUTPUT_REPOSITORY("輸入 REPOSITORY 輸出資料夾路徑: \n", "output.repository_path", "/repository");

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
    return HibernateOutputPathEnum.class;
  }
}
