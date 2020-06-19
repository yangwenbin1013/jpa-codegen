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
    // 子文件夹名
    private String flag;
    // 文件后缀，如Controller，Service
    private String classNameSuffix;
    // ftl模板文件名
    private String ftlName;

    // 以下字段没有用到
    private String packageName;
    private String savePath;
}
