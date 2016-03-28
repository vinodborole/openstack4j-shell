package com.vinodborole.openstack4j.app.commands;

import java.util.List;

import com.vinodborole.openstack4j.app.api.CinderAPI;
import com.vinodborole.openstack4j.app.commands.factory.IOsp4jShellCommands;
import com.vinodborole.openstack4j.app.utils.Osp4jShellCommmandHelpInfo;

public class Osp4jShellCinderCommands implements IOsp4jShellCommands{

    private static final Osp4jShellCinderCommands INSTANCE = new Osp4jShellCinderCommands();
    private Osp4jShellCinderCommands(){}
    public static Osp4jShellCinderCommands getInstance(){
        return INSTANCE;
    }   
    
    @Override
    public void executeCommand(Commands command, List<String> params) throws Exception{
        switch(command!=null?command:command.NULL){
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
                        Osp4jShellCommmandHelpInfo.cinderHelp();
                    }
                    break;
                    case NULL:
                        System.err.println("Invaid command"); 
                }
            }
        }
        
    }

}
