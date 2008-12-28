package af.commons.tests;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import af.commons.errorhandling.ErrorHandler;
import af.commons.logging.ApplicationLog;
import af.commons.logging.LoggingSystem;

public class TestErrorHandling extends JFrame implements ActionListener {

    JButton b1 = new JButton("make crit. err. dialog");
    JButton b2 = new JButton("make recov. err. dialog");
    JButton b3 = new JButton("uncaught exception on EDT");

    public TestErrorHandling() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        Box hb = Box.createVerticalBox();
        hb.add(b1);
        hb.add(b2);
        hb.add(b3);
        getContentPane().add(hb);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            ErrorHandler.getInstance().makeCritErrDialog(
                    "crit error!!!!", new RuntimeException("error!!!!"));
        }
        if (e.getSource() == b2) {
            ErrorHandler.getInstance().makeRecovErrDialog(
                    "recov error", new RuntimeException("recov error"));
        }
        if (e.getSource() == b3) {
            throw new RuntimeException("sneaky");
        }
    }

    public static void main(String[] args) {

        ApplicationLog appLog = new ApplicationLog();

        if (JOptionPane.showConfirmDialog(null, "Init log. system?", "", JOptionPane.OK_CANCEL_OPTION)
                == JOptionPane.OK_OPTION) {
            LoggingSystem.init("/commons-logging.properties", false, true, appLog);
            // TODO remove
            LoggingSystem.getInstance();
        }

        ErrorHandler.init("bernd_bischl@gmx.net", true, true);

//        TestErrorHandling teh = new TestErrorHandling();
//        teh.pack();
//        teh.setVisible(true);

//         exc. on main thread / comment out to test rest
        throw new RuntimeException("on main thread");
    }

}
