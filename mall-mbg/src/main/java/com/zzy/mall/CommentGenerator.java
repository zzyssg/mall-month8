package com.zzy.mall;

import cn.hutool.core.util.StrUtil;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;
import java.util.Set;

/**
 * @program: mall
 * @description: 自定义生成注释
 * @author: zzy
 * @create: 2024-07-22
 */
public class CommentGenerator extends DefaultCommentGenerator {

    private boolean addRemarkComments = false;

    private static final String SUFFIX_MAPPER = "Mapper";
    private static final String SUFFIX_EXAMPLE = "Example";

    private static final String API_MODEL_PROPERTY_IMPORTED  =  "io.swagger.annotations.ApiModelProperty";

    @Override
    public void addConfigurationProperties(Properties props){
        super.addConfigurationProperties(props);
        this.addRemarkComments = Boolean.parseBoolean(props.getProperty("addRemarkComments"));

    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn){
        String remarks = introspectedColumn.getRemarks();
        if (!StrUtil.isEmpty(remarks)){
            //处理remarks，将remarks的双引号转为单引号
            if (remarks.contains("\"")){
                remarks = remarks.replace("\"","'");
            }
            //添加API注解
            remarks = "@ApiModelProperty(\"" + remarks + "\")";
            field.addJavaDocLine(remarks);
        }

    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit){
        super.addJavaFileComment(compilationUnit);
        FullyQualifiedJavaType type = compilationUnit.getType();
        if (!type.toString().endsWith(SUFFIX_MAPPER) && !type.toString().endsWith(SUFFIX_EXAMPLE)){
            compilationUnit.addImportedType(new FullyQualifiedJavaType(API_MODEL_PROPERTY_IMPORTED));
        }
    }



}