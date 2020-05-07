package backupMain;

		import java.awt.Dimension;
		import java.awt.EventQueue;
		import java.awt.Toolkit;

		import javax.swing.JButton;
		import javax.swing.JFrame;
		import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;






		public class backupLocations {
			private static JFrame locationFrame;

			public static void start() {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							locationFrame.setVisible(true);
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							locationFrame.setLocation(dim.width/2-locationFrame.getSize().width/2, dim.height/2-locationFrame.getSize().height/2);
							locationFrame.setTitle("Pocket BackupTool");
							locationFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/icon.png")));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
			

			

			public backupLocations() {
				initialize();
			}


			public void initialize() {

				locationFrame = new JFrame();
				locationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				locationFrame.getContentPane().setLayout(null);
				locationFrame.getContentPane().setLayout(null);
				locationFrame.setBounds(0,0,437,141);

				JLabel lblCurrentBackupFolders = new JLabel("Change backup folder for:");
				lblCurrentBackupFolders.setBounds(10, 0, 212, 34);
				locationFrame.getContentPane().add(lblCurrentBackupFolders);
				
				JButton btnLocalBackup = new JButton("Local Backup");
				btnLocalBackup.addActionListener(new ActionListener() {
					@SuppressWarnings("static-access")
					public void actionPerformed(ActionEvent arg0) {
						Object[] options = {"Change",
						"Close"};
				int n = JOptionPane.showOptionDialog(locationFrame,
						"Current Backup folder is: " + BackUp.getBackupFolder(),
								"Backup Folder",
								JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.INFORMATION_MESSAGE,
								null,
								options,
								options[1]);
				if(n==0){
					BackupFolder back = new BackupFolder();
					back.start();
				}
					}
				});
				btnLocalBackup.setBounds(36, 56, 131, 23);
				locationFrame.getContentPane().add(btnLocalBackup);
				
				JButton btnCloudBackup = new JButton("Cloud Backup");
				btnCloudBackup.addActionListener(new ActionListener() {
					@SuppressWarnings("static-access")
					public void actionPerformed(ActionEvent e) {
						cloudBackup cloud = new cloudBackup();
						cloud.start();
					}
				});
				btnCloudBackup.setBounds(208, 56, 131, 23);
				locationFrame.getContentPane().add(btnCloudBackup);
				


			}
		}
