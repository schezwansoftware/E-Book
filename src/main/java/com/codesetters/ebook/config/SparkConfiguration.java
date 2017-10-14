package com.codesetters.ebook.config;

import org.apache.spark.SparkConf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scala.tools.cmd.gen.AnyVals;
@Configuration
public class SparkConfiguration {
    private transient SparkConf defaultSparkConfiguration;
    public static final String KEYSPACENAME="ebook";
    public SparkConf getDefaultSparkConfiguration() {
        defaultSparkConfiguration=new SparkConf();
        defaultSparkConfiguration.setMaster("local[4]");
        defaultSparkConfiguration.setAppName("ebook");
        defaultSparkConfiguration.set("","localhost");
        return defaultSparkConfiguration;
    }


}
