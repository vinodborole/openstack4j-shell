/**
 * @author viborole
 */
package com.vinodborole.openstack4j.app.commands.factory;

import java.util.List;

import com.vinodborole.openstack4j.app.commands.Commands;
/**
 * Common interface implemented by concrete command classes.
 *  
 * @author vinod borole
 */
public interface IOsp4jShellCommands {

    @Deprecated
    public void executeCommand(Commands command, List<String> params) throws Exception;
    
    public void executeCommand(String[] args) throws Exception;
    
}
 