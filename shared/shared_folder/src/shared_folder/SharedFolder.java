package shared_folder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;


import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;


public class SharedFolder {

		
	
	public static void main(String srgs[]) {
	
	}
}



/*
public class SharedFolder {
    static final String USER_NAME = "asus";
    static final String PASSWORD = "shyam";
    
    static final String NETWORK_FOLDER = "smb://192.168.43.103/";
 
  
 


    
    public DefaultMutableTreeNode showFiles(String user,String path,String fileName) {
    	try {

        	NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(user);
        	DefaultMutableTreeNode dft = new DefaultMutableTreeNode(fileName);
        	SmbFile sFile = new SmbFile(path, auth);
        	sFile.setAllowUserInteraction(true);
        	System.out.print(sFile.getContent());
        	/*for(SmbFile s : sFile.listFiles()) {
        		if(s.isDirectory()) {
        			dft.add(showFiles(user,path+s.getName()+"/",s.getName()));
        		}else {
            		DefaultMutableTreeNode tempNode = new DefaultMutableTreeNode(s.getName());
            		dft.add( new DefaultMutableTreeNode(tempNode));
            	}
        	}
        	
        	
        	//return dft;
        	
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	return null;
    }

}
*/

