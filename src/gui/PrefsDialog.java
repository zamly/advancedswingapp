package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

public class PrefsDialog extends JDialog {
	
	private JButton ok;
	private JButton cancel;
	private JSpinner portSpinner;
	private SpinnerNumberModel spinnerNumModel;
	private JTextField username;
	private JPasswordField pass;
	
	private PrefsListener prefsListener;
	
	public PrefsDialog(JFrame parent) {
		super(parent, "Preferences", false);
		
		ok = new JButton("OK");
		cancel = new JButton("Cancel");
		spinnerNumModel = new SpinnerNumberModel(3306, 0, 9999, 1);
		portSpinner = new JSpinner(spinnerNumModel);
		username = new JTextField(10);
		pass = new JPasswordField(10);
		
		layoutComponents();
		
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = username.getText();
				char[] password = pass.getPassword();
				int port = (int) portSpinner.getValue();
				
				if (prefsListener != null) {
					prefsListener.preferencesSet(user, new String(password), port);
				}
				setVisible(false);
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		setSize(270, 240);
		setLocationRelativeTo(parent);
	}
	
	private void layoutComponents() {
		
		JPanel controlPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		Border spaceBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border titleBorder = BorderFactory.createTitledBorder("Database Preferences");
		
		controlPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));
		//buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		controlPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridy = 0;
		gc.gridx = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;
		Insets rightPadding = new Insets(0, 0, 0, 15);
		Insets noPadding = new Insets(0, 0, 0, 0);
		
		///////////////////////////////
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlPanel.add(new JLabel("Username: "), gc);
		
		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlPanel.add(username, gc);
		
		///////////////////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlPanel.add(new JLabel("Password: "), gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlPanel.add(pass, gc);
		
		///////////////////////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlPanel.add(new JLabel("Port: "), gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlPanel.add(portSpinner, gc);
		
		/////////////Button Panel//////////////////
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		
		Dimension btnSize = cancel.getPreferredSize();
		ok.setPreferredSize(btnSize);
		
		setLayout(new BorderLayout());
		add(controlPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
	}

	public void setDefaults(String user, String pass, int port) {
		username.setText(user);
		this.pass.setText(pass);
		portSpinner.setValue(port);
	}
	
	public void setPrefsListener(PrefsListener listener) {
		this.prefsListener = listener;
	}

}
