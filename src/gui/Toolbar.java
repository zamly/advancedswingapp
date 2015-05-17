package gui;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;

//Instead of JPanel the class now extends the JToolBar.
public class Toolbar extends JToolBar implements ActionListener{
	
	private JButton saveBtn;
	private JButton refreshBtn;
	private ToolbarListener listener;
	
	public Toolbar() {
		
		//set the toolbar as non dragable
		//setFloatable(false);
		
		//setBorder(BorderFactory.createEtchedBorder());
		saveBtn = new JButton();
		saveBtn.setIcon(Util.createIcon("/images/146794.gif"));
		saveBtn.setToolTipText("Save");
		
		refreshBtn = new JButton();
		refreshBtn.setIcon(Util.createIcon("/images/145011.gif"));
		refreshBtn.setToolTipText("Refresh");
		
		saveBtn.addActionListener(this);
		refreshBtn.addActionListener(this);
		
		//setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(saveBtn);
		//addSeparator();
		add(refreshBtn);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();
		if (clicked == saveBtn) {
			listener.saveEventOccurred();
		} else {
			listener.refreshEventOccurred();
		}
	}
	
	public void setToolbarListener(ToolbarListener listener) {
		this.listener = listener;
	}

}
