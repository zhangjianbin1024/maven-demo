package com.maven.plugin.myke;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * maven 目标 mykeHelp 任务处理逻辑类
 * <p>
 * 插件执行命令：mvn org.myke:maven-plugin:mykeHelp
 *
 * @author: zh
 * @date: 2020/2/16/016 18:03
 */
//@Mojo注解用来标注这个类是一个目标类， maven对插件进行构建的时候会根据这个注解来找到这个插件的目标
@Mojo(name = "mykeHelp")
public class HelpMojo extends AbstractMojo {


    /**
     * String 类型参数
     * <p>
     * 要显示的问候语。
     * <p>
     *
     * @Parameter 注解将变量标识为mojo参数
     */
    @Parameter(property = "sayhi.greeting", defaultValue = "Hello World!")
    private String greeting;

    /**
     * Boolean参数
     */
    @Parameter
    private boolean myBoolean;


    /**
     * 数字类型参数
     */
    @Parameter
    private Integer myInteger;

    /**
     * File类型参数
     */
    @Parameter
    private File myFile;

    public enum Color {
        GREEN,
        RED,
        BLUE
    }

    /**
     * 枚举类型参数
     */
    @Parameter
    private Color myColor;

    /**
     * 数组类型参数
     */
    @Parameter
    private String[] myArray;

    /**
     * Collections类型参数
     */
    @Parameter
    private List myList;

    /**
     * Maps类型参数
     */
    @Parameter
    private Map myMap;

    /**
     * Properties类型参数
     */
    @Parameter
    private Properties myProperties;

    public static class Person {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    /**
     * 自定义类型参数
     */
    @Parameter
    private Person person;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        this.getLog().info("mykeHelp maven plugin start!");

        Field[] declaredFields = HelpMojo.class.getDeclaredFields();

        Arrays.stream(declaredFields).forEach(f -> {
            if (f.isAccessible()) {
                f.setAccessible(true);
            }
            try {
                this.getLog().info(f.getName() + ":" + f.get(this));
            } catch (IllegalAccessException e) {
                this.getLog().warn(e);
            }
        });
        this.getLog().info("mykeHelp maven plugin end!");
    }
}
