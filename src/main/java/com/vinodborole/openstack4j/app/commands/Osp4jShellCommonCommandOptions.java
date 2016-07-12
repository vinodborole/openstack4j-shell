package com.vinodborole.openstack4j.app.commands;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
/**
 * Common Shell options used in openstack cloud commands
 *  
 * @author vinod borole
 */
public class Osp4jShellCommonCommandOptions {

    //command help
    private static Options novaHelpOptions;
    private static Options glanceHelpOptions;
    private static Options neutronHelpOptions;
    private static Options cinderHelpOptions;
    private static Options printHelpOptions;
    private static Options deleteHelpOptions;
    
    //nova command help
    private static Options novaShowHelpOptions;
    private static Options novaStartHelpOptions;
    private static Options novaRestartHelpOptions;
    private static Options novaStopHelpOptions;
    private static Options novaDownloadHelpOptions;
    private static Options novaBootHelpOptions;
    private static Options novaBootVolumeHelpOptions;
    private static Options novaBootDefaultHelpOptions;
    private static Options novaBootVolumeDefaultHelpOptions;
    private static Options novaBootCustomHelpOptions;
    private static Options novaBootVolumeCustomHelpOptions;
    private static Options novaDeleteHelpOptions;
    private static Options novaStatusHelpOptions;
    private static Options novaSnapshotHelpOptions;
    
    //cinder command help
    private static Options cinderShowHelpOptions;
    private static Options cinderCreateFromSnapshotHelpOptions;
    private static Options cinderCreateFromImageHelpOptions;
    private static Options cindervolumeAttachHelpOptions;
    private static Options cindervolumeDettachHelpOptions;
    private static Options cinderuploadToImageHelpOptions;
    private static Options cinderdeleteHelpOptions;
    private static Options cindercreateHelpOptions;
    //glance command help
    private static Options glanceImageCreateHelpOptions;
    private static Options glanceImageDownloadHelpOptions;
    
    //neutron command help
    private static Options neutronShowHelpOptions;
    private static Options neutronNetCreateHelpOptions;
    private static Options neutronNetDeleteHelpOptions;
    private static Options neutronRouterCreateHelpOptions;
    private static Options neutronRouterDeleteHelpOptions;
    private static Options neutronRouterShowHelpOptions;
    private static Options neutronRouterInterfaceAddHelpOptions;
    private static Options neutronRouterInterfaceDeleteHelpOptions;
    private static Options neutronNetCreateDefaultHelpOptions;
    
    static {
        
        printHelpOptions=constructPrintHelpOptions();
        deleteHelpOptions=constructDeleteHelpOptions();
        
        novaHelpOptions=constructNovaOptions();
        glanceHelpOptions=constructGlanceOptions();
        neutronHelpOptions=constructNeutronOptions();
        cinderHelpOptions=constructCinderOptions();
        
        novaShowHelpOptions=constructNovaShowOptions();
        novaStartHelpOptions=constructNovaStartOptions();
        novaRestartHelpOptions=constructNovaRestartOptions();
        novaStopHelpOptions=constructNovaStopOptions();
        novaDownloadHelpOptions=constructNovaDownloadOptions();
        novaBootHelpOptions=constructNovaBootOptions();
        novaBootVolumeHelpOptions=constructNovaBootVolumeOptions();
        novaBootDefaultHelpOptions=constructNovaBootDefaultOptions();
        novaBootVolumeDefaultHelpOptions=constructNovaBootVolumeDefaultOptions();
        novaBootCustomHelpOptions=constructNovaBootCustomOptions();
        novaBootVolumeCustomHelpOptions=constructNovaBootVolumeCustomOptions();
        novaDeleteHelpOptions=constructNovaDeleteOptions();
        novaStatusHelpOptions=constructNovaStatusOptions();
        novaSnapshotHelpOptions=constructNovaSnapshotOptions();
        
        cinderShowHelpOptions=constructCinderShowOption();
        cinderCreateFromSnapshotHelpOptions=constructCinderCreateFromSnapshotOption();
        cinderCreateFromImageHelpOptions=constructCinderCreateFromImageOption();
        
        cindervolumeAttachHelpOptions=constructCinderVolumeAttachOption();
        cindervolumeDettachHelpOptions=constructCinderVolumeDettachOption();
        cinderuploadToImageHelpOptions=constructCinderUploadToImageOption();
        cinderdeleteHelpOptions=constructCinderDeleteOption();
        cindercreateHelpOptions=constructCinderCreateOption();
        
        
        glanceImageCreateHelpOptions=constructGlanceImageCreateOptions();
        glanceImageDownloadHelpOptions=constructGlanceImageDownloadOptions();
        
        neutronShowHelpOptions=constructNeutronShowOptions();
        neutronNetCreateHelpOptions=constructNeutronNetCreateOptions();
        neutronNetDeleteHelpOptions=constructNeutronNetDeleteOptions();
        neutronRouterCreateHelpOptions=constructNeutronRouterCreateOptions();
        neutronRouterDeleteHelpOptions=constructNeutronRouterDeleteOptions();
        neutronRouterShowHelpOptions=constructNeutronRouterShowOptions();
        neutronRouterInterfaceAddHelpOptions=constructNeutronRouterInterfaceAddOptions();
        neutronRouterInterfaceDeleteHelpOptions=constructNeutronRouterInterfaceDeleteOptions();
        neutronNetCreateDefaultHelpOptions=constructNeutronNetCreateDefaultOptions();
    }
    
