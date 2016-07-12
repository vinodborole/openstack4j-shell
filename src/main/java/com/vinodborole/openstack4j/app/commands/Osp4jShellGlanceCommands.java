package com.vinodborole.openstack4j.app.commands;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.openstack4j.model.image.Image;

import com.vinodborole.openstack4j.app.api.CommonAPI;
import com.vinodborole.openstack4j.app.api.GlanceAPI;
import com.vinodborole.openstack4j.app.commands.factory.IOsp4jShellCommands;
import com.vinodborole.openstack4j.app.utils.OspPrintWriter;
/**
 * Responsible for executing glance commands on Openstack cloud
 *  
 * @author vinod borole
 */
public class Osp4jShellGlanceCommands extends Osp4jShellCommands implements IOsp4jShellCommands{

    private static final Osp4jShellGlanceCommands INSTANCE = new Osp4jShellGlanceCommands();
    private Osp4jShellGlanceCommands(){}
    public static Osp4jShellGlanceCommands getInstance(){
        return INSTANCE;
    }   
    
    @Override
    public void executeCommand(String[] args) throws Exception {
       Commands subcommand=Commands.fromString(args.length > 1 ? args[1]:null);
       switch(subcommand!=null?subcommand:Commands.NULL){
           case IMAGE_LIST:
           {
              List<? extends Image> images= GlanceAPI.imageList();
              GlanceAPI.printImagesDetails(images);
           }
           break;
           case IMAGE_CREATE:
           {
               Options imageCreateOptions=Osp4jShellCommonCommandOptions.getGlanceImageCreateHelpOptions();
               try{
                   if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],imageCreateOptions)){
                       CommandLine line = subCommandParser.parse(imageCreateOptions, Arrays.copyOfRange(args, 2, args.length));
                       Image image= GlanceAPI.imageCreate(line.getOptionValue(Commands.Arguments.FILE.getArgString()),line.getOptionValue(Commands.Arguments.NAME.getArgString()));
                       if(image!=null){
                           GlanceAPI.printImageDetails(image);
                       }else{
                           System.err.println("Upload failed");
                       }
                   }
               }catch(ParseException e){
                   OspPrintWriter.printHelp(args[0]+" "+args[1], imageCreateOptions);
               }
           }
           break;
           case IMAGE_DOWNLOAD:
           {
               Options imageDownloadOptions=Osp4jShellCommonCommandOptions.getGlanceImageDownloadHelpOptions();
               try{
                   if(!OspPrintWriter.isHelpRequested(args[0],args[1],args[2],imageDownloadOptions)){
                       CommandLine line = subCommandParser.parse(imageDownloadOptions, Arrays.copyOfRange(args, 2, args.length));
                       boolean status=CommonAPI.downloadImage(line.getOptionValue(Commands.Arguments.ID.getArgString()), line.getOptionValue(Commands.Arguments.FILE.getArgString()), line.getOptionValue(Commands.Arguments.NAME.getArgString()));
                       System.out.println("Download status: "+status); 
                   }
               }catch(ParseException e){
                   System.out.println(e.getMessage());
                   OspPrintWriter.printHelp(args[0]+" "+args[1], imageDownloadOptions);
               }
           }
           break;
           case NULL:;
           case HELP:
           { 
               OspPrintWriter.printHelp(args[0], Osp4jShellCommonCommandOptions.getGlanceHelpOptions());
           }
           break;
           default:
               throw new Exception("Invalid Command!"); 
       }
       
    }
}