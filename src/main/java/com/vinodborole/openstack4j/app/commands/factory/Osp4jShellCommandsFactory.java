package com.vinodborole.openstack4j.app.commands.factory;

import com.vinodborole.openstack4j.app.Osp4jSession;
import com.vinodborole.openstack4j.app.ShellContext;
import com.vinodborole.openstack4j.app.commands.Commands;
import com.vinodborole.openstack4j.app.commands.Osp4jShellCinderCommands;
import com.vinodborole.openstack4j.app.commands.Osp4jShellCommanCommands;
import com.vinodborole.openstack4j.app.commands.Osp4jShellGlanceCommands;
import com.vinodborole.openstack4j.app.commands.Osp4jShellNeutronCommands;
import com.vinodborole.openstack4j.app.commands.Osp4jShellNovaCommands;

public class Osp4jShellCommandsFactory {

    private static final Osp4jShellCommandsFactory INSTANCE = new Osp4jShellCommandsFactory();
    private Osp4jShellCommandsFactory(){}
    public static Osp4jShellCommandsFactory getInstance(){
        return INSTANCE;
    }   
    
    public IOsp4jShellCommands getShellCommandExecutor(Commands command){
        
        switch(command!=null?command:command.NULL){
            case SOURCE:
            {
                return Osp4jShellCommanCommands.getInstance();
            }
            case PRINT:
            {
                return Osp4jShellCommanCommands.getInstance();
            }
            case NOVA:
            {
                return Osp4jShellNovaCommands.getInstance();
            }
            case NEUTRON:
            {
                return Osp4jShellNeutronCommands.getInstance();
            }
            case CINDER:
            {
                return Osp4jShellCinderCommands.getInstance();
            }
            case GLANCE:
            {
                return Osp4jShellGlanceCommands.getInstance();
            }
            case DELETE:
            {
                return Osp4jShellCommanCommands.getInstance();
            }
            case FLUSH:
            {
                return Osp4jShellCommanCommands.getInstance();
            }
            case LOGGING_YES:
            {
                return Osp4jShellCommanCommands.getInstance();
            }
            case LOGGING_NO:
            {
                return Osp4jShellCommanCommands.getInstance();
            }
            case HELP:
            {
                return Osp4jShellCommanCommands.getInstance();
            }
            default:
                System.err.println("Invaid command");
        }
        
        return null;
    }
}
