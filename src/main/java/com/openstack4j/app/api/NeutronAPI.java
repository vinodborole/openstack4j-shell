package com.openstack4j.app.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.FloatingIP;
import org.openstack4j.model.network.Network;

import com.openstack4j.app.Osp4jSession;

public class NeutronAPI {
    private enum NeutronKey{
        NETWORKID;
    }
    private static Map<NeutronKey, String> neutronMemory= new HashMap<NeutronKey, String>();
    
    public static void addToMemory(NeutronKey key, String value){
        neutronMemory.put(key, value);
    }
    public static String getFromMemory(NeutronKey key){
        return neutronMemory.get(key);
    }
    public static void removeFromMemory(NeutronKey key){
        neutronMemory.remove(key);
    }
    public static void flushMemory(){
        Iterator<NeutronKey> entriesIterator = neutronMemory.keySet().iterator();
        while (entriesIterator.hasNext()) {
            neutronMemory.remove(entriesIterator.next().toString());
        } 
    }
    public static void listfloatingIps() {
        OSClient os=Osp4jSession.getOspSession();
        final List<? extends FloatingIP> ips = os.compute().floatingIps().list();
        for (final FloatingIP ip : ips) {
            System.out.println("fixed ip:"+ip.getFixedIpAddress()+",  floatingip: "+ip.getFloatingIpAddress()+",   Id: "+ip.getId()+",   InstanceId:"+ip.getInstanceId()+",   Pool:"+ip.getPool());
        }
    }
    public static void netList(){
        OSClient os=Osp4jSession.getOspSession();
        List<? extends Network> networks=os.networking().network().list();
        for(Network network: networks){
            System.out.println(network.getId()+", "+network.getName()+", "+network.getSubnets().toString());
        }
    }
}
