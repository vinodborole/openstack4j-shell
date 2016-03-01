package com.openstack4j.app.api;

import java.util.List;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.FloatingIP;
import org.openstack4j.model.network.Network;

import com.openstack4j.app.Osp4jSession;

public class NeutronAPI {
    protected enum NeutronKey{
        NEUTRON_NETWORKID;
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
