package com.openstack4j.app.api;

import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.openstack4j.model.compute.Server;
import org.openstack4j.model.image.Image;
import org.openstack4j.model.network.Router;
import org.openstack4j.model.network.SecurityGroup;
import org.openstack4j.model.network.SecurityGroupRule;
import org.openstack4j.model.storage.block.VolumeSnapshot;

import com.openstack4j.app.model.NetworkModel;

public class TenantAPI {
    /**
     * @author viborole
     */
    public static void printTenantInfo() {
        System.out.println("================================================================================");
        System.out.println("----------------------------SERVER DETAILS---------------------------------------");
        System.out.println("================================================================================");
        NovaAPI.printServersDetails(NovaAPI.listServers());
        System.out.println("");
        System.out.println("");
        System.out.println("================================================================================");
        System.out.println("----------------------------VOLUME DETAILS---------------------------------------");
        System.out.println("================================================================================");
                     CinderAPI.printVolumeDetails(CinderAPI.listvolumes());
                     System.out.println("");
                     System.out.println("");
                     System.out.println("================================================================================");
                     System.out.println("----------------------------VOLUME SNAPSHOT DETAILS-------------------------------");
                     System.out.println("================================================================================");
                     System.out.println("");
                     System.out.println("");
        CinderAPI.printVolumeSnapDetails(CinderAPI.getAllVolumeSnapshots());
        System.out.println("");
        System.out.println("");
        System.out.println("================================================================================");
        System.out.println("----------------------------IMAGE DETAILS---------------------------------------");
        System.out.println("================================================================================");
        GlanceAPI.printImagesDetails(GlanceAPI.imageList());
        System.out.println("");
        System.out.println("");
        System.out.println("================================================================================");
        System.out.println("----------------------------NETWORK DETAILS---------------------------------------");
        System.out.println("================================================================================");
        NeutronAPI.printNetsDetails(NeutronAPI.netList());
        System.out.println("");
        System.out.println("");
        System.out.println("================================================================================");
        System.out.println("----------------------------ROUTER DETAILS---------------------------------------");
        System.out.println("================================================================================");
        NeutronAPI.printRouterDetails(NeutronAPI.getAllRouters());
        System.out.println("");
        System.out.println("");
        System.out.println("================================================================================");
        System.out.println("----------------------------SECURITY GROUP DETAIL---------------------------------");
        System.out.println("================================================================================");
        NeutronAPI.printSecurityGroup(NeutronAPI.getAllSecurityGroups());
        List<? extends SecurityGroup> secGroups = NeutronAPI.getAllSecurityGroups();
        for(SecurityGroup secGrp : secGroups){
            System.out.println("");
            System.out.println("");
            System.out.println("================================================================================");
            System.out.println("----------------------------SECURITY GROUP RULE DETAILS-------------------------");
            System.out.println("================================================================================");
            NeutronAPI.printSecurityGroupRule(NeutronAPI.getAllSecurityRules(secGrp.getId()));
        }
    }
    /**
     * @author viborole
     */
    public static void deleteAllVMs() {
        List<? extends Server> servers = NovaAPI.listServers();
        System.out.println("Total number of VM's found "+servers.size());
        System.out.println("Deleting Vms..");
        for(Server server: servers){
            NovaAPI.delete(server.getId());
        }
    }

    /**
     * @author viborole
     */
    public static void deleteAllVolumes() throws Exception {
        Set<String> volumes=CinderAPI.getAllAvailaibleVolumes();
        System.out.println("Total number of Volumes found "+volumes.size());
        System.out.println("Deleting Volumes..");
        CinderAPI.deleteVolumes(volumes);
    }

    /**
     * @author viborole
     */
    public static void deleteAllVolumeSnapshots() {
        List<? extends VolumeSnapshot> volsnapshotList=CinderAPI.getAllVolumeSnapshots();
        System.out.println("Total number of Volume Snapshots found "+volsnapshotList.size());
        System.out.println("Deleting Volume Snapshots..");
        for(VolumeSnapshot volSnap: volsnapshotList){
            CinderAPI.deleteVolumeSnapshot(volSnap.getId());
        }
    }

    /**
     * @author viborole
     */
    public static void deleteAllImages() {
        List<? extends Image> images = GlanceAPI.imageList();
        System.out.println("Total number of Images found "+images.size());
        System.out.println("Deleting Non-Public Images only..");
        for(Image image: images){
            if(!image.isPublic())
                GlanceAPI.delete(image.getId());
        }
    }
 
    /**
     * @author viborole
     */
    public static void deleteAllNetworks() throws Exception {
        List<NetworkModel> netList=NeutronAPI.netList();
        System.out.println("Total number of Networks found "+netList.size());
        System.out.println("Deleting Networks..");
        for(NetworkModel netModel :netList){
            NeutronAPI.delete(netModel.getId());
        }
    }

    /**
     * @author viborole
     */
    public static void deleteAllRouters() {
        List<? extends Router> routers = NeutronAPI.getAllRouters();
        System.out.println("Total number of Routers found "+routers.size());
        System.out.println("Deleting Routers..");
        for(Router router:routers){
            NeutronAPI.deleteRouter(router.getId());
        }
    }

    /**
     * @author viborole
     */
    public static void deleteAllSecurityGroupRules() {
        System.out.println("This won't delete the default security group.");
        List<? extends SecurityGroup> secGroups = NeutronAPI.getAllSecurityGroups();
        System.out.println("Total number of Security Group found "+secGroups.size());
        for(SecurityGroup secGrp : secGroups){
            if(!"default".equals(secGrp.getName())){
                List<? extends SecurityGroupRule> secRules = NeutronAPI.getAllSecurityRules(secGrp.getId());
                System.out.println("Total number of Security Rules for Security Group "+secGrp.getName() +" found "+secRules.size());
                System.out.println("Deleting Security Group Rules..");
                for(SecurityGroupRule secGrpRule: secRules){
                    NeutronAPI.deleteSecurityRule(secGrpRule.getId());
                }
                System.out.println("Deleting Security Group..");
                NeutronAPI.deleteSecurityGroup(secGrp.getId());
            }
        }
    }
    
    public static void deleteTenantInfo()  throws Exception{
        System.out.println("You are about to delete following information...");
        printTenantInfo();
        System.out.println("Delete will begin in 10 secounds; if you wish to stop please exit the program by pressing Ctr+X");
        int SECONDS = 10; // The delay in minutes
        Timer timer = new Timer();
         timer.schedule(new TimerTask() {
            @Override
            public void run(){ 
                System.out.println("Deleting All the tenant information.....");
                try{deleteAll();}catch(Exception e){System.out.println("Incomplete delete..");e.printStackTrace();} 
            }
         }, SECONDS * 1000);
    }
    private static void deleteAll() throws Exception{
        deleteAllVMs();
        deleteAllVolumes();
        deleteAllVolumeSnapshots();
        deleteAllImages();
        deleteAllNetworks();
        deleteAllRouters();
        deleteAllSecurityGroupRules();
    }

}
