package io.github.gcdd1993.jpa.codegen.render;

import io.github.gcdd1993.jpa.codegen.config.CodeGeneratorConfig;
import io.github.gcdd1993.jpa.codegen.config.ModuleConfig;
import io.github.gcdd1993.jpa.codegen.metadata.EntityInfo;
import io.github.gcdd1993.jpa.codegen.util.FreeMarkerUtils;

import java.util.*;

/**
 * TODO
 *
 * @author gaochen
 * Created on 2019/6/20.
 */
public class DefaultRender implements IRender {

    private final CodeGeneratorConfig config;

    private Map<String, RenderingResponse> lastRenderingResponseMap = new HashMap<>();

    public DefaultRender(CodeGeneratorConfig config) {
        this.config = config;
    }

    @Override
    public final RenderingResponse render(EntityInfo entityInfo, String module) {
        RenderingRequest renderingRequest = new RenderingRequest();
        renderingRequest.setLastRenderResponse(lastRenderingResponseMap);

        // 从config中，获取相应模块的配置，如Controller，Service等的配置
        ModuleConfig moduleConfig = config.getModuleConfigMap().get(module);
        // 设置要生成的类的类名
        renderingRequest.setClassName(entityInfo.getClassName().replace(config.getEntityFlag().substring(0, 1).toUpperCase().concat(config.getEntityFlag().substring(1)),
                moduleConfig.getClassNameSuffix()));
        // 设置要生成的类的包名
        String packageName = entityInfo.getPackageName().replace(config.getEntityFlag(), moduleConfig.getFlag());
        renderingRequest.setPackageName(packageName);
        // 设置生成的文件的路径
        renderingRequest.setSavePath("src/main/java/" + packageName.replace(".", "/") + "/");
        // 设置模板文件的名字
        renderingRequest.setFtlName(moduleConfig.getFtlName());
        // 设置模板文件的目录
        renderingRequest.setFtlPath(config.getFtlPath());
        renderingRequest.setCover(config.isCover());

        renderingRequest.setEntity(entityInfo);

        renderingRequest.setAuthor(config.getAuthor());
        renderingRequest.setComments(config.getComments());
        renderingRequest.setDate(config.getDate());

        // fields ，只支持基本类型映射
        renderingRequest.setOtherParams(config.getOtherParams());

        // check for other imports
        renderingRequest.setImports(checkImports(entityInfo));

        // use freemarker to render code.
        RenderingResponse lastRenderingResponse = FreeMarkerUtils.process(renderingRequest);

        lastRenderingResponseMap.put(module, lastRenderingResponse);
        return lastRenderingResponse;
    }

    private List<String> checkImports(EntityInfo entityInfo) {
        Set<String> imports = new HashSet<>();
        entityInfo.getFields().parallelStream().forEach(t -> {
            if (!t.getTypeName().contains("java.lang") && t.getTypeName().indexOf(".") > -1) {
                imports.add(t.getTypeName());
            }
        });
        return new ArrayList<>(imports);
    }

}
