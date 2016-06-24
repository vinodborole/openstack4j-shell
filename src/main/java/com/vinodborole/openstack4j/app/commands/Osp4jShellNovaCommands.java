package com.vinodborole.openstack4j.app.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.compute.Flavor;

import com.vinodborole.openstack4j.app.api.GlanceAPI;
import com.vinodborole.openstack4j.app.api.NeutronAPI;
import com.vinodborole.openstack4j.app.api.NovaAPI;
import com.vinodborole.openstack4j.app.commands.factory.IOsp4jShellCommands;
import com.vinodborole.openstack4j.app.utils.Osp4jShellCommmandHelpInfo;
import com.vinodborole.openstack4j.app.utils.OspPrintWriter;
/**
 * Responsible for executing nova commands on Openstack cloud
 *  
 * @author vinod borole
 */
public class Osp4jShellNovaCommands extends Osp4jShellCommands implements IOsp4jShellCommands{

    private static final Osp4jShellNovaCommands INSTANCE = new Osp4jShellNovaCommands();
    private Osp4jShellNovaCommands(){}
    public static Osp4jShellNovaCommands getInstance(){
        return INSTANCE;
    }   
    
    public void executeCommand(Commands command, List<String> params) throws Exception {
        switch(command!=null?command:Commands.NULL){
        case NOVA:
        {
            Commands subcommand=Commands.fromString(params.get(1));
            switch(subcommand!=null?subcommand:Commands.NULL){
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
                case BOOT_DEFAULT:
                { 
                    String serverId=NovaAPI.bootdefault(params.get(2),params.get(3));
                    NovaAPI.printServerDetails(NovaAPI.getServer(serverId));
                }
                break;
                case BOOT_VOLUME_DEFAULT:
                {
                    String serverId=NovaAPI.bootvolumedefault(params.get(2),params.get(3));
                    NovaAPI.printServerDetails(NovaAPI.getServer(serverId));
                }
                break;
                case BOOT_CUSTOM:
                {
                	Map<String, String> ids = new HashMap<String,String>();
                	try{
                		Flavor flavor=NovaAPI.getFlavor(Integer.parseInt(params.get(3)), Integer.parseInt(params.get(4)), Integer.parseInt(params.get(5)));
                		ids=NeutronAPI.createNetworking(params.get(6),params.get(7));
                		String serverId=NovaAPI.boot(params.get(2), flavor.getId(), ids.get("network"), params.get(7), false);
                		ids.put("server", serverId);
                		NovaAPI.printServerDetails(NovaAPI.getServer(serverId));
                	}catch(Exception e){
                		NeutronAPI.deleteNetworking(ids);
                	}
                }
                break;
                case BOOT_VOLUME_CUSTOM:
                {
                	Map<String, String> ids = new HashMap<String,String>();
                	try{
                		Flavor flavor=NovaAPI.getFlavor(50, 2048, 1);
                		ids=NeutronAPI.createNetworking(params.get(3),params.get(4));
                		String serverId=NovaAPI.boot(params.get(2), flavor.getId(), ids.get("network"), params.get(4), true);
                		ids.put("server", serverId);
                		NovaAPI.printServerDetails(NovaAPI.getServer(serverId));
                	}catch(Exception e){
                		NeutronAPI.deleteNetworking(ids);
                	}
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
                case LIST:
                {
                    NovaAPI.printServersDetails(NovaAPI.listServers());
                }
                break;
                case SHOW:
                {
                    NovaAPI.printServerDetails(NovaAPI.getServer(params.get(2)));
                }
                break;
                case HELP:
                {
                    Osp4jShellCommmandHelpInfo.novaHelp();
                }
                break;
                case NULL:
                    System.err.println("Invaid command");
                default:
                    break;
            }
         }
            default:
                break;
     }
        
    }
    public void executeCommand(String[] args) throws Exception {
        Commands subcommand=Commands.fromString(args.length > 1 ? args[1]:null);
        switch(subcommand!=null?subcommand:Commands.NULL){
            case START:
            {
                Options serverStartOptions=Osp4jShellCommonCommandOptions.getNovaStartHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(serverStartOptions, Arrays.copyOfRange(args, 2, args.length));
                    ActionResponse response = NovaAPI.startVM(line.getOptionValue("id"));
                    if(response.isSuccess()){
                        NovaAPI.printServerDetails(NovaAPI.getServer(line.getOptionValue("id")));
                    }else{
                        System.err.println("Failure!"); 
                    }
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], serverStartOptions);
                }
                
            }
            break;
            case STOP:
            {
                Options serverStopOptions=Osp4jShellCommonCommandOptions.getNovaStopHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(serverStopOptions, Arrays.copyOfRange(args, 2, args.length));
                    boolean response = NovaAPI.stopVM(line.getOptionValue("id"));
                    if(response){
                        NovaAPI.printServerDetails(NovaAPI.getServer(line.getOptionValue("id")));
                    }else{
                        System.err.println("Failure!");
                    }
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], serverStopOptions);
                }
                
           }
            break;
            case RESTART:
            {
                Options serverRestartOptions=Osp4jShellCommonCommandOptions.getNovaRestartHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(serverRestartOptions, Arrays.copyOfRange(args, 2, args.length));
                    ActionResponse response = NovaAPI.restartVM(line.getOptionValue("id"));
                    if(response.isSuccess()){
                        NovaAPI.printServerDetails(NovaAPI.getServer(line.getOptionValue("id")));
                    }else{
                        System.err.println("Failure!");
                    }
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], serverRestartOptions);
                }
            }
            break;
            case DOWNLOAD:
            {
                Options serverDownloadOptions=Osp4jShellCommonCommandOptions.getNovaDownloadHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(serverDownloadOptions, Arrays.copyOfRange(args, 2, args.length));
                    boolean response=NovaAPI.downloadVM(line.getOptionValue("id"),line.getOptionValue("file"),line.getOptionValue("name"));
                    System.out.println(response);
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], serverDownloadOptions);
                }
            }
            break;
            case FLAVOR_LIST:
            {
                NovaAPI.listflavor();
            }
            break;
            case BOOT:
            {
                Options serverBootOptions=Osp4jShellCommonCommandOptions.getNovaBootHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(serverBootOptions, Arrays.copyOfRange(args, 2, args.length));
                    String serverId= NovaAPI.boot(line.getOptionValue("id"),line.getOptionValue("falvorid"),line.getOptionValue("netid"),line.getOptionValue("name"),false);
                    NovaAPI.printServerDetails(NovaAPI.getServer(serverId));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], serverBootOptions);
                }
            }
            break;
            case BOOT_VOLUME:
            {
                Options serverBootOptions=Osp4jShellCommonCommandOptions.getNovaBootVolumeHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(serverBootOptions, Arrays.copyOfRange(args, 2, args.length));
                    String serverId = NovaAPI.boot(line.getOptionValue("id"),line.getOptionValue("falvorid"),line.getOptionValue("netid"),line.getOptionValue("name"),true);
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], serverBootOptions);
                }
               
            }
            break;
            case BOOT_DEFAULT:
            { 
                Options serverBootDefaultOptions=Osp4jShellCommonCommandOptions.getNovaBootDefaultHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(serverBootDefaultOptions, Arrays.copyOfRange(args, 2, args.length));
                    String serverId=NovaAPI.bootdefault(line.getOptionValue("id"),line.getOptionValue("name"));
                    NovaAPI.printServerDetails(NovaAPI.getServer(serverId));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], serverBootDefaultOptions);
                }
            }
            break;
            case BOOT_VOLUME_DEFAULT:
            {
                Options serverBootVolumeDefaultOptions=Osp4jShellCommonCommandOptions.getNovaBootVolumeDefaultHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(serverBootVolumeDefaultOptions, Arrays.copyOfRange(args, 2, args.length));
                    String serverId=NovaAPI.bootvolumedefault(line.getOptionValue("id"),line.getOptionValue("name"));
                    NovaAPI.printServerDetails(NovaAPI.getServer(serverId));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], serverBootVolumeDefaultOptions);
                }
            }
            break;
            case BOOT_CUSTOM:
            {
                Options serverBootCustomOptions=Osp4jShellCommonCommandOptions.getNovaBootCustomHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(serverBootCustomOptions, Arrays.copyOfRange(args, 2, args.length));
                    Map<String, String> ids = new HashMap<String,String>();
                    try{
                        Flavor flavor=NovaAPI.getFlavor(Integer.parseInt(line.getOptionValue("disk")), Integer.parseInt(line.getOptionValue("ram")), Integer.parseInt(line.getOptionValue("vcpu")));
                        ids=NeutronAPI.createNetworking(line.getOptionValue("cidr"),line.getOptionValue("name"));
                        String serverId=NovaAPI.boot(line.getOptionValue("id"), flavor.getId(), ids.get("network"), line.getOptionValue("name"), false);
                        ids.put("server", serverId);
                        NovaAPI.printServerDetails(NovaAPI.getServer(serverId));
                    }catch(Exception e){
                        NeutronAPI.deleteNetworking(ids);
                    }
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], serverBootCustomOptions);
                }
            }
            break;
            case BOOT_VOLUME_CUSTOM:
            {
                Options serverBootVolumeCustomOptions=Osp4jShellCommonCommandOptions.getNovaBootVolumeCustomHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(serverBootVolumeCustomOptions, Arrays.copyOfRange(args, 2, args.length));
                    Map<String, String> ids = new HashMap<String,String>();
                    try{
                        Flavor flavor=NovaAPI.getFlavor(50, 2048, 1);
                        ids=NeutronAPI.createNetworking(line.getOptionValue("cidr"),line.getOptionValue("name"));
                        String serverId=NovaAPI.boot(line.getOptionValue("id"), flavor.getId(), ids.get("network"), line.getOptionValue("name"), true);
                        ids.put("server", serverId);
                        NovaAPI.printServerDetails(NovaAPI.getServer(serverId));
                    }catch(Exception e){
                        NeutronAPI.deleteNetworking(ids);
                    }
                }catch(ParseException e){
                    System.out.println(e.getMessage()); 
                    OspPrintWriter.printHelp(args[0]+" "+args[1], serverBootVolumeCustomOptions);
                }
            }
            break;
            case DELETE:
            {
                Options serverDeleteOptions=Osp4jShellCommonCommandOptions.getNovaDeleteHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(serverDeleteOptions, Arrays.copyOfRange(args, 2, args.length));
                    NovaAPI.delete(line.getOptionValue("id"));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], serverDeleteOptions);
                }
            }
            break;
            case STATUS:
            {
                Options serverStatusOptions=Osp4jShellCommonCommandOptions.getNovaStatusHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(serverStatusOptions, Arrays.copyOfRange(args, 2, args.length));
                    NovaAPI.status(line.getOptionValue("id"));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], serverStatusOptions);
                }
            }
            break;
            case SNAPSHOT:
            {
                Options serverSnapshotOptions=Osp4jShellCommonCommandOptions.getNovaSnapshotHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(serverSnapshotOptions, Arrays.copyOfRange(args, 2, args.length));
                    String imageId=NovaAPI.createSnapshot(line.getOptionValue("id"), line.getOptionValue("name"));
                    GlanceAPI.printImageDetails(GlanceAPI.getImageDetail(imageId));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], serverSnapshotOptions);
                }
            }
            break;
            case LIST:
            {
                NovaAPI.printServersDetails(NovaAPI.listServers());
            }
            break;
            case SHOW:
            {
                Options serverShowOptions=Osp4jShellCommonCommandOptions.getNovaShowHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(serverShowOptions, Arrays.copyOfRange(args, 2, args.length));
                    NovaAPI.printServerDetails(NovaAPI.getServer(line.getOptionValue("id")));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], serverShowOptions);
                }
            }
            break;
            case NULL:;
            case HELP:
            {
                OspPrintWriter.printHelp(args[0], Osp4jShellCommonCommandOptions.getNeutronHelpOptions());
            }
            break;
            default:
                throw new Exception("Invalid Command!"); 
        }        
    }
    

}