package io.github.gcdd1993.jpa.codegen.render;

import io.github.gcdd1993.jpa.codegen.metadata.EntityInfo;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 渲染请求
 *
 * @author gaochen
 * Created on 2019/6/20.
 */
@Data
public class RenderingRequest {
    // 在模板文件中使用的属性
    private String ftlName;
    private String ftlPath;
    private String savePath;
    private String packageName;
    private boolean cover;
    private String className;
    private String author;
    private String date;
    private String comments;
    private EntityInfo entity;
    private List<String> imports;
    // 一个Entity对应多个响应，如Controller，Service等的代码
    private Map<String, RenderingResponse> lastRenderResponse;
    private Map<String, String> otherParams;
}
