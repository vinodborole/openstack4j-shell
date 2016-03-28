package com.vinodborole.openstack4j.app.commands;

import java.util.List;

import com.vinodborole.openstack4j.app.api.NeutronAPI;
import com.vinodborole.openstack4j.app.commands.factory.IOsp4jShellCommands;
import com.vinodborole.openstack4j.app.utils.Osp4jShellCommmandHelpInfo;

public class Osp4jShellNeutronCommands implements IOsp4jShellCommands{

    private static final Osp4jShellNeutronCommands INSTANCE = new Osp4jShellNeutronCommands();
    private Osp4jShellNeutronCommands(){}
    public static Osp4jShellNeutronCommands getInstance(){
        return INSTANCE;
    }   
    
    @Override
    public void executeCommand(Commands command, List<String> params) throws Exception{

        switch(command!=null?command:command.NULL){
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
                    Osp4jShellCommmandHelpInfo.neutronHelp();
                }
                break;
                case NULL:
                    System.err.println("Invaid command"); 
            }
        }
     }
        
    }

}