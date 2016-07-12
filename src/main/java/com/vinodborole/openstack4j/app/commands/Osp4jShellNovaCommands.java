package com.vinodborole.openstack4j.app.commands;

import java.util.Arrays;
import java.util.HashMap;
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
    
    @Override
    public void executeCommand(String[] args) throws Exception {
        Commands subcommand=Commands.fromString(args.length > 1 ? args[1]:null);
        switch(subcommand!=null?subcommand:Commands.NULL){
            case START:
            {
                Options serverStartOptions=Osp4jShellCommonCommandOptions.getNovaStartHelpOptions();
                try{
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],serverStartOptions)){
                        CommandLine line = subCommandParser.parse(serverStartOptions, Arrays.copyOfRange(args, 2, args.length));
                        ActionResponse response = NovaAPI.startVM(line.getOptionValue(Commands.Arguments.ID.getArgString()));
                        if(response.isSuccess()){
                            NovaAPI.printServerDetails(NovaAPI.getServer(line.getOptionValue(Commands.Arguments.ID.getArgString())));
                        }else{
                            System.err.println("Failure!"); 
                        }
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
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],serverStopOptions)){
                        CommandLine line = subCommandParser.parse(serverStopOptions, Arrays.copyOfRange(args, 2, args.length));
                        boolean response = NovaAPI.stopVM(line.getOptionValue(Commands.Arguments.ID.getArgString()));
                        if(response){
                            NovaAPI.printServerDetails(NovaAPI.getServer(line.getOptionValue(Commands.Arguments.ID.getArgString())));
                        }else{
                            System.err.println("Failure!");
                        }
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
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],serverRestartOptions)){
                        CommandLine line = subCommandParser.parse(serverRestartOptions, Arrays.copyOfRange(args, 2, args.length));
                        ActionResponse response = NovaAPI.restartVM(line.getOptionValue(Commands.Arguments.ID.getArgString()));
                        if(response.isSuccess()){
                            NovaAPI.printServerDetails(NovaAPI.getServer(line.getOptionValue(Commands.Arguments.ID.getArgString())));
                        }else{
                            System.err.println("Failure!");
                        }
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
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],serverDownloadOptions)){
                        CommandLine line = subCommandParser.parse(serverDownloadOptions, Arrays.copyOfRange(args, 2, args.length));
                        boolean response=NovaAPI.downloadVM(line.getOptionValue(Commands.Arguments.ID.getArgString()),line.getOptionValue(Commands.Arguments.FILE.getArgString()),line.getOptionValue(Commands.Arguments.NAME.getArgString()));
                        System.out.println(response);
                    }
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
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],serverBootOptions)){
                        CommandLine line = subCommandParser.parse(serverBootOptions, Arrays.copyOfRange(args, 2, args.length));
                        String serverId= NovaAPI.boot(line.getOptionValue(Commands.Arguments.ID.getArgString()),line.getOptionValue(Commands.Arguments.FLAVORID.getArgString()),line.getOptionValue(Commands.Arguments.NETID.getArgString()),line.getOptionValue(Commands.Arguments.NAME.getArgString()),false);
                        NovaAPI.printServerDetails(NovaAPI.getServer(serverId));
                    }
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
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],serverBootOptions)){
                        CommandLine line = subCommandParser.parse(serverBootOptions, Arrays.copyOfRange(args, 2, args.length));
                        String serverId = NovaAPI.boot(line.getOptionValue(Commands.Arguments.ID.getArgString()),line.getOptionValue(Commands.Arguments.FLAVORID.getArgString()),line.getOptionValue(Commands.Arguments.NETID.getArgString()),line.getOptionValue(Commands.Arguments.NAME.getArgString()),true);
                    }
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
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],serverBootDefaultOptions)){
                        CommandLine line = subCommandParser.parse(serverBootDefaultOptions, Arrays.copyOfRange(args, 2, args.length));
                        String serverId=NovaAPI.bootdefault(line.getOptionValue(Commands.Arguments.ID.getArgString()),line.getOptionValue(Commands.Arguments.NAME.getArgString()));
                        NovaAPI.printServerDetails(NovaAPI.getServer(serverId));
                    }
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
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],serverBootVolumeDefaultOptions)){
                        CommandLine line = subCommandParser.parse(serverBootVolumeDefaultOptions, Arrays.copyOfRange(args, 2, args.length));
                        String serverId=NovaAPI.bootvolumedefault(line.getOptionValue(Commands.Arguments.ID.getArgString()),line.getOptionValue(Commands.Arguments.NAME.getArgString()));
                        NovaAPI.printServerDetails(NovaAPI.getServer(serverId));
                    }
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
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],serverBootCustomOptions)){
                        CommandLine line = subCommandParser.parse(serverBootCustomOptions, Arrays.copyOfRange(args, 2, args.length));
                        Map<String, String> ids = new HashMap<String,String>();
                        try{
                            Flavor flavor=NovaAPI.getFlavor(Integer.parseInt(line.getOptionValue(Commands.Arguments.DISK.getArgString())), Integer.parseInt(line.getOptionValue(Commands.Arguments.RAM.getArgString())), Integer.parseInt(line.getOptionValue(Commands.Arguments.VCPU.getArgString())));
                            ids=NeutronAPI.createNetworking(line.getOptionValue(Commands.Arguments.CIDR.getArgString()),line.getOptionValue(Commands.Arguments.NAME.getArgString()));
                            String serverId=NovaAPI.boot(line.getOptionValue(Commands.Arguments.ID.getArgString()), flavor.getId(), ids.get("network"), line.getOptionValue(Commands.Arguments.NAME.getArgString()), false);
                            ids.put("server", serverId);
                            NovaAPI.printServerDetails(NovaAPI.getServer(serverId));
                        }catch(Exception e){
                            NeutronAPI.deleteNetworking(ids);
                        }
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
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],serverBootVolumeCustomOptions)){
                        CommandLine line = subCommandParser.parse(serverBootVolumeCustomOptions, Arrays.copyOfRange(args, 2, args.length));
                        Map<String, String> ids = new HashMap<String,String>();
                        try{
                            Flavor flavor=NovaAPI.getFlavor(50, 2048, 1);
                            ids=NeutronAPI.createNetworking(line.getOptionValue(Commands.Arguments.CIDR.getArgString()),line.getOptionValue(Commands.Arguments.NAME.getArgString()));
                            String serverId=NovaAPI.boot(line.getOptionValue(Commands.Arguments.ID.getArgString()), flavor.getId(), ids.get("network"), line.getOptionValue(Commands.Arguments.NAME.getArgString()), true);
                            ids.put("server", serverId);
                            NovaAPI.printServerDetails(NovaAPI.getServer(serverId));
                        }catch(Exception e){
                            NeutronAPI.deleteNetworking(ids);
                        }
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
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],serverDeleteOptions)){
                        CommandLine line = subCommandParser.parse(serverDeleteOptions, Arrays.copyOfRange(args, 2, args.length));
                        NovaAPI.delete(line.getOptionValue(Commands.Arguments.ID.getArgString()));
                    }
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
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],serverStatusOptions)){
                        CommandLine line = subCommandParser.parse(serverStatusOptions, Arrays.copyOfRange(args, 2, args.length));
                        NovaAPI.status(line.getOptionValue(Commands.Arguments.ID.getArgString()));
                    }
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
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],serverSnapshotOptions)){
                        CommandLine line = subCommandParser.parse(serverSnapshotOptions, Arrays.copyOfRange(args, 2, args.length));
                        String imageId=NovaAPI.createSnapshot(line.getOptionValue(Commands.Arguments.ID.getArgString()), line.getOptionValue(Commands.Arguments.NAME.getArgString()));
                        GlanceAPI.printImageDetails(GlanceAPI.getImageDetail(imageId));
                    }
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
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],serverShowOptions)){
                        CommandLine line = subCommandParser.parse(serverShowOptions, Arrays.copyOfRange(args, 2, args.length));
                        NovaAPI.printServerDetails(NovaAPI.getServer(line.getOptionValue(Commands.Arguments.ID.getArgString())));
                    }
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], serverShowOptions);
                }
            }
            break;
            case NULL:;
            case HELP:
            {
                OspPrintWriter.printHelp(args[0], Osp4jShellCommonCommandOptions.getNovaHelpOptions());
            }
            break;
            default:
                throw new Exception("Invalid Command!"); 
        }        
    }
    

}