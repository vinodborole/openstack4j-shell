package com.vinodborole.openstack4j.app.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.compute.FlavorService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.Addresses;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.Server.Status;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.model.compute.builder.BlockDeviceMappingBuilder;
import org.openstack4j.model.compute.builder.ServerCreateBuilder;
import org.openstack4j.model.network.Network;

import com.vinodborole.openstack4j.app.Osp4jSession;
import com.vinodborole.openstack4j.app.api.CinderAPI.CinderKey;
import com.vinodborole.openstack4j.app.api.GlanceAPI.GlanceKey;
import com.vinodborole.openstack4j.app.api.NeutronAPI.NeutronKey;
import com.vinodborole.openstack4j.app.utils.TableBuilder;
/**
 * Nova API
 *  
 * @author vinod borole
 */
public class NovaAPI {

    protected enum NovaKey{
        NOVA_SERVERID;
    }
   
    public static ActionResponse startVM(String serverId) throws Exception{
        OSClient os=Osp4jSession.getOspSession();
        serverId=CommonAPI.takeFromMemory(NovaKey.NOVA_SERVERID,serverId);
        return os.compute().servers().action(serverId, Action.START);
    }

    public static boolean stopVM(String serverId) throws Exception{
        OSClient os=Osp4jSession.getOspSession();
        serverId=CommonAPI.takeFromMemory(NovaKey.NOVA_SERVERID,serverId);
        os.compute().servers().action(serverId, Action.STOP);
        return waitUntilServerSHUTOFF(os,serverId);
    }
    public static ActionResponse restartVM(String serverId) throws Exception{
        serverId=CommonAPI.takeFromMemory(NovaKey.NOVA_SERVERID,serverId);
        stopVM(serverId);
        return startVM(serverId);
    }
    public static boolean downloadVM(String serverId, String downloadLocation,String name) throws Exception{
        stopVM(serverId);
        String imageId=createSnapshot(serverId, name);
        boolean response= CommonAPI.downloadImage(imageId, downloadLocation, name);
        startVM(serverId);
        return response;
    }
    
    public static Server getServer(String serverId) throws Exception {
        OSClient os=Osp4jSession.getOspSession();
        return os.compute().servers().get(serverId);
    }

    public static List<? extends Server> listServers() throws Exception{
        OSClient os=Osp4jSession.getOspSession();
        List<? extends Server> servers=os.compute().servers().list();
        return servers;
    }
    