    public static Options getCinderVolumeAttachHelpOptions() {
        return cindervolumeAttachHelpOptions;
    }
   
    public static Options getCinderVolumeDettachHelpOptions() {
        return cindervolumeDettachHelpOptions;
    }
    public static Options getCinderUploadToImageHelpOptions() {
        return cinderuploadToImageHelpOptions;
    }
    public static Options getCinderDeleteHelpOptions() {
        return cinderdeleteHelpOptions;
    }
    public static Options getCinderCreateHelpOptions() {
        return cindercreateHelpOptions;
    }
    public static Options getNovaHelpOptions() {
        return novaHelpOptions;
    }
    public static Options getGlanceHelpOptions() {
        return glanceHelpOptions;
    }
    public static Options getNeutronHelpOptions() {
        return neutronHelpOptions;
    }
    public static Options getCinderHelpOptions() {
        return cinderHelpOptions;
    }
    public static Options getNovaShowHelpOptions() {
        return novaShowHelpOptions;
    }
    public static Options getNovaStartHelpOptions() {
        return novaStartHelpOptions;
    }
    public static Options getNovaRestartHelpOptions() {
        return novaRestartHelpOptions;
    }
    public static Options getNovaStopHelpOptions() {
        return novaStopHelpOptions;
    }
    public static Options getNovaDownloadHelpOptions() {
        return novaDownloadHelpOptions;
    }
    public static Options getNovaBootHelpOptions() {
        return novaBootHelpOptions;
    }
    public static Options getNovaBootVolumeHelpOptions() {
        return novaBootVolumeHelpOptions;
    }
    public static Options getNovaBootDefaultHelpOptions() {
        return novaBootDefaultHelpOptions;
    }
    public static Options getNovaBootVolumeDefaultHelpOptions() {
        return novaBootVolumeDefaultHelpOptions;
    }
    public static Options getNovaBootCustomHelpOptions() {
        return novaBootCustomHelpOptions;
    }
    public static Options getNovaBootVolumeCustomHelpOptions() {
        return novaBootVolumeCustomHelpOptions;
    }
    public static Options getNovaDeleteHelpOptions() {
        return novaDeleteHelpOptions;
    }
    public static Options getNovaStatusHelpOptions() {
        return novaStatusHelpOptions;
    }
    public static Options getNovaSnapshotHelpOptions() {
        return novaSnapshotHelpOptions;
    }
    public static Options getCinderShowHelpOptions() {
        return cinderShowHelpOptions;
    }
    public static Options getCinderCreateFromSnapshotHelpOptions() {
        return cinderCreateFromSnapshotHelpOptions;
    }
    public static Options getCinderCreateFromImageHelpOptions() {
        return cinderCreateFromImageHelpOptions;
    }
    public static Options getGlanceImageCreateHelpOptions() {
        return glanceImageCreateHelpOptions;
    }
    public static Options getGlanceImageDownloadHelpOptions() {
        return glanceImageDownloadHelpOptions;
    }
    public static Options getNeutronShowHelpOptions() {
        return neutronShowHelpOptions;
    }
    public static Options getNeutronNetCreateHelpOptions() {
        return neutronNetCreateHelpOptions;
    }
    public static Options getNeutronNetDeleteHelpOptions() {
        return neutronNetDeleteHelpOptions;
    }
    public static Options getNeutronRouterCreateHelpOptions() {
        return neutronRouterCreateHelpOptions;
    }
    public static Options getNeutronRouterDeleteHelpOptions() {
        return neutronRouterDeleteHelpOptions;
    }
    public static Options getNeutronRouterShowHelpOptions() {
        return neutronRouterShowHelpOptions;
    }
    public static Options getNeutronRouterInterfaceAddHelpOptions() {
        return neutronRouterInterfaceAddHelpOptions;
    }
    public static Options getNeutronRouterInterfaceDeleteHelpOptions() {
        return neutronRouterInterfaceDeleteHelpOptions;
    }
    public static Options getNeutronNetCreateDefaultHelpOptions() {
        return neutronNetCreateDefaultHelpOptions;
    }
    public static Options getPrintHelpOptions() {
        return printHelpOptions;
    }
    public static Options getDeleteHelpOptions() {
        return deleteHelpOptions;
    }

