package af.commons.widgets.wizard;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import af.commons.widgets.WidgetFactory;

public abstract class Wizard {

    private final WizardModel wizardModel;
    protected final WizardController wizardController;

    protected final JDialog wizard;

    private JPanel cardPanel;
    private CardLayout cardLayout;

    private JButton backButton;
    private JButton nextButton;
    private JButton cancelButton;

    private JLabel titleLabel;

    private int returnCode;
    private JFrame parentFrame;
    boolean closeIsKill;
    String confirmMessage;
    
    public Wizard(JFrame parentFrame, WizardController wizardController, String title, boolean closeIsKill) {
        //this.control = control;
        this.parentFrame = parentFrame;
        this.wizardController = wizardController;
        this.closeIsKill = closeIsKill;
        this.confirmMessage = confirmMessage;
        wizardController.setWizard(this);
        wizardModel = new WizardModel();
        wizard = new JDialog(parentFrame, true);
        wizard.setTitle(title);

        initComponents();
        registerPanels();

        String first = wizardController.getFirstPanel();
        setCurrentPanel(first);
        backButton.addActionListener(wizardController);
        nextButton.addActionListener(wizardController);

        setBackButtonEnabled(false);
        setNextButtonEnabled(true);


        if (closeIsKill) {
            wizard.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            wizard.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    int result = JOptionPane.showConfirmDialog(wizard, 
                    	"Do You really want to exit the programm?");
                    		// "You can't close the Wizard in between!\nPlease Continue.\nDo You really want to exit the programm?");
                    if (result == JOptionPane.OK_OPTION) {
                        //ControlClient.exit();
                    }
                }
            });
        } else {
            wizard.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }


        //datawizard.setSize(500,500);
        wizard.pack();
        wizard.setLocationRelativeTo(parentFrame);
        wizard.setVisible(true);

    }

    abstract protected void registerPanels();

    abstract public Object getReturnValue();

    abstract public void finish();

    public void close() {
        wizard.dispose();
        if (closeIsKill) { 
        	//TODO Is System.exit(0); here okay?        	
        }
    }

    public String getCurrentIdentifier() {
        return wizardModel.getCurrentPanelDescriptor().getPanelDescriptorIdentifier();
    }

    public WizardPanelDescriptor getCurrentPanelDescriptor() {
        return wizardModel.getCurrentPanelDescriptor();
    }


    public void setCurrentPanel(String id) {
        // Code omitted

        WizardPanelDescriptor oldPanelDescriptor = wizardModel.getCurrentPanelDescriptor();

        if (oldPanelDescriptor != null)
            oldPanelDescriptor.aboutToHidePanel();

        wizardModel.setCurrentPanel(id);

        WizardPanelDescriptor current = wizardModel.getCurrentPanelDescriptor();
        current.aboutToDisplayPanel();

        cardLayout.show(cardPanel, id);
        titleLabel.setText(current.getTitle());

        current.displayingPanel();
        setBackButtonEnabled(current.isBackEnabled());
        setNextButtonEnabled(current.isNextEnabled());
        if (current.isLastPanel()) {
            nextButton.setText("Finish");
        } else {
            nextButton.setText("Next");
        }
    }

    public void registerWizardPanel(String id, WizardPanelDescriptor panel) {
        cardPanel.add(panel.getPanelComponent(), id);
        wizardModel.registerPanel(id, panel);
    }


    public void disableButtons() {
        setBackButtonEnabled(false);
        setNextButtonEnabled(false);
    }

    public void enableButtons() {
        setBackButtonEnabled(true);
        setNextButtonEnabled(true);
    }

    public void setBackButtonEnabled(boolean b) {
        backButton.setEnabled(b);
    }

    public void setNextButtonEnabled(boolean b) {
        nextButton.setEnabled(b);
    }

   
    private void initComponents() {

        JPanel buttonPanel = new JPanel();
        JPanel titlePanel = new JPanel();
        Box buttonBox = new Box(BoxLayout.X_AXIS);

        cardPanel = new JPanel();
        cardPanel.setBorder(WidgetFactory.makeEmptyBorder());

        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        backButton = new JButton("Back");
        nextButton = new JButton("Next");
        cancelButton = new JButton("Cancel");

        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(new JSeparator(), BorderLayout.SOUTH);
        Box titleBox = new Box(BoxLayout.X_AXIS);
        titleBox.add(Box.createHorizontalGlue());
        titleLabel = new JLabel("");
        titleLabel.setFont(new Font(titleLabel.getFont().getFontName(), Font.PLAIN, 16));
        titleBox.add(titleLabel);
        titleBox.add(Box.createHorizontalGlue());
        titleBox.setBorder(WidgetFactory.makeEmptyBorder());
        titlePanel.add(titleBox, "Center");


        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(new JSeparator(), BorderLayout.NORTH);

        buttonBox.setBorder(WidgetFactory.makeEmptyBorder());
        buttonBox.add(backButton);
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(nextButton);
        //buttonBox.add(Box.createHorizontalStrut(30));
        //buttonBox.add(cancelButton);
        buttonPanel.add(buttonBox, java.awt.BorderLayout.CENTER);
        wizard.getContentPane().add(titlePanel, java.awt.BorderLayout.NORTH);
        wizard.getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);
        wizard.getContentPane().add(cardPanel, java.awt.BorderLayout.CENTER);

    }

}