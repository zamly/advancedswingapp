package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import model.Message;

/**
 * 
 * @author Sony
 *
 * Note: this demonstrates using a arbitrary component as a list for box renderer
 * probably overkill in this case to use JPanel when JLabel could be used directly.
 */

public class MessageListRenderer implements ListCellRenderer{

	private JPanel panel;
	private JLabel label;
	
	private Color selectedColor;
	private Color normalColor;
	
	public MessageListRenderer() {
		panel = new JPanel();
		label = new JLabel();
		
		selectedColor = new Color(210, 210, 255);
		normalColor = Color.white;
		
		label.setIcon(Util.createIcon("/images/146904.gif"));
	
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(label);
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		Message message = (Message) value;
		label.setText(message.getTitle());
		
		panel.setBackground((cellHasFocus? selectedColor: normalColor));
		//label.setBackground((cellHasFocus? selectedColor: normalColor));
		//label.setOpaque(true);
		
		return panel;
	}

}
