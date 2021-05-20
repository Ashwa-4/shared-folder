package shared_folder;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

public class HelperClass {
	
	
	public Data[] getExcelData(String user,String path) {
		
		
		try {
	        	NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(user);
	        	SmbFile sFile = new SmbFile(path, auth);
		        int number = sFile.list().length;
		        Data data[]=new Data[number];
		        for(int i = 0 ;i<number;i++) {
		        	data[i] = new Data();
		        }
	        	int index = 0;
	        	for(SmbFile file : sFile.listFiles()) {
	        		
	   
	        		data[index].setName(file.getName().replaceAll("/", ""));
	        		data[index].setCreationTime(new Long(file.getDate()).toString());
	        		data[index].setDirectory(file.isDirectory());
	        		
	        		if(file.isDirectory())
	        			data[index].setNumberOfFile(file.list().length);
	        		else
	        			data[index].setNumberOfFile(0);
	        		
	        		data[index].setSize(file.length()/1024);
	        		
	        		data[index].setPath(file.getPath());
	        		data[index].setLastmodified(new Long(file.getLastModified()).toString());
	        		data[index].setPath(path+file.getName());
	        		index++;
	        	}
	        	return data;
        	}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public DefaultMutableTreeNode showFiles(String user,String path,String fileName) {
    	DefaultMutableTreeNode dft;
		try {
        	NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(user);
        	dft = new DefaultMutableTreeNode(fileName.replaceAll("/", ""));
        	SmbFile sFile = new SmbFile(path, auth);
        	
        	for(SmbFile file : sFile.listFiles()) {
        		String temp = file.getName().replaceAll("/", ""); 
        		if(file.isDirectory()) {
        			dft.add(showFiles(user,path+file.getName(),temp));
        			
        		}else {
        			dft.add(new DefaultMutableTreeNode(temp));
        		}
        		
        	}
     
        	return dft;
    
		}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
	
	
		
}
