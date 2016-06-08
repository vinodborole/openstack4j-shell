package com.vinodborole.openstack4j.app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * Responsible for printing help info for commands
 *  
 * @author vinod borole
 */
public class Osp4jShellCommmandHelpInfo {
    public static void allHelp(){
        String helpdata=getHelpData().toString();
        helpdata=helpdata.replace("<PRINT>", "");
        helpdata=helpdata.replace("</PRINT>", "");
        
        helpdata=helpdata.replace("<NOVA>", "");
        helpdata=helpdata.replace("</NOVA>", "");
        
        helpdata=helpdata.replace("<CINDER>", "");
        helpdata=helpdata.replace("</CINDER>", "");

        helpdata=helpdata.replace("<GLANCE>", "");
        helpdata=helpdata.replace("</GLANCE>", "");
        
        helpdata=helpdata.replace("<NEUTRON>", "");
        helpdata=helpdata.replace("</NEUTRON>", "");

        helpdata=helpdata.replace("<BASIC>", "");
        helpdata=helpdata.replace("</BASIC>", "");

        helpdata=helpdata.replace("<DELETE>", "");
        helpdata=helpdata.replace("</DELETE>", "");
        System.out.println(helpdata);
    }
    /**
     * @author viborole
     * @throws IOException 
     */
    public static StringBuffer getHelpData() {
        StringBuffer help = new StringBuffer();
        InputStream is=null;
        InputStreamReader r=null;
        BufferedReader br=null;
        try{
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            is = classLoader.getResourceAsStream("Help.txt");
            r = new InputStreamReader(is);
            br = new BufferedReader(r);
            String line;
            while ((line = br.readLine())!= null){
                if(!line.isEmpty()){
                    help.append(line).append("\n");
                }
            }
        }catch(Exception e){
            
        }finally{
            try{
                if(is!=null) is.close();
                if(r!=null) r.close();
                if(br!=null) br.close();
            }catch(Exception e){e.printStackTrace();}
        }
        return help;
    }
    public static void printHelp() throws Exception{
        StringBuffer help = getHelpData();
        String data=help.substring(help.indexOf("<PRINT>"), help.indexOf("</PRINT>"));
        System.out.println(data.replace("<PRINT>", ""));
    }
    public static void deleteHelp(){
        StringBuffer help = getHelpData();
        String data=help.substring(help.indexOf("<DELETE>"), help.indexOf("</DELETE>"));
        System.out.println(data.replace("<DELETE>", ""));
    }
    public static void glanceHelp(){
        StringBuffer help = getHelpData();
        String data=help.substring(help.indexOf("<GLANCE>"), help.indexOf("</GLANCE>"));
        System.out.println(data.replace("<GLANCE>", ""));
        
    }
    public static void novaHelp(){
        StringBuffer help = getHelpData();
        String data=help.substring(help.indexOf("<NOVA>"), help.indexOf("</NOVA>"));
        System.out.println(data.replace("<NOVA>", ""));
    }
    public static void cinderHelp() {
        StringBuffer help = getHelpData();
        String data=help.substring(help.indexOf("<CINDER>"), help.indexOf("</CINDER>"));
        System.out.println(data.replace("<CINDER>", ""));
        
    }    
    public static void neutronHelp() {
        StringBuffer help = getHelpData();
        String data=help.substring(help.indexOf("<NEUTRON>"), help.indexOf("</NEUTRON>"));
        System.out.println(data.replace("<NEUTRON>", ""));
    }    
    public static void configFormat(){
        System.out.println("CONFIG FILE FORMAT");
        System.out.println("------------------");
        System.out.println("File name should end with '.properties'");
        System.out.println("Example entries expected:");
        System.out.println("");
        System.out.println("OS_AUTH_URL=https://[URL]:5000/v2.0");
        System.out.println("OS_TENANT_ID=afafagagag");
        System.out.println("OS_TENANT_NAME=PROJECT_NAME");
        System.out.println("OS_USERNAME=username");
        System.out.println("OS_PASSWORD=password");
        System.out.println("OS_REGION_NAME=region");
        System.out.println("OS_ENABLE_SSL=true");
        System.out.println("OS_ENABLE_LOGGING=false");
    }




}
