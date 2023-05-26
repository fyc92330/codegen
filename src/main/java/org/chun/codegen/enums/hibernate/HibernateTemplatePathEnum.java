package org.chun.codegen.enums.hibernate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.chun.codegen.enums.interfaces.ITemplatePathEnum;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@AllArgsConstructor
public enum HibernateTemplatePathEnum implements ITemplatePathEnum {

  TEMPLATE_ENTITY("", "", new ClassPathResource("template/entity.ftl")),
  TEMPLATE_REPOSITORY("", "", new ClassPathResource("template/repository.ftl"));

  private final String message;
  private final String paramTitle;
  @Getter
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
    return null;
  }

  public String getTemplateFileName() throws IOException {
    return this.template.getFile().getName();
  }

}
