package org.af.commons.widgets;

import java.awt.Frame;
import java.awt.color.ColorSpace;
import java.awt.image.ColorConvertOp;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import org.af.commons.images.filter.GaussianFilter;
import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.effect.BufferedImageOpEffect;
import org.jdesktop.jxlayer.plaf.ext.LockableUI;

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
    	/* ColorConvertOp grayScale =
    	        new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);    	 
    	return new BufferedImageOpEffect(grayScale); */
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
