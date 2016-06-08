package com.vinodborole.openstack4j.app.commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
/**
 * Common Shell options used in openstack cloud commands
 *  
 * @author vinod borole
 */
public class Osp4jShellCommonCommandOptions {
    public static Option nameOption(String desc, boolean required) {
        return Option.builder("name").hasArg(true).required(required).type(String.class).numberOfArgs(1).longOpt("name").argName("name").desc(desc).build();     
    }
    public static Option idOption(String desc, boolean required) {
        return Option.builder("id").hasArg(true).required(required).longOpt("id").type(String.class).numberOfArgs(1).argName("id").desc(desc).build();     
    }
    public static Option fileOption(String desc, boolean required) {
        return Option.builder("file").hasArg(true).required(required).longOpt("file").type(String.class).numberOfArgs(1).argName("file").desc(desc).build();     
    }
    public static Option flavorOption(String desc, boolean required) {
        return Option.builder("falvorid").hasArg(true).required(required).longOpt("falvorid").type(String.class).numberOfArgs(1).argName("falvorid").desc(desc).build();     
    }
    public static Option netIdOption(String desc, boolean required) {
        return Option.builder("netid").hasArg(true).required(required).longOpt("netid").type(String.class).numberOfArgs(1).argName("netid").desc(desc).build();     
    }
    public static Option vcpuOption(String desc, boolean required) {
        return Option.builder("vcpu").hasArg(true).required(required).longOpt("vcpu").type(Integer.class).numberOfArgs(1).argName("vcpu").desc(desc).build();     
    }
    public static Option ramOption(String desc, boolean required) {
        return Option.builder("ram").hasArg(true).required(required).longOpt("ram").type(Integer.class).numberOfArgs(1).argName("ram").desc(desc).build();     
    }
    public static Option diskOption(String desc, boolean required) {
        return Option.builder("disk").hasArg(true).required(required).longOpt("disk").type(Integer.class).numberOfArgs(1).argName("disk").desc(desc).build();     
    }
    public static Option cidrOption(String desc, boolean required) {
        return Option.builder("cidr").hasArg(true).required(required).longOpt("cidr").type(String.class).numberOfArgs(1).argName("cidr").desc(desc).build();     
    }    
    public static Option sizeOption(String desc, boolean required) {
        return Option.builder("size").hasArg(true).required(required).longOpt("size").type(Integer.class).numberOfArgs(1).argName("size").desc(desc).build();     
    }   
    public static Options glanceOptions() {
        Options options = new Options();
        options.addOption(Option.builder("imageCreate").hasArg(true).longOpt("image-create").argName("image-create").desc("Create a new image.").build());
        options.addOption(Option.builder("imageList").hasArg(false).longOpt("image-list").argName("image-list").desc("List images you can access.").build());
        options.addOption(Option.builder("imageDowload").hasArg(true).longOpt("image-download").argName("image-download").desc("Download a specific image.").build());
        return options;
    }
    public static Options neutronOptions() {
        Options options = new Options();
        options.addOption(Option.builder("help").longOpt("help").argName("help").desc("Help for neutron commands.").build());
        options.addOption(Option.builder("netlist").hasArg(false).longOpt("net-list").argName("net-list").desc("List networks.").build());
        options.addOption(Option.builder("netshow").hasArg(true).longOpt("net-show").argName("net-show").desc("Show network details").build());
        options.addOption(Option.builder("netcreate").hasArg(true).longOpt("net-create").argName("net-create").desc("Create a new network").build());
        options.addOption(Option.builder("netdelete").hasArg(true).longOpt("net-delete").argName("net-delete").desc("Delete the network").build());
        options.addOption(Option.builder("netcreatedefault").hasArg(true).longOpt("net-create-default").argName("net-create-default").desc("Create network, subnet and router with default settings").build());
        options.addOption(Option.builder("routerlist").hasArg(false).longOpt("router-list").argName("router-list").desc("List Routers.").build());
        options.addOption(Option.builder("routercreate").hasArg(true).longOpt("router-create").argName("router-create").desc("Create a  Routers.").build());
        options.addOption(Option.builder("routerdelete").hasArg(true).longOpt("router-delete").argName("router-delete").desc("Delete the Routers.").build());
        options.addOption(Option.builder("routershow").hasArg(true).longOpt("router-show").argName("router-show").desc("Show router details.").build());
        options.addOption(Option.builder("routerinterfaceadd").hasArg(true).longOpt("router-interface-add").argName("router-interface-add").desc("Create the router interface for the given subnet.").build());
        options.addOption(Option.builder("routerinterfacedelete").hasArg(true).longOpt("router-interface-delete").argName("router-interface-delete").desc("Delete the router interface of the given subnet.").build());
        return options;
    }
    public static Options novaOptions() {
        Options options = new Options();
        options.addOption(Option.builder("help").longOpt("help").argName("help").desc("Help for nova commands.").build());
        options.addOption(Option.builder("novastart").hasArg(true).longOpt("start").argName("start").desc("Star server").build());
        options.addOption(Option.builder("novastop").hasArg(true).longOpt("stop").argName("stop").desc("Stop server").build());
        options.addOption(Option.builder("novarestart").hasArg(true).longOpt("restart").argName("restart").desc("Restart server").build());
        options.addOption(Option.builder("novadownload").hasArg(true).longOpt("download").argName("download").desc("Download server").build());
        options.addOption(Option.builder("novaflavorlist").hasArg(false).longOpt("flavor-list").argName("flavor-list").desc("List Flavor.").build());
        options.addOption(Option.builder("novaboot").hasArg(true).longOpt("boot").argName("boot").desc("Create server").build());
        options.addOption(Option.builder("novabootvolume").hasArg(true).longOpt("boot-volume").argName("boot-volume").desc("Create server from volume").build());
        options.addOption(Option.builder("novabootdefault").hasArg(true).longOpt("boot-default").argName("boot-default").desc("Create server with default settings").build());
        options.addOption(Option.builder("novabootvolumedefault").hasArg(true).longOpt("boot-volume-default").argName("boot-volume-default").desc("Create server from volume with default settings").build());
        options.addOption(Option.builder("novabootcustom").hasArg(true).longOpt("boot-custom").argName("boot-custom").desc("Create server from custom settings").build());
        options.addOption(Option.builder("novabootvolumecustom").hasArg(true).longOpt("boot-volume-custom").argName("boot-volume-custom").desc("Create server from volume with custom settings").build());
        options.addOption(Option.builder("novadelete").hasArg(true).longOpt("delete").argName("delete").desc("Delete server").build());
        options.addOption(Option.builder("novastatus").hasArg(true).longOpt("status").argName("status").desc("Get server status").build());
        options.addOption(Option.builder("novasnapshot").hasArg(true).longOpt("snapshot").argName("snapshot").desc("Create snapshot for server").build());
        options.addOption(Option.builder("novaflavorlist").hasArg(false).longOpt("list").argName("list").desc("List Servers.").build());
        options.addOption(Option.builder("novashow").hasArg(true).longOpt("show").argName("show").desc("Show server details").build());
        return options;
    }


    
    
}
