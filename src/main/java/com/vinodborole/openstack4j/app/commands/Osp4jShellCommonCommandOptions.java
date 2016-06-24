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
        novaShowHelpOptions=constructNovaOptions();
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
    @Deprecated
    public static Options glanceOptions() {
        Options options = new Options();
        options.addOption(Option.builder("imageCreate").hasArg(true).longOpt("image-create").argName("image-create").desc("Create a new image.").build());
        options.addOption(Option.builder("imageList").hasArg(false).longOpt("image-list").argName("image-list").desc("List images you can access.").build());
        options.addOption(Option.builder("imageDowload").hasArg(true).longOpt("image-download").argName("image-download").desc("Download a specific image.").build());
        return options;
    }
    @Deprecated
    public static Options neutronOptions() {
        Options options = new Options();
        options.addOption(Option.builder("help").longOpt("help").argName("help").desc("Help for neutron commands.").build());
        options.addOption(Option.builder("netlist").hasArg(false).longOpt("net-list").argName("net-list").desc("List networks.").build());
        options.addOption(Option.builder("netshow").hasArg(true).longOpt("net-show").argName("net-show").desc("Show network details").build());
        options.addOption(Option.builder("netcreate").hasArg(true).longOpt("net-create").argName("net-create").desc("Create a new network").build());
        options.addOption(Option.builder("netdelete").hasArg(true).longOpt("net-delete").argName("net-delete").desc("Delete the network").build());
        options.addOption(Option.builder("netcreatedefault").hasArg(true).longOpt("net-create-default").argName("net-create-default").desc("Create network, subnet and router with default settings").build());
        options.addOption(Option.builder("routerlist").hasArg(false).longOpt("router-list").argName("router-list").desc("List Routers.").build());
        options.addOption(Option.builder("routercreate").hasArg(true).longOpt("router-create").argName("router-create").desc("Create a  Routers.").build());
        options.addOption(Option.builder("routerdelete").hasArg(true).longOpt("router-delete").argName("router-delete").desc("Delete the Routers.").build());
        options.addOption(Option.builder("routershow").hasArg(true).longOpt("router-show").argName("router-show").desc("Show router details.").build());
        options.addOption(Option.builder("routerinterfaceadd").hasArg(true).longOpt("router-interface-add").argName("router-interface-add").desc("Create the router interface for the given subnet.").build());
        options.addOption(Option.builder("routerinterfacedelete").hasArg(true).longOpt("router-interface-delete").argName("router-interface-delete").desc("Delete the router interface of the given subnet.").build());
        return options;
    }
    @Deprecated
    public static Options novaOptions() {
        Options options = new Options();
        options.addOption(Option.builder("help").longOpt("help").argName("help").desc("Help for nova commands.").build());
        options.addOption(Option.builder("novastart").hasArg(true).longOpt("start").argName("start").desc("Star server").build());
        options.addOption(Option.builder("novastop").hasArg(true).longOpt("stop").argName("stop").desc("Stop server").build());
        options.addOption(Option.builder("novarestart").hasArg(true).longOpt("restart").argName("restart").desc("Restart server").build());
        options.addOption(Option.builder("novadownload").hasArg(true).longOpt("download").argName("download").desc("Download server").build());
        options.addOption(Option.builder("novaflavorlist").hasArg(false).longOpt("flavor-list").argName("flavor-list").desc("List Flavor.").build());
        options.addOption(Option.builder("novaboot").hasArg(true).longOpt("boot").argName("boot").desc("Create server").build());
        options.addOption(Option.builder("novabootvolume").hasArg(true).longOpt("boot-volume").argName("boot-volume").desc("Create server from volume").build());
        options.addOption(Option.builder("novabootdefault").hasArg(true).longOpt("boot-default").argName("boot-default").desc("Create server with default settings").build());
        options.addOption(Option.builder("novabootvolumedefault").hasArg(true).longOpt("boot-volume-default").argName("boot-volume-default").desc("Create server from volume with default settings").build());
        options.addOption(Option.builder("novabootcustom").hasArg(true).longOpt("boot-custom").argName("boot-custom").desc("Create server from custom settings").build());
        options.addOption(Option.builder("novabootvolumecustom").hasArg(true).longOpt("boot-volume-custom").argName("boot-volume-custom").desc("Create server from volume with custom settings").build());
        options.addOption(Option.builder("novadelete").hasArg(true).longOpt("delete").argName("delete").desc("Delete server").build());
        options.addOption(Option.builder("novastatus").hasArg(true).longOpt("status").argName("status").desc("Get server status").build());
        options.addOption(Option.builder("novasnapshot").hasArg(true).longOpt("snapshot").argName("snapshot").desc("Create snapshot for server").build());
        options.addOption(Option.builder("novaflavorlist").hasArg(false).longOpt("list").argName("list").desc("List Servers.").build());
        options.addOption(Option.builder("novashow").hasArg(true).longOpt("show").argName("show").desc("Show server details").build());
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
        Option netShow=Option.builder("netShow").longOpt("net-show").valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("netId").desc("Show network details").build();
        Option netCreate=Option.builder("netCreate").longOpt("net-create").valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("name").desc("Create a new network").build();
        Option netDelete=Option.builder("netDelete").longOpt("net-delete").valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("netId").desc("Delete the network").build();
        Option routerCreate=Option.builder("routerCreate").longOpt("router-create").valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("name").desc("Create a  Router").build();
        Option routerDelete=Option.builder("routerDelete").longOpt("router-delete").valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("routerId").desc("Delete the Router").build();
        Option routerShow=Option.builder("routerShow").longOpt("router-show").valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("routerId").desc("Show router details").build();
        Option routerInterfaceAdd=Option.builder("routerInterfaceAdd").longOpt("router-interface-add").valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("routerId> <subnetId").desc("Create the router interface for the given subnet").build();
        Option routerInterfaceDelete=Option.builder("routerInterfaceDelete").longOpt("router-interface-delete").valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("routerId> <subnetId").desc("Delete the router interface of the given subnet").build();
        Option netCreateDefault=Option.builder("netCreateDefault").longOpt("net-create-default").valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("name").desc("Create network, subnet and router with default settings").build();
        final Options options = new Options();
        options.addOption("netList", "net-list", false, "List networks")
        .addOption("routerList", "router-list", false, "List Routers")
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
        Option imageCreate=Option.builder("imageCreate").longOpt("image-create").valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("imagePath> <name").desc("Create a new image").build();;
        Option imageDowload=Option.builder("imageDownload").longOpt("image-download").valueSeparator(' ').hasArg(true).numberOfArgs(3).argName("imageId> <downloadlocation> <name").desc("Download a specific image").build();;
        final Options options = new Options();
        options.addOption("imageList", "image-list", false, "List images")
        .addOption(imageCreate)
        .addOption(imageDowload)
        .addOption(helpOption());
        return options;
    }

    
    private static Options constructNovaOptions(){
        Option show = Option.builder("show").longOpt("show").valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("serverId").desc("Show server details").build();
        Option start =Option.builder("start").longOpt("start").valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("serverId").desc("Start server").build();
        Option stop =Option.builder("stop").longOpt("stop").valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("serverId").desc("Stop server").build();
        Option restart =Option.builder("restart").longOpt("restart").valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("serverId").desc("Restart server").build();
        Option download =Option.builder("download").longOpt("download").valueSeparator(' ').hasArg(true).numberOfArgs(3).argName("serverId> <downloadlocation> <name").desc("Download server").build();
        Option boot =Option.builder("boot").longOpt("boot").valueSeparator(' ').hasArg(true).numberOfArgs(4).argName("imageId> <flavorId> <netId> <name").desc("Create server").build();
        Option bootVolume =Option.builder("bootVolume").longOpt("boot-volume").valueSeparator(' ').hasArg(true).numberOfArgs(4).argName("volumeId> <flavorId> <netId> <name").desc("Create server from volume").build();
        Option bootDefault =Option.builder("bootDefault").longOpt("boot-default").valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("imageId> <name").desc("Create server with default settings").build();
        Option bootVolumeDefault =Option.builder("bootVolumeDefault").longOpt("boot-volume-default").valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("volumeId> <name").desc("Create server from volume with default settings").build();
        Option bootCustom =Option.builder("bootCustom").longOpt("boot-custom").valueSeparator(' ').hasArg(true).numberOfArgs(6).argName("imageId> <disk> <ram> <cpu> <cidr> <name").desc("Create server from custom settings").build();
        Option bootVolumeCustom =Option.builder("bootVolumeCustom").longOpt("boot-volume-custom").valueSeparator(' ').hasArg(true).numberOfArgs(3).argName("volumeId> <cidr> <name").desc("Create server from volume with custom settings").build();
        Option delete =Option.builder("delete").longOpt("delete").valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("serverId").desc("Delete server").build();
        Option status =Option.builder("status").longOpt("status").valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("serverId").desc("Get server status").build();
        Option snapshot =Option.builder("snapshot").longOpt("snapshot").valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("serverId> <name").desc("Create snapshot for server").build();
        final Options options = new Options();
        options.addOption("list", "list", false, "List servers")
        .addOption("flavorList", "flavor-list", false, "List flavors")
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
        Option show = Option.builder("show").longOpt("show").valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("volumeId").desc("Show volume details").build();
        Option createFromImage = Option.builder("createFromImage").longOpt("create-from-image").valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("imageId> <size-in-gb> <name").desc("Create volume from Image").build();
        Option createFromVolumeSnapshot = Option.builder("createFromVolumeSnapshot").longOpt("create-from-volume-snapshot").valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("snapshotId> <size-in-gb> <name").desc("Create volume from snapshot").build();
        
        Option create = Option.builder("create").longOpt("create").valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("size-in-gb> <name").desc("Create volume").build();
        Option volumeAttach= Option.builder("volumeAttach").longOpt("volume-attach").valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("serverId> <volumeId").desc("Attach volume").build();
        Option volumeDettach= Option.builder("volumeDettach").longOpt("volume-dettach").valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("serverId> <volumeId").desc("Detach volume").build();
        Option uploadToImage= Option.builder("uploadToImage").longOpt("upload-to-image").valueSeparator(' ').hasArg(true).numberOfArgs(2).argName("volumeId> <name").desc("Upload volume to Glance").build();
        Option delete= Option.builder("delete").longOpt("delete").valueSeparator(' ').hasArg(true).numberOfArgs(1).argName("volumeId").desc("Delete volume").build();
        
        final Options options = new Options();
        options.addOption("list", "list", false, "List volumes")
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
        return Option.builder("help").longOpt("help").desc("Help").build(); 
    }
    private static Option serverIdOption(String desc, boolean required) {
        return Option.builder("serverId").hasArg(true).required(required).longOpt("serverId").type(String.class).numberOfArgs(1).argName("serverid").desc(desc).build();     
    }
    private static Option idOption(String desc, boolean required) {
        return Option.builder("id").hasArg(true).required(required).longOpt("id").type(String.class).numberOfArgs(1).argName("id").desc(desc).build();     
    }
    private static Option volumeIdOption(boolean required) {
        return Option.builder("volumeId").hasArg(true).required(required).longOpt("volumeid").type(String.class).numberOfArgs(1).argName("volumeid").desc("Volume Id").build();     
    }
    private static Option imageIdOption(boolean required) {
        return Option.builder("imgId").hasArg(true).required(required).longOpt("imgid").type(String.class).numberOfArgs(1).argName("imgid").desc("Image Id").build();     
    }
    private static Option flavorIdOption(boolean required) {
        return Option.builder("flavorId").hasArg(true).required(required).longOpt("flavorid").type(String.class).numberOfArgs(1).argName("flavorid").desc("Flavor Id").build();     
    }
    private static Option subnetIdOption(boolean required) {
        return Option.builder("subnetId").hasArg(true).required(required).longOpt("subnetId").type(String.class).numberOfArgs(1).argName("subnetid").desc("Subnet Id").build();     
    }
    private static Option sizeOption(String desc, boolean required) {
        return Option.builder("size").hasArg(true).required(required).longOpt("size").type(Integer.class).numberOfArgs(1).argName("size").desc(desc).build();     
    }
    private static Option nameOption(String desc, boolean required) {
        return Option.builder("name").hasArg(true).required(required).type(String.class).numberOfArgs(1).longOpt("name").argName("name").desc(desc).build();     
    }
    private static Option fileOption(String desc, boolean required) {
        return Option.builder("file").hasArg(true).required(required).longOpt("file").type(String.class).numberOfArgs(1).argName("file").desc(desc).build();     
    }
    private static Option flavorOption(String desc, boolean required) {
        return Option.builder("falvorid").hasArg(true).required(required).longOpt("falvorid").type(String.class).numberOfArgs(1).argName("falvorid").desc(desc).build();     
    }
    private static Option netIdOption(String desc, boolean required) {
        return Option.builder("netid").hasArg(true).required(required).longOpt("netid").type(String.class).numberOfArgs(1).argName("netid").desc(desc).build();     
    }
    private static Option vcpuOption(String desc, boolean required) {
        return Option.builder("vcpu").hasArg(true).required(required).longOpt("vcpu").type(Integer.class).numberOfArgs(1).argName("vcpu").desc(desc).build();     
    }
    private static Option ramOption(String desc, boolean required) {
        return Option.builder("ram").hasArg(true).required(required).longOpt("ram").type(Integer.class).numberOfArgs(1).argName("ram").desc(desc).build();     
    }
    private static Option diskOption(String desc, boolean required) {
        return Option.builder("disk").hasArg(true).required(required).longOpt("disk").type(Integer.class).numberOfArgs(1).argName("disk").desc(desc).build();     
    }
    private static Option cidrOption(String desc, boolean required) {
        return Option.builder("cidr").hasArg(true).required(required).longOpt("cidr").type(String.class).numberOfArgs(1).argName("cidr").desc(desc).build();     
    }    
     
}
