package com.vinodborole.openstack4j.app.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.net.util.SubnetUtils;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.FloatingIP;
import org.openstack4j.model.network.AttachInterfaceType;
import org.openstack4j.model.network.IP;
import org.openstack4j.model.network.IPVersionType;
import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.Port;
import org.openstack4j.model.network.Router;
import org.openstack4j.model.network.RouterInterface;
import org.openstack4j.model.network.SecurityGroup;
import org.openstack4j.model.network.SecurityGroupRule;
import org.openstack4j.model.network.Subnet;
import org.openstack4j.model.network.options.PortListOptions;

import com.google.common.base.Strings;
import com.vinodborole.openstack4j.app.Osp4jSession;
import com.vinodborole.openstack4j.app.utils.TableBuilder;
/**
 * Neutron API
 *  
 * @author vinod borole
 */
public class NeutronAPI {
    protected enum NeutronKey{
        NEUTRON_NETWORKID,
        NEUTRON_ROUTERID,
        NEUTRON_SUBNETID,
        NEUTRON_SUB_INTERFACEID;
    }
    public static void listfloatingIps() throws Exception {
        OSClient os=Osp4jSession.getOspSession();
        final List<? extends FloatingIP> ips = os.compute().floatingIps().list();
        printFloatingIpDetails(ips);
    }
    public static List<Network> netList(){
        List<Network> networkLst = new ArrayList<Network>();
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
                networkLst.add(network); 
            }
            
