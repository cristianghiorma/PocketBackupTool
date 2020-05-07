package backupMain;

            import java.io.File;

import com.jcraft.jsch.Channel;
            import com.jcraft.jsch.ChannelSftp;
            import com.jcraft.jsch.JSch;
            import com.jcraft.jsch.JSchException;
            import com.jcraft.jsch.Session;
            import com.jcraft.jsch.SftpException;

            public class FileTransfer {
                public static void main(String copyFrom, String hostname, String username, String password) {
                    //String hostname = "212.237.58.187";
                    //String username = "root";
                    //String password = "Testbackup12";
                    //"D:/Backups/backup_24-03-2019_15-39.zip"
                    JSch jsch = new JSch();
                    Session session = null;
                    System.out.println("Trying to connect.....");
                    try {
                        session = jsch.getSession(username, hostname, 22);
                        session.setConfig("StrictHostKeyChecking", "no");
                        session.setPassword(password);
                        session.connect(); 
                        Channel channel = session.openChannel("sftp");
                        channel.connect();
                        ChannelSftp sftpChannel = (ChannelSftp) channel; 
                        sftpChannel.cd("/root/backups");
                        File localFile = new File(copyFrom);
                        sftpChannel.put(localFile.getAbsolutePath(),localFile.getName());
                        sftpChannel.disconnect();
                        channel.disconnect();
                        session.disconnect();
                        System.out.println("Done !!");
                        
                    } catch (JSchException e) {
                        e.printStackTrace();  
                    } catch (SftpException e) {
                        e.printStackTrace();
                    }
                }
            }
   