    private static Options constructDeleteHelpOptions() {
        Option tenantAllInstance=Option.builder().longOpt(Commands.TENANT_ALL_INSTANCES.getCommandString()).hasArg(false).desc("Delete all tenant instances").build();
        Option tenantAllVolumes=Option.builder().longOpt(Commands.TENANT_ALL_VOLUMES.getCommandString()).hasArg(false).desc("Delete all tenant volumes").build();
        Option tenantAllVolumeSnapshots=Option.builder().longOpt(Commands.TENANT_ALL_VOLUME_SNAPSHOTS.getCommandString()).hasArg(false).desc("Delete all volume snapshots").build();
        Option tenantAllImages=Option.builder().longOpt(Commands.TENANT_ALL_IMAGES.getCommandString()).hasArg(false).desc("Delete all images").build();
        Option tenantAllSecurityGroupRules=Option.builder().longOpt(Commands.TENANT_ALL_SECURITY_GROUP_RULES.getCommandString()).hasArg(false).desc("Delete all security group rules").build();
        Option tenantAllNetworks=Option.builder().longOpt(Commands.TENANT_ALL_NETWORKS.getCommandString()).hasArg(false).desc("Delete all networks").build();
        Option tenantInfo=Option.builder().longOpt(Commands.TENANT_INFO.getCommandString()).hasArg(false).desc("Delete all tenant data (instance, networks, images, volumes, snapshots, routers)").build();
        Option tenantAllRouters=Option.builder().longOpt(Commands.TENANT_ALL_ROUTERS.getCommandString()).hasArg(false).desc("Delete all tenant routers").build();

        final Options options = new Options();
        options.addOption(tenantAllInstance)
        .addOption(tenantAllVolumes)
        .addOption(tenantAllVolumeSnapshots)
        .addOption(tenantAllImages)
        .addOption(tenantAllSecurityGroupRules)
        .addOption(tenantAllNetworks)
        .addOption(tenantInfo)
        .addOption(tenantAllRouters);
        return options;
    }
    private static Options constructPrintHelpOptions() {
        Option help=Option.builder().longOpt(Commands.HELP.getCommandString()).hasArg(false).desc("Show help").build();
        Option config=Option.builder().longOpt(Commands.CONFIG.getCommandString()).hasArg(false).desc("Show config").build();
        Option tenantList=Option.builder().longOpt(Commands.TENANT_LIST.getCommandString()).hasArg(false).desc("Show tenant list").build();
        Option tenantShow=Option.builder().longOpt(Commands.TENANT_INFO.getCommandString()).hasArg(false).desc("Show current tenant details").build();
        final Options options = new Options();
        options.addOption(help)
        .addOption(config)
        .addOption(tenantList)
        .addOption(tenantShow);
        return options;
    }
    private static Options constructNeutronNetCreateDefaultOptions() {
        final Options options = new Options();
        options.addOption(nameOption("Name of the network", true))
        .addOption(helpOption());
        return options;
    }

    private static Options constructNeutronRouterInterfaceDeleteOptions() {
        final Options options = new Options();
        options.addOption(helpOption())
        .addOption(idOption("Router Id",true))
        .addOption(subnetIdOption(true));
        return options;
    }

    private static Options constructNeutronRouterInterfaceAddOptions() {
        final Options options = new Options();
        options.addOption(helpOption())
        .addOption(idOption("Router Id",true))
        .addOption(subnetIdOption(true));
        return options;
    }

