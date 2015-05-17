
import gui.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class App {

	public static void main(String[] args) {
		
		try {
			//UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				//System.out.println(info.getName());
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
		 
		SwingUtilities.invokeLater(new Runnable() {		
			public void run() {
				new MainFrame();
			}		
		});
	}

}
