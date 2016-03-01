package com.openstack4j.app.api;

import java.util.List;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.FloatingIP;
import org.openstack4j.model.network.Network;

import com.openstack4j.app.Osp4jSession;
import com.openstack4j.app.utils.TableBuilder;

public class NeutronAPI {
    protected enum NeutronKey{
        NEUTRON_NETWORKID;
    }

    public static void listfloatingIps() {
        OSClient os=Osp4jSession.getOspSession();
        final List<? extends FloatingIP> ips = os.compute().floatingIps().list();
        printFloatingIpDetails(ips);
    }
    public static void netList(){
        OSClient os=Osp4jSession.getOspSession();
        List<? extends Network> networks=os.networking().network().list();
        for (final Network network : networks ) {
            System.out.println(network.getId()+"   "+network.getName()+"   "+network.getStatus().toString()+"   "+network.getNeutronSubnets().get(0).getCidr().toString()+"   "+network.getTenantId());
        }
    }
    public static void printFloatingIpDetails(List<? extends FloatingIP> ips){
        TableBuilder tb=getTableBuilder("Floating");
        for (final FloatingIP ip : ips) {
            addFloatingIpRow(tb, ip);
        }
        System.out.println(tb.toString());
    }
    public static void printNetsDetails(List<? extends Network> networks){
        TableBuilder tb = getTableBuilder("Net");
        for (final Network network : networks ) {
            addNetRow(tb,network);
        }
        System.out.println(tb.toString());
    } 

    public static void printNetDetails(Network network){
        TableBuilder tb = getTableBuilder("Net");
        addNetRow(tb,network);
        System.out.println(tb.toString());
    }
        
    private static TableBuilder getTableBuilder(String type) {
        TableBuilder tb = new TableBuilder();
        if(type.equals("Net")){
            tb.addRow("Network Id", "Network Name", "Network Status","CIDR [0]","Tenant ID");
            tb.addRow("--------","-----------","-------------","-----------","-----------------");
        }else if(type.equals("Floating")){
            tb.addRow("Id", "Fixed IP Address", "Floating IP","Instance ID","Pool");
            tb.addRow("---","----------------"," -----------","-----------","-----");
        }
        return tb;
     }
    private static void addFloatingIpRow(TableBuilder tb, FloatingIP ip){
        tb.addRow(ip.getId(),ip.getFixedIpAddress(),ip.getFloatingIpAddress(),ip.getInstanceId(),ip.getPool());
    }
    private static void addNetRow(TableBuilder tb,Network network){
        tb.addRow(network.getId(),network.getName(),network.getStatus().toString(),network.getNeutronSubnets().get(0).getCidr().toString(),network.getTenantId());
    }
}
