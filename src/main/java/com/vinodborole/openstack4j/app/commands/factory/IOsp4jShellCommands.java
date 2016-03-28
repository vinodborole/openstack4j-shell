/**
 * @author viborole
 */
package com.vinodborole.openstack4j.app.commands.factory;

import java.util.List;

import com.vinodborole.openstack4j.app.commands.Commands;

public interface IOsp4jShellCommands {

    public void executeCommand(Commands command, List<String> params) throws Exception;
}
