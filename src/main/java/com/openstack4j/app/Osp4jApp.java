/**
 * @author viborole
 */
package com.openstack4j.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.identity.ServiceManagerService;
import org.openstack4j.api.image.ImageService;
import org.openstack4j.model.common.Payload;
import org.openstack4j.model.common.Payloads;
import org.openstack4j.model.compute.ActionResponse;
import org.openstack4j.model.compute.FloatingIP;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ext.AvailabilityZone;
import org.openstack4j.model.identity.Endpoint;
import org.openstack4j.model.identity.Service;
import org.openstack4j.model.identity.ServiceEndpoint;
import org.openstack4j.model.identity.Tenant;
import org.openstack4j.model.image.ContainerFormat;
import org.openstack4j.model.image.DiskFormat;
import org.openstack4j.model.image.Image;
import org.openstack4j.model.network.AttachInterfaceType;
import org.openstack4j.model.network.IP;
import org.openstack4j.model.network.IPVersionType;
import org.openstack4j.model.network.NetFloatingIP;
import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.Port;
import org.openstack4j.model.network.Router;
import org.openstack4j.model.network.Subnet;
import org.openstack4j.model.network.options.PortListOptions;
import org.openstack4j.openstack.OSFactory;

import com.google.common.base.Strings;


public class Osp4jApp {

    /**
     * @author viborole
     * @throws Exception
     */
    final static String serverUrl = "https://us-texas-3.cloud.cisco.com:5000/v2.0";
    final static String username1 = "cvg4-tx3.gen";
    //final static String credential = "3e71df2e12984e9b906294dd254c7520";
    final static String credential = "8d6febbcf16f41c1ba474926581b64f5";
    final static String  tenant = "CVG-DEV2";
    //final static String  tenant = "CVG-QA4"; 
   public static void main(final String[] args) throws Exception {
//        System.out.println("Inside Openstack4j App");
//        // final String serverUrl = "https://us-texas-1.cisco.com:5000/v2.0";
//        // final String username1 = "icfuser.gen";
//        // final String credential = "icfuser100";
//        // final String tenant = "intercloud-admin";
//
//
       System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
      System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "DEBUG");
       System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "ERROR");
//        
        final OSClient os = OSFactory.builder().endpoint(serverUrl).credentials(username1, credential).tenantName(tenant).useNonStrictSSLClient(true).authenticate();
//        //final OSClient os = OSFactory.builder().endpoint(serverUrl).credentials(username1, credential).useNonStrictSSLClient(true).authenticate();
//        //getLocations(os);  
        listVPCs(os);
