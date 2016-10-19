package org.jenkinsci.plugins.buildlabel.administration;

import java.util.regex.*;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.util.FormValidation;

public class Color extends AbstractDescribableImpl<Color> {
    private static final Pattern COLOR_ID_VALIDATOR = Pattern.compile("^[_a-zA-Z]+[_a-zA-Z0-9-]*$");
    private static final Pattern COLOR_VALIDATOR = Pattern
            .compile("^(#[0-9A-Fa-f]{3,6}|[rR][gG][bB]\\([0-9]{1,3},[0-9]{1,3},[0-9]{1,3}\\)|[hH][sS][lL]\\([0-9]{1,3}%?,[0-9]{1,3}%?,[0-9]{1,3}%?\\)|[A-Za-z]+)$");
    private final String m_id;
    private final String m_backgroundColor;
    private final String m_textColor;

    @DataBoundConstructor
    public Color(String id, String backgroundColor, String textColor) {
        m_id = id;
        m_backgroundColor = backgroundColor;
        m_textColor = textColor;
    }

    public String getId() {
        return m_id;
    }

    public String getBackgroundColor() {
        return m_backgroundColor;
    }

    public String getTextColor() {
        return m_textColor;
    }

    @Extension
    public static class SetupConfigItemDescriptor extends Descriptor<Color> {

        @Override
        public String getDisplayName() {
            return "";
        }

        @SuppressWarnings("unused")
        public FormValidation doCheckId(@QueryParameter String value) {
            if (value != null && COLOR_ID_VALIDATOR.matcher(value).matches()) {
                return FormValidation.ok();
            } else {
                return FormValidation.error("Invalid id format");
            }
        }

        @SuppressWarnings("unused")
        public FormValidation doCheckBackgroundColor(@QueryParameter String value) {
            if (value != null && COLOR_VALIDATOR.matcher(value).matches()) {
                return FormValidation.ok();
            } else {
                return FormValidation.error("Color is invalid");
            }
        }

        @SuppressWarnings("unused")
        public FormValidation doCheckTextColor(@QueryParameter String value) {
            if (value != null && COLOR_VALIDATOR.matcher(value).matches()) {
                return FormValidation.ok();
            } else {
                return FormValidation.error("Color is invalid");
            }
        }
    }
}
