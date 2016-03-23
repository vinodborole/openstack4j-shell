package com.openstack4j.app.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.FloatingIP;
import org.openstack4j.model.network.IP;
import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.Port;
import org.openstack4j.model.network.Router;
import org.openstack4j.model.network.SecurityGroup;
import org.openstack4j.model.network.SecurityGroupRule;
import org.openstack4j.model.network.Subnet;
import org.openstack4j.model.network.options.PortListOptions;

import com.google.common.base.Strings;
import com.openstack4j.app.Osp4jSession;
import com.openstack4j.app.model.NetworkModel;
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
    public static List<NetworkModel> netList(){
        List<NetworkModel> networkLst = new ArrayList<NetworkModel>();
        OSClient os=Osp4jSession.getOspSession();
            List<? extends Network> networks=os.networking().network().list();
            for (final Network network : networks ) {
                List<? extends Subnet> subnets = network.getNeutronSubnets();
                List<String> cidr = new ArrayList<String>();
                if(subnets!=null){
                    for(Subnet subnet: subnets){
                        if(subnet!=null)
                            cidr.add(subnet.getCidr());
                    }
                }
                networkLst.add(new NetworkModel.NetworkModelBuilder(network.getId()).name(network.getName()).status(network.getStatus().toString()).subnets(cidr).tenantId(network.getTenantId()).build());
            }
            
            return networkLst; 
    }
    public static List<? extends SecurityGroup> getAllSecurityGroups(){
        OSClient os=Osp4jSession.getOspSession();
        return os.networking().securitygroup().list();
    }
    public static List<? extends SecurityGroupRule> getAllSecurityRules(String secGrpId){
        OSClient os=Osp4jSession.getOspSession();
        return os.networking().securitygroup().get(secGrpId).getRules();
    }
    
    public static void deleteSecurityRule(String secRuleId){
        Osp4jSession.getOspSession().networking().securityrule().delete(secRuleId);
    }
    public static void deleteSecurityGroup(String secGrpId){
        Osp4jSession.getOspSession().networking().securitygroup().delete(secGrpId);
    }
    public static void delete(String networkId) throws Exception {
        OSClient os=Osp4jSession.getOspSession();
        Network network = os.networking().network().get(networkId);
        if (network != null) {
            List<? extends Subnet> subnetLst = network.getNeutronSubnets();
            for(Subnet subnet : subnetLst){
                if(subnet!=null){
                    String subnetId =  subnet.getId();
                    String routerId = getRouterFromSubnet(os, subnetId);
                    if (!Strings.isNullOrEmpty(subnetId ) && !Strings.isNullOrEmpty(routerId)) {
                        os.networking().router().detachInterface(routerId, subnetId, null);
                        os.networking().subnet().delete(subnetId);
                        if (isNetworkDeleteAllowed(os, networkId)) {
                            os.networking().network().delete(networkId);
                        }
                    } else if (!Strings.isNullOrEmpty(subnetId)) {
                        os.networking().subnet().delete(subnetId);
                        if (isNetworkDeleteAllowed(os, networkId)) {
                            os.networking().network().delete(networkId);
                        }
                    }
            }
            }
        }
    }
    public static void deleteRouter(String routerId) {
        OSClient os=Osp4jSession.getOspSession();
        os.networking().router().delete(routerId);
    }
    public static boolean isNetworkDeleteAllowed(OSClient os, String networkId) throws Exception {
        Network network = os.networking().network().get(networkId);
        List<? extends Subnet> subnetLst = getSubnets(network);
        if (subnetLst != null && !subnetLst.isEmpty())
            return false;
        return true;
    }
    private static List<? extends Subnet> getSubnets(Network network) {
        return network.getNeutronSubnets();
    }
    private static String getRouterFromSubnet(OSClient os, String subnetId) {
        List<? extends Router> routers = getAllRouters();
        if (routers != null && !routers.isEmpty()) {
            for (Router router : routers) {
                List<? extends Port> portList = getRouterPortList(os, router.getId());
                for (Port port : portList) {
                    Set<? extends IP> ips = port.getFixedIps();
                    for (IP ip : ips) {
                        if (subnetId.equals(ip.getSubnetId())) {
                            return router.getId();
                        }
                    }
                }
            }
        }
        return null;
    }
    public static List<? extends Router> getAllRouters() {
        OSClient os=Osp4jSession.getOspSession();
        List<? extends Router> routers = os.networking().router().list();
        return routers;
    }
    private static List<? extends Port> getRouterPortList(OSClient os, String routerId) {
        return os.networking().port().list(PortListOptions.create().deviceId(routerId));
    }
    public static void printFloatingIpDetails(List<? extends FloatingIP> ips){
        TableBuilder tb=getTableBuilder("Floating");
        for (final FloatingIP ip : ips) {
            addFloatingIpRow(tb, ip);
        }
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }
    public static void printNetsDetails(List<NetworkModel> networks){
        TableBuilder tb = getTableBuilder("Net");
        for (final NetworkModel network : networks ) {
            addNetRow(tb,network);
        }
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    } 

    public static void printNetDetails(NetworkModel network){
        TableBuilder tb = getTableBuilder("Net");
        addNetRow(tb,network);
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }
        
    public static void printRouterDetails(Router router){
        TableBuilder tb = getTableBuilder("Router");
        addRouterRow(tb, router);
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }
    public static void printRouterDetails(List<? extends Router> routers){
        TableBuilder tb = getTableBuilder("Router");
        for(Router router:routers){
            addRouterRow(tb, router);
        }
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }
    public static void printSecurityGroup(SecurityGroup secGrp){
        TableBuilder tb = getTableBuilder("SecurityGroup");
        addSecurityGroupRow(tb, secGrp);
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }
    public static void printSecurityGroup(List<? extends SecurityGroup> secGrps){
        TableBuilder tb = getTableBuilder("SecurityGroup");
        for(SecurityGroup secGrp : secGrps){
            addSecurityGroupRow(tb, secGrp);
        }
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }
    public static void printSecurityGroupRule(SecurityGroupRule secGrpRule){
        TableBuilder tb = getTableBuilder("SecurityGroupRule");
        addSecurityGroupRuleRow(tb, secGrpRule);
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }
    public static void printSecurityGroupRule(List<? extends SecurityGroupRule> secGrpRules){
        TableBuilder tb = getTableBuilder("SecurityGroupRule");
        for(SecurityGroupRule secGrpRule : secGrpRules){
            addSecurityGroupRuleRow(tb, secGrpRule);
        }
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }
    private static TableBuilder getTableBuilder(String type) {
        TableBuilder tb = new TableBuilder();
        if(type.equals("Net")){
            tb.addRow("Network Id", "Network Name", "Network Status","CIDR","Tenant ID");
            tb.addRow("--------","-----------","-------------","-----------","--------------");
        }else if(type.equals("Floating")){
            tb.addRow("Floating IP Id", "Fixed IP Address", "Floating IP","Instance ID","Pool");
            tb.addRow("-----------","----------------"," -----------","-----------","-----");
        }else if(type.equals("Router")){
            tb.addRow("Router Id","Name","Status","Routes","External Gateway Info");
            tb.addRow("---------","---------","---------","---------","---------");
        }else if(type.equals("SecurityGroup")){
            tb.addRow("SecurityGroup Id","Name","Description");
            tb.addRow("--------------","---------","---------");
        }else if(type.equals("SecurityGroupRule")){
            tb.addRow("SecurityGroupRule Id","Protocol","PortRange Max","PortRange Min","RemoteIP Prefix","Security Group Id");
            tb.addRow("------------------","---------","---------","---------","---------","---------");
        }
        return tb;
     }
    private static void addSecurityGroupRow(TableBuilder tb, SecurityGroup secGrp){
        tb.addRow(secGrp.getId(),secGrp.getName(),secGrp.getDescription());
    }
    private static void addSecurityGroupRuleRow(TableBuilder tb, SecurityGroupRule secGrpRule){
        tb.addRow(secGrpRule.getId(),secGrpRule.getProtocol(),String.valueOf(secGrpRule.getPortRangeMax()),String.valueOf(secGrpRule.getPortRangeMin()),secGrpRule.getRemoteIpPrefix(),secGrpRule.getSecurityGroupId());
    }
    private static void addRouterRow(TableBuilder tb, Router router){
        tb.addRow(router.getId(),router.getName(),router.getStatus().toString(),router.getRoutes().toString(),router.getExternalGatewayInfo().toString());
    }
    private static void addFloatingIpRow(TableBuilder tb, FloatingIP ip){
        tb.addRow(ip.getId(),ip.getFixedIpAddress(),ip.getFloatingIpAddress(),ip.getInstanceId(),ip.getPool());
    }
    private static void addNetRow(TableBuilder tb,NetworkModel network){ 
        tb.addRow(network.getId(),network.getName(),network.getStatus().toString(),network.getSubnets().toString(),network.getTenantId());
    }
 

}