    public static String createSnapshot(String serverId , String name) throws Exception{
        OSClient os=Osp4jSession.getOspSession();
        serverId=CommonAPI.takeFromMemory(NovaKey.NOVA_SERVERID,serverId);
        Server server=os.compute().servers().get(serverId);
        String imageId = os.compute().servers().createSnapshot(serverId, name);
        waitUntilImageACTIVE(os,imageId); 
        Map<String,String> metadata=server.getMetadata();
        Boolean bootFromVolume=new Boolean(metadata.get("BOOT_FROM_VOLUME"));
        System.out.println(bootFromVolume);
        if(bootFromVolume){
            CinderAPI.getVolumeSnapshot(metadata.get("VOLUME_ID"));
        }
        
        return imageId;
    }
    private static void waitUntilImageACTIVE(OSClient os,String imageId) throws Exception {
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
    
    public static List<? extends Flavor> listflavor() throws Exception{
        OSClient os=Osp4jSession.getOspSession();
        FlavorService flavorService = os.compute().flavors();
        List<? extends Flavor> flavorList = flavorService.list();
        printFlavorsDetails(flavorList);
        return flavorList;
    }
    public static String boot(String imageOrVolumeId, String flavorId, String netId, String name, boolean bootfromVolume) throws Exception {
        OSClient os=Osp4jSession.getOspSession();
        List<String> netList = new ArrayList<String>();
        netId=CommonAPI.takeFromMemory(NeutronKey.NEUTRON_NETWORKID, netId);
        netList.add(netId);
        ServerCreateBuilder builder = null; 
        Map<String,String> metadata = new HashMap<String,String>();
        if(bootfromVolume){
            imageOrVolumeId=CommonAPI.takeFromMemory(CinderKey.VOLUME_ID, imageOrVolumeId);
            metadata.put("BOOT_FROM_VOLUME", "true");
            metadata.put("VOLUME_ID", imageOrVolumeId);
            builder = Builders.server().name(name).flavor(flavorId).addMetadata(metadata).networks(netList);
            BlockDeviceMappingBuilder blockDeviceMappingBuilder = Builders.blockDeviceMapping().uuid(imageOrVolumeId).deviceName("/dev/vda").bootIndex(0).deleteOnTermination(true);
            builder.blockDevice(blockDeviceMappingBuilder.build());
        }else{
            metadata.put("BOOT_FROM_VOLUME", "false");
            imageOrVolumeId=CommonAPI.takeFromMemory(GlanceKey.IMAGE_ID, imageOrVolumeId);
            builder = Builders.server().name(name).image(imageOrVolumeId).flavor(flavorId).networks(netList).addMetadata(metadata);
        }
        ServerCreate serverOptions = builder.build();
        Server server = os.compute().servers().boot(serverOptions);
        CommonAPI.addToMemory(NovaKey.NOVA_SERVERID, server.getId());
        waitUntilServerActive(os,server.getId()); 
        printServerDetails(os.compute().servers().get(server.getId()));
        return server.getId();
    }
    public static String bootdefault(String imageId, String name) throws Exception {
        imageId=CommonAPI.takeFromMemory(GlanceKey.IMAGE_ID, imageId);
        Flavor flavor=getFlavor(50, 2048, 1);
        Network network=NeutronAPI.createNetDefault(name);
        String serverId = boot(imageId, flavor.getId(),network.getId(),name,false);
        return serverId;
    }
    public static String bootvolumedefault(String volumeId, String name) throws Exception {
        volumeId=CommonAPI.takeFromMemory(CinderKey.VOLUME_ID, volumeId);
        Flavor flavor=getFlavor(50, 2048, 1);
        Network network=NeutronAPI.createNetDefault(name);
        String serverId = boot(volumeId, flavor.getId(),network.getId(),name,true);
        return serverId;
    }
    public static Flavor getFlavor(int edisk, int eram , int evcpu) throws Exception{
        List<? extends Flavor> flavors=listflavor();
        for(Flavor flavor : flavors){
            int disk=flavor.getDisk();
            int ram=flavor.getRam();
            int vcpu=flavor.getVcpus();
            if(disk==edisk && ram == eram && evcpu==vcpu)
                return flavor;
        }
        return null;
    }
    private static void waitUntilServerActive(OSClient os,String serverId) throws Exception {
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
    private static void waitUntilServerDeleted(OSClient os, String serverId) throws Exception{
        while(true){
            Server server=os.compute().servers().get(serverId);
            Server.Status status=Status.DELETED;
            if(server!=null)
                status=server.getStatus();
            System.out.println("current status: "+status.toString());
            if(status.equals(Status.DELETED)){
                break; 
            }else{
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }
        }
    }
    private static boolean waitUntilServerSHUTOFF(OSClient os,String serverId) throws Exception {
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
    public static void delete(String serverId) throws Exception {
        OSClient os=Osp4jSession.getOspSession();
        serverId=CommonAPI.takeFromMemory(NovaKey.NOVA_SERVERID,serverId);
        os.compute().servers().delete(serverId);
        waitUntilServerDeleted(os,serverId);
    }
    public static void status(String serverId) throws Exception {
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
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }
    public static void printFlavorDetails(Flavor flavor){
        TableBuilder tb=getTableBuilder("Flavor");
        addFlavorRow(tb, flavor);
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }    
    public static void printServersDetails(List<? extends Server> servers){
        TableBuilder tb=getTableBuilder("Server");
        for (final Server server : servers ) {
            addServerRow(tb,server);
        }
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }

    public static void printServerDetails(Server server){
        TableBuilder tb = getTableBuilder("Server");
        addServerRow(tb,server);
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }
        
    private static TableBuilder getTableBuilder(String type) {
        TableBuilder tb = new TableBuilder();
        if(type.equals("Server")){
        tb.addRow("Server Id", "Server Name", "Image Name","Flavor","Status","Power State","AccessIPV4", "AccessIPV6", "Addresses","Metadata");
        tb.addRow("--------", "-------------","----------","------","------","------------","----------","-----------","-----------","----------");
        }
        else if(type.equals("Flavor")){
            tb.addRow("Flavor Id", "Disk", "Ram", "VCPU","Name");
            tb.addRow("---","-----","----","-----","-------");
        }
        return tb;
     }
    
    private static void addFlavorRow(TableBuilder tb, Flavor flavor){
        tb.addRow(flavor.getId(),String.valueOf(flavor.getDisk()),String.valueOf(flavor.getRam()),String.valueOf(flavor.getVcpus()),flavor.getName());
    }
    private static void addServerRow(TableBuilder tb,Server server){
        String metadata="";
        String imageName="";
        String addresses="";
        Map<String,String> metadataMap=server.getMetadata();
        if(metadataMap!=null && !metadataMap.isEmpty())
            metadata=metadataMap.toString();
        Image image=server.getImage();
        if(image!=null)
            imageName=image.getName();
        Addresses addressesobj= server.getAddresses();
        if(addresses!=null)
            addresses=addressesobj.toString();
        tb.addRow(server.getId(),server.getName(),imageName,server.getFlavor().getName(),server.getStatus().toString(),server.getPowerState(),server.getAccessIPv4(),server.getAccessIPv6(),addresses,metadata);
    }


    
    
}
