/**
 * @author viborole
 */
package com.vinodborole.openstack4j.app;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.ActionResponse;
import org.openstack4j.model.identity.Tenant;
import org.openstack4j.model.image.Image;

import com.google.common.base.Strings;
import com.vinodborole.openstack4j.app.api.CinderAPI;
import com.vinodborole.openstack4j.app.api.CommonAPI;
import com.vinodborole.openstack4j.app.api.GlanceAPI;
import com.vinodborole.openstack4j.app.api.NeutronAPI;
import com.vinodborole.openstack4j.app.api.NovaAPI;
import com.vinodborole.openstack4j.app.api.TenantAPI;
import com.vinodborole.openstack4j.app.utils.TableBuilder;


public class Osp4jShell {
    public static void main(String[] args) throws Exception {
     if(args.length>0 && !Strings.isNullOrEmpty(args[0]) && args[0].equalsIgnoreCase("testsuite")){
         if(!Strings.isNullOrEmpty(args[1])){
             File file = new File(args[1]);
             FileReader fileReader = new FileReader(file);
             BufferedReader console = new BufferedReader(fileReader);
             while (true) {
                 String commandLine = console.readLine();
                 executeCommand(commandLine);
             }
         }else{
             System.out.println("Missing arguments, please use following command.");
             System.out.println("java -jar <jarname> testsuite <testsuitefilepath>");
             System.exit(0);
         }
     }else{ 
             Console console=System.console();
             if(console!=null){
                 while (true) {
                     executeShell(console);
                 }
             }else{
                 System.out.println("No Console associated, Cannot execute the program!");
             }
     }
  }
    private static void executeShell(Console console){
        System.out.print("osp>");
        String commandLine = console.readLine();
        executeCommand(commandLine);
    }
    /** 
     * @author viborole
     */
    private static void executeCommand(String commandLine) {
    try{  
            if(commandLine==null){
                System.exit(0);
            }else if (commandLine.equals("") || commandLine.startsWith("#")){
              return;
            }else if(commandLine.equalsIgnoreCase("exit"))  {
                System.exit(0);
            }
            // Split the string into a String Array
            ArrayList<String> params = new ArrayList<String>();
            String[] lineSplit = commandLine.split(" ");
             
            int size = lineSplit.length;
            for (int i=0; i<size; i++)  {
                params.add(lineSplit[i]); 
                //System.out.println(lineSplit[i]);
             } 
            Commands command=Commands.fromString(params.get(0));
            switch(command!=null?command:command.NULL){
                case SOURCE:
                {
                    System.out.println("loading properties");
                    Osp4jSession.loadProperties(params.get(1));
                }
                    break;
                case PRINT:
                {
                    Commands subcommand=Commands.fromString(params.get(1));
                    switch(subcommand!=null?subcommand:subcommand.NULL){
                        case CONFIG:
                        {
                            PrintWriter writer = new PrintWriter(System.out);
                            Osp4jSession.getConfigProperties().list(writer);
                            writer.flush();
                        }
                        break;
                        case TENANT_LIST:
                        {
                            OSClient os=Osp4jSession.getOspSession();
                            List<? extends Tenant> lstTenant=os.identity().tenants().list();
                            TableBuilder tb = new TableBuilder();
                            tb.addRow("Tenant ID","Tenant Name");
                            tb.addRow("---------","---------");
                            for(Tenant t : lstTenant){
                                tb.addRow(t.getId(),t.getName());
                            }
                            System.out.println(tb.toString());
                        }
                        break;
                        case HELP:
                        {
                            printHelp();
                         }
                        break;
                        case TENANT_INFO:
                        {
                            TenantAPI.printTenantInfo();
                        }
                        break;
                        case NULL:
                            System.err.println("Invaid command");
                    }
                }
                    break; 
                case NOVA:
                {
                    Commands subcommand=Commands.fromString(params.get(1));
                    switch(subcommand!=null?subcommand:subcommand.NULL){
                        case START:
                        {
                            ActionResponse response = NovaAPI.startVM(params.get(2));
                            if(response.isSuccess()){
                                NovaAPI.printServerDetails(NovaAPI.getServer(params.get(2)));
                            }else{
                                System.err.println("Failure!"); 
                            }
                            
                        }
                        break;
                        case STOP:
                        {
                            boolean response = NovaAPI.stopVM(params.get(2));
                            if(response){
                                NovaAPI.printServerDetails(NovaAPI.getServer(params.get(2)));
                            }else{
                                System.err.println("Failure!");
                            }
                        }
                        break;
                        case RESTART:
                        {
                            ActionResponse response = NovaAPI.restartVM(params.get(2));
                            if(response.isSuccess()){
                                NovaAPI.printServerDetails(NovaAPI.getServer(params.get(2)));
                            }else{
                                System.err.println("Failure!");
                            }
                        }
                        break;
                        case DOWNLOAD:
                        {
                            boolean response=NovaAPI.downloadVM(params.get(2),params.get(3),params.get(4));
                            System.out.println(response);
                        }
                        break;
                        case IMAGE_CREATE:
                        {
                            String imageId=NovaAPI.createSnapshot(params.get(2),params.get(3));
                            GlanceAPI.printImageDetails(GlanceAPI.getImageDetail(imageId));
                        }
                        break;
                        case FLAVOR_LIST:
                        {
                            NovaAPI.listflavor();
                        }
                        break;
                        case BOOT:
                        {
                           String serverId= NovaAPI.boot(params.get(2),params.get(3),params.get(4),params.get(5),false);
                           NovaAPI.printServerDetails(NovaAPI.getServer(serverId));
                        }
                        break;
                        case BOOT_VOLUME:
                        {
                           String serverId = NovaAPI.boot(params.get(2),params.get(3),params.get(4),params.get(5),true);
                           
                        }
                        break;
                        case DELETE:
                        {
                            NovaAPI.delete(params.get(2));
                        }
                        break;
                        case STATUS:
                        {
                            NovaAPI.status(params.get(2));
                        }
                        break;
                        case SNAPSHOT:
                        {
                            String imageId=NovaAPI.createSnapshot(params.get(2), params.get(3));
                            GlanceAPI.printImageDetails(GlanceAPI.getImageDetail(imageId));
                        }
                        break;
                        case HELP:
                        {
                            novaHelp();
                        }
                        break;
                        case NULL:
                            System.err.println("Invaid command");
                    }
                }
                break;
                case NEUTRON:
                {
                    Commands subcommand=Commands.fromString(params.get(1));
                    switch(subcommand!=null?subcommand:subcommand.NULL){
                        case NET_LIST:
                        {
                            NeutronAPI.printNetsDetails(NeutronAPI.netList());
                        }
                        break;
                        case HELP:
                        {
                            neutronHelp();
                        }
                        break;
                        case NULL:
                            System.err.println("Invaid command"); 
                    }
                }
                break;
                case CINDER:
                {
                    Commands subcommand=Commands.fromString(params.get(1));
                    switch(subcommand!=null?subcommand:subcommand.NULL){
                        case CREATE:
                        {
                            CinderAPI.createVolume(Integer.valueOf(params.get(2)), params.get(3));
                        }
                        break;
                        case CREATE_FROM_IMAGE:
                        {
                            CinderAPI.createVolumeFromImage(params.get(2), Integer.valueOf(params.get(3)), params.get(4));
                        }
                        break;
                        case CREATE_FROM_VOLUME_SNAPSHOT:
                        {
                            CinderAPI.createVolumeFromVolumeSnap(params.get(2), Integer.valueOf(params.get(3)), params.get(4));
                        }
                        break;
                        case LIST:
                        {
                            CinderAPI.listvolumes();
                        }
                        break;
                        case SHOW:
                        {
                            CinderAPI.show(params.get(2));
                        }
                        break;
                        case VOLUME_ATTACH:
                        {
                            System.out.println("Under construction");
                        }
                        break;
                        case VOLUME_DETTACH:
                        {
                            System.out.println("Under construction");
                        }
                        break;
                        case DELETE:
                        {
                            boolean isVolDeleted = CinderAPI.deleteVolume(params.get(2));
                            System.out.println("Result: "+isVolDeleted);
                        }
                        break;
                        case UPLOAD_TO_IMAGE:
                        {
                            CinderAPI.uploadVolumeToImage(params.get(2), params.get(3));
                        }
                        break;
                        case HELP:
                        {
                            cinderHelp();
                        }
                        break;
                        case NULL:
                            System.err.println("Invaid command"); 
                    }
                }
                break;
                case GLANCE:
                {
                    Commands subcommand=Commands.fromString(params.get(1));
                    switch(subcommand!=null?subcommand:subcommand.NULL){
                        case IMAGE_LIST:
                        {
                           List<? extends Image> images= GlanceAPI.imageList();
                           GlanceAPI.printImagesDetails(images);
                        }
                        break;
                        case IMAGE_CREATE:
                        {
                            Image image= GlanceAPI.imageCreate(params.get(2), params.get(3));
                            if(image!=null){
                                GlanceAPI.printImageDetails(image);
                            }else{
                                System.err.println("Upload failed");
                            }
                        }
                        break;
                        case IMAGE_DOWNLOAD:
                        { 
                            boolean status=CommonAPI.downloadImage(params.get(2), params.get(3), params.get(4));
                            System.out.println("Download status: "+status); 
                        }
                        break;
                        case HELP:
                        {
                            glanceHelp();
                        }
                        break;
                        case NULL:
                            System.err.println("Invaid command");
                    }
                }
                break;
                case DELETE:
                {
                    Commands subcommand=Commands.fromString(params.get(1));
                    switch(subcommand!=null?subcommand:subcommand.NULL){
                        case TENANT_ALL_INSTANCES:
                        {
                            TenantAPI.deleteAllVMs();
                        }
                        break;
                        case TENANT_ALL_VOLUMES:
                        {
                            TenantAPI.deleteAllVolumes();
                        }
                        break;
                        case TENANT_ALL_VOLUME_SNAPSHOTS:
                        {
                            TenantAPI.deleteAllVolumeSnapshots();
                        }
                        break;
                        case TENANT_ALL_IMAGES:
                        {
                            TenantAPI.deleteAllImages();
                        }
                        break;
                        case TENANT_ALL_NETWORKS:
                        { 
                            TenantAPI.deleteAllNetworks();
                        }
                        break;
                        case TENANT_ALL_ROUTERS:
                        {
                            TenantAPI.deleteAllRouters();
                        }
                        break;
                        case TENANT_ALL_SECURITY_GROUP_RULES:
                        {
                            TenantAPI.deleteAllSecurityGroupRules();
                        }
                        case TENANT_INFO:
                        {
                            TenantAPI.deleteTenantInfo();
                        }
                        break;     
                        case HELP:
                        {
                           deleteHelp();
                        }
                        break;
                        case NULL:
                            System.err.println("Invaid command");
                    }
                }
                break;
                case FLUSH:
                {
                    ShellContext.getContext().getShellMemory().flushMemory();
                }
                break;
                case LOGGING_YES:
                {
                    Osp4jSession.enableLogging();
                }
                break;
                case LOGGING_NO:
                {
                    Osp4jSession.disableLogging();
                }
                break;
                case HELP:
                {
                   allHelp();
                }
                break;
                default:
                    System.err.println("Invaid command");
            }
            
            }catch(Exception e){
            //suppress exception
        }
    }

    

    
    private static void allHelp(){
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
    private static StringBuffer getHelpData() {
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
    
    private static void printHelp() throws Exception{
        StringBuffer help = getHelpData();
        String data=help.substring(help.indexOf("<PRINT>"), help.indexOf("</PRINT>"));
        System.out.println(data.replace("<PRINT>", ""));
    }
    
    private static void deleteHelp(){
        StringBuffer help = getHelpData();
        String data=help.substring(help.indexOf("<DELETE>"), help.indexOf("</DELETE>"));
        System.out.println(data.replace("<DELETE>", ""));
    }
    
    private static void glanceHelp(){
        StringBuffer help = getHelpData();
        String data=help.substring(help.indexOf("<GLANCE>"), help.indexOf("</GLANCE>"));
        System.out.println(data.replace("<GLANCE>", ""));
        
    }
    
    private static void novaHelp(){
        StringBuffer help = getHelpData();
        String data=help.substring(help.indexOf("<NOVA>"), help.indexOf("</NOVA>"));
        System.out.println(data.replace("<NOVA>", ""));
    }
    private static void cinderHelp() {
        StringBuffer help = getHelpData();
        String data=help.substring(help.indexOf("<CINDER>"), help.indexOf("</CINDER>"));
        System.out.println(data.replace("<CINDER>", ""));
        
    }    
    private static void neutronHelp() {
        StringBuffer help = getHelpData();
        String data=help.substring(help.indexOf("<NEUTRON>"), help.indexOf("</NEUTRON>"));
        System.out.println(data.replace("<NEUTRON>", ""));
    }    
    private static void configFormat(){
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
    
    private enum Commands{
        SOURCE("source"),
        NULL("null"),
        PRINT("print"),
        TENANT_LIST("tenant-list"),
        CONFIG("config"),
        NOVA("nova"),
        START("start"),
        STOP("stop"),
        DOWNLOAD("download"),
        RESTART("restart"),
        GLANCE("glance"),
        IMAGE_LIST("image-list"),
        IMAGE_CREATE("image-create"),
        IMAGE_DOWNLOAD("image-download"),
        HELP("help"),
        LOGGING_YES("logging-yes"),
        LOGGING_NO("logging-no"), 
        FLAVOR_LIST("flavor-list"), NEUTRON("neutron"), CINDER("cinder"),NET_LIST("net-list"), BOOT("boot"), DELETE("delete"), STATUS("status"), RUN("run"), TESTSUITE("testsuite"), 
        FLUSH("flush"), TENANT_INFO("tenant-info"),CREATE("create"), CREATE_FROM_IMAGE("create-from-image"), CREATE_FROM_VOLUME_SNAPSHOT("create-from-volume-snapshot"), LIST("list"), VOLUME_ATTACH("volume-attach"), SHOW("show"), VOLUME_DETTACH("volume-dettach"), BOOT_VOLUME("boot-volume"), SNAPSHOT("snapshot"), UPLOAD_TO_IMAGE("upload-to-image"), TENANT_ALL_INSTANCES("tenant-all-instances"), TENANT_ALL_VOLUME_SNAPSHOTS("tenant-all-volume-snapshots"), TENANT_ALL_IMAGES("tenant-all-images"), TENANT_ALL_VOLUMES("tenant-all-volumes"), TENANT_ALL_NETWORKS("tenant-all-networks"), TENANT_ALL_ROUTERS("tenant-all-routers"),TENANT_ALL_SECURITY_GROUP_RULES("tenant-all-security-group-rules");
        
        
        private String commandString;
        private Commands(String commandString){
            this.commandString=commandString;
        }
        public String getCommandString() {
            return this.commandString;
        }
        public static Commands fromString(String text) {
            if (text != null) {
              for (Commands c : Commands.values()) {
                  if(c!=null)
                      if (text.equalsIgnoreCase(c.commandString)) {
                          return c; 
                      }
              }
            }
            return null;
          }
    }
}