            return networkLst; 
    }
    
    public static Map<String,String> createNetworking(String cidr, String name) throws Exception{
    	Map<String, String> ids = new HashMap<String,String>();
    	
    	Network network=NeutronAPI.createNetwork(name);
		ids.put("network", network.getId());
		
		List<? extends Router> routers=NeutronAPI.getAllRouters();
		
		Router router=null;
		if(routers!=null && !routers.isEmpty()){
			router = routers.get(0);
			ids.put("router", router.getId());
		}else{
			router = NeutronAPI.createRouter(name);
			ids.put("newrouter", router.getId());
		}
		
		Subnet subnet=NeutronAPI.createSubnet(network.getId(), name, cidr);
		ids.put("subnet", subnet.getId());
		
		RouterInterface routerInterface=NeutronAPI.addSubnetInterfaceToRouter(router.getId(), subnet.getId());
		ids.put("routerInterface", routerInterface.getId());
    	
    	return ids;
    }
    
    public static void deleteNetworking(Map<String, String> ids) throws Exception{
    	if(!ids.isEmpty()) {
			if(ids.containsKey("routerInterface")){
				String routerId = ids.containsKey("router")?ids.get("router"):ids.get("newrouter");
				NeutronAPI.deleteSubnetInterfaces(routerId, ids.get("subnet"));
			}
    		if(ids.containsKey("subnet")){
    			NeutronAPI.deleteSubnet(ids.get("subnet"));
    		}
    		NeutronAPI.delete(ids.get("network"));
    		if(ids.containsKey("newrouter")){
    			NeutronAPI.deleteRouter(ids.get("newrouter"));
    		}
		}
    }
    
    public static Network createNetDefault(String name) throws Exception{
        Network network=createNetwork(name);
        Subnet subnet = createSubnet(network.getId(), name, "10.1.1.0/24");
        Router router = createRouter(name);
        RouterInterface routerInterface=addSubnetInterfaceToRouter(router.getId(), subnet.getId());
        return getNetDetails(network.getId());
    }
    
    public static Network createNetwork(String networkName) throws Exception {
        Network osNetwork = Builders.network().name(networkName + "_shellNetwork_"+new Date()).adminStateUp(true).build();
        osNetwork = Osp4jSession.getOspSession().networking().network().create(osNetwork);
        CommonAPI.addToMemory(NeutronKey.NEUTRON_NETWORKID, osNetwork.getId());
        return osNetwork != null ? osNetwork: null;
    }

    public static Subnet createSubnet(String networkId, String subnetName, String cidrBlock) throws Exception {
    	if(isValidCidr(cidrBlock)){
	        networkId=CommonAPI.takeFromMemory(NeutronKey.NEUTRON_NETWORKID, networkId);
	        Subnet subnet = Builders.subnet().name(subnetName + "_shellSubnet_"+new Date()).networkId(networkId).ipVersion(IPVersionType.V4).cidr(cidrBlock).enableDHCP(true).build();
	        subnet = Osp4jSession.getOspSession().networking().subnet().create(subnet);
	        CommonAPI.addToMemory(NeutronKey.NEUTRON_SUBNETID, subnet.getId());
	        return subnet != null ? subnet : null;
    	}else{
    		throw new Exception("Invalid CIDR block");
    	}
    }

    public static boolean isValidCidr(String cidrBlock) throws Exception {
    	try{SubnetUtils utils = new SubnetUtils(cidrBlock); return true;}catch(Exception e){}
		return false;
	}
	public static Router createRouter(String routerName) throws Exception {
        // if exists router with external connectivity
        List<? extends Router> routers = getAllRouters();
        if (routers != null && !routers.isEmpty()) {
            for (Router router : routers) {
                String extNetworkId = getExternalNetwork().getId();
                if (extNetworkId != null && extNetworkId.equals(router.getExternalGatewayInfo().getNetworkId())) {
                    return router;
                }
            }
        }
        Router router = Builders.router().name(routerName + "_shellRouter_"+new Date()).adminStateUp(true).externalGateway(getExternalNetwork().getId()).build();
        router = Osp4jSession.getOspSession().networking().router().create(router);
        CommonAPI.addToMemory(NeutronKey.NEUTRON_ROUTERID, router.getId());
        return router != null ? router : null;
    }

    public static Network getExternalNetwork() throws Exception {
        List<? extends Network> networkLst = Osp4jSession.getOspSession().networking().network().list();
        for (Network network : networkLst) {
            if (network.isRouterExternal()) {
                return network;
            }
        }
        return null;
    }

    public static RouterInterface addSubnetInterfaceToRouter(String routerId, String subnetId) throws Exception {
        if (routerId != null && subnetId != null){
            routerId=CommonAPI.takeFromMemory(NeutronKey.NEUTRON_ROUTERID, routerId);
            subnetId=CommonAPI.takeFromMemory(NeutronKey.NEUTRON_SUBNETID, subnetId);
            RouterInterface routerInterface= Osp4jSession.getOspSession().networking().router().attachInterface(routerId, AttachInterfaceType.SUBNET, subnetId);
            CommonAPI.addToMemory(NeutronKey.NEUTRON_SUB_INTERFACEID,routerInterface.getId());
            return routerInterface;
        }
        return null;
    }

    public static void cleanUpNetwork(String networkId, String subnetId, String routerId) throws Exception {
        if (!Strings.isNullOrEmpty(routerId)) {
            if (!Strings.isNullOrEmpty(subnetId)) {
                deleteSubnetInterfaces(routerId, subnetId);
                deleteSubnet(subnetId);
            }

        } else if (!Strings.isNullOrEmpty(subnetId)) {
            deleteSubnet(subnetId);
        }
        if (!Strings.isNullOrEmpty(networkId))
            deleteNetwork(networkId);
    }


    public static void deleteSubnet(String subnetId) throws Exception {
        subnetId=CommonAPI.takeFromMemory(NeutronKey.NEUTRON_SUBNETID, subnetId);
        Osp4jSession.getOspSession().networking().subnet().delete(subnetId);
    }

    public static void deleteRouter(String routerId) throws Exception{
        routerId=CommonAPI.takeFromMemory(NeutronKey.NEUTRON_ROUTERID, routerId);
        Osp4jSession.getOspSession().networking().router().delete(routerId);
    }

    private static Network getNetwork(String networkId) throws Exception {
        networkId=CommonAPI.takeFromMemory(NeutronKey.NEUTRON_NETWORKID, networkId);
        return Osp4jSession.getOspSession().networking().network().get(networkId);
    }

    private static List<? extends Network> getAllNetwork() throws Exception{
        return Osp4jSession.getOspSession().networking().network().list();
    }
    private static Subnet getSubnet(String subnetId) throws Exception {
        subnetId=CommonAPI.takeFromMemory(NeutronKey.NEUTRON_SUBNETID, subnetId);
        return Osp4jSession.getOspSession().networking().subnet().get(subnetId);
    }

    private static Router getRouter(String routerId) throws Exception {
        routerId=CommonAPI.takeFromMemory(NeutronKey.NEUTRON_ROUTERID, routerId);
        return Osp4jSession.getOspSession().networking().router().get(routerId);
    }

    public static String getSubnetIdFromExistingNetwork(String networkId, String cidrBlock) throws Exception {
        Network network = getNetwork(networkId);
        if (network != null) {
            List<? extends Subnet> subnetLst = getSubnets(network);
            if (subnetLst != null && !subnetLst.isEmpty()) {
                for (Subnet subnet : subnetLst) {
                    if (subnet.getCidr().equals(cidrBlock)) {
                        return subnet.getId();
                    }
                }
            }
        }
        return null;
    }
    
    public static boolean isSubnetExists(String cidrBlock) throws Exception{
        List<? extends Network> netList = getAllNetwork();
        for(Network network : netList){
            if(network!=null){
                List<? extends Subnet> subnetLst = getSubnets(network);
                if (subnetLst != null && !subnetLst.isEmpty()) {
                    for (Subnet subnet : subnetLst) {
                        if (subnet!=null && subnet.getCidr().equals(cidrBlock)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static String getRouterFromExistingNetwork(String networkId, String cidrBlock) throws Exception {
        String subnetId = getSubnetIdFromExistingNetwork(networkId, cidrBlock);
        return getRouterFromSubnet(subnetId);
    }

    public static void deleteNetwork(String networkId, String cidrBlock) throws Exception {
        String subnetId = getSubnetIdFromExistingNetwork(networkId, cidrBlock);
        String routerId = getRouterFromSubnet(subnetId);
        if (!Strings.isNullOrEmpty(subnetId) && !Strings.isNullOrEmpty(routerId)) {
            deleteSubnetInterfaces(routerId, subnetId);
            deleteSubnet(subnetId);
            deleteNetwork(networkId);
        } else if (!Strings.isNullOrEmpty(subnetId)) {
            deleteSubnet(subnetId);
            deleteNetwork(networkId);
        }
    }

    public static boolean isRouterDeletionAllowed(String routerId) throws Exception {
        routerId=CommonAPI.takeFromMemory(NeutronKey.NEUTRON_ROUTERID, routerId);
        List<? extends Port> portList = getRouterPortList(routerId);
        if (portList != null && !portList.isEmpty())
            return false;
        return true;
    }


    public static void deleteSubnetInterfaces(String routerId, String subnetId) throws Exception {
        routerId=CommonAPI.takeFromMemory(NeutronKey.NEUTRON_ROUTERID, routerId);
        subnetId=CommonAPI.takeFromMemory(NeutronKey.NEUTRON_SUBNETID, subnetId);
        Osp4jSession.getOspSession().networking().router().detachInterface(routerId, subnetId, null);
    }

    public static void deleteNetwork(String networkId) throws Exception {
        networkId=CommonAPI.takeFromMemory(NeutronKey.NEUTRON_NETWORKID, networkId);
        if (isNetworkDeleteAllowed(networkId)) {
            Osp4jSession.getOspSession().networking().network().delete(networkId);
        }
    }
    
    
    public static List<? extends SecurityGroup> getAllSecurityGroups() throws Exception{
        OSClient os=Osp4jSession.getOspSession();
        return os.networking().securitygroup().list();
    }
    public static List<? extends SecurityGroupRule> getAllSecurityRules(String secGrpId) throws Exception{
        OSClient os=Osp4jSession.getOspSession();
        return os.networking().securitygroup().get(secGrpId).getRules();
    }
    
    public static void deleteSecurityRule(String secRuleId) throws Exception{
        Osp4jSession.getOspSession().networking().securityrule().delete(secRuleId);
    }
    public static void deleteSecurityGroup(String secGrpId) throws Exception{
        Osp4jSession.getOspSession().networking().securitygroup().delete(secGrpId);
    }
    public static void delete(String networkId) throws Exception {
        OSClient os=Osp4jSession.getOspSession();
        networkId=CommonAPI.takeFromMemory(NeutronKey.NEUTRON_NETWORKID, networkId);
        Network network = os.networking().network().get(networkId);
        if (network != null) {
            List<? extends Subnet> subnetLst = network.getNeutronSubnets();
            for(Subnet subnet : subnetLst){
                if(subnet!=null){
                    String subnetId =  subnet.getId();
                    String routerId = getRouterFromSubnet(subnetId);
                    if (!Strings.isNullOrEmpty(subnetId ) && !Strings.isNullOrEmpty(routerId)) {
                        os.networking().router().detachInterface(routerId, subnetId, null);
                        os.networking().subnet().delete(subnetId);
                        if (isNetworkDeleteAllowed(networkId)) {
                            os.networking().network().delete(networkId);
                        }
                    } else if (!Strings.isNullOrEmpty(subnetId)) {
                        os.networking().subnet().delete(subnetId);
                        if (isNetworkDeleteAllowed(networkId)) {
                            os.networking().network().delete(networkId);
                        }
                    }
            }
            }
        }
    }

    public static boolean isNetworkDeleteAllowed(String networkId) throws Exception {
        Network network = Osp4jSession.getOspSession().networking().network().get(networkId);
        List<? extends Subnet> subnetLst = getSubnets(network);
        if (subnetLst != null && !subnetLst.isEmpty())
            return false;
        return true;
    }
    private static List<? extends Subnet> getSubnets(Network network) throws Exception {
        return network.getNeutronSubnets();
    }
    private static String getRouterFromSubnet(String subnetId) throws Exception {
        List<? extends Router> routers = getAllRouters();
        if (routers != null && !routers.isEmpty()) {
            for (Router router : routers) {
                List<? extends Port> portList = getRouterPortList(router.getId());
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
    
    public static Network getNetDetails(String networkId) throws Exception {
        networkId=CommonAPI.takeFromMemory(NeutronKey.NEUTRON_NETWORKID, networkId);
        return Osp4jSession.getOspSession().networking().network().get(networkId);
    }
    
    public static List<? extends Router> getAllRouters() throws Exception {
        OSClient os=Osp4jSession.getOspSession();
        List<? extends Router> routers = os.networking().router().list();
        return routers;
    }
    
    public static Router getRouterDetails(String routerId) throws Exception {
        routerId=CommonAPI.takeFromMemory(NeutronKey.NEUTRON_ROUTERID, routerId);
        OSClient os=Osp4jSession.getOspSession();
        Router router = os.networking().router().get(routerId);
        return router;
    }
    public static List<? extends Port> getRouterPortList(String routerId) throws Exception {
        routerId=CommonAPI.takeFromMemory(NeutronKey.NEUTRON_ROUTERID, routerId);
        return Osp4jSession.getOspSession().networking().port().list(PortListOptions.create().deviceId(routerId));
    }
    public static void printFloatingIpDetails(List<? extends FloatingIP> ips){
        TableBuilder tb=getTableBuilder("Floating");
        for (final FloatingIP ip : ips) {
            addFloatingIpRow(tb, ip);
        }
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }
    public static void printNetsDetails(List<Network> networks){
        TableBuilder tb = getTableBuilder("Net");
        for (final Network network : networks ) {
            addNetRow(tb,network);
        }
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    } 

    public static void printNetDetails(Network network){
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
    private static void addNetRow(TableBuilder tb,Network network){ 
        tb.addRow(network.getId(),network.getName(),network.getStatus().toString(),network.getSubnets().toString(),network.getTenantId());
    }
  

 

}
