package org.af.commons.widgets.lists;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.af.commons.collections.ListUtils;


import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

//TODO make vertical version work

public class SplitList<T> extends JPanel implements ActionListener, ListSelectionListener {

    private static boolean lock = false;

    public SideList<T> leftList;
    public SideList<T> rightList;
    //private SplitListModel<T> model;
//    private JButton swapButton;
    private JButton toLeftButton;
    private JButton toRightButton;
    private JButton allToLeftButton;
    private JButton allToRightButton;

    private String titleLeft = "";
    private String titleRight = "";
    private final boolean horizontalOrientation ;

    private List<SplitListChangeListener<T>> listeners =
            new ArrayList<SplitListChangeListener<T>>();

    public SplitList() {
        this(new ArrayList<T>(), new ArrayList<T>(), true);
    }

    public SplitList(List<T> left) {
        this(left, new ArrayList<T>(), true);
    }

    public SplitList(List<T> left,  boolean horizontal) {
        this(left, new ArrayList<T>(), horizontal);
    }

    final static String imagepath = "/af/commons/images";

    public SplitList(List<T> left, List<T> right, boolean horizontal) {
        this.horizontalOrientation = horizontal;
        if (horizontalOrientation) {
            toLeftButton = new JButton(new ImageIcon(getClass().getResource(imagepath+"/left.gif")));
            toRightButton = new JButton(new ImageIcon(getClass().getResource(imagepath+"/right.gif")));
            allToLeftButton = new JButton(new ImageIcon(getClass().getResource(imagepath+"/all_to_left.gif")));
            allToRightButton = new JButton(new ImageIcon(getClass().getResource(imagepath+"/all_to_right.gif")));
        } else {
            toLeftButton = new JButton(new ImageIcon(getClass().getResource(imagepath+"/top.gif")));
            toRightButton = new JButton(new ImageIcon(getClass().getResource(imagepath+"/bottom.gif")));
            allToLeftButton = new JButton(new ImageIcon(getClass().getResource(imagepath+"/all_to_top.gif")));
            allToRightButton = new JButton(new ImageIcon(getClass().getResource(imagepath+"/all_to_bottom.gif")));
        }

        leftList = new SideList<T>(new MyListModel<T>(left));
        rightList = new SideList<T>(new MyListModel<T>(right));
       // leftList.setVisibleRowCount(10);
        //rightList.setVisibleRowCount(10);
        //leftList.setFixedCellWidth(100);
        //rightList.setFixedCellWidth(100);

        //model = new SplitListModel<T>(left, right);
        //leftList.setModel(model.leftModel);
        //rightList.setModel(model.rightModel);
        toLeftButton.addActionListener(this);
        toRightButton.addActionListener(this);
        allToLeftButton.addActionListener(this);
        allToRightButton.addActionListener(this);
        leftList.addListSelectionListener(this);
        rightList.addListSelectionListener(this);
        doTheLayout();
        modelStateChanged();
    }

    private void doTheLayout() {
        if (horizontalOrientation)
            doTheLayoutHorizontally();
        else
            doTheLayoutVertically();
    }
    
    public void setEnabled(boolean enabled) {        
        super.setEnabled(enabled);
        toLeftButton.setEnabled(enabled);
        toRightButton.setEnabled(enabled);
        allToLeftButton.setEnabled(enabled);
        allToRightButton.setEnabled(enabled);
        leftList.setEnabled(enabled);
        rightList.setEnabled(enabled);
    }

    private void doTheLayoutHorizontally() {
        allToLeftButton.setMaximumSize(new Dimension(20, 20));
        allToRightButton.setMaximumSize(new Dimension(20, 20));
        toLeftButton.setMaximumSize(new Dimension(20, 20));
        toRightButton.setMaximumSize(new Dimension(20, 20));

        FormLayout layout = new FormLayout(
            "5dlu, pref:grow, 5dlu, 10dlu,5dlu, pref:grow, 5dlu", // columns
            "pref, pref:grow, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, pref:grow");      // rows

        setLayout(layout);

        CellConstraints cc = new CellConstraints();

        JScrollPane sp1 = new JScrollPane(leftList);
        JScrollPane sp2 = new JScrollPane(rightList);

        add(new JLabel(titleLeft),  cc.xy(2,1));
        add(new JLabel(titleRight), cc.xy(6,1));
        add(sp1,                    cc.xywh(2,2,1,9));
        add(sp2,                    cc.xywh(6,2,1,9));
        add(toRightButton,          cc.xy(4,3));
        add(allToRightButton,       cc.xy(4,5));
        add(toLeftButton,           cc.xy(4,7));
        add(allToLeftButton,        cc.xy(4,9));


        int w1 =sp1.getPreferredSize().width;
        int h1 =sp1.getPreferredSize().height;
        int w2 =sp2.getPreferredSize().width;
        int h2 =sp2.getPreferredSize().height;

        if (leftList.getModel().getSize() == 0)
            w1 = w2;
        else if (rightList.getModel().getSize() == 0)
            w2 = w1;

        if (w1 > 150)
            w1 = 150;
        if (w2 > 150)
            w2 = 150;

        sp1.setPreferredSize(new Dimension(w1, h1));
        sp2.setPreferredSize(new Dimension(w2, h2));
    }

