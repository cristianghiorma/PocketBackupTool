package backupMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BackupMain {

	public static void main(String[] args) throws ParseException, FileNotFoundException{		
		BackUp back = new BackUp();
		back.readFromFile(BackUp.getConfig());
		PocketTray.main(args);
		thread2.start();

	}

	static Thread thread2 = new Thread () {
		public void run () {	
			while(true){
				System.gc();
				BackUp.deleteIfSize(BackUp.getMaxSize());
				Date timeToBack = null;
				try {
					timeToBack = new SimpleDateFormat("HH:mm").parse(BackUp.getBackupTime());
				} catch (ParseException e2) {
					e2.printStackTrace();
				}
				Date currentTime = null;
				try {
					currentTime = new SimpleDateFormat("HH:mm").parse(BackUp.getCurrentTime());
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				long seconds = (timeToBack.getTime()-currentTime.getTime())/1000; //check how much time is till next backup
				if(seconds<0){
					try {
						System.gc();
						Thread.sleep(7*1000*3600);
					} catch (InterruptedException e) {
						Thread.interrupted();
					} //if backup hour passed today sleep for 7 hours
				}else if(seconds>600){
					try {
						Thread.sleep((seconds-600)*1000);//if backup time hasn't passed yet sleep till there are 600 seconds until backup
					} catch (InterruptedException e) {
						Thread.interrupted();
					} 
				}
				if(BackUp.getBackupTime().equals(BackUp.getCurrentTime())){	 //if it is backup time, start backup
					doTheBackup();
					try {
						System.gc();
						Thread.sleep(55000);//sleep to make user user is not asked twice for backup
					} catch (InterruptedException e) {
						Thread.interrupted();
					}
				}

			}

		}


	};

	public static void doTheBackup() {

		BackUp back = new BackUp();
		PocketTray.setTrayToolTip("The tool is currently doing backup");
		BackUp.clearFolders(); //clear old backup data 
		if(BackUp.getSearchAreaSize()==0){
			BackUp.dialog(2,"No search folder selected, please add!", "Pocket BackupTool");
		}else{
			File folder = new File(BackUp.getBackupFolder());
			if(!folder.exists()){
				folder.mkdir();
			}
			if(!folder.exists()){
				BackUp.dialog(2,"Backup Folder is invalid. Please check setting!", "Warning!");
			}else{
				for (int folderCounter = 0; folderCounter < BackUp.getSearchAreaSize(); folderCounter++){
					if(BackUp.isActive.get(folderCounter)){
						File f = new File(BackUp.getSearchElement(folderCounter));
						if(f.exists()){
							if(BackUp.isRecursive.get(folderCounter)==true){

								if(BackUp.getMessage()){
									BackUp.dialog(2, "Searching folder recursive " + BackUp.getSearchElement(folderCounter), "Pocket BackupTool ");
								}
								BackUp.searchFile(BackUp.getSearchElement(folderCounter));

							}else{
								if(BackUp.getMessage()){
									BackUp.dialog(2, "Searching folder non-recursive " + BackUp.getSearchElement(folderCounter), "Pocket BackupTool ");
								}
								BackUp.searchFileNonRecursive(BackUp.getSearchElement(folderCounter));
							}
						}else{
							BackUp.dialog(2,"Folder " + BackUp.getSearchElement(folderCounter) + " does not exist! Please remove!","Warning!");
						}
					}
				}
				for (int maskCounter = 0; maskCounter < BackUp.getMaskSize(); maskCounter++){
					BackUp.toBack(BackUp.getMaskElement(maskCounter)) ;
				}
				//search for files to be backed up


				if(BackUp.hasFilesToBackup()){ //check if there are backup files today
					if(BackUp.getMessage()){
						BackUp.dialog(2, "Copying files to Temporary Folder","Pocket BackupTool");
					}
					for (int i=0;i<BackUp.GetFoldersCount();i++) { 
						String object = BackUp.GetFoldersElement(i);    
						try {
							BackUp.copyFile(object , BackUp.getTempFolder()  + object.replaceAll(":", "_"));
						} catch (IOException e1) {
							e1.printStackTrace();
						}	
					} //copying files to temporary folders
					if(BackUp.getMessage()){
						BackUp.dialog(2, "Zipping Files","Pocket BackupTool"); 
					}
					back.addToArchive(BackUp.getTempFolder() , BackUp.getBackupFolder()  + "\\backup" + "_" +  BackUp.getCurrentDate() + "_" + BackUp.getCurrentTime().replaceAll(":", "-") + ".zip");
					FileTransfer.main(BackUp.getBackupFolder()  + "\\backup" + "_" +  BackUp.getCurrentDate() + "_" + BackUp.getCurrentTime().replaceAll(":", "-") + ".zip", BackUp.getHostname(), BackUp.getUsername(), BackUp.getPassword());
					//zipping files
					if(BackUp.getMessage()){
						BackUp.dialog(2, "Deleting Temporary Folder","Pocket BackupTool");
					}
					BackUp.folderdel(BackUp.getTempFolder()); //deleting temporary files after backup
					if(BackUp.getMessage()){
						BackUp.dialog(2, "Backup Done!","BackUp Tool");
					}
					PocketTray.setTrayToolTip("Pocket Backup Tool");
				}else{
					if(BackUp.getMessage()){
						BackUp.dialog(2, "There are no files to backup today", "Pocket BackupTool");
					}
					PocketTray.setTrayToolTip("Pocket Backup Tool");
				}
			}


		}

	}
}