//        //listfloatingIps(os);
//        //listfloatingIpsPerTenant(os);
//        //listNetfloatingIps(os);
//        //System.out.println("Uploading image...");
//        //uploadImage(os,args[0],args[1],args[2]);
//        //createSnapshot(os,args[0]);
//        //System.out.println(os.getEndpoint());
////        String networkId=createNetwork(os);
////        System.out.println("network id: "+networkId);
////        String subnetId=createSubnet(os, networkId);
////        System.out.println("subnetId: "+subnetId);
////        String routerId=createRouter(os); 
////        System.out.println("routerId: "+routerId);
////        addSubnetInterfaceToRouter(os,routerId, subnetId);
//        //cleanUpNetwork(networkId, subnetId, routerId,os);
//         //getRouter(os,"d8b5af9c-2205-4d04-8eee-f68845a28fb3");
//        
//        //createSecurityGroup(os);
//        //createSecurityRule(os);
//        System.out.println("downloading image for imageID:  "+ args[0]+", at location:"+args[1]);
//        downloadImage(os, args[0], args[1]);
//        
    }

   
    private static void createSecurityRule(OSClient os) {
        
    }

    private static void createSecurityGroup(OSClient os) {
        
    }

    private static void getRouter(OSClient os, String string) {
        Router router=os.networking().router().get(string);
        for(Port port : os.networking().port().list(PortListOptions.create().deviceId(router.getId()))){
            Set<? extends IP> ips=port.getFixedIps();
            for(IP ip : ips){
               System.out.println("subnetId: "+ip.getSubnetId());
            }
        }

        
    }

    private static void cleanUpNetwork(String networkId, String subnetId, String routerId,OSClient os) {
        System.out.println("*****Clean up network*******");
        if(!Strings.isNullOrEmpty(routerId)){
            if(!Strings.isNullOrEmpty(subnetId)){
                deleteSubnetInterfaces(routerId, subnetId, os);
                deleteSubnet(subnetId, os);
            }
            if(isRouterDeletionAllowed(routerId, os)){
                System.out.println("*****Deleting Router*******");
                deleteRouter(routerId, os);
            }
        }else{
            deleteSubnet(subnetId, os);
        }
        deleteNetwork(networkId,os);
    }

   private static boolean isRouterDeletionAllowed(String routerId, OSClient os) {
        System.out.println("*****Is Router deletion allowed*******");
        List<? extends Port> portList=os.networking().port().list(PortListOptions.create().deviceId(routerId));
        if(portList!=null && !portList.isEmpty())
            for(Port port : portList){
                System.out.println(port.getDeviceId() + ","+port.getDeviceOwner()+"," + port.getName()+","+port.getState());
                return false;
            }
        return true;
    }

    private static void deleteSubnetInterfaces(String routerId, String subnetId, OSClient os) {
        System.out.println("*****Deleting Subnet Interface from router*******");
        os.networking().router().detachInterface(routerId, subnetId, null);
    }

    private static void deleteNetwork(String networkId, OSClient os) {
        if(isNetworkDeleteAllowed(networkId, os)){
            System.out.println("*****Deleting Network*******");
            os.networking().network().delete(networkId);
        }
        
    }

    private static boolean isNetworkDeleteAllowed(String networkId, OSClient os) {
        System.out.println("*****Is Network deletion allowed*******");
        Network network=os.networking().network().get(networkId);
        List<? extends Subnet> subnetLst=network.getNeutronSubnets();
        if(subnetLst!=null && !subnetLst.isEmpty())
            for(Subnet subnet : subnetLst){
                System.out.println(subnet.getCidr()+","+subnet.getId()+","+subnet.getName());
                return false;
            }
        return true;
    }

    private static void deleteSubnet(String subnetId, OSClient os) {
        System.out.println("*****Deleting Subnet*******");
        os.networking().subnet().delete(subnetId);
    }

    private static void deleteRouter(String routerId, OSClient os) {
        os.networking().router().delete(routerId);
    }

    private static void addSubnetInterfaceToRouter(OSClient os, String routerId, String subnetId) {
        System.out.println("*****Attaching Subnet Interface to Router*******");
        if(routerId!=null && subnetId!=null)
            os.networking().router().attachInterface(routerId, AttachInterfaceType.SUBNET, subnetId);
    }

    private static String createSubnet(OSClient os, String networkId) {
        System.out.println("*****Creating Subnet*******");
        Subnet subnet = Builders.subnet().name("test_subnet")
                .networkId(networkId)
                .tenantId(getTenantId(os))
                .ipVersion(IPVersionType.V4)
                .cidr("10.0.0.0/24")
                .enableDHCP(true)
                .build();
        subnet = os.networking().subnet().create(subnet);
        return subnet!=null?subnet.getId():null;
    }

    private static String createRouter(OSClient os) {
        System.out.println("*****Creating Router*******");
        Router router = Builders.router()
                .name("test_router")
                .adminStateUp(true)
                .tenantId(getTenantId(os))
                .externalGateway(getExternalNetwork(os)).build();
        router = os.networking().router().create(router);
        return router!=null?router.getId():null;
    }

    private static String getExternalNetwork(OSClient os) {
        //System.out.println("*****Get External Network Info*******");
        List<? extends Network> networkLst=os.networking().network().list();
        for(Network network : networkLst){
            if(network.isRouterExternal()){
                System.out.println("external network: "+network.getName()+", "+network.getId());
                return network.getId();
            }
        }
        return null;
    }

    private static String createNetwork(OSClient os) {
        System.out.println("*****Creating Network*******");
        org.openstack4j.model.network.Network osNetwork = Builders.network()
                .name("test_Network")
                .tenantId(getTenantId(os))
                .adminStateUp(true)
                .build();
        osNetwork = os.networking().network().create(osNetwork);
        return osNetwork!=null?osNetwork.getId():null;
    }

    private static String getTenantId(OSClient os) {
        //System.out.println("*****Get Tenant by Name - "+tenant+"*******");
        return os.identity().tenants().getByName(tenant).getId();
    }

    private static void listfloatingIpsPerTenant(OSClient os) {
        List<? extends Tenant> tlst=os.identity().tenants().list();
        for(Tenant t:tlst){
            System.out.println("****** "+t.getName()+"**********");
            OSClient os1 = OSFactory.builder().endpoint(serverUrl).credentials(username1, credential).tenantId(t.getId()).authenticate();
            listfloatingIps(os1);
        }
    }

    private static void listVPCs(OSClient os) {
        List<? extends Tenant> lstTenant=os.identity().tenants().list();
        for(Tenant t : lstTenant){
            System.out.println(t.getName()+","+t.getId());
        }
        
    }

    private static void getLocations(final OSClient os) {
        ServiceManagerService sms=os.identity().services();
        List<? extends Service> lsSrv=sms.list();
        System.out.println("listing service");
        for(Service s : lsSrv){
            System.out.println(s.getName()+","+s.getId()+","+s.getType());
        }
        System.out.println("listing endpoints");
        List<? extends ServiceEndpoint> ls1=sms.listEndpoints();
        for(ServiceEndpoint s : ls1){
            System.out.println(s.getRegion()+","+ s.getId());
        }

        System.out.println("listing token endpoints");
        List<? extends Endpoint> ls2=os.identity().listTokenEndpoints();
        for(Endpoint e : ls2){
            System.out.println(e.getRegion()+","+e.getName()+","+e.getTenantId());
        }
        System.out.println("listing zones");
        List<? extends AvailabilityZone> list= os.compute().zones().list();
        for(AvailabilityZone z : list){
            System.out.println(z.getZoneName()+", "+z.getZoneState());
        }
        
       
        
    }

    private static void createSnapshot(final OSClient os, String id) throws Exception{
        String imageId = os.compute().servers().createSnapshot(id, "snapshot-osp4j-test01");
        if(imageId!=null){
            System.out.println("Snapshot created successfully!! - "+ imageId);
        }
        else{
            System.out.println("Error in snapshot creation");
        }
    }

    private static void listfloatingIps(final OSClient os) {
        final List<? extends FloatingIP> ips = os.compute().floatingIps().list();
        for (final FloatingIP ip : ips) {
            System.out.println("fixed ip:"+ip.getFixedIpAddress()+",  floatingip: "+ip.getFloatingIpAddress()+",   Id: "+ip.getId()+",   InstanceId:"+ip.getInstanceId()+",   Pool:"+ip.getPool());
            if (ip.getInstanceId() != null && !Strings.isNullOrEmpty(ip.getInstanceId())) {
               // final String address = ip.getFloatingIpAddress();
               // assignFloatingIp(os, address);
                System.out.println("server name: "+os.compute().servers().get(ip.getInstanceId()).getName());
            }
        }
    }

    private static void assignFloatingIp(final OSClient os, final String IpId) {
        System.out.println("Assigning floating Ip");
        final Server server = os.compute().servers().get("a382b060-c7a4-4107-ada9-b31be67e7f2a");
        final ActionResponse ar = os.compute().floatingIps().addFloatingIP(server, IpId);
        final ActionResponse ar1 = os.compute().floatingIps().removeFloatingIP(server, IpId);
        System.out.println("Assigned");
        System.out.println(ar.isSuccess());
    }

    private static void listNetfloatingIps(final OSClient os) {
        final List<? extends NetFloatingIP> netFloatingIP = os.networking().floatingip().list();
        for (final NetFloatingIP ip : netFloatingIP) {
            System.out.println(ip);
        }
    }

    /**
     * @author viborole
     */
    private static void uploadImage(final OSClient os, String imagePath, String imageName, String version) throws IOException {
//        final List<? extends Image> images = os.images().list();
//        System.out.println("######## Get Image list ######");
//        for (final Image image : images) {
//            System.out.println(image.getName());
//        }
        System.out.println("######################################");
        //final String imagePath = "/opt/capi-images/img660ee49d-93d3-25d5-6443-948642726b02";
        //final String imageName = "lvfs.qcow2";
        // Create a Payload - we will use URL in this example

        System.out.println("imagePath: "+imagePath+", ImageName: "+imageName +", Upload Version: "+version);
       
        
        // Upload Version - 1
        if("1".equalsIgnoreCase(version)){
          System.out.println("upload version - 1");
          System.out.println("Reserve image...");
         Image image = os.images().reserve(Builders.image().name(imageName+"_testAPP").build());
          System.out.println("Upload  image: " + imageName);
          final Payload<File> payload = Payloads.create(new File(imagePath + "/" + imageName));
          image = image.toBuilder().containerFormat(ContainerFormat.BARE).diskFormat(DiskFormat.QCOW2).build();
         os.images().upload(image.getId(), payload, image);
        }
        
        
        // Upload Version - 2 
        if("2".equalsIgnoreCase(version)){
          System.out.println("upload version - 2");
          System.out.println("uploading directly...");
          System.out.println("file: " + imagePath + "/" + imageName);
          final Payload<File> payload = Payloads.create(new File(imagePath + "/" + imageName));
          System.out.println("payload: " + payload.open().available());
          final Image image = os.images().upload("ictmp", payload, null);
        }
        // Upload Version -3
        if("3".equalsIgnoreCase(version)){
            System.out.println("upload version - 3");
            final Payload<File> payload = Payloads.create(new File(imagePath + "/" + imageName));
            System.out.println("payload: " + payload.open().available());
            Image image = Builders.image().name(imageName).containerFormat(ContainerFormat.BARE).diskFormat(DiskFormat.QCOW2).build();
            System.out.println("image: " + image.toString());
            image = os.images().create(image, payload);
            System.out.println("Upload Complete...");
            System.out.println("Size: " + image.getSize() + ",Status :" + image.getStatus() + ",DiskFormat :" + image.getDiskFormat());
        }
    }

}
