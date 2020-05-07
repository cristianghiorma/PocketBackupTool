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



public class AddMask {
	private static JFrame maskFrame;


	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					maskFrame.setVisible(true);
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					maskFrame.setLocation(dim.width/2-maskFrame.getSize().width/2, dim.height/2-maskFrame.getSize().height/2);
					maskFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/icon.png")));
					maskFrame.setTitle("File Extensions");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AddMask() {
		initialize();
	}


	public void initialize() {
		maskFrame = new JFrame();
		maskFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		maskFrame.getContentPane().setLayout(null);		
		JPanel maskPanel = new JPanel();
		maskPanel.setBounds(10, 33, 275, 138);
		maskFrame.getContentPane().add(maskPanel);
		maskPanel.setLayout(null);
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(0, 0, 275, 138);
		maskPanel.add(scroll);
		final DefaultTableModel maskModel = new DefaultTableModel();
		final JTable maskTable = new JTable(maskModel);
		scroll.setBackground(Color.white);
		scroll.getViewport().setBackground(Color.WHITE);
		maskFrame.pack();
		maskTable.setBounds(0, 138, 208, -138);
		Dimension x = new Dimension(5,0);
		maskTable.setIntercellSpacing(x);
		maskTable.setDefaultEditor(Object.class, null);
		scroll.setViewportView(maskTable);		
		maskModel.addColumn("Extension",BackUp.getMask().toArray());
		maskFrame.getContentPane().setLayout(null);
		maskFrame.setBounds(0,0,499,247);
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(103, 182, 81, 23);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(maskTable.getSelectedRow()==-1){
					BackUp.dialog(2, "No item selected", "Warning");
				}else{
					int del[] = maskTable.getSelectedRows();
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
						BackUp.removeMask(del[i]);
						maskModel.removeRow(del[i]);
					}
					maskModel.setColumnCount(0);
					maskModel.addColumn("Extension",BackUp.getMask().toArray());
				}
			}
		});
		maskFrame.getContentPane().add(btnRemove);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(10, 183, 81, 23);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msk= JOptionPane.showInputDialog(null,"Please enter your extension : ","Pocket BackupTool", JOptionPane.PLAIN_MESSAGE);
				if (msk!=null){
					if(!msk.equals("")){
						BackUp.addToMask(msk);
					}
				}
				maskModel.setColumnCount(0);
				maskModel.addColumn("Extension",BackUp.getMask().toArray());
			}

		});
		maskFrame.getContentPane().add(btnAdd);

		JButton btnCancel = new JButton("Close");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				maskFrame.dispose();
			}
		});
		btnCancel.setBounds(196, 182, 89, 23);
		maskFrame.getContentPane().add(btnCancel);

		JLabel lblCurrentAddedExtensions = new JLabel("Current added extensions");
		lblCurrentAddedExtensions.setBounds(10, 0, 248, 45);
		maskFrame.getContentPane().add(lblCurrentAddedExtensions);




	}
}