    private void doTheLayoutVertically() {
        allToLeftButton.setMaximumSize(new Dimension(20, 20));
        allToRightButton.setMaximumSize(new Dimension(20, 20));
        toLeftButton.setMaximumSize(new Dimension(20, 20));
        toRightButton.setMaximumSize(new Dimension(20, 20));

        FormLayout layout = new FormLayout(
            "pref:grow, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref:grow",
            "pref:grow, 5dlu, pref, 5dlu, pref:grow");

        setLayout(layout);

        CellConstraints cc = new CellConstraints();

        JScrollPane sp1 = new JScrollPane(leftList);
        JScrollPane sp2 = new JScrollPane(rightList);

//        add(new JLabel(titleLeft),  cc.xy(2,1));
//        add(new JLabel(titleRight), cc.xy(6,1));
        add(sp1,                    cc.xyw(1,1,11));
        add(toRightButton,          cc.xy(3,3));
        add(allToRightButton,       cc.xy(5,3));
        add(toLeftButton,           cc.xy(7,3));
        add(allToLeftButton,        cc.xy(9,3));
        add(sp2,                    cc.xyw(1,5,11));


        int w1 =sp1.getPreferredSize().width;
        int h1 =sp1.getPreferredSize().height;
        int w2 =sp2.getPreferredSize().width;
        int h2 =sp2.getPreferredSize().height;

        if (leftList.getModel().getSize() == 0)
            w1 = w2;
        else if (rightList.getModel().getSize() == 0)
            w2 = w1;

        if (w1 > 150)
            w1 = 150;
        if (w2 > 150)
            w2 = 150;

        sp1.setPreferredSize(new Dimension(w1, h1));
        sp2.setPreferredSize(new Dimension(w2, h2));
    }

//    public void setTitleLeft(String titleLeft) {
//        this.titleLeft = titleLeft;
//    }
//
//    public void setTitleRight(String titleRight) {
//        this.titleRight = titleRight;
//    }



    void leftToRight(List<T> objects){
        leftList.getModel().removeElements(objects);
        rightList.getModel().addElements(objects);
        modelStateChanged();
    }

    public void rightToLeft(List<T> objects){
        rightList.getModel().removeElements(objects);
        leftList.getModel().addElements(objects);
        modelStateChanged();
    }

    Dimension getPreferredSize(SideList list) {
        if (list == leftList)
            return  rightList.getPreferredSize();
        else
            return  leftList.getPreferredSize();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toRightButton) {
            if (!leftList.isSelectionEmpty())
                leftToRight(leftList.getSelecectedValues());
        }
        else if (e.getSource() == toLeftButton) {
            if (!rightList.isSelectionEmpty())
                rightToLeft(rightList.getSelecectedValues());
        }
        else if (e.getSource() == allToRightButton) {
            allToRight();
        }
        else if (e.getSource() == allToLeftButton) {
            allToLeft();
        }
        leftList.clearSelection();
        rightList.clearSelection();
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!lock && e.getSource() == leftList) {
            lock = true;
            rightList.clearSelection();
            lock = false;
        }
        else if (!lock && e.getSource() == rightList) {
            lock = true;
            leftList.clearSelection();
            lock = false;
        }
    }

    public List<T> getLeft() {
        return leftList.getModel().getAllElements();
    }

    public List<T> getRight() {
        return rightList.getModel().getAllElements();
    }

    public List<T> getSelectedValues() {
        return getRight();
    }

    public List<String> getSelectedStrings() {
        return ListUtils.toString(getRight());
    }


    public void setLeft(List<T> data) {
        leftList.setModel(new MyListModel(data));
        revalidate();
        modelStateChanged();
    }

    public void setRight(List<T> data) {
        rightList.setModel(new MyListModel(data));
        revalidate();
        modelStateChanged();
    }

    public void allToLeft() {
        rightToLeft(rightList.getModel().getAllElements());
    }

    public void allToRight() {
        leftToRight(leftList.getModel().getAllElements());
    }


    public void setVisibleRowCount(int n) {
        leftList.setVisibleRowCount(n);
        rightList.setVisibleRowCount(n);
        doTheLayout();
    }

    protected void modelStateChanged() {
        for (SplitListChangeListener<T> slcl:listeners) {
            slcl.modelStateChanged(getLeft(), getRight());
        }
    }

    public void addSplitListChangeListener(SplitListChangeListener<T> slcl) {
        listeners.add(slcl);
    }


}

