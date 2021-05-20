package shared_folder;

import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.XmlException;


import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Random;

public class Window_1 {

	
	private static JFrame frmSharedFolder;
	private JTextField txtusername;
	private JPasswordField txtpassword;
	private JTextField txthost;
	private JTextField txtPath;
	private JTree jTree;
	private HelperClass helper;
	private static String currentPath="";

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window_1 window = new Window_1();
					window.frmSharedFolder.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Window_1() {
		initialize();
	}

	private void initialize() {
		helper = new HelperClass();
		frmSharedFolder = new JFrame();
		frmSharedFolder.setTitle("Shared Folder");
		frmSharedFolder.setResizable(false);
		frmSharedFolder.setBounds(100, 100, 696, 576);
		frmSharedFolder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSharedFolder.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Indexing Of Folders On Shared Drive");
		lblNewLabel.setBounds(130, 11, 395, 25);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		frmSharedFolder.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setBounds(10, 69, 72, 16);
		frmSharedFolder.getContentPane().add(lblNewLabel_1);
		
		txtusername = new JTextField();
		txtusername.setBounds(94, 66, 217, 22);
		frmSharedFolder.getContentPane().add(txtusername);
		txtusername.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setBounds(10, 101, 72, 16);
		frmSharedFolder.getContentPane().add(lblNewLabel_2);
		
		txtpassword = new JPasswordField();
		txtpassword.setBounds(94, 98, 217, 22);
		frmSharedFolder.getContentPane().add(txtpassword);
		
		JLabel lblNewLabel_3 = new JLabel("Host");
		lblNewLabel_3.setBounds(393, 69, 56, 16);
		frmSharedFolder.getContentPane().add(lblNewLabel_3);
		
		txthost = new JTextField();
		txthost.setBounds(459, 66, 217, 22);
		txthost.setColumns(10);
		frmSharedFolder.getContentPane().add(txthost);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 160, 666, 271);
		frmSharedFolder.getContentPane().add(scrollPane);
		
		jTree = new JTree();
		scrollPane.setViewportView(jTree);
		DefaultTreeModel dmtn=null;
		jTree.setModel(dmtn);
		
		jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		jTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				Object obj[] = e.getPath().getPath();
				String actualPath="";
				for(Object o : obj) {
					actualPath = actualPath+"/"+o.toString();
				}
				
				currentPath = actualPath;
			}
		});
		
		
		JLabel lblNewLabel_4 = new JLabel("Path");
		lblNewLabel_4.setBounds(393, 101, 56, 16);
		frmSharedFolder.getContentPane().add(lblNewLabel_4);
		
		txtPath = new JTextField();
		txtPath.setColumns(10);
		txtPath.setBounds(459, 98, 217, 22);
		frmSharedFolder.getContentPane().add(txtPath);
		
		JButton btnNewButton = new JButton("Connect");
		btnNewButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				
				String username = txtusername.getText();
				String password = txtpassword.getText();
				String host 	= txthost.getText();
				String path		= txtPath.getText();
				String networkfolder = "smb://"+host+"/"+path+"/";
				String user = username + ":" + password;
				currentPath = path;
				try{
	                
	                NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null,username,password);
	                SmbFile sFile = new SmbFile(networkfolder, auth);
	                if(sFile.exists()) {
	                	DefaultTreeModel dmtn;
	                	dmtn = new DefaultTreeModel(helper.showFiles(user, networkfolder, path));
	                	jTree.setModel(dmtn);
	                }else {
	                	JOptionPane.showMessageDialog(frmSharedFolder, "Failed to Connect");
	                }
	                
	            } catch (Exception e) {
	            	JOptionPane.showMessageDialog(frmSharedFolder, "Failed to Connect");
	            	e.printStackTrace();
	            }
			
			
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String username = txtusername.getText();
				String password = txtpassword.getText();
				String host 	= txthost.getText();
				String path		= txtPath.getText();
				String networkfolder = "smb://"+host+"/"+path+"/";
				String user = username + ":" + password;
				currentPath = path;
				try{
	                
	                NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null,username,password);
	                SmbFile sFile = new SmbFile(networkfolder, auth);
	                if(sFile.exists()) {
	                	DefaultTreeModel dmtn;
	                	dmtn = new DefaultTreeModel(helper.showFiles(user, networkfolder, path));
	                	jTree.setModel(dmtn);
	                }else {
	                	JOptionPane.showMessageDialog(frmSharedFolder, "Failed to Connect");
	                }
	                
	            } catch (Exception e) {
	            	JOptionPane.showMessageDialog(frmSharedFolder, "Failed to Connect");
	            	e.printStackTrace();
	            }
				
			}
		});
		btnNewButton.setBounds(579, 131, 97, 25);
		frmSharedFolder.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Extract Report");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = txtusername.getText();
				String password = txtpassword.getText();
				String host 	= txthost.getText();
				String path		= txtPath.getText();
				String networkfolder = "smb://"+host+"/"+path+"/";
				String user = username + ":" + password;
				Data data[] = helper.getExcelData(user, networkfolder);
				exportToExcel(data);
			}
		});
		btnNewButton_1.setBounds(544, 442, 132, 25);
		frmSharedFolder.getContentPane().add(btnNewButton_1);
		
		JButton btnCreateFolder = new JButton("Create Folder");
		btnCreateFolder.setBounds(10, 442, 132, 25);
		frmSharedFolder.getContentPane().add(btnCreateFolder);
		btnCreateFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//createFolder();
				String nameOfFolder = JOptionPane.showInputDialog(frmSharedFolder,"Enter name of folder");
				createFolder(currentPath,nameOfFolder);
			}
		});
		
		JButton btnRemoveFolder = new JButton("Remove Folder");
		btnRemoveFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeFolder(currentPath);
			}
		});
		btnRemoveFolder.setBounds(152, 442, 132, 25);
		frmSharedFolder.getContentPane().add(btnRemoveFolder);
		
		JButton btnRenameFolder = new JButton("Rename Folder");
		btnRenameFolder.setBounds(10, 469, 132, 25);
		frmSharedFolder.getContentPane().add(btnRenameFolder);
		btnRenameFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(frmSharedFolder,"Enter new Name");
				renameFolder(currentPath,name);
			}
		});
		
		JButton btnDownloadFile = new JButton("Download File");
		btnDownloadFile.setBounds(152, 469, 132, 25);
		frmSharedFolder.getContentPane().add(btnDownloadFile);
		btnDownloadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = new JFileChooser();
				file.setMultiSelectionEnabled(false);
				file.setFileHidingEnabled(true);
				file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				file.setDialogTitle("Select folder to download file");
				
				if(file.showOpenDialog(frmSharedFolder)==JFileChooser.APPROVE_OPTION) {
					File f = file.getSelectedFile();
					downloadFile(currentPath,f.getPath().replaceAll("\\\\", "/"));
				}
			}
		});
		
		JButton btnUploadFile = new JButton("Upload File");
		btnUploadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = new JFileChooser();
				file.setMultiSelectionEnabled(false);
				file.setFileHidingEnabled(true);
				if(file.showOpenDialog(frmSharedFolder)==JFileChooser.APPROVE_OPTION) {
					File f = file.getSelectedFile();
					uploadFile(currentPath,f.getPath(),f.getName());
				}
			}
		});
		btnUploadFile.setBounds(10, 497, 132, 25);
		frmSharedFolder.getContentPane().add(btnUploadFile);
	}
	
	

