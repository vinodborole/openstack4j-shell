package com.vinodborole.openstack4j.app.commands;

import java.util.List;

import org.openstack4j.model.compute.ActionResponse;

import com.vinodborole.openstack4j.app.api.GlanceAPI;
import com.vinodborole.openstack4j.app.api.NovaAPI;
import com.vinodborole.openstack4j.app.commands.factory.IOsp4jShellCommands;
import com.vinodborole.openstack4j.app.utils.Osp4jShellCommmandHelpInfo;

public class Osp4jShellNovaCommands implements IOsp4jShellCommands{

    private static final Osp4jShellNovaCommands INSTANCE = new Osp4jShellNovaCommands();
    private Osp4jShellNovaCommands(){}
    public static Osp4jShellNovaCommands getInstance(){
        return INSTANCE;
    }   
    
    @Override
    public void executeCommand(Commands command, List<String> params) throws Exception {
        switch(command!=null?command:command.NULL){
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
            }
         }
     }
        
    }

}