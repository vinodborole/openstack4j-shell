package com.vinodborole.openstack4j.app.commands;

import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.vinodborole.openstack4j.app.api.CinderAPI;
import com.vinodborole.openstack4j.app.commands.factory.IOsp4jShellCommands;
import com.vinodborole.openstack4j.app.utils.OspPrintWriter;
/**
 * Responsible for executing cinder commands on Openstack cloud
 *  
 * @author vinod borole
 */
public class Osp4jShellCinderCommands extends Osp4jShellCommands implements IOsp4jShellCommands{

    private static final Osp4jShellCinderCommands INSTANCE = new Osp4jShellCinderCommands();
    private Osp4jShellCinderCommands(){}
    public static Osp4jShellCinderCommands getInstance(){
        return INSTANCE;
    }   
    
    @Override
    public void executeCommand(String[] args) throws Exception {
        Commands subcommand=Commands.fromString(args.length > 1 ? args[1]:null);
        switch(subcommand!=null?subcommand:Commands.NULL){
            case CREATE:
            {
                Options volumeCreateOptions=Osp4jShellCommonCommandOptions.getCinderCreateHelpOptions();
                try{
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],volumeCreateOptions)){
                        CommandLine line = subCommandParser.parse(volumeCreateOptions, Arrays.copyOfRange(args, 2, args.length));
                        CinderAPI.createVolume(Integer.valueOf(line.getOptionValue(Commands.Arguments.SIZE.getArgString())), line.getOptionValue(Commands.Arguments.NAME.getArgString()));
                    }
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1], volumeCreateOptions);
                }
            }
            break;
            case CREATE_FROM_IMAGE:
            {
                Options volumeCreateFromImageOptions=Osp4jShellCommonCommandOptions.getCinderCreateFromImageHelpOptions();
                try{
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],volumeCreateFromImageOptions)){
                        CommandLine line = subCommandParser.parse(volumeCreateFromImageOptions, Arrays.copyOfRange(args, 2, args.length));
                        CinderAPI.createVolumeFromImage(line.getOptionValue(Commands.Arguments.ID.getArgString()), Integer.valueOf(line.getOptionValue(Commands.Arguments.SIZE.getArgString())), line.getOptionValue(Commands.Arguments.NAME.getArgString()));
                    }
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1],  volumeCreateFromImageOptions);
                }
            }
            break;
            case CREATE_FROM_VOLUME_SNAPSHOT:
            {
                Options volumeCreateFromSnapshotOptions=Osp4jShellCommonCommandOptions.getCinderCreateFromSnapshotHelpOptions();
                try{
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],volumeCreateFromSnapshotOptions)){
                        CommandLine line = subCommandParser.parse(volumeCreateFromSnapshotOptions, Arrays.copyOfRange(args, 2, args.length));
                        CinderAPI.createVolumeFromVolumeSnap(line.getOptionValue(Commands.Arguments.ID.getArgString()), Integer.valueOf(line.getOptionValue(Commands.Arguments.SIZE.getArgString())), line.getOptionValue(Commands.Arguments.NAME.getArgString()));
                    }
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1],  volumeCreateFromSnapshotOptions);
                }
            }
            break;
            case LIST:
            {
                CinderAPI.listvolumes();
            }
            break;
            case SHOW:
            {
                Options volumeShowOptions=Osp4jShellCommonCommandOptions.getCinderShowHelpOptions();
                try{
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],volumeShowOptions)){
                        CommandLine line = subCommandParser.parse(volumeShowOptions, Arrays.copyOfRange(args, 2, args.length));
                        CinderAPI.show(line.getOptionValue(Commands.Arguments.ID.getArgString()));
                    }
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1],  volumeShowOptions);
                }
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
                Options volumeDeleteOptions=Osp4jShellCommonCommandOptions.getCinderDeleteHelpOptions();
                try{
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],volumeDeleteOptions)){
                        CommandLine line = subCommandParser.parse(volumeDeleteOptions, Arrays.copyOfRange(args, 2, args.length));
                        boolean isVolDeleted = CinderAPI.deleteVolume(line.getOptionValue(Commands.Arguments.ID.getArgString()));
                        System.out.println("Result: "+isVolDeleted);
                    }
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1],  volumeDeleteOptions);
                }
           }
            break;
            case UPLOAD_TO_IMAGE:
            {
                Options volumeToImageOptions=Osp4jShellCommonCommandOptions.getCinderUploadToImageHelpOptions();
                try{
                    if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],volumeToImageOptions)){
                        CommandLine line = subCommandParser.parse(volumeToImageOptions, Arrays.copyOfRange(args, 2, args.length));
                        CinderAPI.uploadVolumeToImage(line.getOptionValue(Commands.Arguments.ID.getArgString()), line.getOptionValue(Commands.Arguments.NAME.getArgString()));
                    }
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    OspPrintWriter.printHelp(args[0]+" "+args[1],  volumeToImageOptions);
                }
            }
            break;
            case NULL:;
            case HELP:
            {
                OspPrintWriter.printHelp(args[0], Osp4jShellCommonCommandOptions.getCinderHelpOptions());
            }
            break;
            default:
                throw new Exception("Invalid Command!"); 
        }        
    }
    
    
}
