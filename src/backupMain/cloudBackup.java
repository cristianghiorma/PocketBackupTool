package backupMain;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;






public class cloudBackup {
	private static JFrame frame;
	private JTextField hostnameField;
	private JTextField usernameField;
	private JTextField passwordField;

	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
					frame.setTitle("Pocket BackupTool");
					frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/icon.png")));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	

	public cloudBackup() {
		initialize();
	}


	public void initialize() {

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		final DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Backup Folders",BackUp.getSearch().toArray());
		model.addColumn("Recursive", BackUp.isRecursive.toArray());
		model.addColumn("Enabled", BackUp.isActive.toArray());
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setLayout(null);
		frame.setBounds(0,0,332,219);

		JLabel lblCurrentBackupFolders = new JLabel("Hostname:");
		lblCurrentBackupFolders.setBounds(10, 11, 68, 34);
		frame.getContentPane().add(lblCurrentBackupFolders);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 56, 68, 27);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 94, 68, 27);
		frame.getContentPane().add(lblPassword);
		
		hostnameField = new JTextField();
		hostnameField.setBounds(108, 18, 107, 20);
		frame.getContentPane().add(hostnameField);
		hostnameField.setColumns(10);
		hostnameField.setText(BackUp.getHostname());
		
		usernameField = new JTextField();
		usernameField.setBounds(108, 56, 107, 20);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		usernameField.setText(BackUp.getUsername());
		
		passwordField = new JPasswordField();
		passwordField.setBounds(108, 97, 107, 20);
		frame.getContentPane().add(passwordField);
		passwordField.setColumns(10);
		passwordField.setText(BackUp.getPassword());
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BackUp.setHostname(hostnameField.getText());
				BackUp.setUsername(usernameField.getText());
				BackUp.setPassword(passwordField.getText());
				System.out.println(BackUp.getHostname() + BackUp.getUsername() + BackUp.getPassword());
			}
		});
		btnSave.setBounds(43, 146, 89, 23);
		frame.getContentPane().add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnCancel.setBounds(191, 146, 89, 23);
		frame.getContentPane().add(btnCancel);
		


	}
}
