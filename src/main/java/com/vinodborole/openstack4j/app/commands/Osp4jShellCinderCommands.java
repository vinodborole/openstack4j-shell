package com.vinodborole.openstack4j.app.commands;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.vinodborole.openstack4j.app.api.CinderAPI;
import com.vinodborole.openstack4j.app.commands.factory.IOsp4jShellCommands;
import com.vinodborole.openstack4j.app.utils.Osp4jShellCommmandHelpInfo;
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
    public void executeCommand(Commands command, List<String> params) throws Exception{
        switch(command!=null?command:Commands.NULL){
            case CINDER:
            {
                Commands subcommand=Commands.fromString(params.get(1));
                switch(subcommand!=null?subcommand:Commands.NULL){
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
                        Osp4jShellCommmandHelpInfo.cinderHelp();
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
            case CREATE:
            {
                Options volumeCreateOptions=Osp4jShellCommonCommandOptions.getCinderCreateHelpOptions();
                try{
                    CommandLine line = subCommandParser.parse(volumeCreateOptions, Arrays.copyOfRange(args, 2, args.length));
                    CinderAPI.createVolume(Integer.valueOf(line.getOptionValue("size")), line.getOptionValue("name"));
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
                    CommandLine line = subCommandParser.parse(volumeCreateFromImageOptions, Arrays.copyOfRange(args, 2, args.length));
                    CinderAPI.createVolumeFromImage(line.getOptionValue("id"), Integer.valueOf(line.getOptionValue("size")), line.getOptionValue("name"));
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
                    CommandLine line = subCommandParser.parse(volumeCreateFromSnapshotOptions, Arrays.copyOfRange(args, 2, args.length));
                    CinderAPI.createVolumeFromVolumeSnap(line.getOptionValue("id"), Integer.valueOf(line.getOptionValue("size")), line.getOptionValue("name"));
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
                    CommandLine line = subCommandParser.parse(volumeShowOptions, Arrays.copyOfRange(args, 2, args.length));
                    CinderAPI.show(line.getOptionValue("id"));
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
                    CommandLine line = subCommandParser.parse(volumeDeleteOptions, Arrays.copyOfRange(args, 2, args.length));
                    boolean isVolDeleted = CinderAPI.deleteVolume(line.getOptionValue("id"));
                    System.out.println("Result: "+isVolDeleted);
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
                    CommandLine line = subCommandParser.parse(volumeToImageOptions, Arrays.copyOfRange(args, 2, args.length));
                    CinderAPI.uploadVolumeToImage(line.getOptionValue("id"), line.getOptionValue("name"));
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
