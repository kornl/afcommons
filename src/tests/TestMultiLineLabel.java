package tests;

import org.af.commons.widgets.MultiLineLabel;

import javax.swing.*;
import java.awt.*;

public class TestMultiLineLabel {
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(200, 800);
        MultiLineLabel lab = new MultiLineLabel(
                "test dsf sdfsd f dsf sdfsdf sdfsdfsdfsdsdf  sdffsdsdfsdf sdfsdf  " +
                "test dsf sdfsd f dsf sdfsdf sdfsdfsdfsdsdf  sdffsdsdfsdf sdfsdf  " +
                "test dsf sdfsd f dsf sdfsdf sdfsdfsdfsdsdf  sdffsdsdfsdf sdfsdf  " +
                "aaa aaaaa bbbb aaaaaaaaaaaaaaa bbb aabbababa aabba aaabbxxxxxxx \n" +
                "aaa aaaaa bbbb aaaaaaaaaaaaaaa bbb aabbababa aabba aaabbxxx \n" +
                "aaa aaaaa bbbb aaaaaaaaaaaaaaa bbb aabbababa aabba aaabb" +
                "aaa aaaaa bbbb aaaaaaaaaaaaaaa bbb aabbababa aabba aaabbxxx\n" +
                "test dsf sdfsd f dsf sdfsdf sdfsdfsdfsdsdf  sdffsdsdfsdf sdfsdf"
        ) ;
        lab.setAntiAlias(false);
        lab.setTextColor(Color.green);
        f.getContentPane().add(lab);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
