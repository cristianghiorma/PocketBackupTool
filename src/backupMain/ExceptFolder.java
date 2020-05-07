package backupMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;

public class ExceptFolder {
	private static JFrame frame;


	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
					frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/icon.png")));
					frame.setTitle("Exception Folders");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ExceptFolder() {
		initialize();
	}


	public void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(10, 33, 275, 138);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(0, 0, 275, 138);
		panel.add(scroll);
		final DefaultTableModel model = new DefaultTableModel();
		final JTable table = new JTable(model);
		scroll.setBackground(Color.white);
		scroll.getViewport().setBackground(Color.WHITE);
		frame.pack();
		table.setBounds(0, 138, 208, -138);
		Dimension x = new Dimension(5,0);
		table.setIntercellSpacing(x);
		table.setDefaultEditor(Object.class, null);
		scroll.setViewportView(table);		
		model.addColumn("Folder name",BackUp.getExcept().toArray());
		frame.getContentPane().setLayout(null);
		frame.setBounds(0,0,310,254);
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(103, 182, 81, 23);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()==-1){
					BackUp.dialog(2, "No item selected", "Warning");
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
						BackUp.removeExcept(del[i]);
						model.removeRow(del[i]);
					}
					model.setColumnCount(0);
					model.addColumn("Folder Name",BackUp.getExcept().toArray());
				}
			}
		});
		frame.getContentPane().add(btnRemove);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(10, 183, 81, 23);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msk= JOptionPane.showInputDialog(null,"Please enter folder name : ","Pocket BackupTool", JOptionPane.PLAIN_MESSAGE);
				if (msk!=null){
					if(!msk.equals("")){
						BackUp.addToExcept(msk);
					}
				}
				model.setColumnCount(0);
				model.addColumn("Folder Name",BackUp.getExcept().toArray());
			}

		});
		frame.getContentPane().add(btnAdd);

		JButton btnCancel = new JButton("Close");
		btnCancel.setBounds(196, 182, 89, 23);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		frame.getContentPane().add(btnCancel);

		JLabel lblCurrentAddedExtensions = new JLabel("Current exception folders");
		lblCurrentAddedExtensions.setBounds(10, 0, 248, 45);
		frame.getContentPane().add(lblCurrentAddedExtensions);

	}
}
