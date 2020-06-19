package io.github.gcdd1993.jpa.codegen.config;

import lombok.Data;

/**
 * 模块名称
 *
 * @author gaochen
 * Created on 2019/6/20.
 */
@Data
public class ModuleConfig {
    // 模块的约定信息配置，ftl模板文件名
    private String flag;
    private String packageName;
    private String classNameSuffix;
    private String ftlName;
    private String savePath;
}
