package com.vinodborole.openstack4j.app.commands.factory;

import com.vinodborole.openstack4j.app.commands.Commands;
import com.vinodborole.openstack4j.app.commands.Osp4jShellCinderCommands;
import com.vinodborole.openstack4j.app.commands.Osp4jShellCommonCommands;
import com.vinodborole.openstack4j.app.commands.Osp4jShellGlanceCommands;
import com.vinodborole.openstack4j.app.commands.Osp4jShellNeutronCommands;
import com.vinodborole.openstack4j.app.commands.Osp4jShellNovaCommands;
/**
 * Factory Shell command is responsible to instantiate all command classes. 
 *  
 * @author vinod borole
 */
public class Osp4jShellCommandsFactory {

    private static final Osp4jShellCommandsFactory INSTANCE = new Osp4jShellCommandsFactory();
    private Osp4jShellCommandsFactory(){}
    public static Osp4jShellCommandsFactory getInstance(){
        return INSTANCE;
    }    
    
    public IOsp4jShellCommands getShellCommandExecutor(Commands command) throws Exception{
        
        switch(command!=null?command:command.NULL){
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
            case SOURCE:
            case PRINT:
            case DELETE:
            case FLUSH:
            case LOGGING_YES:
            case LOGGING_NO:
            case HELP:
                return Osp4jShellCommonCommands.getInstance();
            default:
                throw new Exception("Invalid Command!"); 
        }
    }
}
