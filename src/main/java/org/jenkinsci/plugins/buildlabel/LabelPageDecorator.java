package org.jenkinsci.plugins.buildlabel;

import java.util.*;
import org.jenkinsci.plugins.buildlabel.administration.Color;
import org.jenkinsci.plugins.buildlabel.administration.ColorConfiguration;
import hudson.Extension;
import hudson.model.PageDecorator;

@Extension
public class LabelPageDecorator extends PageDecorator {
    public List<Color> getColors() {
        return ColorConfiguration.get().getColors();
    }
}
