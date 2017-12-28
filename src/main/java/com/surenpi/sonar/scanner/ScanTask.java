package com.surenpi.sonar.scanner;

import com.surenpi.jenkins.pipeline.step.DurableController;
import hudson.EnvVars;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.durabletask.Controller;
import org.jenkinsci.plugins.durabletask.DurableTask;
import org.sonarsource.scanner.api.EmbeddedScanner;
import org.sonarsource.scanner.api.LogOutput;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * @author suren
 */
public class ScanTask extends DurableTask implements Serializable
{
    private final ScannerStep step;

    public ScanTask(ScannerStep step)
    {
        this.step = step;
    }

    @Override
    public Controller launch(EnvVars env, FilePath workspace, Launcher launcher,
                             TaskListener listener) throws IOException, InterruptedException
    {
        PrintStream logger = listener.getLogger();

        LogOutput out = new LogOutput(){

            @Override
            public void log(String s, Level level)
            {
                logger.println(s);
            }
        };

        EmbeddedScanner scanner = EmbeddedScanner.create(out);

        scanner.start();

        Properties pro = new Properties();
        //必需配置
        pro.put("sonar.projectKey", step.getProjectKey());
        pro.put("sonar.projectBaseDir", step.getProjectBaseDir()); //工程路径
        pro.put("sonar.sources", step.getSources()); //要扫描的模块文件夹

        //可选配置
        putOption("sonar.host.url", step.getHost(), pro);
        putOption("sonar.login", step.getLogin(), pro);

        scanner.runAnalysis(pro);

        return new DurableController();
    }

    private void putOption(String key, String value, Properties pro)
    {
        if(key == null)
        {
            return;
        }

        pro.put(key, value);
    }
}