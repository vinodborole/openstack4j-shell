package com.openstack4j.app.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.compute.FlavorService;
import org.openstack4j.api.image.ImageService;
import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.ActionResponse;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.Server.Status;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.model.compute.builder.ServerCreateBuilder;
import org.openstack4j.model.image.Image;

import com.openstack4j.app.Osp4jSession;

public class NovaAPI {

    private enum NovaKey{
        SERVERID;
    }
    private static Map<NovaKey, String> novaMemory= new HashMap<NovaKey, String>();
    
    public static void addToMemory(NovaKey key, String value){
        novaMemory.put(key, value);
    }
    public static String getFromMemory(NovaKey key){
        return novaMemory.get(key);
    }
    public static void removeFromMemory(NovaKey key){
        novaMemory.remove(key);
    }
    public static void flushMemory(){
        Iterator<NovaKey> entriesIterator = novaMemory.keySet().iterator();
        while (entriesIterator.hasNext()) {
            novaMemory.remove(entriesIterator.next().toString());
        } 
    }
    
    public static ActionResponse startVM(String serverId){
        OSClient os=Osp4jSession.getOspSession();
        serverId=takeFromMemory(NovaKey.SERVERID,serverId);
        return os.compute().servers().action(serverId, Action.START);
    }
    private static String takeFromMemory(NovaKey key , String serverId) {
        if(serverId.equalsIgnoreCase("$")){
            return getFromMemory(key);
        }else{
            return serverId;
        }
    }
    public static boolean stopVM(String serverId){
        OSClient os=Osp4jSession.getOspSession();
        serverId=takeFromMemory(NovaKey.SERVERID,serverId);
        os.compute().servers().action(serverId, Action.STOP);
        return waitUntilServerSHUTOFF(os,serverId);
    }
    public static ActionResponse restartVM(String serverId){
        serverId=takeFromMemory(NovaKey.SERVERID,serverId);
        stopVM(serverId);
        return startVM(serverId);
    }
    public static boolean downloadVM(String serverId, String downloadLocation,String name){
        stopVM(serverId);
        OSClient os=Osp4jSession.getOspSession();
        serverId=takeFromMemory(NovaKey.SERVERID,serverId);
        String imageId = os.compute().servers().createSnapshot(serverId, name);
        waitUntilImageACTIVE(os,imageId);
        boolean response= downloadImage(imageId, downloadLocation, name);
        startVM(serverId);
        return response;
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
    
    public static boolean downloadImage(String imageId, String downloadLocation, String name){
        OSClient os=Osp4jSession.getOspSession();
        ImageService imgService= os.images();
        Image image=imgService.get(imageId);
        if(image!=null){
            System.out.println("image info: "+image.toString());
            InputStream is =imgService.getAsStream(imageId);
            if(is!=null){
                try{
                    String downLoadFileName = downloadLocation + File.separator + name + ".qcow2";
                    FileOutputStream outputStream = new FileOutputStream(new File(downLoadFileName));
                    int read = 0;
                    final byte[] bytes = new byte[1048576];
                    while ((read = is.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                        outputStream.flush();
                    } 
                    System.out.println("download complete.."+ downLoadFileName);
                    return true;
                }catch(Exception e){
                    System.err.println("Exception : "+e.getMessage());
                    return false;
                }
            }else{
                System.err.println("Download error.. inputstream is null");
                return false;
            }
        }else{
            System.err.println("Image with ID "+imageId+" not found!");
            return false;
        }
    }
    public static void listflavor(){
        OSClient os=Osp4jSession.getOspSession();
        FlavorService flavorService = os.compute().flavors();
        List<? extends Flavor> flavorList = flavorService.list();
        for (Flavor flavor : flavorList) {
            System.out.println(flavor.getId()+", "+flavor.getDisk()+","+flavor.getRam()+","+flavor.getVcpus()+","+flavor.getName());
        }
    }
    public static void boot(String imageId, String flavorId, String netId, String name) {
        System.out.println("booting vm...");
        OSClient os=Osp4jSession.getOspSession();
        List<String> netList = new ArrayList<String>();
        netList.add(netId);
        ServerCreateBuilder builder = Builders.server().name(name).image(imageId).flavor(flavorId).networks(netList);;
        ServerCreate serverOptions = builder.build();
        Server server = os.compute().servers().boot(serverOptions);
        System.out.println("saving to memory..");
        addToMemory(NovaKey.SERVERID, server.getId());
        waitUntilServerActive(os,server.getId()); 
        System.out.println("server created");
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
        serverId=takeFromMemory(NovaKey.SERVERID,serverId);
        return os.compute().servers().delete(serverId);
    }
    public static void status(String serverId) {
        OSClient os=Osp4jSession.getOspSession();
        serverId=takeFromMemory(NovaKey.SERVERID,serverId);
        Server server=os.compute().servers().get(serverId);
        System.out.println("status: "+server.getStatus());
    }
}
