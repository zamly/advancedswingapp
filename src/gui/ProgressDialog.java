package gui;

import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ProgressDialog extends JDialog{
	
	private JButton cancel;
	private JProgressBar progressBar;
	private ProgressDialogListener listener;
	
	public ProgressDialog(Window parent, String title) {
		super(parent, title, ModalityType.APPLICATION_MODAL);
	
		cancel = new JButton("Cancel");
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		
		//if the maximum value is unknown.
		//progressBar.setIndeterminate(true);
		
		setLayout(new FlowLayout());
		Dimension size = cancel.getPreferredSize();
		size.width = 400;
		progressBar.setPreferredSize(size);
		progressBar.setString("Retrieving Messages...");
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listener != null) {
					listener.progressDialogCanceled();
				}
			}
		});
		
		add(progressBar);
		add(cancel);
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				if (listener != null) {
					listener.progressDialogCanceled();
				}
			}
			
			public void windowOpened(WindowEvent e) {
			}
			public void windowClosed(WindowEvent e) {
			}
			public void windowIconified(WindowEvent e) {
			}
			public void windowDeiconified(WindowEvent e) {
			}
			public void windowActivated(WindowEvent e) {
			}
			public void windowDeactivated(WindowEvent e) {
			}
		});
		
		pack();
		
		setLocationRelativeTo(parent);
	}
	
	public void setMaximum(int value) {
		progressBar.setMaximum(value);
	}
	
	public void setValue(int value) {
		progressBar.setString(
				String.format("%d%% Complete", (100*value/progressBar.getMaximum())));
		progressBar.setValue(value);
	}

	/**
	 * everything else in the swing application will be suspended
	 * when the progress bar becomes visible. so need to invoke it
	 * in a seperate thread.
	 */
	@Override
	public void setVisible(final boolean visible) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (visible == false) {
					 try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
					 setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				} else {
					progressBar.setValue(0);
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				}
				ProgressDialog.super.setVisible(visible);
			}
		});
	}
	
	
	public void setProgressDialogListener(ProgressDialogListener listener) {
		this.listener = listener;
	}
	

}
