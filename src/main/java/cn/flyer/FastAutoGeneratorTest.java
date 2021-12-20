package cn.flyer;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastAutoGeneratorTest {
    /**
     * 执行初始化数据库脚本
     */
    private static final String url = "jdbc:mysql://1.117.176.58:3307/csmss?useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8";
    private static final String username = "root";
    private static final String userpassword = "dgxAIjrj1314";
    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder(url, username, userpassword);

    public static void main(String[] args) {
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                // 全局配置
                .globalConfig((scanner,builder) -> builder.author("flyer")
                        .enableSwagger()
                        .fileOverride() //覆盖原文件
                        .outputDir(scanner.apply("请输入项目地址"))
                        .disableOpenDir()
                )
                // 包配置
                .packageConfig(builder -> builder.parent("cn.csmss"))
                // 策略配置
                .strategyConfig((scanner, builder) -> builder
                        .addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                        .controllerBuilder()
                        .superClass("cn.csmss.controller.BaseController")
                        .enableRestStyle()
                        .serviceBuilder()
                        .superServiceClass("cn.csmss.service.BaseService")
                        .mapperBuilder()
                        .enableMapperAnnotation()
                        .enableBaseResultMap()
                        .enableBaseColumnList()
                        .entityBuilder()
                        .superClass("cn.csmss.entity.BaseEntity")
                        .addSuperEntityColumns("id", "create_by", "create_time", "modified_by", "modified_time", "valid")
                        .logicDeleteColumnName("valid")
                        .enableLombok()
                        .addTableFills(new Property("created_time", FieldFill.INSERT))
                        .addTableFills(new Property("modify_time", FieldFill.INSERT_UPDATE))
                        .build())
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
