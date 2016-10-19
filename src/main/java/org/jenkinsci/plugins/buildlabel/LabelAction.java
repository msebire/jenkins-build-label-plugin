package org.jenkinsci.plugins.buildlabel;

import javax.annotation.*;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;
import hudson.model.BuildBadgeAction;

@ExportedBean
public class LabelAction implements BuildBadgeAction {
    private final String m_text;
    @Nullable
    private final String m_colorId;

    public LabelAction(String text, @Nullable String colorId) {
        m_text = text;
        m_colorId = colorId;
    }

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "";
    }

    @Override
    public String getUrlName() {
        return null;
    }

    @Exported
    public String getText() {
        return m_text;
    }

    @Exported
    public String getColorId() {
        return m_colorId;
    }
}
