import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ClassFrame extends JFrame implements ActionListener {

	private JPanel myPanel;
	private JTextField numberOfThreadsField;
	private JTextField precisionField;
	private JTextField outputFileField;
	private JTextArea outputField;
	private JTextArea logsField;
    private JButton submitBtn = new JButton("Calculate Pi");
    private boolean isQuiet;
    
    public ClassFrame(boolean isQuiet)
    {
    	this.isQuiet = isQuiet;
    	
        myPanel = new JPanel();
        myPanel.setName("Pi");
        add(myPanel);
        
        
        numberOfThreadsField = new JTextField();
        JLabel threadsCountLabel = new JLabel("ThreadsCount");
        threadsCountLabel.setLabelFor(numberOfThreadsField);
        myPanel.add(threadsCountLabel);
        myPanel.add(numberOfThreadsField);
        
        precisionField = new JTextField();
        JLabel precisionLabel = new JLabel("Precision");
        precisionLabel.setLabelFor(precisionField);
        myPanel.add(precisionLabel);
        myPanel.add(precisionField);
        
        outputFileField = new JTextField();
        JLabel outputFileNameLabel = new JLabel("Output File name");
        outputFileNameLabel.setLabelFor(precisionField);
        myPanel.add(outputFileNameLabel);
        myPanel.add(outputFileField);
        
        myPanel.setLayout(new GridLayout(0, 1));
        myPanel.add(submitBtn);
        submitBtn.addActionListener(this);
        
        outputField = new JTextArea();
        outputField.setEditable(false);
        JLabel resultLabel = new JLabel("Result");
        threadsCountLabel.setLabelFor(outputField);
        myPanel.add(resultLabel);
        myPanel.add(outputField);

        logsField = new JTextArea();
        logsField.setEditable(false);
        JLabel logsLabel = new JLabel("Logs");
        logsLabel.setLabelFor(logsField);
        myPanel.add(logsLabel);
        myPanel.add(logsField);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == submitBtn) {
        	try{
        		int threadsCount = Integer.parseInt(numberOfThreadsField.getText());
        		int precisionVal = Integer.parseInt(precisionField.getText());
        		String outputFileName = outputFileField.getText();
        		ProgramParams programParams;
        		if(isQuiet){
        			programParams = new ProgramParams(threadsCount, precisionVal, isQuiet, outputFileName, outputField, null);
        		} else {
        			programParams = new ProgramParams(threadsCount, precisionVal, isQuiet, outputFileName, outputField, logsField);
        		}
        		
        		Utility.computeMeasureWriteToFilePi(programParams);
        		        		
        	} catch(NumberFormatException nfe){
        		logsField.setText("Invalid data \n" + nfe.getMessage());
        	}
        }
    }
}
