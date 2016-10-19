package org.jenkinsci.plugins.buildlabel.administration;

import java.util.*;
import org.kohsuke.stapler.StaplerRequest;
import hudson.Extension;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;

@Extension
public class ColorConfiguration extends GlobalConfiguration {
    private List<Color> m_colors = new ArrayList<>();

    public ColorConfiguration() {
        super();
        if (getConfigFile().exists()) {
            load();
        } else {
            m_colors.add(new Color("primary", "#337AB7", "#FFFFFF"));
            m_colors.add(new Color("success", "#5CB85C", "#FFFFFF"));
            m_colors.add(new Color("info", "#5BC0DE", "#FFFFFF"));
            m_colors.add(new Color("warning", "#F0AD4E", "#FFFFFF"));
            m_colors.add(new Color("danger", "#D9534F", "#FFFFFF"));
            save();
        }
    }

    public List<Color> getColors() {
        return m_colors;
    }

    public void setColors(List<Color> colors) {
        m_colors = colors;
    }

    @Override
    public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
        req.bindJSON(this, json);
        save();
        return true;
    }

    public static ColorConfiguration get() {
        return GlobalConfiguration.all().get(ColorConfiguration.class);
    }
}
