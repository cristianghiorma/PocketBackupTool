package backupMain;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

class BackUp{

	private static List<String> searchArea = new ArrayList<String>();
	private static List<String> Mask = new ArrayList<String>();
	private static List<String> oldBack = new ArrayList<String>(); 
	private static String backupFolder = new String();
	private static String backupTime = new String();
	private static String tempFolder = new String();
	private static boolean message;
	private static List<String> folders = new ArrayList<String>();
	private static List<String> toCheck = new ArrayList<String>();
	public static List<Boolean> isRecursive = new ArrayList<Boolean>();
	public static List<Boolean> isActive = new ArrayList<Boolean>();
	private static File config = new File("config.ini");
	private static List<String> backupsSize = new ArrayList<String>();
	private static List<String> exceptFolder = new ArrayList<String>();
	private static long maxSize;
	private static String hostname;
	private static String username;
	private static String password;

	public static String getCurrentDate() {
		return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
	}
	private static Date yesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}
	public static String getHostname() {
		return hostname;
	}
	public static String getUsername() {
		return username;
	}
	public static String getPassword() {
		return password;
	}
	public static void setHostname(String host) {
		hostname = host;
	}

	public static void setUsername(String user) {
		username = user;
	}

	public static void setPassword(String pass) {
		password = pass;
	}

	public static void setSize(long size){
		maxSize = size;
	}
	public static String getYesterdayDateString() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		return dateFormat.format(yesterday());
	}
	public static List<String> getExcept(){
		return exceptFolder;
	}

	public static void removeExcept(int i){
		exceptFolder.remove(i);
	}

	public static void addToExcept(String s){
		exceptFolder.add(s);
	}

	public static String getCurrentTime() {
		return new SimpleDateFormat("HH:mm").format(new Date());
	}

	public static String getBackupTime(){ 
		return backupTime;
	}

	public static void removeSearch(int i){
		searchArea.remove(i);
	}

	public static void removeBack(int i){
		File f = new File(oldBack.get(i));
		f.delete();
		oldBack.remove(i);

	}

	public static void removeMask(int i){
		Mask.remove(i);
	}

	public static List<String> getSearch(){
		return searchArea;
	}

	public static List<String> getBack(){
		oldBack.clear();
		File directory = new File(BackUp.getBackupFolder());
		if(directory.exists()){
			File[] listFiles = directory.listFiles();           
			for(File listFile : listFiles) {
				oldBack.add(listFile.toString());
			}
		}
		return oldBack;
	}

	public static boolean checkForYesterdayBackup(){
		File file = new File(getBackupFolder());
		File[] list = file.listFiles();		
		if(list!=null){
			for(File fil : list){
				if(timeModified(fil).equals(getYesterdayDateString())){
					return true;
				}
			}
		}
		return false;
	}

	public static List<String> getMask(){
		return Mask;
	}

	public static String getConfig(){
		return config.toString();
	}

	public static boolean getMessage(){
		return message;
	}

	public static void setMessage(boolean state){
		message = state;
	}

	public static String printSearch(){
		String s = new String();
		for(int i = 0; i<searchArea.size();i++){
			if(isRecursive.get(i)==true){
				if(isActive.get(i)==true){
					s = s + searchArea.get(i) + "<>r>y"   +  ";";
				}else{
					s = s + searchArea.get(i) + "<>r>n"   +  ";";
				}
			}else{
				if(isActive.get(i)==true){
					s = s + searchArea.get(i) + "<>n>y"   +  ";";
				}else{
					s = s + searchArea.get(i) + "<>n>n"   +  ";";
				}
			}
		}
		return s;
	}

	public static String printSearch1(){
		String s = new String();
		for(int i = 0; i<searchArea.size();i++){
			s = s + searchArea.get(i)  +  ";";

		}
		return s;
	}

	public static String printMask(){
		String s = new String();
		for(int i = 0; i<Mask.size();i++){
			s = s + Mask.get(i) + ";";
		}
		return s;
	}

	public static String printExcept(){
		String s = new String();
		for(int i = 0; i<exceptFolder.size();i++){
			s = s + exceptFolder.get(i) + ";";
		}
		return s;
	}

	public static void setTemp(){ 
		tempFolder = backupFolder + "\\Temp\\" ;
	}

	public static String timeModified(File fileName){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String lastModified = sdf.format(fileName.lastModified());
		return lastModified;
	}

	public static void setBackTime(String s){
		backupTime = s;
	}

	public static void addToMask(String msk){
		Mask.add(msk);
	}

	public static void addSearchFolder(String s){
		searchArea.add(s);
	}

	public static void setBackupFolder(String s){
		backupFolder = s;
	}

	public static int getSearchAreaSize(){
		return searchArea.size();
	}

	public static int getMaskSize(){
		return Mask.size();
	}

	public static String getSearchElement(int i){
		return searchArea.get(i);
	}

	public static String getMaskElement(int i){
		return Mask.get(i);
	}

	public static String getBackupFolder(){
		return backupFolder;
	}

	public static String getTempFolder(){
		return tempFolder;
	}

	public static int GetFoldersCount(){
		return folders.size();
	}

	public static String GetFoldersElement(int i){
		return folders.get(i);
	}

	public static void clearFolders(){
		folders.clear();
	}

	public static String getSearchFolders(){
		String s = new String();
		for(int i =0 ; i<searchArea.size();i++){
			s= s + "\n"+ searchArea.get(i) ;
		}
		return s;
	}

	public static boolean hasFilesToBackup(){
		if(folders.isEmpty()){
			return false;
		}
		return true;
	}

	public void readFromFile(String fileName) throws ParseException, FileNotFoundException{	
		try{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			line = br.readLine();
			String[] aux = line.split(";");
			searchArea.clear();
			isRecursive.clear();
			isActive.clear();
			if(!aux[0].equals("")){
				for(int i = 0; i<aux.length;i++){
					String[] auxR = aux[i].split("<>");
					searchArea.add(auxR[0]);
					try{
						if(!auxR[1].split(">")[0].equals("n")){
							isRecursive.add(true);
						}else{
							isRecursive.add(false);
						}
						try{
							if(!auxR[1].split(">")[1].equals("n")){
								isActive.add(true);
							}else{
								isActive.add(false);
							}
						}catch(java.lang.ArrayIndexOutOfBoundsException ex1){
							isActive.add(true);
						}
					}catch(java.lang.ArrayIndexOutOfBoundsException ex){
						isRecursive.add(true);
						isActive.add(true);
					}
				}
			}
			line = br.readLine();
			backupFolder = line;
			line = br.readLine();
			String[] aux2 = line.split(";");
			Mask.clear();
			if(!aux2[0].equals("")){
				for(int i = 0; i<aux2.length;i++){
					Mask.add(aux2[i]);
				}
			}
			tempFolder = backupFolder + "\\Temp\\" ;
			line = br.readLine();
			backupTime = line;
			line = br.readLine();
			String[] aux3 = line.split(";");
			exceptFolder.clear();
			if(!aux3[0].equals("")){
				for(int i = 0; i<aux3.length;i++){
					exceptFolder.add(aux3[i]);
				}
			}
			line = br.readLine();
			maxSize = Long.valueOf(line).longValue();
			try{
				line = br.readLine();
				if(line.equals("true")){
					message = true;
				}else{
					message = false;
				}
			}catch(java.lang.NullPointerException msg){
				message = false;
			}
			line = br.readLine();
			String[] auxCloud = line.split("<>");
			if(auxCloud[0].equals("")) {
				hostname = "127.0.0.0";
			}else {
				hostname = auxCloud[0];
			}
			username = auxCloud[1];
			password = auxCloud[2];
			System.out.println(hostname + username + password);
			br.close();

		}catch (IOException e){
			e.printStackTrace();
		}

	}	

	public static void searchFile(String startFolder){
		File file = new File(startFolder);
		File[] list = file.listFiles();		
		if(list!=null){
			if(BackUp.checkForYesterdayBackup()){
				for(File fil : list){

					if(fil.isDirectory()){
						if(!checkRestrictedFolder(fil)){
							searchFile(fil.toString());
						}			
					}else if((timeModified(fil).equals(getCurrentDate()))){
						toCheck.add(fil.toString());
					}
				}
			}else{
				for(File fil : list){
					if(fil.isDirectory()){
						if(!checkRestrictedFolder(fil)){
							searchFile(fil.toString());
						}			
					}else if((timeModified(fil).equals(getCurrentDate())) || (timeModified(fil).equals(getYesterdayDateString()))){
						toCheck.add(fil.toString());
					}
				}
			}

		}
	}

	public static void searchFileNonRecursive(String startFolder){
		File file = new File(startFolder);
		File[] list = file.listFiles();		
		if(list!=null){
			if(BackUp.checkForYesterdayBackup()){
				for(File fil : list){
					if(!fil.isDirectory()){
						if((timeModified(fil).equals(getCurrentDate()))){
							toCheck.add(fil.toString());
						}
					}
				}
			}else{
				for(File fil : list){
					if(!fil.isDirectory()){
						if((timeModified(fil).equals(getCurrentDate())) ||  (timeModified(fil).equals(getYesterdayDateString()))){
							toCheck.add(fil.toString());
						}
					}
				}
			}
		}
	}

	public static void toBack(String name){
		for(int i = 0 ; i<toCheck.size();i++){
			String extension = "";

			int j = toCheck.get(i).lastIndexOf('.');
			if (j >= 0) {
				extension = toCheck.get(i).substring(j);
			}
			if(extension.equals(name)){
				folders.add(toCheck.get(i));
			}
		}
	}

	public static boolean checkRestrictedFolder(File f){
		for(int i = 0 ; i<exceptFolder.size();i++){
			if((f.getPath()+"\\").contains("\\"+exceptFolder.get(i)+"\\"))
				return true;
		}
		return false;
	}

	public static void copyFile(String srce, String desti) throws IOException{
		File src = new File(srce);
		File dest = new File(desti);
		if(!dest.exists()){
			String absolutePath = dest.getAbsolutePath();
			String filePath = absolutePath.
					substring(0,absolutePath.lastIndexOf(File.separator));
			File folderspath = new File(filePath);
			folderspath.mkdirs();
			dest.createNewFile();
		}
		FileInputStream in = null;
		FileOutputStream out = null;
		FileChannel source = null;
		FileChannel destination = null;
		try{
			in = new FileInputStream(src);
			source = in.getChannel();
			out = new FileOutputStream(dest);
			destination = out.getChannel();
			long transfered = 0;
			long bytes = source.size();
			while(transfered<bytes){
				transfered += destination.transferFrom(source, 0,source.size());
				destination.position(transfered);
			}
		}finally{
			if(source != null){
				source.close();
			} else if (in != null){
				in.close();
			}
			if(destination != null){
				destination.close();
			} else if (out != null){
				out.close();
			}
		}
	}

	public void addToArchive(String inDir, String outFile) {
		File inputDir = new File(inDir);
		File outputZipFile = new File(outFile);
		outputZipFile.getParentFile().mkdirs();

		String inputDirPath = inputDir.getAbsolutePath();
		byte[] buffer = new byte[1024];

		FileOutputStream fileOs = null;
		ZipOutputStream zipOs = null;
		try {
			List<File> allFiles = this.listChildFiles(inputDir);
			fileOs = new FileOutputStream(outputZipFile);
			//
			zipOs = new ZipOutputStream(fileOs);
			for (File file : allFiles) {
				String filePath = file.getAbsolutePath();


				String entryName = filePath
						.substring(inputDirPath.length() + 1);

				ZipEntry ze = new ZipEntry(entryName);
				zipOs.putNextEntry(ze);
				FileInputStream fileIs = new FileInputStream(filePath);

				int len;
				while ((len = fileIs.read(buffer)) > 0) {
					zipOs.write(buffer, 0, len);
				}
				fileIs.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeQuite(zipOs);
			closeQuite(fileOs);
		}

	}

	private static void closeQuite(OutputStream out) {
		try {
			out.close();
		} catch (Exception e) {
		}
	}

	private List<File> listChildFiles(File dir) throws IOException {
		List<File> allFiles = new ArrayList<File>();

		File[] childFiles = dir.listFiles();
		for (File file : childFiles) {
			if (file.isFile()) {
				allFiles.add(file);
			} else {
				List<File> files = this.listChildFiles(file);
				allFiles.addAll(files);
			}
		}
		return allFiles;
	}

	public static void folderdel(String path){
		File f= new File(path);
		if(f.exists()){
			String[] list= f.list();
			if(list.length==0){
				if(f.delete()){
					return;
				}
			}
			else {
				for(int i=0; i<list.length ;i++){
					File f1= new File(path+"\\"+list[i]);
					if(f1.isFile()&& f1.exists()){
						f1.delete();
					}
					if(f1.isDirectory()){
						folderdel(""+f1);
					}
				}
				folderdel(path);
			}
		}
	}

	public static void dialog(int seconds,String message, String Title){
		final JOptionPane optionPane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
		final JDialog dlg = new JDialog();
		dlg.setTitle(Title);
		dlg.setModal(true);
		dlg.setContentPane(optionPane);		
		dlg.setSize(200,200);
		dlg.setLocationRelativeTo(null);
		dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dlg.pack();

		@SuppressWarnings("serial")
		Timer timer = new Timer(seconds*1000, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				dlg.dispose();
			}
		});
		timer.setRepeats(false);
		timer.start();
		dlg.setVisible(true);
	}

	public static long findSize(String path) { 
		long totalSize = 0;
		ArrayList<String> directory = new ArrayList<String>();
		File file = new File(path);

		if(file.isDirectory()) { 
			directory.add(file.getAbsolutePath());
			while (directory.size() > 0) {
				String folderPath = directory.get(0);
				directory.remove(0);
				File folder = new File(folderPath);
				File[] filesInFolder = folder.listFiles();
				int noOfFiles = filesInFolder.length;

				for(int i = 0 ; i < noOfFiles ; i++) { 
					File f = filesInFolder[i];
					if(f.isDirectory()) { 
						directory.add(f.getAbsolutePath());
					} else { 
						totalSize+=f.length();
					} 
				} 
			} 
		} else { 
			totalSize = file.length();
		} 
		return totalSize;
	}

	@SuppressWarnings("static-access")
	public static void deleteIfSize(long maxSize){
		long size = BackUp.findSize(BackUp.getBackupFolder());
		if(size>=maxSize*1048576){
			BackUp.dialog(2, "Old backups exceeded " + maxSize + " MB, please delete some", "Warning");
			OlderBackups o = new OlderBackups();
			o.start();
		}
	}

	public static String findSizeDbl(String path) { 
		DecimalFormat df = new DecimalFormat("#0.00"); 
		double totalSize = 0;
		ArrayList<String> directory = new ArrayList<String>();
		File file = new File(path);

		if(file.isDirectory()) { 
			directory.add(file.getAbsolutePath());
			while (directory.size() > 0) {
				String folderPath = directory.get(0);
				directory.remove(0);
				File folder = new File(folderPath);
				File[] filesInFolder = folder.listFiles();
				int noOfFiles = filesInFolder.length;

				for(int i = 0 ; i < noOfFiles ; i++) { 
					File f = filesInFolder[i];
					if(f.isDirectory()) { 
						directory.add(f.getAbsolutePath());
					} else { 
						totalSize+=f.length();
					} 
				} 
			} 
		} else { 
			totalSize = file.length();
		} 
		return df.format(totalSize/1048576);
	}

	public static void setBackSize(){
		backupsSize.clear();
		for(int i = 0; i<oldBack.size();i++){
			backupsSize.add(findSizeDbl(oldBack.get(i)));
		}
	}
	public static List<String> getBackSize(){
		BackUp.setBackSize();
		return backupsSize;
	}
	public static long getMaxSize(){
		return maxSize;
	}
}
