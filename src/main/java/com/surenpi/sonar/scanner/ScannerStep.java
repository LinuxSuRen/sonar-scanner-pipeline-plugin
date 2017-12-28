package com.surenpi.sonar.scanner;

import com.surenpi.jenkins.pipeline.step.DurableExecution;
import com.surenpi.jenkins.pipeline.step.DurableStep;
import com.surenpi.jenkins.pipeline.step.DurableTaskStepDescriptor;
import hudson.Extension;
import org.jenkinsci.plugins.durabletask.DurableTask;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

/**
 * @author suren
 */
public class ScannerStep extends DurableStep
{
    private String host;
    private String login;

    private String projectKey;
    private String projectBaseDir;
    private String sources;

    @DataBoundConstructor
    public ScannerStep(){}

    @Override
    public StepExecution start(StepContext context) throws Exception
    {
        return new DurableExecution(context, this);
    }

    @Override
    protected DurableTask task()
    {
        return new ScanTask(this);
    }

    @Extension
    public static final class DescriptorImpl extends DurableTaskStepDescriptor
    {
        @Override
        public String getFunctionName()
        {
            return "sonarScan";
        }

        @Override
        public String getDisplayName()
        {
            return "sonar scanner";
        }
    }

    public String getHost()
    {
        return host;
    }

    @DataBoundSetter
    public void setHost(String host)
    {
        this.host = host;
    }

    public String getLogin()
    {
        return login;
    }

    @DataBoundSetter
    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getProjectKey()
    {
        return projectKey;
    }

    @DataBoundSetter
    public void setProjectKey(String projectKey)
    {
        this.projectKey = projectKey;
    }

    public String getProjectBaseDir()
    {
        return projectBaseDir;
    }

    @DataBoundSetter
    public void setProjectBaseDir(String projectBaseDir)
    {
        this.projectBaseDir = projectBaseDir;
    }

    public String getSources()
    {
        return sources;
    }

    @DataBoundSetter
    public void setSources(String sources)
    {
        this.sources = sources;
    }
}