package io.github.gcdd1993.jpa.codegen.metadata;

import io.github.gcdd1993.jpa.codegen.util.ReflectUtils;

import javax.persistence.IdClass;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 默认的实体解析器
 *
 * @author gaochen
 * Created on 2019/6/21.
 */
public class DefaultEntityInfoParser extends BaseEntityParser {

    /**
     * 主键注解
     */
    private static final List<Class<? extends Annotation>> ID_CLASS_LIST = Arrays.asList(javax.persistence.Id.class,
            org.springframework.data.annotation.Id.class);

    @Override
    public EntityInfo parseEntity(Class<?> clazz) {
        EntityInfo entityInfo = new EntityInfo();
        entityInfo.setClassName(clazz.getSimpleName());

        String packageName = clazz.getPackage().getName();

        entityInfo.setPackageName(packageName);

        Table tableAnnotation = clazz.getAnnotation(javax.persistence.Table.class);
        if (tableAnnotation != null) {
            entityInfo.setTableName(tableAnnotation.name());
        }
        return entityInfo;
    }

    @Override
    public List<FieldInfo> parseField(Class<?> clazz) {
        List<FieldInfo> fields = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field f : declaredFields) {
            Package packageType = f.getType().getPackage();
            boolean javaLangType = packageType == null || "java.lang".equals(packageType.getName());
            boolean isId = false;
            for (Class<? extends Annotation> idClass : ID_CLASS_LIST) {
                boolean hasAnnotation = ReflectUtils.hasAnnotation(f, idClass);
                // 跳过主键字段
                if (hasAnnotation) {
                    isId = true;
                    break;
                }
            }

            // 只解析出基本类型
            if (/*javaLangType &&*/ !isId) {
                FieldInfo fieldInfo = new FieldInfo();
                fieldInfo.setClassName(f.getType().getSimpleName());
                fieldInfo.setTypeName(f.getType().getTypeName());
                fieldInfo.setPackageName(packageType == null ? f.getType().getTypeName() : packageType.getName());

                fieldInfo.setName(f.getName());

                // 字段注解
                Annotation[] fieldAnnotations = f.getAnnotations();
                if (fieldAnnotations.length > 0) {
                    List<AnnotationInfo> annotationInfos = new ArrayList<>();

                    for (Annotation annotation : fieldAnnotations) {
                        AnnotationInfo annotationInfo = new AnnotationInfo();
                        annotationInfo.setClassName(annotation.annotationType().getSimpleName());
                        annotationInfo.setTypeName(annotation.annotationType().getTypeName());
                        annotationInfo.setPackageName(annotation.annotationType().getPackage().getName());
                        annotationInfos.add(annotationInfo);
                    }
                    fieldInfo.setAnnotations(annotationInfos);
                }

                fields.add(fieldInfo);
            }
        }
        // try from super class
        Class<?> maybeExists = clazz.getSuperclass();
        if (!Object.class.equals(maybeExists)) {
            fields.addAll(parseField(maybeExists));
        }
        return fields;
    }

    @Override
    public IdInfo parseId(Class<?> clazz) {
        // 尝试从类注解获取主键信息
        IdClass idClassAnnotation = clazz.getAnnotation(javax.persistence.IdClass.class);
        if (idClassAnnotation != null) {
            Package p = idClassAnnotation.value().getPackage();
            IdInfo idInfo = new IdInfo();
            idInfo.setClassName(idClassAnnotation.value().getSimpleName());
            idInfo.setTypeName(idClassAnnotation.value().getTypeName());
            idInfo.setPackageName(p == null ? idClassAnnotation.value().getTypeName() : p.getName());
            return idInfo;
        }
        // 尝试获取第一个带主键注解的字段
        for (Class<? extends Annotation> idClass : ID_CLASS_LIST) {
            Optional<Field> fieldByAnnotation = ReflectUtils.getFieldByAnnotation(clazz, idClass);
            if (fieldByAnnotation.isPresent()) {
                Field field = fieldByAnnotation.get();
                IdInfo idInfo = new IdInfo();
                idInfo.setClassName(field.getType().getSimpleName());
                idInfo.setTypeName(field.getType().getTypeName());
                idInfo.setPackageName(field.getType().getPackage() == null ? field.getType().getTypeName()
                        : field.getType().getPackage().getName());
                return idInfo;
            }
        }
        // try from super class
        Class<?> maybeExists = clazz.getSuperclass();
        if (!Object.class.equals(maybeExists)) {
            return parseId(maybeExists);
        }
        return null;
    }
}
