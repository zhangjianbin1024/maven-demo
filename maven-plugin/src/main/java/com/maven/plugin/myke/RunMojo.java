package com.maven.plugin.myke;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 插件任务：打包成 jar后，运行jar
 * <p>
 * 插件中需要通过java命令调用打包好的jar包，然后运行
 */
//运行run目标的时候，会先执行构件的 package阶段，也就是先执行项目的打包阶段，打包完成之后才会执行run目标。
@Mojo(name = "run", defaultPhase = LifecyclePhase.PACKAGE)
// @Execute 这个注解可以配置这个目标执行之前可以先执行的生命周期的阶段或者需要先执行的插件目标。
@Execute(phase = LifecyclePhase.PACKAGE)
public class RunMojo extends AbstractMojo {

    /**
     * 打包好的构件的路径
     */
    @Parameter(defaultValue = "${project.build.directory}\\${project.artifactId}-${project.version}.jar")
    private String jarPath;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            this.getLog().info("RunMojo Started:" + this.jarPath);

            // ProcessBuilder 此类用于创建操作系统进程，它提供一种启动和管理进程
            ProcessBuilder builder = new ProcessBuilder("java", "-jar", this.jarPath);

            //启动进程
            final Process process = builder.start();

            new Thread(() -> {
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                try {
                    String s;
                    while ((s = reader.readLine()) != null) {
                        System.out.println(s);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            //优雅关闭线程
            //在JVM销毁前执行的一个线程.
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    RunMojo.this.getLog().info("Destroying...");
                    process.destroy();
                    RunMojo.this.getLog().info("Shutdown hook finished.");
                }
            });

            process.waitFor();
            this.getLog().info("Finished.");
        } catch (Exception e) {
            this.getLog().warn(e);
        }
    }
}