    private static Options constructNeutronRouterShowOptions() {
        final Options options = new Options();
        options.addOption(helpOption())
        .addOption(idOption("Router Id",true));
        return options;
    }

    private static Options constructNeutronRouterDeleteOptions() {
        final Options options = new Options();
        options.addOption(helpOption())
        .addOption(idOption("Router Id",true));
        return options;
    }

    private static Options constructNeutronRouterCreateOptions() {
        final Options options = new Options();
        options.addOption(helpOption())
        .addOption(nameOption("Router name",true));
        return options;
    }

    private static Options constructNeutronNetDeleteOptions() {
        final Options options = new Options();
        options.addOption(helpOption())
        .addOption(idOption("Network Id",true));
        return options;
    }

    private static Options constructNeutronNetCreateOptions() {
        final Options options = new Options();
        options.addOption(helpOption())
        .addOption(nameOption("Network name",true));;
        return options;
    }

    private static Options constructNeutronShowOptions() {
        final Options options = new Options();
        options.addOption(helpOption())
        .addOption(idOption("Network Id",true));;
        return options;
    }

    private static Options constructGlanceImageDownloadOptions() {
        final Options options = new Options();
        options.addOption(helpOption())
        .addOption(idOption("Image Id", true))
        .addOption(fileOption("Download location", true))
        .addOption(nameOption("Downloaded file name", true));
        return options;
    }

    private static Options constructGlanceImageCreateOptions() {
        final Options options = new Options();
        options.addOption(helpOption())
        .addOption(fileOption("Image location", true))
        .addOption(nameOption("Image name", true));
        return options;
    }

