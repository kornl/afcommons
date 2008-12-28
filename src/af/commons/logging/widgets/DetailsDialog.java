package af.commons.logging.widgets;

import javax.swing.JFrame;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class DetailsDialog extends JFrame {
    private DetailsPanel detailsPanel;


    public DetailsDialog(DetailsPanel detailsPanel) {
        this.detailsPanel = detailsPanel;
        setTitle("Details");
        doTheLayout();
        pack();
        int w = getWidth();
        int h = getHeight();
        if (w > 800)
            w = 800;
        if (h < 600)
            h = 600;
        setSize(w,h);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void doTheLayout() {
        String cols = "fill:pref:grow";
        String rows = "fill:pref:grow";
        FormLayout layout = new FormLayout(cols, rows);

        CellConstraints cc = new CellConstraints();
        setLayout(layout);

        getContentPane().add(detailsPanel , cc.xy(1,1));
    }
}
