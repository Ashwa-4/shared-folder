package shared_folder;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) {
        String url = "smb://192.168.43.14/software";
        String userName = "A1";
        String password = "ash";
        String domain = null;
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domain, userName, password);
        try {
        	SmbFile s = new SmbFile(url,auth);
        	s.getContent();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    * Recursively scans through the folder for files and prints the name of folder and file
    */
    public static void doRecursiveLookup(SmbFile smb) {
        try {
            if (smb.isDirectory()) {
                System.out.println(smb.getName());
                for (SmbFile f : smb.listFiles()) {
                    if (f.isDirectory()) {
                        doRecursiveLookup(f);
                    } else {
                        System.out.println("\t:" + f.getName());
                    }
                }
            } else {
                System.out.println("\t:" + smb.getName());
            }
        } catch (SmbException e) {
            e.printStackTrace();
        }
    }
}