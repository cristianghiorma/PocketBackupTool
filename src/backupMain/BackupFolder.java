package backupMain;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JFileChooser;

public class BackupFolder{

	private static JFrame folderFrame;
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				//	frame.setVisible(true);
					folderFrame.setTitle("Select folder");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public BackupFolder() {
		initialize();
	}
	@SuppressWarnings("serial")
	private void initialize() {
		folderFrame = new JFrame();
		folderFrame.setBounds(100, 100, 604, 429);
		folderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		folderFrame.getContentPane().setLayout(null);
		
		folderFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/icon.png")));

		  final JFileChooser folderChooser = new JFileChooser() {
	            public void approveSelection() {
	                if (getSelectedFile().isFile()) {
	                    return;
	                } else
	                    super.approveSelection();
	            }
	    };
	    folderChooser.setDialogTitle("Select Backup Folder");
	    folderChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
		folderChooser.setBounds(0, 0, 582, 397);
		 if (folderChooser.showOpenDialog(folderFrame) == JFileChooser.APPROVE_OPTION) { 
		      String fld = folderChooser.getSelectedFile().toString();
		      BackUp.setBackupFolder(fld);
		      BackUp.setTemp();
		      folderFrame.dispose();
		      }
		    else {
		    	folderFrame.dispose();
		    }


	}
}
