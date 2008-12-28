package af.commons.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.JFrame;

import af.commons.widgets.lists.SplitList;


class Stupid {
    String title;
    Stupid(String title) {
        this.title = title;
    }

    public String toString() {
        return title;
    }
}


public class SplitListTest extends JFrame {

    public SplitListTest() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        String[] x1={"apple", "pear", "orange", "cherry"};
        SplitList<String> s1 = new SplitList<String>(Arrays.asList(x1));

        Integer[] x2a={10, 15, 20, 23, 67};
        Integer[] x2b={95, 100};
        SplitList<Integer> s2 = new SplitList<Integer>(Arrays.asList(x2a), Arrays.asList(x2b), true);
//        s2.setTitleLeft("left numbers");
//        s2.setTitleLeft("right numbers");

        List<Stupid> x3 = new ArrayList<Stupid>();
        x3.add(new Stupid("x"));
        x3.add(new Stupid("y"));
        x3.add(new Stupid("z"));
        x3.add(new Stupid("z"));
        SplitList<Stupid> s3 = new SplitList<Stupid>(x3);


        List<Integer> x4= new ArrayList<Integer>();
        for (int i=0; i<100; i++)
            x4.add(i);
        SplitList<Integer> s4 = new SplitList<Integer>(x4);


        Box box = Box.createVerticalBox();

        box.add(s1);
        box.add(Box.createVerticalStrut(20));
        box.add(s2);
        box.add(Box.createVerticalStrut(20));
        box.add(s3);
        box.add(Box.createVerticalStrut(20));
        box.add(s4);


        getContentPane().add(box);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new SplitListTest();
    }
}
