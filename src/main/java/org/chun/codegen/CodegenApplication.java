package org.chun.codegen;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.codegen.constant.ValidParamConst;
import org.chun.codegen.service.IProcessService;
import org.chun.codegen.service.OperatorService;
import org.chun.codegen.service.ProducerService;
import org.chun.codegen.utils.ScannerUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class CodegenApplication implements CommandLineRunner {

  private final ProducerService producerService;
  private final OperatorService operatorService;
  private final Environment environment;

  public static void main(String[] args) {
    SpringApplication.run(CodegenApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    producerService.execute();

    ///Users/chun/project/nuance/codegen/src/main/resources


//    producerService.execute();
//    this.rule().execute();

//    log.info("e == {}", System.getenv("HOME"));
//    log.info("envmap == {}", System.getProperty("spring.profiles.active"));
//    log.info("env == {}", environment.getDefaultProfiles());
//
//    System.getenv().forEach((k,v) -> log.info("{} ------------->>>> {} }", k , v));
//    log.info("properties:{}", System.getProperties().toString().replaceAll(",", "\n,"));
  }

  //fixme
  private IProcessService rule() {
    return ScannerUtil.input("").equals(ValidParamConst.Y_STR)
        ? producerService
        : operatorService;
  }
}

