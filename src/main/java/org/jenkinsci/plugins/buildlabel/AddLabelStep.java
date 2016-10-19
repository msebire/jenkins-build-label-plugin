package org.jenkinsci.plugins.buildlabel;

import javax.annotation.*;
import javax.inject.*;
import org.jenkinsci.plugins.buildlabel.administration.ColorConfiguration;
import org.jenkinsci.plugins.workflow.steps.AbstractStepDescriptorImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractStepImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractSynchronousNonBlockingStepExecution;
import org.jenkinsci.plugins.workflow.steps.StepContextParameter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import hudson.Extension;
import hudson.model.Run;
import hudson.util.ListBoxModel;

public class AddLabelStep extends AbstractStepImpl {
    private final String m_text;
    @Nullable
    private String m_colorId;

    @DataBoundConstructor
    public AddLabelStep(String text) {
        m_text = text;
    }

    public String getText() {
        return m_text;
    }

    @Nullable
    public String getColorId() {
        return m_colorId;
    }

    @DataBoundSetter
    public void setColorId(@Nullable String colorId) {
        m_colorId = colorId;
    }

    public static final class Execution extends AbstractSynchronousNonBlockingStepExecution<String> {
        @StepContextParameter
        transient Run<?, ?> m_run;
        @Inject
        transient AddLabelStep m_step;

        @Override
        protected String run() throws Exception {
            m_run.addAction(new LabelAction(m_step.getText(), m_step.getColorId()));
            return "\"" + m_step.getText() + "\" label added";
        }
    }

    @Extension
    public static final class DescriptorImpl extends AbstractStepDescriptorImpl {
        public DescriptorImpl() {
            super(AddLabelStep.Execution.class);
        }

        @Override
        public String getFunctionName() {
            return "addLabel";
        }

        @Nonnull
        @Override
        public String getDisplayName() {
            return "Add a label to the current build";
        }

        public ListBoxModel doFillColorIdItems() {
            ListBoxModel result = new ListBoxModel();
            ColorConfiguration.get().getColors().forEach(color -> result.add(color.getId()));
            return result;
        }
    }
}
