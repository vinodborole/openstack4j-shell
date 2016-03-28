package com.vinodborole.openstack4j.app.commands;

import java.util.List;

import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.Router;
import org.openstack4j.model.network.Subnet;

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
                case NET_CREATE_DEFAULT:
                {
                    Network network = NeutronAPI.createNetDefault(params.get(2));
                    NeutronAPI.printNetDetails(network);
                }
                break;
                case NET_SHOW:
                {
                    Network network=NeutronAPI.getNetDetails(params.get(2));
                    NeutronAPI.printNetDetails(network);
                }
                break;
                case NET_CREATE:
                {
                    Network network=NeutronAPI.createNetwork(params.get(2));
                    NeutronAPI.printNetDetails(network);
                }
                break;
                case NET_DELETE:
                {
                    NeutronAPI.deleteNetwork(params.get(2));
                }
                break;
                case ROUTER_CREATE:
                {
                    NeutronAPI.printRouterDetails(NeutronAPI.createRouter(params.get(2)));
                }
                break;
                case ROUTER_DELETE:
                {
                    NeutronAPI.deleteRouter(params.get(2));
                }
                break;
                case ROUTER_LIST:
                {
                    NeutronAPI.printRouterDetails(NeutronAPI.getAllRouters());
                }
                break;
                case ROUTER_SHOW:
                {
                    NeutronAPI.printRouterDetails(NeutronAPI.getRouterDetails(params.get(2)));
                }
                break;
                case ROUTER_INTERFACE_ADD :
                {
                    NeutronAPI.addSubnetInterfaceToRouter(params.get(2), params.get(3));
                }
                break;
                case ROUTER_INTERFACE_DELETE:
                {
                    NeutronAPI.deleteSubnetInterfaces(params.get(2), params.get(3));
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