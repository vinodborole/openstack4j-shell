package com.vinodborole.openstack4j.app.commands;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.vinodborole.openstack4j.app.api.CinderAPI;
import com.vinodborole.openstack4j.app.commands.factory.IOsp4jShellCommands;
import com.vinodborole.openstack4j.app.utils.Osp4jShellCommmandHelpInfo;
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
                Options volumeCreateOptions=getVolumeCreateOptions();
                try{
                    CommandLine line = subCommandParser.parse(volumeCreateOptions, Arrays.copyOfRange(args, 2, args.length));
                    CinderAPI.createVolume(Integer.valueOf(line.getOptionValue("size")), line.getOptionValue("name"));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    subCommandhelpFormatter.printHelp(args[0]+" "+args[1], volumeCreateOptions);
                }
            }
            break;
            case CREATE_FROM_IMAGE:
            {
                Options volumeCreateFromImageOptions=getVolumeCreateFromImageOptions();
                try{
                    CommandLine line = subCommandParser.parse(volumeCreateFromImageOptions, Arrays.copyOfRange(args, 2, args.length));
                    CinderAPI.createVolumeFromImage(line.getOptionValue("id"), Integer.valueOf(line.getOptionValue("size")), line.getOptionValue("name"));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    subCommandhelpFormatter.printHelp(args[0]+" "+args[1], volumeCreateFromImageOptions);
                }
            }
            break;
            case CREATE_FROM_VOLUME_SNAPSHOT:
            {
                Options volumeCreateFromSnapshotOptions=getVolumeCreateFromSnapshotOptions();
                try{
                    CommandLine line = subCommandParser.parse(volumeCreateFromSnapshotOptions, Arrays.copyOfRange(args, 2, args.length));
                    CinderAPI.createVolumeFromVolumeSnap(line.getOptionValue("id"), Integer.valueOf(line.getOptionValue("size")), line.getOptionValue("name"));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    subCommandhelpFormatter.printHelp(args[0]+" "+args[1], volumeCreateFromSnapshotOptions);
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
                Options volumeShowOptions=getVolumeShowOptions();
                try{
                    CommandLine line = subCommandParser.parse(volumeShowOptions, Arrays.copyOfRange(args, 2, args.length));
                    CinderAPI.show(line.getOptionValue("id"));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    subCommandhelpFormatter.printHelp(args[0]+" "+args[1], volumeShowOptions);
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
                Options volumeDeleteOptions=getVolumeDeleteOptions();
                try{
                    CommandLine line = subCommandParser.parse(volumeDeleteOptions, Arrays.copyOfRange(args, 2, args.length));
                    boolean isVolDeleted = CinderAPI.deleteVolume(line.getOptionValue("id"));
                    System.out.println("Result: "+isVolDeleted);
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    subCommandhelpFormatter.printHelp(args[0]+" "+args[1], volumeDeleteOptions);
                }
           }
            break;
            case UPLOAD_TO_IMAGE:
            {
                Options volumeToImageOptions=getVolumeToImageOptions();
                try{
                    CommandLine line = subCommandParser.parse(volumeToImageOptions, Arrays.copyOfRange(args, 2, args.length));
                    CinderAPI.uploadVolumeToImage(line.getOptionValue("id"), line.getOptionValue("name"));
                }catch(ParseException e){
                    System.out.println(e.getMessage());
                    subCommandhelpFormatter.printHelp(args[0]+" "+args[1], volumeToImageOptions);
                }
            }
            break;
            case NULL:;
            case HELP:
            {
                subCommandhelpFormatter.printHelp(args[0], getHelpOptions());
            }
            break;
            default:
                break; 
        }        
    }
    private Options getHelpOptions() {
        Options options = new Options();
        options.addOption(Option.builder("help").longOpt("help").argName("help").desc("Help for cinder commands.").build());
        options.addOption(Option.builder("cinderlist").hasArg(false).longOpt("list").argName("list").desc("List volumes.").build());
        options.addOption(Option.builder("cindershow").hasArg(true).longOpt("show").argName("show").desc("Show volume details").build());
        options.addOption(Option.builder("createfromvolumesnapshot").hasArg(true).longOpt("create-from-volume-snapshot").argName("create-from-volume-snapshot").desc("Create volume from snapshot").build());
        options.addOption(Option.builder("createfromimage").hasArg(true).longOpt("create-from-image").argName("create-from-image").desc("Create volume from Image").build());
        options.addOption(Option.builder("volumeattach").hasArg(true).longOpt("volume-attach").argName("volume-attach").desc("Attach volume").build());
        options.addOption(Option.builder("volumedettach").hasArg(true).longOpt("volume-dettach").argName("volume-dettach").desc("Dettach volume").build());
        options.addOption(Option.builder("volumedelete").hasArg(true).longOpt("delete").argName("delete").desc("Delete volume").build());
        options.addOption(Option.builder("volumeuploadtoimage").hasArg(true).longOpt("upload-to-image").argName("upload-to-image").desc("Upload volume to image").build());
        return options;
    }
    private Options getVolumeToImageOptions() {
        Options options = new Options();
        options.addOption(Osp4jShellCommonCommandOptions.idOption("An indentifier for the volume",true));
        options.addOption(Osp4jShellCommonCommandOptions.nameOption("A name for the volume",true));
        return options;
    }
    private Options getVolumeDeleteOptions() {
        Options options = new Options();
        options.addOption(Osp4jShellCommonCommandOptions.idOption("An indentifier for the volume",true));
        return options;
    }
    private Options getVolumeShowOptions() {
        Options options = new Options();
        options.addOption(Osp4jShellCommonCommandOptions.idOption("An indentifier for the volume",true));
        return options;
    }
    private Options getVolumeCreateFromSnapshotOptions() {
        Options options = new Options();
        options.addOption(Osp4jShellCommonCommandOptions.nameOption("A name for the volume",true));
        options.addOption(Osp4jShellCommonCommandOptions.sizeOption("Expected Volume size",true));
        options.addOption(Osp4jShellCommonCommandOptions.idOption("An indentifier for the snapshot",true));
        return options;
    }
    private Options getVolumeCreateFromImageOptions() {
        Options options = new Options();
        options.addOption(Osp4jShellCommonCommandOptions.nameOption("A name for the volume",true));
        options.addOption(Osp4jShellCommonCommandOptions.sizeOption("Expected Volume size",true));
        options.addOption(Osp4jShellCommonCommandOptions.idOption("An indentifier for the image",true));
        return options;
    }
    private Options getVolumeCreateOptions() {
        Options options = new Options();
        options.addOption(Osp4jShellCommonCommandOptions.sizeOption("Expected Volume size",true));
        options.addOption(Osp4jShellCommonCommandOptions.nameOption("A name for the volume",true));
        return options;
    }

}
