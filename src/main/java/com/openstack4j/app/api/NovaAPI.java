package com.openstack4j.app.api;

import java.util.ArrayList;
import java.util.List;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.compute.FlavorService;
import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.ActionResponse;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.FloatingIP;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.Server.Status;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.model.compute.builder.ServerCreateBuilder;
import org.openstack4j.model.network.Network;
import org.openstack4j.openstack.compute.domain.NovaServer.Servers;

import com.openstack4j.app.Osp4jSession;
import com.openstack4j.app.utils.TableBuilder;

public class NovaAPI {

    protected enum NovaKey{
        NOVA_SERVERID;
    }
   
    public static ActionResponse startVM(String serverId){
        OSClient os=Osp4jSession.getOspSession();
        serverId=CommonAPI.takeFromMemory(NovaKey.NOVA_SERVERID,serverId);
        return os.compute().servers().action(serverId, Action.START);
    }

    public static boolean stopVM(String serverId){
        OSClient os=Osp4jSession.getOspSession();
        serverId=CommonAPI.takeFromMemory(NovaKey.NOVA_SERVERID,serverId);
        os.compute().servers().action(serverId, Action.STOP);
        return waitUntilServerSHUTOFF(os,serverId);
    }
    public static ActionResponse restartVM(String serverId){
        serverId=CommonAPI.takeFromMemory(NovaKey.NOVA_SERVERID,serverId);
        stopVM(serverId);
        return startVM(serverId);
    }
    public static boolean downloadVM(String serverId, String downloadLocation,String name){
        stopVM(serverId);
        String imageId=createSnapshot(serverId, name);
        boolean response= CommonAPI.downloadImage(imageId, downloadLocation, name);
        startVM(serverId);
        return response;
    }
    
    public static Server getServer(String serverId) {
        OSClient os=Osp4jSession.getOspSession();
        return os.compute().servers().get(serverId);
    }

    public static String createSnapshot(String serverId , String name){
        OSClient os=Osp4jSession.getOspSession();
        serverId=CommonAPI.takeFromMemory(NovaKey.NOVA_SERVERID,serverId);
        String imageId = os.compute().servers().createSnapshot(serverId, name);
        waitUntilImageACTIVE(os,imageId);
        return imageId;
    }
    private static void waitUntilImageACTIVE(OSClient os,String imageId) {
        System.out.println("wait until image is ACTIVE..");
        while(true){
            org.openstack4j.model.image.Image.Status status =  os.images().get(imageId).getStatus();
            System.out.println("current status: "+status.toString());
            if(status.equals(org.openstack4j.model.image.Image.Status.ACTIVE)){
                break;
            }else{
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }
        }
    }
    
    public static void listflavor(){
        OSClient os=Osp4jSession.getOspSession();
        FlavorService flavorService = os.compute().flavors();
        List<? extends Flavor> flavorList = flavorService.list();
        printFlavorsDetails(flavorList);
    }
    public static String boot(String imageId, String flavorId, String netId, String name) {
        System.out.println("booting vm...");
        OSClient os=Osp4jSession.getOspSession();
        List<String> netList = new ArrayList<String>();
        netList.add(netId);
        ServerCreateBuilder builder = Builders.server().name(name).image(imageId).flavor(flavorId).networks(netList);;
        ServerCreate serverOptions = builder.build();
        Server server = os.compute().servers().boot(serverOptions);
        System.out.println("saving to memory..");
        CommonAPI.addToMemory(NovaKey.NOVA_SERVERID, server.getId());
        waitUntilServerActive(os,server.getId()); 
        return server.getId();
    }
    private static void waitUntilServerActive(OSClient os,String serverId) {
        System.out.println("wait until ACTIVE..");
        while(true){
            Status status =  os.compute().servers().get(serverId).getStatus();
            System.out.println("current status: "+status.toString());
            if(status.equals(Status.ACTIVE)){
                break;
            }else{
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }
        }
    }
    private static boolean waitUntilServerSHUTOFF(OSClient os,String serverId) {
        System.out.println("wait until SHUTOFF..");
        while(true){
            Status status =  os.compute().servers().get(serverId).getStatus();
            System.out.println("current status: "+status.toString());
            if(status.equals(Status.SHUTOFF)){
                break;
            }else{
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }
        }
        return true;
    }
    public static ActionResponse delete(String serverId) {
        OSClient os=Osp4jSession.getOspSession();
        serverId=CommonAPI.takeFromMemory(NovaKey.NOVA_SERVERID,serverId);
        return os.compute().servers().delete(serverId);
    }
    public static void status(String serverId) {
        OSClient os=Osp4jSession.getOspSession();
        serverId=CommonAPI.takeFromMemory(NovaKey.NOVA_SERVERID,serverId);
        Server server=os.compute().servers().get(serverId);
        printServerDetails(server);
    }
    
    public static void printFlavorsDetails(List<? extends Flavor> flavors){
        TableBuilder tb=getTableBuilder("Flavor");
        for (final Flavor flavor : flavors) {
            addFlavorRow(tb, flavor);
        }
        System.out.println(tb.toString());
    }
    public static void printFlavorDetails(Flavor flavor){
        TableBuilder tb=getTableBuilder("Flavor");
        addFlavorRow(tb, flavor);
        System.out.println(tb.toString());
    }    
    public static void printServersDetails(List<? extends Server> servers){
        TableBuilder tb=getTableBuilder("Server");
        for (final Server server : servers ) {
            addServerRow(tb,server);
        }
        System.out.println(tb.toString());
    }

    public static void printServerDetails(Server server){
        TableBuilder tb = getTableBuilder("Server");
        addServerRow(tb,server);
        System.out.println(tb.toString());
    }
        
    private static TableBuilder getTableBuilder(String type) {
        TableBuilder tb = new TableBuilder();
        if(type.equals("Server")){
        tb.addRow("Server Id", "Server Name", "Image Name","Flavor","Status","Power State","AccessIPV4", "AccessIPV6", "Addresses");
        tb.addRow("--------", "-------------","----------","------","------","------------","----------","-----------","-----------");
        }
        else if(type.equals("Flavor")){
            tb.addRow("Id", "Disk", "Ram", "VCPU","Name");
            tb.addRow("---","-----","----","-----","-------");
        }
        return tb;
     }
    
    private static void addFlavorRow(TableBuilder tb, Flavor flavor){
        tb.addRow(flavor.getId(),String.valueOf(flavor.getDisk()),String.valueOf(flavor.getRam()),String.valueOf(flavor.getVcpus()),flavor.getName());
    }
    private static void addServerRow(TableBuilder tb,Server server){
        tb.addRow(server.getId(),server.getName(),server.getImage().getName(),server.getFlavor().getName(),server.getStatus().toString(),server.getPowerState(),server.getAccessIPv4(),server.getAccessIPv6(),server.getAddresses().toString());
    }
    
    
}
