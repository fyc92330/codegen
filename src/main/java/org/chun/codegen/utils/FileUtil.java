package org.chun.codegen.utils;

import lombok.SneakyThrows;
import org.chun.codegen.exception.PropertyFileGeneratorFailException;
import org.chun.codegen.exception.PropertyFileNotExistsException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.chun.codegen.constant.SystemParamConst.PROPERTIES_OUTPUT_PATH;

public class FileUtil {

  public static void writeByResource(String filePath, String content) {
    ClassPathResource resource = new ClassPathResource(filePath);
    writeByResource(resource, content);
  }

  @SneakyThrows
  public static void writeByResource(ClassPathResource resource, String content) {
    if (resource.exists() && resource.isFile()) {
      write(resource.getFile(), content);
    } else {
      write(new File(PROPERTIES_OUTPUT_PATH + resource.getFilename()), content);
    }
  }

  public static void write(String filePath, String content) {
    File file = new File(filePath);
    if (file.exists() && file.isFile()) {
      write(file, content);
    } else {
      throw new PropertyFileNotExistsException(filePath);
    }
  }

  public static void write(File file, String content) {
    try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
      writer.write(content);
    } catch (IOException e) {
      throw new PropertyFileGeneratorFailException(file.getName());
    }
  }
}
