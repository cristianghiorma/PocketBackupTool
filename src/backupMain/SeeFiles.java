package backupMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;






public class SeeFiles {
	private static JFrame frame;

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
	

	

	public SeeFiles() {
		initialize();
	}


	public void initialize() {

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		final DefaultTableModel model = new DefaultTableModel();
		final JTable table = new JTable(model){

			private static final long serialVersionUID = 1L;

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				default:
					return Boolean.class;
				}
			}
			public boolean isCellEditable(int row, int column) {
				return column != 0;
			}

			public void setValueAt(Object aValue, int row, int column) {
				if (aValue instanceof Boolean && column == 1) {
					BackUp.isRecursive.set(row, (boolean)aValue);
					model.setColumnCount(0);
					model.addColumn("Backup Folders",BackUp.getSearch().toArray());
					model.addColumn("Recursive", BackUp.isRecursive.toArray());
					model.addColumn("Enabled", BackUp.isActive.toArray());
					this.getColumnModel().getColumn(0).setPreferredWidth(600);
					this.getColumnModel().getColumn(1).setPreferredWidth(83);	
					this.getColumnModel().getColumn(2).setPreferredWidth(67);
				}
				if (aValue instanceof Boolean && column == 2) {
					BackUp.isActive.set(row, (boolean)aValue);
					model.setColumnCount(0);
					model.addColumn("Backup Folders",BackUp.getSearch().toArray());
					model.addColumn("Recursive", BackUp.isRecursive.toArray());
					model.addColumn("Enabled", BackUp.isActive.toArray());
					this.getColumnModel().getColumn(0).setPreferredWidth(600);
					this.getColumnModel().getColumn(1).setPreferredWidth(83);	
					this.getColumnModel().getColumn(2).setPreferredWidth(67);
				}
			}
		};
		table.setShowHorizontalLines(false);
		table.setShowGrid(false);
		table.setShowVerticalLines(false);
		JScrollPane scroll = new JScrollPane();
		table.setFillsViewportHeight(true);
		model.addColumn("Backup Folders",BackUp.getSearch().toArray());
		model.addColumn("Recursive", BackUp.isRecursive.toArray());
		model.addColumn("Enabled", BackUp.isActive.toArray());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(600);
		table.getColumnModel().getColumn(1).setPreferredWidth(83);
		table.getColumnModel().getColumn(2).setPreferredWidth(67);
		scroll.setBounds(10, 34, 752, 394);
		scroll.setBackground(Color.white);
		scroll.getViewport().setBackground(Color.WHITE);
		Dimension x = new Dimension(7,0);
		table.setIntercellSpacing(x);
		table.setDefaultEditor(Object.class, null);
		scroll.setViewportView(table);		
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(scroll);
		frame.getContentPane().setLayout(null);
		frame.setBounds(0,0,788,511);

		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(427, 439, 89, 23);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()==-1){
					BackUp.dialog(2, "No folder selected", "Warning");
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
						BackUp.removeSearch(del[i]);
						BackUp.isRecursive.remove(del[i]);
						BackUp.isActive.remove(del[i]);
						model.removeRow(del[i]);
					}
					model.setColumnCount(0);
					model.addColumn("Backup Folders",BackUp.getSearch().toArray());
					model.addColumn("Recursive", BackUp.isRecursive.toArray());
					model.addColumn("Enabled", BackUp.isActive.toArray());
					table.getColumnModel().getColumn(0).setPreferredWidth(600);
					table.getColumnModel().getColumn(1).setPreferredWidth(83);	
					table.getColumnModel().getColumn(2).setPreferredWidth(67);	
				}
			}
		});
		frame.getContentPane().add(btnRemove);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(40, 439, 89, 23);
		btnAdd.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				BrowseFolder br = new BrowseFolder();
				br.start();
				model.setColumnCount(0);
				model.addColumn("Backup Folders",BackUp.getSearch().toArray());
				model.addColumn("Recursive", BackUp.isRecursive.toArray());
				model.addColumn("Enabled", BackUp.isActive.toArray());
				table.getColumnModel().getColumn(0).setPreferredWidth(600);
				table.getColumnModel().getColumn(1).setPreferredWidth(83);	
				table.getColumnModel().getColumn(2).setPreferredWidth(67);	
	
			}
		});
		frame.getContentPane().add(btnAdd);

		JButton btnClose = new JButton("Close");
		btnClose.setBounds(611, 439, 89, 23);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		frame.getContentPane().add(btnClose);

		JLabel lblCurrentBackupFolders = new JLabel("Current backup folders");
		lblCurrentBackupFolders.setBounds(10, 0, 212, 34);
		frame.getContentPane().add(lblCurrentBackupFolders);
		
		JButton btnSort = new JButton("Sort");
		btnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    for (int i = 0; i < BackUp.getSearch().size(); i++) {
			        for (int j = 1; j < (BackUp.getSearch().size() - i); j++) {

			            if ((BackUp.getSearch().get(j).compareTo(BackUp.getSearch().get(j-1)))<0) {
			            	String temp = BackUp.getSearch().get(j - 1);
			            	Boolean bool = BackUp.isRecursive.get(j-1);
			            	Boolean boolActive = BackUp.isActive.get(j-1);
			            	BackUp.getSearch().set(j-1, BackUp.getSearch().get(j));
			            	BackUp.isRecursive.set(j-1,BackUp.isRecursive.get(j));
			            	BackUp.isRecursive.set(j,bool);
			            	BackUp.isActive.set(j-1,BackUp.isActive.get(j));
			            	BackUp.isActive.set(j,boolActive);
			            	BackUp.getSearch().set(j, temp);
			            }

			        }
			    }
			    model.setColumnCount(0);
				model.addColumn("Backup Folders",BackUp.getSearch().toArray());
				model.addColumn("Recursive", BackUp.isRecursive.toArray());
				model.addColumn("Enabled", BackUp.isActive.toArray());
				table.getColumnModel().getColumn(0).setPreferredWidth(600);
				table.getColumnModel().getColumn(1).setPreferredWidth(83);	
				table.getColumnModel().getColumn(2).setPreferredWidth(67);	
				
			}
		});
		btnSort.setBounds(232, 439, 89, 23);
		frame.getContentPane().add(btnSort);
		


	}
}
