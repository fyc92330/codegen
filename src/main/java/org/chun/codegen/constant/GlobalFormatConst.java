package org.chun.codegen.constant;

public class GlobalFormatConst {
  public static final String PROPERTIES_NAME_FORMAT = "application-%s.properties";
  public static final String PROPERTIES_TITLE_FORMAT = "## %s ##";
  public static final String DATABASE_NAME_PATTERN_STR = "(?<=//\\w+\\.[\\w:]+/)[^/?]+";
  public static final String PROFILE_ACTIVE_PATTERN_STR = "application-([^.]+)\\.properties";
}
