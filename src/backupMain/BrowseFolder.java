package backupMain;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class BrowseFolder{

	private static JFrame frame = new JFrame();
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setTitle("Select folder");
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public BrowseFolder() {
		initialize();
	}

	private void initialize() {


		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/icon.png")));
		final JFileChooser chooser = new JFileChooser() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -766932875596430400L;

			public void approveSelection() {
				if (getSelectedFile().isFile()) {
					return;
				} else
					super.approveSelection();
			}
		};
		chooser.setDialogTitle("Add folder to backup list");
		chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
		chooser.setBounds(0, 0, 582, 397);
		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) { 
			String fld = chooser.getSelectedFile().toString();
			frame.dispose();
			if(BackUp.getSearch().contains(fld)){
				BackUp.dialog(2, "Directory already exist!", "Warning");
			}else{
			BackUp.addSearchFolder(fld);	
			BackUp.isRecursive.add(true);
			BackUp.isActive.add(true);
			}
		}else{
			frame.dispose();
		}

	}
}
