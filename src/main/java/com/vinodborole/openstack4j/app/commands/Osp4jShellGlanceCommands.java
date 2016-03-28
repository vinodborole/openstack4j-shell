package com.vinodborole.openstack4j.app.commands;

import java.util.List;

import org.openstack4j.model.image.Image;

import com.vinodborole.openstack4j.app.api.CommonAPI;
import com.vinodborole.openstack4j.app.api.GlanceAPI;
import com.vinodborole.openstack4j.app.commands.factory.IOsp4jShellCommands;
import com.vinodborole.openstack4j.app.utils.Osp4jShellCommmandHelpInfo;

public class Osp4jShellGlanceCommands implements IOsp4jShellCommands{

    private static final Osp4jShellGlanceCommands INSTANCE = new Osp4jShellGlanceCommands();
    private Osp4jShellGlanceCommands(){}
    public static Osp4jShellGlanceCommands getInstance(){
        return INSTANCE;
    }   
    
    @Override
    public void executeCommand(Commands command, List<String> params) throws Exception{
        switch(command!=null?command:command.NULL){
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
                        Osp4jShellCommmandHelpInfo.glanceHelp();
                    }
                    break;
                    case NULL:
                        System.err.println("Invaid command");
                }
            }
        }
        
    }

}