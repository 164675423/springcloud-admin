package com.zh.am.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.IFileCreate;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * mybatis-plus的代码生成器配置
 *
 * @author zh
 * @date 2020/2/29
 */
public class CodeGenerator {
  private final static String BASE_PACKAGE = "com.zh.am";

  /**
   * <p>
   * 读取控制台内容
   * </p>
   */
  public static String scanner(String tip) {
    Scanner scanner = new Scanner(System.in);
    StringBuilder help = new StringBuilder();
    help.append("请输入" + tip + "：");
    System.out.println(help.toString());
    if (scanner.hasNext()) {
      String ipt = scanner.next();
      if (StringUtils.isNotBlank(ipt)) {
        return ipt;
      }
    }
    throw new MybatisPlusException("请输入正确的" + tip + "！");
  }

  public static void main(String[] args) {
    // 代码生成器
    AutoGenerator mpg = new AutoGenerator();
    // 全局配置
    GlobalConfig gc = new GlobalConfig();
    gc.setFileOverride(true);
    gc.setAuthor("");
    String projectPath = System.getProperty("user.dir");
    gc.setOutputDir(projectPath + "/src/main/java");
    gc.setOpen(false);
    gc.setIdType(IdType.NONE);
    gc.setBaseResultMap(true);//生成的mapper xml增加baseResultMap
    // gc.setSwagger2(true); 实体属性 Swagger2 注解
    mpg.setGlobalConfig(gc);

    // 数据源配置
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setUrl("jdbc:mysql://localhost:3306/access_management?useSSL=false");
    // dsc.setSchemaName("public");
    dsc.setDriverName("com.mysql.jdbc.Driver");
    dsc.setUsername("root");
    dsc.setPassword("root");
    dsc.setTypeConvert(new MySqlTypeConvert() {
      @Override
      public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        if (fieldType.toLowerCase().contains("timestamp")) {
          return DbColumnType.TIMESTAMP;
        }
        if (fieldType.toLowerCase().contains("datetime")) {
          return DbColumnType.DATE;
        }
        return super.processTypeConvert(globalConfig, fieldType);
      }
    });
    mpg.setDataSource(dsc);

    // 包配置
    PackageConfig pc = new PackageConfig();
    pc.setParent(BASE_PACKAGE);
    //包名
//    String secPackage = scanner("业务模块");
//    pc.setController("api." + secPackage);
//    pc.setEntity("entity." + secPackage);
//    pc.setService("service." + secPackage);
//    pc.setMapper("dao." + secPackage);
//    pc.setServiceImpl("service." + secPackage + ".impl");
    pc.setController("api");
    pc.setMapper("dao");
    mpg.setPackageInfo(pc);

    // 自定义配置
    InjectionConfig cfg = new InjectionConfig() {
      @Override
      public void initMap() {
        // to do nothing
      }
    };

    // 如果模板引擎是 freemarker
    String templatePath = "/templates/mapper.xml.ftl";

    // 自定义输出配置
    List<FileOutConfig> focList = new ArrayList<>();
    // 自定义配置会被优先输出
    focList.add(new FileOutConfig(templatePath) {
      @Override
      public String outputFile(TableInfo tableInfo) {
        // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
        return projectPath + "/src/main/resources/mapper/"
            + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
      }
    });

    cfg.setFileCreate(new IFileCreate() {
      @Override
      public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
        // 判断自定义文件夹是否需要创建
        checkDir(filePath);
        //对于已存在的文件，只需重复生成 entity 和 mapper.xml
        File file = new File(filePath);
        boolean exist = file.exists();
        if (exist) {
          if (filePath.endsWith("Mapper.xml") || FileType.ENTITY == fileType) {
            return true;
          } else {
            return false;
          }
        }
        return true;
      }
    });

    cfg.setFileOutConfigList(focList);
    mpg.setCfg(cfg);

    // 配置模板
    TemplateConfig templateConfig = new TemplateConfig();
    templateConfig.setXml(null);
    templateConfig.setController("");
    mpg.setTemplate(templateConfig);
    // 策略配置
    StrategyConfig strategy = new StrategyConfig();
    strategy.setNaming(NamingStrategy.underline_to_camel);
    strategy.setColumnNaming(NamingStrategy.underline_to_camel);
    strategy.setVersionFieldName("row_version");
//    strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
    strategy.setEntityLombokModel(false);
    strategy.setRestControllerStyle(true);
    // 公共父类
//    strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
    // 写于父类中的公共字段
//    strategy.setSuperEntityColumns("id");
    strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
    strategy.setControllerMappingHyphenStyle(true);
    strategy.setTablePrefix(pc.getModuleName() + "_");
    mpg.setStrategy(strategy);
    mpg.setTemplateEngine(new FreemarkerTemplateEngine());
    mpg.execute();
  }

}
