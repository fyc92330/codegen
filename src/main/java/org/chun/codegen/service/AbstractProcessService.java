package org.chun.codegen.service;

import jakarta.annotation.PostConstruct;
import org.chun.codegen.reocrd.CodegenModuleCore;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public abstract class AbstractProcessService implements IProcessService {
  private volatile CodegenModuleCore core;
  protected ClassPathResource propertiesResource;

  @PostConstruct
  void defaultModule() {
    core = CodegenModuleCore.defaultModule();
  }

  public void main(String profileActive) throws Exception {
    if (core.diff(profileActive)) {
      core = this.inputParameter(profileActive);
    }
    this.generate(core);
  }

}
