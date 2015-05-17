package gui;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {
	
	private JLabel nameLabel;
	private JLabel occLabel;
	private JTextField nameField;
	private JTextField occField;
	private JButton okbtn;
	private JList<AgeCategory> ageList;
	private JComboBox<String> empCombo;
	private JCheckBox citizenship;
	private JTextField taxId;
	private JLabel taxLabel;
	
	private JRadioButton male;
	private JRadioButton female;
	private ButtonGroup gender;
	
	private FormListener listener;
	
	public FormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 260;
		setPreferredSize(dim);
		setMinimumSize(dim);
		
		nameLabel = new JLabel("Name: ");
		occLabel = new JLabel("Occupation: ");
		nameField = new JTextField(10);
		occField = new JTextField(10);
		okbtn = new JButton("Submit");
		ageList = new JList<AgeCategory>();
		empCombo = new JComboBox<String>();
		citizenship = new JCheckBox();
		taxId = new JTextField(10);
		taxLabel = new JLabel("Tax ID: ");
		
		//seting mnumonics on Submit button
		okbtn.setMnemonic(KeyEvent.VK_S);
		
		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		nameLabel.setLabelFor(nameField);
		
		taxId.setEnabled(false);
		taxLabel.setEnabled(false);
		
		male = new JRadioButton("Male");
		male.setActionCommand("male");
		female = new JRadioButton("Female");
		female.setActionCommand("female");
		gender = new ButtonGroup();
		gender.add(male);
		gender.add(female);
		male.setSelected(true);
		
		citizenship.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isChecked = citizenship.isSelected();
				if (isChecked) {
					taxId.setEnabled(isChecked);
					taxLabel.setEnabled(isChecked);
				} else {
					taxId.setEnabled(false);
					taxLabel.setEnabled(false);
				}
			}
		});
		
		DefaultListModel<AgeCategory> ageModel = new DefaultListModel<AgeCategory>();
		ageModel.addElement(new AgeCategory(0,"Under 18"));
		ageModel.addElement(new AgeCategory(1,"18 to 65"));
		ageModel.addElement(new AgeCategory(2,"65 or above"));
		ageList.setModel(ageModel);
		
		ageList.setPreferredSize(new Dimension(110, 70));
		ageList.setBorder(BorderFactory.createEtchedBorder());
		ageList.setSelectedIndex(1);
		
		//setup ComboBox
		DefaultComboBoxModel<String> comModel = new DefaultComboBoxModel<String>();
		comModel.addElement("Employed");
		comModel.addElement("Self-Employed");
		comModel.addElement("Unemployed");
		empCombo.setModel(comModel);
		empCombo.setSelectedIndex(0);
		empCombo.setEditable(true);
		
		okbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String occ = occField.getText(); 
				AgeCategory age =  ageList.getSelectedValue();
				String emp = (String) empCombo.getSelectedItem();
				boolean isCitizen = citizenship.isSelected();
				String tid = taxId.getText();
				String gndr = gender.getSelection().getActionCommand();
				
				FormEvent fe = new FormEvent(this, name, occ, age.getId(), 
						emp, isCitizen, tid, gndr);
				if (listener != null && !name.equals("")) {
					listener.formEventOccurred(fe);
					nameField.setText("");
					occField.setText("");
					ageList.setSelectedIndex(0);
					empCombo.setSelectedIndex(0);
					citizenship.setSelected(false);
					taxId.setText("");
				}
			}
		});
		
		Border inner = BorderFactory.createTitledBorder("Add Person");
		Border outer = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outer, inner));
		
		layoutComponents();
	}
	
	
	private void layoutComponents() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
			
		/////////////////1//////////////////
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(nameLabel, gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(nameField, gc);
		
		/////////////2/////////////////////
		gc.weightx = 1;
		gc.weighty = 0.1;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(occField, gc);
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(occLabel, gc);
		
		/////////////////3//////////////////
		gc.weightx = 1;
		gc.weighty = 0.2;
		gc.gridy = 2;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(ageList, gc);
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Age:"), gc);
		
		/////////////////4//////////////////
		gc.weightx = 1;
		gc.weighty = 0.2;
		gc.gridy = 3;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(empCombo, gc);
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Employment:"), gc);
		
		/////////////////5//////////////////
		gc.weightx = 1;
		gc.weighty = 0.2;
		gc.gridy = 4;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(citizenship, gc);
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("US CITIZEN:"), gc);
		
		/////////////////6//////////////////
		gc.weightx = 1;
		gc.weighty = 0.2;
		gc.gridy = 5;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(taxId, gc);
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(taxLabel, gc);
		
		/////////////////7//////////////////
		gc.weightx = 1;
		gc.weighty = 0.02;
		gc.gridy = 6;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(male, gc);
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Gender:"), gc);
		
		gc.weightx = 1;
		gc.weighty = 0.2;
		gc.gridy = 7;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(female, gc);
		
		/////////////////8//////////////////
		gc.weightx = 1;
		gc.weighty = 2;
		gc.gridy = 8;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(okbtn, gc);
	}
	
	public void setFormListener(FormListener listener) {
		this.listener = listener;
	}

}

class AgeCategory {
	private int ageId;
	private String text;
	
	public AgeCategory(int ageId, String txt) {
		this.ageId = ageId;
		this.text = txt;
	}
	
	public int getId() {
		return ageId;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return text;
	}
	
}
