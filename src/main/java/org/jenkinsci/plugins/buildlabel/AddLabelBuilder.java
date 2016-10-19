package org.jenkinsci.plugins.buildlabel;

import java.io.*;
import javax.servlet.*;
import org.jenkinsci.plugins.buildlabel.administration.ColorConfiguration;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import jenkins.tasks.SimpleBuildStep;

public class AddLabelBuilder extends Builder implements SimpleBuildStep {

    private final String m_text;
    private final String m_colorId;

    @DataBoundConstructor
    public AddLabelBuilder(String text, String colorId) {
        m_text = text;
        m_colorId = colorId;
    }

    public String getText() {
        return m_text;
    }

    public String getColorId() {
        return m_colorId;
    }

    @Override
    public void perform(Run<?, ?> build, FilePath workspace, Launcher launcher, TaskListener listener) {
        build.addAction(new LabelAction(m_text, m_colorId));
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        public DescriptorImpl() {
            load();
        }

        public FormValidation doCheckName(@QueryParameter String value) throws IOException, ServletException {
            if (value.length() == 0) {
                return FormValidation.error("Please set a text");
            }
            if (value.length() > 20) {
                return FormValidation.warning("Isn't the m_text too long?");
            }
            return FormValidation.ok();
        }

        public ListBoxModel doFillColorIdItems() {
            ListBoxModel result = new ListBoxModel();
            ColorConfiguration.get().getColors().forEach(color -> result.add(color.getId()));
            return result;
        }

        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        /**
         * This human readable m_text is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Add a label to the build";
        }
    }
}