    private static Options constructNeutronOptions() {
        Option netShow=Option.builder().longOpt(Commands.NET_SHOW.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("netId").desc("Show network details").build();
        Option netCreate=Option.builder().longOpt(Commands.NET_CREATE.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("name").desc("Create a new network").build();
        Option netDelete=Option.builder().longOpt(Commands.NET_DELETE.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("netId").desc("Delete the network").build();
        Option routerCreate=Option.builder().longOpt(Commands.ROUTER_CREATE.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("name").desc("Create a  Router").build();
        Option routerDelete=Option.builder().longOpt(Commands.ROUTER_DELETE.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("routerId").desc("Delete the Router").build();
        Option routerShow=Option.builder().longOpt(Commands.ROUTER_SHOW.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("routerId").desc("Show router details").build();
        Option routerInterfaceAdd=Option.builder().longOpt(Commands.ROUTER_INTERFACE_ADD.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("routerId> <subnetId").desc("Create the router interface for the given subnet").build();
        Option routerInterfaceDelete=Option.builder().longOpt(Commands.ROUTER_INTERFACE_DELETE.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("routerId> <subnetId").desc("Delete the router interface of the given subnet").build();
        Option netCreateDefault=Option.builder().longOpt(Commands.NET_CREATE_DEFAULT.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("name").desc("Create network, subnet and router with default settings").build();
        Option netList=Option.builder().longOpt(Commands.NET_LIST.getCommandString()).valueSeparator(' ').hasArg(false).desc("List networks").build();
        Option routerList=Option.builder().longOpt(Commands.ROUTER_LIST.getCommandString()).valueSeparator(' ').hasArg(false).desc("List Routers").build();
        final Options options = new Options();
        options.addOption(netList)
        .addOption(routerList)
        .addOption(netShow)
        .addOption(netCreate)
        .addOption(netDelete)
        .addOption(routerCreate)
        .addOption(routerDelete)
        .addOption(routerShow)
        .addOption(routerInterfaceAdd)
        .addOption(routerInterfaceDelete)
        .addOption(netCreateDefault)
        .addOption(helpOption());
        return options;
    }

    private static Options constructGlanceOptions() {
        Option imageCreate=Option.builder().longOpt(Commands.IMAGE_CREATE.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("imagePath> <name").desc("Create a new image").build();;
        Option imageDowload=Option.builder().longOpt(Commands.IMAGE_DOWNLOAD.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(3).argName("imageId> <downloadlocation> <name").desc("Download a specific image").build();;
        Option imageList=Option.builder().longOpt(Commands.IMAGE_LIST.getCommandString()).valueSeparator(' ').hasArg(false).desc("List images").build();
        final Options options = new Options();
        options.addOption(imageList)
        .addOption(imageCreate)
        .addOption(imageDowload)
        .addOption(helpOption());
        return options;
    }

    
    private static Options constructNovaOptions(){
        Option show = Option.builder().longOpt(Commands.SHOW.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("serverId").desc("Show server details").build();
        Option start =Option.builder().longOpt(Commands.START.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("serverId").desc("Start server").build();
        Option stop =Option.builder().longOpt(Commands.STOP.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("serverId").desc("Stop server").build();
        Option restart =Option.builder().longOpt(Commands.RESTART.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("serverId").desc("Restart server").build();
        Option download =Option.builder().longOpt(Commands.DOWNLOAD.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(3).argName("serverId> <downloadlocation> <name").desc("Download server").build();
        Option boot =Option.builder().longOpt(Commands.BOOT.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(4).argName("imageId> <flavorId> <netId> <name").desc("Create server").build();
        Option bootVolume =Option.builder().longOpt(Commands.BOOT_VOLUME.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(4).argName("volumeId> <flavorId> <netId> <name").desc("Create server from volume").build();
        Option bootDefault =Option.builder().longOpt(Commands.BOOT_DEFAULT.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("imageId> <name").desc("Create server with default settings").build();
        Option bootVolumeDefault =Option.builder().longOpt(Commands.BOOT_VOLUME_DEFAULT.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("volumeId> <name").desc("Create server from volume with default settings").build();
        Option bootCustom =Option.builder().longOpt(Commands.BOOT_CUSTOM.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(6).argName("imageId> <disk> <ram> <cpu> <cidr> <name").desc("Create server from custom settings").build();
        Option bootVolumeCustom =Option.builder().longOpt(Commands.BOOT_VOLUME_CUSTOM.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(3).argName("volumeId> <cidr> <name").desc("Create server from volume with custom settings").build();
        Option delete =Option.builder().longOpt(Commands.DELETE.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("serverId").desc("Delete server").build();
        Option status =Option.builder().longOpt(Commands.STATUS.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("serverId").desc("Get server status").build();
        Option snapshot =Option.builder().longOpt(Commands.SNAPSHOT.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("serverId> <name").desc("Create snapshot for server").build();
        Option list=Option.builder().longOpt(Commands.LIST.getCommandString()).valueSeparator(' ').hasArg(false).desc("List servers").build();
        Option flavorList=Option.builder().longOpt(Commands.FLAVOR_LIST.getCommandString()).valueSeparator(' ').hasArg(false).desc("List flavors").build();
        final Options options = new Options();
        options.addOption(list)
        .addOption(flavorList)
        .addOption(start)
        .addOption(stop)
        .addOption(restart)
        .addOption(download)
        .addOption(boot)
        .addOption(bootVolume)
        .addOption(bootDefault)
        .addOption(bootVolumeDefault)
        .addOption(bootCustom)
        .addOption(bootVolumeCustom)
        .addOption(delete)
        .addOption(status)
        .addOption(snapshot)
        .addOption(helpOption())
        .addOption(show);
        return options;
    }
    private static Options constructNovaShowOptions(){
        final Options options = new Options();
        options.addOption(idOption("Server Id",true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructNovaStartOptions(){
        final Options options = new Options();
        options.addOption(idOption("Server Id",true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructNovaRestartOptions(){
        final Options options = new Options();
        options.addOption(idOption("Server Id",true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructNovaStopOptions(){
        final Options options = new Options();
        options.addOption(idOption("Server Id",true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructNovaDownloadOptions(){
        final Options options = new Options();
        options.addOption(idOption("Server Id",true))
        .addOption(fileOption("Download location",true))
        .addOption(nameOption("Name of download file",true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructNovaBootOptions(){
        final Options options = new Options();
        options.addOption(imageIdOption(true))
        .addOption(flavorIdOption(true))
        .addOption(netIdOption("Network Id", true))
        .addOption(nameOption("Name of server",true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructNovaBootVolumeOptions(){
        final Options options = new Options();
        options.addOption(volumeIdOption(true))
        .addOption(flavorIdOption(true))
        .addOption(netIdOption("Network Id", true))
        .addOption(nameOption("Name of server",true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructNovaBootDefaultOptions(){
        final Options options = new Options();
        options.addOption(imageIdOption(true))
        .addOption(nameOption("Name of server",true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructNovaBootVolumeDefaultOptions(){
        final Options options = new Options();
        options.addOption(volumeIdOption(true))
        .addOption(nameOption("Name of server",true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructNovaBootCustomOptions(){
        final Options options = new Options();
        options.addOption(imageIdOption(true))
        .addOption(diskOption("Disk size",true))
        .addOption(ramOption("Ram size",true))
        .addOption(vcpuOption("VCPU size",true))
        .addOption(cidrOption("Network CIDR",true))
        .addOption(nameOption("Name of server",true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructNovaBootVolumeCustomOptions(){
        final Options options = new Options();
        options.addOption(volumeIdOption(true))
        .addOption(diskOption("Disk size",true))
        .addOption(ramOption("Ram size",true))
        .addOption(vcpuOption("VCPU size",true))
        .addOption(cidrOption("Network CIDR",true))
        .addOption(nameOption("Name of server",true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructNovaDeleteOptions(){
        final Options options = new Options();
        options.addOption(idOption("Server Id",true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructNovaStatusOptions(){
        final Options options = new Options();
        options.addOption(idOption("Server Id",true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructNovaSnapshotOptions(){
        final Options options = new Options();
        options.addOption(idOption("Server Id",true))
        .addOption(nameOption("Name of snapshot",true))
        .addOption(helpOption());
        return options;
    }
    
    
    private static Options constructCinderOptions() {
        Option show = Option.builder().longOpt(Commands.SHOW.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("volumeId").desc("Show volume details").build();
        Option createFromImage = Option.builder().longOpt(Commands.CREATE_FROM_IMAGE.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("imageId> <size-in-gb> <name").desc("Create volume from Image").build();
        Option createFromVolumeSnapshot = Option.builder().longOpt(Commands.CREATE_FROM_VOLUME_SNAPSHOT.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("snapshotId> <size-in-gb> <name").desc("Create volume from snapshot").build();
        
        Option create = Option.builder().longOpt(Commands.CREATE.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("size-in-gb> <name").desc("Create volume").build();
        Option volumeAttach= Option.builder().longOpt(Commands.VOLUME_ATTACH.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("serverId> <volumeId").desc("Attach volume").build();
        Option volumeDettach= Option.builder().longOpt(Commands.VOLUME_DETTACH.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("serverId> <volumeId").desc("Detach volume").build();
        Option uploadToImage= Option.builder().longOpt(Commands.UPLOAD_TO_IMAGE.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("volumeId> <name").desc("Upload volume to Glance").build();
        Option delete= Option.builder().longOpt(Commands.DELETE.getCommandString()).valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("volumeId").desc("Delete volume").build();
        Option list=Option.builder().longOpt(Commands.LIST.getCommandString()).valueSeparator(' ').hasArg(false).desc("List volumes").build();
        final Options options = new Options();
        options.addOption(list)
                .addOption(createFromImage)
                .addOption(createFromVolumeSnapshot)
                .addOption(helpOption())
                .addOption(show)
                .addOption(volumeAttach)
                .addOption(volumeDettach)
                .addOption(uploadToImage)
                .addOption(delete)
                .addOption(create);
        return options;
    }
    
    private static Options constructCinderCreateOption(){
        final Options options = new Options();
        options.addOption(sizeOption("Size in GB", true))
        .addOption(nameOption("Volume name", true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructCinderVolumeAttachOption(){
        final Options options = new Options();
        options.addOption(idOption("Volume Id",true))
        .addOption(serverIdOption("Server Id",true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructCinderVolumeDettachOption(){
        final Options options = new Options();
        options.addOption(idOption("Volume Id",true))
        .addOption(serverIdOption("Server Id",true))
        .addOption(helpOption());
        return options; 
    }
    private static Options constructCinderUploadToImageOption(){
        final Options options = new Options();
        options.addOption(idOption("Volume Id",true))
        .addOption(nameOption("image name", true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructCinderDeleteOption(){
        final Options options = new Options();
        options.addOption(idOption("Volume Id",true))
        .addOption(helpOption());
        return options;
    }
    
    private static Options constructCinderShowOption(){
        final Options options = new Options();
        options.addOption(idOption("Volume Id",true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructCinderCreateFromImageOption() {
        final Options options = new Options();
        options.addOption(idOption("Image Id", true))
        .addOption(sizeOption("Expected volume size in GB",true))
        .addOption(nameOption("Name",true))
        .addOption(helpOption());
        return options;
    }
    private static Options constructCinderCreateFromSnapshotOption() {
        final Options options = new Options();
        options.addOption(idOption("Snapshot Id", true))
        .addOption(sizeOption("Expected volume size in GB",true))
        .addOption(nameOption("Name",true))
        .addOption(helpOption());
        
        return options;
    }
    private static Option helpOption(){
        return Option.builder().longOpt(Commands.HELP.getCommandString()).desc("Help").build(); 
    }
    private static Option serverIdOption(String desc, boolean required) {
        return Option.builder().hasArg(true).required(required).longOpt(Commands.Arguments.SERVERID.getArgString()).type(String.class).numberOfArgs(1).argName(Commands.Arguments.SERVERID.getArgString()).desc(desc).build();     
    }
    private static Option idOption(String desc, boolean required) {
        return Option.builder().hasArg(true).required(required).longOpt(Commands.Arguments.ID.getArgString()).type(String.class).numberOfArgs(1).argName(Commands.Arguments.ID.getArgString()).desc(desc).build();     
    }
    private static Option volumeIdOption(boolean required) {
        return Option.builder().hasArg(true).required(required).longOpt(Commands.Arguments.VOLUMEID.getArgString()).type(String.class).numberOfArgs(1).argName(Commands.Arguments.VOLUMEID.getArgString()).desc("Volume Id").build();     
    }
    private static Option imageIdOption(boolean required) {
        return Option.builder().hasArg(true).required(required).longOpt(Commands.Arguments.IMAGEID.getArgString()).type(String.class).numberOfArgs(1).argName(Commands.Arguments.IMAGEID.getArgString()).desc("Image Id").build();     
    }
    private static Option flavorIdOption(boolean required) {
        return Option.builder().hasArg(true).required(required).longOpt(Commands.Arguments.FLAVORID.getArgString()).type(String.class).numberOfArgs(1).argName(Commands.Arguments.FLAVORID.getArgString()).desc("Flavor Id").build();     
    }
    private static Option subnetIdOption(boolean required) {
        return Option.builder().hasArg(true).required(required).longOpt(Commands.Arguments.SUBNETID.getArgString()).type(String.class).numberOfArgs(1).argName(Commands.Arguments.SUBNETID.getArgString()).desc("Subnet Id").build();     
    }
    private static Option sizeOption(String desc, boolean required) {
        return Option.builder().hasArg(true).required(required).longOpt(Commands.Arguments.SIZE.getArgString()).type(Integer.class).numberOfArgs(1).argName(Commands.Arguments.SIZE.getArgString()).desc(desc).build();     
    }
    private static Option nameOption(String desc, boolean required) {
        return Option.builder().hasArg(true).required(required).type(String.class).numberOfArgs(1).longOpt(Commands.Arguments.NAME.getArgString()).argName(Commands.Arguments.NAME.getArgString()).desc(desc).build();     
    }
    private static Option fileOption(String desc, boolean required) {
        return Option.builder().hasArg(true).required(required).longOpt(Commands.Arguments.FILE.getArgString()).type(String.class).numberOfArgs(1).argName(Commands.Arguments.FILE.getArgString()).desc(desc).build();     
    }
    private static Option netIdOption(String desc, boolean required) {
        return Option.builder().hasArg(true).required(required).longOpt(Commands.Arguments.NETID.getArgString()).type(String.class).numberOfArgs(1).argName(Commands.Arguments.NETID.getArgString()).desc(desc).build();     
    }
    private static Option vcpuOption(String desc, boolean required) {
        return Option.builder().hasArg(true).required(required).longOpt(Commands.Arguments.VCPU.getArgString()).type(Integer.class).numberOfArgs(1).argName(Commands.Arguments.VCPU.getArgString()).desc(desc).build();     
    }
    private static Option ramOption(String desc, boolean required) {
        return Option.builder().hasArg(true).required(required).longOpt(Commands.Arguments.RAM.getArgString()).type(Integer.class).numberOfArgs(1).argName(Commands.Arguments.RAM.getArgString()).desc(desc).build();     
    }
    private static Option diskOption(String desc, boolean required) {
        return Option.builder().hasArg(true).required(required).longOpt(Commands.Arguments.DISK.getArgString()).type(Integer.class).numberOfArgs(1).argName(Commands.Arguments.DISK.getArgString()).desc(desc).build();     
    }
    private static Option cidrOption(String desc, boolean required) {
        return Option.builder().hasArg(true).required(required).longOpt(Commands.Arguments.CIDR.getArgString()).type(String.class).numberOfArgs(1).argName(Commands.Arguments.CIDR.getArgString()).desc(desc).build();     
    }    
     
}
