package org.chun.codegen.enums.mybatis;

import lombok.AllArgsConstructor;
import org.chun.codegen.enums.interfaces.ITemplatePathEnum;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@AllArgsConstructor
public enum MyBatisTemplatePathEnum implements ITemplatePathEnum {

  TEMPLATE_DAO_BASE("", "template.dao_base", new ClassPathResource("template/baseDao.ftl")),
  TEMPLATE_DAO("", "template.dao_extend", new ClassPathResource("template/extendDao.ftl")),
  TEMPLATE_VO_BASE("", "template.vo_base", new ClassPathResource("template/baseVo.ftl")),
  TEMPLATE_VO("", "template.vo_extend", new ClassPathResource("template/extendVo.ftl")),
  TEMPLATE_MAPPER_BASE("", "template.mapper_base", new ClassPathResource("template/baseMapper.ftl")),
  TEMPLATE_MAPPER("", "template.mapper_extend", new ClassPathResource("template/extendMapper.ftl"));

  private final String message;
  private final String paramTitle;
  private final Resource template;

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
    return MyBatisTemplatePathEnum.class;
  }

  public String getTemplateFileName() throws IOException {
    return this.template.getFile().getName();
  }

}