public static void exportToExcel(Data[] filedata)  {
	try {
		Random random  = new Random(1000);
		File f= new File("D://excelfile");
		f.mkdir();
		String filename = "D://excelfile/ExportedExcelFile"+random.nextInt()+".xls";
		f= new File(filename);
		f.createNewFile();
		
		HSSFWorkbook  workbook = new HSSFWorkbook();
		HSSFSheet spreadsheet = workbook.createSheet( " Folder Info ");
		HSSFRow sheetrow = spreadsheet.createRow(0); 
		sheetrow.createCell(0).setCellValue("Name");
		sheetrow.createCell(1).setCellValue("Number of files");
		sheetrow.createCell(2).setCellValue("Creation time");
		sheetrow.createCell(3).setCellValue("Last Modified Time");
		sheetrow.createCell(4).setCellValue("Is Directory");
		sheetrow.createCell(5).setCellValue("Size");
		sheetrow.createCell(6).setCellValue("Path");
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		int rownumber = 2;
		
		
			for(Data files : filedata) {
				sheetrow = spreadsheet.createRow(rownumber++);
				sheetrow.createCell(0).setCellValue(files.name);
				sheetrow.createCell(1).setCellValue(files.numberOfFile);
				
				long t = Long.parseLong(files.creationTime);
				Date cdt = new Date(t); 
				sheetrow.createCell(2).setCellValue(cdt.toString());
				
				t = Long.parseLong(files.creationTime);
				cdt = new Date(t);
				sheetrow.createCell(3).setCellValue(cdt.toString());
				
				
				sheetrow.createCell(4).setCellValue(files.isDirectory);
				sheetrow.createCell(5).setCellValue(files.size+ "Kb");
				sheetrow.createCell(6).setCellValue(files.path);
				
				if(files.numberOfFile>3) {	
					sheetrow.getCell(0).setCellStyle(style);
					sheetrow.getCell(1).setCellStyle(style);
					sheetrow.getCell(2).setCellStyle(style);
					sheetrow.getCell(3).setCellStyle(style);
					sheetrow.getCell(4).setCellStyle(style);
					sheetrow.getCell(5).setCellStyle(style);
					sheetrow.getCell(6).setCellStyle(style);
				}
				
			}
			
			
			
			
			FileOutputStream out = new FileOutputStream(f);
			workbook.write(out);
		    out.close();  
		    
		    int check = JOptionPane.showConfirmDialog(frmSharedFolder, 
                    "File Exported at : "+f.getAbsolutePath() ,
					  "Open File", JOptionPane.YES_NO_OPTION);
			
		    if(check!=1) {
			    
				if(!Desktop.isDesktopSupported())  
				{  
					return;  
				}  
				Desktop desktop = Desktop.getDesktop();  
				if(f.exists())           
					desktop.open(f);               
			}	
		    
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}



	public void createFolder(String path,String name) {
		
		String username = txtusername.getText();
		String password = txtpassword.getText();
		String host 	= txthost.getText();
		String networkfolder = "smb://"+host+path+"/"+name;
		String user = username + ":" + password;
		
		try{
            
            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null,username,password);
            SmbFile sFile = new SmbFile(networkfolder, auth);
            sFile.mkdir();
            reloadTree();
            
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(frmSharedFolder, e.toString());
        	e.printStackTrace();
        }
		
	}
	
	public void renameFolder(String path,String name) {
		String username = txtusername.getText();
		String password = txtpassword.getText();
		String host 	= txthost.getText();
		String networkfolder = "smb://"+host+path;
		
		
		String user = username + ":" + password;
		
		try{
            
            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null,username,password);
            SmbFile oldFile = new SmbFile(networkfolder, auth);
            String temp =  oldFile.getParent()+name;
            SmbFile newFile = new SmbFile(temp, auth);
            oldFile.renameTo(newFile);
            reloadTree();
            
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(frmSharedFolder, e.toString());
        	e.printStackTrace();
        }
	}
	
	public void removeFolder(String path) {
		String username = txtusername.getText();
		String password = txtpassword.getText();
		String host 	= txthost.getText();
		String networkfolder = "smb://"+host+path+"/";
		String user = username + ":" + password;
		
		try{
            
            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null,username,password);
            SmbFile sFile = new SmbFile(networkfolder, auth);
            sFile.delete();
            reloadTree();
            
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(frmSharedFolder, e.toString());
        	e.printStackTrace();
        }
	}

	public void downloadFile(String smbpath,String folder) {
		String username = txtusername.getText();
		String password = txtpassword.getText();
		String host 	= txthost.getText();
		String networkfolder = "smb://"+host+smbpath;
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, username, password);
	    try {
			SmbFile sFile = new SmbFile(networkfolder, auth);
			if(sFile.isFile()) {
				String name = sFile.getName();
				File f = new File(folder+"/"+name);
				f.createNewFile();
				FileOutputStream out = new FileOutputStream(f);
				SmbFileInputStream sis = new SmbFileInputStream(sFile);
				sis.transferTo(out);
				sis.close();
				out.close();
				
				int check = JOptionPane.showConfirmDialog(frmSharedFolder, 
	                    "File Exported at : "+f.getAbsolutePath() ,
						  "Open File", JOptionPane.YES_NO_OPTION);
				if(check!=1) {
				try  
				{     
					if(!Desktop.isDesktopSupported())  
					{  
						JOptionPane.showMessageDialog(frmSharedFolder, "Program not found for opening the file");
						return;  
					}  
					Desktop desktop = Desktop.getDesktop();  
					if(f.exists())           
						desktop.open(f);               
					}  
				catch(Exception e)  
				{ e.printStackTrace();}
				}
				
			}else {
				JOptionPane.showMessageDialog(frmSharedFolder, "Only file Can be downloaded.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void uploadFile(String smbpath,String localpath,String filename) {
		try {
				String username = txtusername.getText();
				String password = txtpassword.getText();
				String host 	= txthost.getText();
				String networkfolder = "smb://"+host+smbpath+"/"+filename;
				NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, username, password);
			    SmbFile sFile = new SmbFile(networkfolder, auth);
				
				File file = new File(localpath);
				byte[] bytes = Files.readAllBytes(file.toPath());
				
				SmbFileOutputStream sfos = new SmbFileOutputStream(sFile);
				sfos.write(bytes);
				sfos.close();
				reloadTree();
				
			} catch (Exception e) {
			   e.printStackTrace ();
			}
	}
	
	public void reloadTree() {
	
		String username = txtusername.getText();
		String password = txtpassword.getText();
		String host 	= txthost.getText();
		String path		= txtPath.getText();
		String networkfolder = "smb://"+host+"/"+path+"/";
		String user = username + ":" + password;
		
		try{
            
            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null,username,password);
            SmbFile sFile = new SmbFile(networkfolder, auth);
            if(sFile.exists()) {
            	DefaultTreeModel dmtn;
            	dmtn = new DefaultTreeModel(helper.showFiles(user, networkfolder, path));
            	jTree.setModel(dmtn);
            }else {
            	JOptionPane.showMessageDialog(frmSharedFolder, "Failed to Connect");
            }
            
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(frmSharedFolder, "Failed to Connect");
        	e.printStackTrace();
        }
	}
}


