package tests;

import org.af.commons.threading.ProgressDialog;
import org.af.commons.threading.SafeSwingWorker;

import javax.swing.*;


class Worker extends SafeSwingWorker<Void, String> {

    protected Void doInBackground() throws Exception {
        publish("bla");
        Thread.sleep(1000);
        setProgress(20);
        Thread.sleep(1000);
        setProgress(40);
        Thread.sleep(1000);
        setProgress(60);
        Thread.sleep(1000);
        setProgress(100);
        return null;
    }
}

public class TestProgressDialog {
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(800, 800);
        JLabel lab = new JLabel("test");
        f.getContentPane().add(lab);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        Worker w = new Worker();
        ProgressDialog<Void, String> pd = ProgressDialog.make(
                f, "pd", w, true, true);
        pd.setVisible(true);
    }
}
