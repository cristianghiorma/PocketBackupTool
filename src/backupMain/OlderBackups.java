package backupMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;




public class OlderBackups {
	private static JFrame frame;

	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
					frame.setTitle("Backups");
					frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/icon.png")));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public OlderBackups() {
		initialize();
	}


	
	
	public void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(10, 33, 703, 349);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(0, 0, 703, 349);
		panel.add(scroll);
		final DefaultTableModel model = new DefaultTableModel();
		final JTable table = new JTable(model);
		scroll.setBackground(Color.white);
		scroll.getViewport().setBackground(Color.WHITE);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setBounds(0, 138, 208, -138);
		scroll.setViewportView(table);		
		table.setDefaultEditor(Object.class, null);
		Dimension x = new Dimension(7,0);
		table.setIntercellSpacing(x);
		model.addColumn("Backups",BackUp.getBack().toArray());
		model.addColumn("Size(MB)",BackUp.getBackSize().toArray());
		table.getColumnModel().getColumn(0).setPreferredWidth(615);
		table.getColumnModel().getColumn(1).setPreferredWidth(85);
		frame.getContentPane().setLayout(null);
		frame.setBounds(0,0,739,500);
		final JLabel lblTotalSize = new JLabel();

		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(123, 416, 99, 23);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()==-1){
					BackUp.dialog(2, "No backup selected", "Warning");
				}else{
					int del[] = table.getSelectedRows();
					boolean swapped = true;
					int j = 0;
					int tmp;
					while (swapped) {
						swapped = false;
						j++;
						for (int i = 0; i < del.length - j; i++) {                                       
							if (del[i] < del[i + 1]) {                          
								tmp = del[i];
								del[i] = del[i + 1];
								del[i + 1] = tmp;
								swapped = true;
							}
						}                
					}

					for(int i = 0; i<del.length;i++){
						BackUp.removeBack(del[i]);
						model.removeRow(del[i]);
					}
					model.setColumnCount(0);
					model.addColumn("Backups",BackUp.getBack().toArray());
					model.addColumn("Size(MB)",BackUp.getBackSize().toArray());
					DecimalFormat df = new DecimalFormat();
					df.setMaximumFractionDigits(2);
					lblTotalSize.setText("Total size: " + df.format((float)BackUp.findSize(BackUp.getBackupFolder())/1048576) + " MB");
					table.getColumnModel().getColumn(0).setPreferredWidth(615);
					table.getColumnModel().getColumn(1).setPreferredWidth(85);
				}
			}
		});
		frame.getContentPane().add(btnRemove);
				
				JLabel lblCurrentBackups = new JLabel("Current Backups");
				lblCurrentBackups.setBounds(10, 11, 129, 23);
				frame.getContentPane().add(lblCurrentBackups);
				
				JButton btnClose = new JButton("Close");
				btnClose.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						frame.dispose();
					}
				});
				btnClose.setBounds(523, 416, 89, 23);
				frame.getContentPane().add(btnClose);
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				lblTotalSize.setText("Total size: " + df.format((float)BackUp.findSize(BackUp.getBackupFolder())/1048576) + " MB");
				lblTotalSize.setBounds(20, 393, 117, 23);
				frame.getContentPane().add(lblTotalSize);
		

		
		

	}
}
