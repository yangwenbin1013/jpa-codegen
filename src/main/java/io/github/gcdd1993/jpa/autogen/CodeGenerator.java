package io.github.gcdd1993.jpa.autogen;

import io.github.gcdd1993.jpa.autogen.config.CodeGeneratorConfig;
import io.github.gcdd1993.jpa.autogen.config.ModuleConfig;
import io.github.gcdd1993.jpa.autogen.model.EntityInfo;
import io.github.gcdd1993.jpa.autogen.model.EntityInfoParser;
import io.github.gcdd1993.jpa.autogen.render.DefaultRender;
import io.github.gcdd1993.jpa.autogen.render.IRender;
import io.github.gcdd1993.jpa.autogen.util.ReflectUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author gaochen
 * Created on 2019/6/18.
 */
public class CodeGenerator {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private static final String SRC_PATH = "src/main/";

    private CodeGeneratorConfig config;

    private Properties properties = new Properties();

    private List<String> moduleList = new LinkedList<>();

    public CodeGenerator(String configLocation) {
        try {
            properties.load(CodeGenerator.class.getResourceAsStream(configLocation));

            config = new CodeGeneratorConfig();
            String entityPackage = properties.getProperty("entity.package");
            if (entityPackage == null) {
                throw new RuntimeException("must give me the entity package");
            }

            config.setEntityPackage(entityPackage);

            // io.github.gcdd1993.entity -> entity flag is entity
            config.setEntityFlag(entityPackage.substring(entityPackage.lastIndexOf(".") + 1));

            config.setAuthor(properties.getProperty("author", System.getProperty("user.name")));
            config.setComments(properties.getProperty("comments", "code generated by jpa codegen"));
            config.setDate(DATE_TIME_FORMATTER.format(LocalDate.now()));

            config.setFtlPath(properties.getProperty("template.dir", SRC_PATH + "resources/template/"));
            config.setCover(Boolean.parseBoolean(properties.getProperty("cover", "false")));

            // custom other params
            Map<String, String> otherParams = new HashMap<>(256);
            for (Object key : properties.keySet()) {
                String keyStr = key.toString();
                if (keyStr.contains(".") &&
                        "custom".equals(keyStr.substring(0, keyStr.indexOf(".")))) {
                    otherParams.put(keyStr.substring(keyStr.indexOf(".") + 1).replace(".", "_"), properties.getProperty(keyStr));
                }
            }
            config.setOtherParams(otherParams);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析模块配置
     *
     * @param module 模块
     * @return 模块配置
     */
    private ModuleConfig parseModuleConfig(String module) {
        ModuleConfig moduleConfig = new ModuleConfig();
        moduleConfig.setClassNameSuffix(properties.getProperty(module + ".class.suffix",
                module.substring(0, 1).toUpperCase().concat(module.substring(1))));
        moduleConfig.setFtlName(config.getFtlPath() + properties.getProperty(module + ".ftlName", module + ".ftl"));

        String packageName = properties.getProperty(module + ".package");
        if (packageName != null) {
            moduleConfig.setPackageName(packageName);
            moduleConfig.setSavePath(SRC_PATH + "java/" + packageName.replace(".", "/") + "/");
        }

        return moduleConfig;
    }

    public void generate() {
        List<Class<?>> entityClasses = ReflectUtils.getClassListByAnnotation(config.getEntityPackage(), "javax.persistence.Entity");
        List<EntityInfo> entityInfos = entityClasses.stream()
                .map(EntityInfoParser::parse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        IRender render = new DefaultRender(config);

        entityInfos.forEach(entityInfo ->
                moduleList.forEach(module -> render.render(entityInfo, module)));
    }

    /**
     * 注册渲染组件
     *
     * @param module 模块名
     */
    public CodeGenerator registerRender(String module) {
        ModuleConfig moduleConfig = parseModuleConfig(module);
        config.getModuleConfigMap().put(module, moduleConfig);
        moduleList.add(module);
        return this;
    }

}
