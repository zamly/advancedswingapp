package gui;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextPanel extends JPanel {
	
	private JTextArea textArea;
	
	public TextPanel() {
		textArea = new JTextArea();
		setLayout(new BorderLayout());
		
		textArea.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		textArea.setFont(new Font("Serif", Font.BOLD, 20));
		
		add(new JScrollPane(textArea), BorderLayout.CENTER);
	}
	
	public void appendText(String text) {
		textArea.append(text);
	}

	public void setText(String contents) {
		textArea.setText(contents);
	}
}
