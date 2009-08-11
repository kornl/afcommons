package org.af.commons.widgets;

import java.awt.Frame;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.effect.BufferedImageOpEffect;
import org.jdesktop.jxlayer.plaf.ext.LockableUI;

import com.jhlabs.image.GaussianFilter;

public class GUIToolKit {
    public static Frame findActiveFrame() {
        Frame[] frames = JFrame.getFrames();
        for (int i = 0; i < frames.length; i++) {
            Frame f = frames[i];
            if (f.isVisible() && f.isFocused())
                return f;
        }
        return null;
    }

    public static BufferedImageOpEffect getDefaultLockedLayerEffect() {
        return new BufferedImageOpEffect(new GaussianFilter(5));
    }

    public static LockableUI setContentPaneAsLockableJXLayer(JRootPane rootPane, JPanel panel) {
        LockableUI lockableUI = new LockableUI();
        lockableUI.setLockedEffects(GUIToolKit.getDefaultLockedLayerEffect());
        JXLayer<JComponent> jxLayer = new JXLayer<JComponent>(panel, lockableUI);
        rootPane.setContentPane(jxLayer);
        return lockableUI;
    }

}
