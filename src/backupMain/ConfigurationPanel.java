package backupMain;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;






public class ConfigurationPanel {

	private static JFrame frame;

	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setTitle("Pocket BackupTool - Settings");
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
					frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/icon.png")));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public ConfigurationPanel() throws ParseException {
		initialize();
	}
	@SuppressWarnings("static-access")
	private void initialize() throws ParseException {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				System.gc();
			}
		});
		frame.setBounds(100, 100, 469, 377);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lblSelectFoldersTo = new JLabel("Select folders to backup:");
		JButton btnSeeCurrent = new JButton("Edit");
		btnSeeCurrent.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				SeeFiles s = new SeeFiles();
				s.start();
			}
		});

		JLabel lblSelectBackupFolder = new JLabel("Select backup destination folder:");

		JLabel lblMask = new JLabel("Select files extensions to backup");
		JButton btnAddOthers = new JButton("Edit");
		btnAddOthers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddMask m = new AddMask();
				m.start();
			}
		});

		JLabel lblBackupTime = new JLabel("Backup Time:");

		final JSpinner spinner = new JSpinner(new SpinnerDateModel());
		final JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinner,"HH:mm");
		spinner.setEditor(timeEditor);
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		Date parsedDate = formatter.parse(BackUp.getBackupTime());
		spinner.setValue(parsedDate);

		JButton btnCheckCurrent = new JButton("View");
		btnCheckCurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backupLocations b = new backupLocations();
				b.start();
			}
		});
		
		final JSpinner spinner_1 = new JSpinner();	
		spinner_1.setModel(new SpinnerNumberModel(new Long(0), null, new Long(10000), new Long(1)));
		spinner_1.setValue(BackUp.getMaxSize());

		JButton btnSaveSettings = new JButton("Save Settings");
		btnSaveSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrintWriter writer;
				try {
					BackUp back = new BackUp();
					writer = new PrintWriter(BackUp.getConfig(), "UTF-8");
					writer.println(BackUp.printSearch());
					writer.println(BackUp.getBackupFolder());
					writer.println(BackUp.printMask());
					BackUp.setBackTime(new SimpleDateFormat("HH:mm").format(spinner.getValue()));
					writer.println(BackUp.getBackupTime());
					writer.println(BackUp.printExcept());
					BackUp.setSize((long)spinner_1.getValue());
					writer.println(BackUp.getMaxSize());
					writer.println(BackUp.getMessage());
					writer.println(BackUp.getHostname() + "<>" + BackUp.getUsername() + "<>" + BackUp.getPassword());
					writer.close();
					String s = "The following settings were saved: \nFolders to backup: " + BackUp.printSearch1() + "\nFile Extensions: " + BackUp.printMask() + "\nBackup folder " + BackUp.getBackupFolder() 
							+ "\nBackup Time: " + BackUp.getBackupTime() + "\nException folders: " + BackUp.printExcept() + "\nBackups Maximum size: " + BackUp.getMaxSize() + " MB";
					BackUp.dialog(4, s , "Backup Info");
					BackUp.clearFolders();
					back.readFromFile(BackUp.getConfig());
					System.gc();
					if(BackupMain.thread2.getState().toString()=="TIMED_WAITING"){
						System.gc();
						BackupMain.thread2.interrupt();
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}			

			}
		});

		JButton btnNewButton_1 = new JButton("View");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OlderBackups old = new OlderBackups();
				old.start();
			}
		});

		JLabel lblOldBackups = new JLabel("Old Backups:");
		
		JButton btnTest = new JButton("View");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ExceptFolder ex = new ExceptFolder();
				ex.start();
			}
		});
		
		JLabel lblExceptionFolders = new JLabel("Exception folders:");
		
		JLabel lblMaximumBackupSize = new JLabel("Maximum backup size (MB):");
		
		JLabel lblMessages = new JLabel("Messages:");
		
		final JCheckBox checkBox = new JCheckBox("");
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(checkBox.isSelected()){
					BackUp.setMessage(true);
				}else{
					BackUp.setMessage(false);
				}
			}
		});
		if(BackUp.getMessage()==true){
			checkBox.setSelected(true);
		}else{
			checkBox.setSelected(false);
		}
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(lblSelectFoldersTo, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE)
							.addGap(51)
							.addComponent(btnSeeCurrent, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(lblMask, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)
							.addGap(62)
							.addComponent(btnAddOthers, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(lblBackupTime, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE)
							.addGap(73)
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(lblSelectBackupFolder, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)
							.addGap(62)
							.addComponent(btnCheckCurrent, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(lblOldBackups, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE)
							.addGap(83)
							.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(lblExceptionFolders, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
							.addGap(93)
							.addComponent(btnTest, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnSaveSettings, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblMaximumBackupSize, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addContainerGap()
									.addComponent(lblMessages, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
							.addGap(84)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10)
									.addComponent(checkBox))
								.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(10, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSelectFoldersTo, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(btnSeeCurrent)))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMask, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAddOthers))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBackupTime, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSelectBackupFolder, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(btnCheckCurrent)))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblOldBackups, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1))
					.addGap(9)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblExceptionFolders, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(btnTest)))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(lblMaximumBackupSize, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblMessages, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
							.addGap(16)
							.addComponent(btnSaveSettings))
						.addComponent(checkBox))
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
		
		





	}
}
