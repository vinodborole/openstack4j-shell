/**
 * @author viborole
 */
package com.vinodborole.openstack4j.app.commands.factory;

/**
 * Common interface implemented by concrete command classes.
 *  
 * @author vinod borole
 */
public interface IOsp4jShellCommands {

    public void executeCommand(String[] args) throws Exception;
    
}
 