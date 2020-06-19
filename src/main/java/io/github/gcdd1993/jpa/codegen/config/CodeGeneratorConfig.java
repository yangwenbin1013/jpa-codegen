package io.github.gcdd1993.jpa.codegen.config;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author gaochen
 * Created on 2019/6/20.
 */
@Data
public class CodeGeneratorConfig {
    // entity的文件夹名
    private String entityFlag;
    // 配置哪些实体类需要生成代码
    private List<Class<?>> entityClasses = new ArrayList<>(256);
    private String ftlPath;
    private String author;
    private String date;
    private String comments;
    private boolean cover;
    // 注册生成哪些模块的代码，如Controller，Service等
    private Map<String, ModuleConfig> moduleConfigMap = new HashMap<>();

    private Map<String, String> otherParams;
}
