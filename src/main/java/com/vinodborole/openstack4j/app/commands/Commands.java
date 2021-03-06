package com.vinodborole.openstack4j.app.commands;
/**
 * Global Commands ENUM
 * 
 * @author vinod borole
 */
public enum Commands {

    SOURCE("source"),
    NULL("null"),
    PRINT("print"),
    TENANT_LIST("tenant-list"),
    CONFIG("config"),
    NOVA("nova"),
    START("start"),
    STOP("stop"),
    DOWNLOAD("download"),
    RESTART("restart"),
    GLANCE("glance"),
    IMAGE_LIST("image-list"),
    IMAGE_CREATE("image-create"),
    IMAGE_DOWNLOAD("image-download"),
    HELP("help"),
    LOGGING_YES("logging-yes"),
    LOGGING_NO("logging-no"), 
    FLAVOR_LIST("flavor-list"), 
    NEUTRON("neutron"), 
    CINDER("cinder"),
    NET_LIST("net-list"), 
    NET_CREATE_DEFAULT("net-create-default"),
    NET_SHOW("net-show"),
    NET_CREATE("net-create"),
    NET_DELETE("net-delete"),
    ROUTER_CREATE("router-create"),
    ROUTER_DELETE("router-delete"),
    ROUTER_LIST("router-list"),
    ROUTER_SHOW("router-show"),
    ROUTER_INTERFACE_ADD("router-interface-add"),
    ROUTER_INTERFACE_DELETE("router-interface-delete"),
    BOOT("boot"), 
    BOOT_DEFAULT("boot-default"),
    DELETE("delete"), 
    STATUS("status"), 
    RUN("run"), 
    TESTSUITE("testsuite"), 
    FLUSH("flush"), 
    TENANT_INFO("tenant-info"),
    CREATE("create"), 
    CREATE_FROM_IMAGE("create-from-image"), 
    CREATE_FROM_VOLUME_SNAPSHOT("create-from-volume-snapshot"), 
    LIST("list"), 
    VOLUME_ATTACH("volume-attach"), 
    SHOW("show"), 
    VOLUME_DETTACH("volume-dettach"), 
    BOOT_VOLUME("boot-volume"), 
    BOOT_VOLUME_DEFAULT("boot-volume-default"),
    BOOT_CUSTOM("boot-custom"),
    BOOT_VOLUME_CUSTOM("boot-volume-custom"),
    SNAPSHOT("snapshot"), 
    UPLOAD_TO_IMAGE("upload-to-image"), 
    TENANT_ALL_INSTANCES("tenant-all-instances"), 
    TENANT_ALL_VOLUME_SNAPSHOTS("tenant-all-volume-snapshots"), 
    TENANT_ALL_IMAGES("tenant-all-images"), 
    TENANT_ALL_VOLUMES("tenant-all-volumes"), 
    TENANT_ALL_NETWORKS("tenant-all-networks"), 
    TENANT_ALL_ROUTERS("tenant-all-routers"),
    TENANT_ALL_SECURITY_GROUP_RULES("tenant-all-security-group-rules");
    
    
    private String commandString;
    private Commands(String commandString){
        this.commandString=commandString;
    }
    public String getCommandString() {
        return this.commandString;
    }
    public static Commands fromString(String text) {
        if (text != null) {
          for (Commands c : Commands.values()) {
              if(c!=null)
                  if (text.equalsIgnoreCase(c.commandString)) {
                      return c; 
                  }
          }
        }
        return null;
      }
    
    
    public enum Arguments{
        NAME("name"),
        NETID("netid"),
        SIZE("size"),
        ID("id"),
        ROUTERID("routerid"),
        SERVERID("serverid"),
        VOLUMEID("volumeid"),
        IMAGEID("imgid"),
        FLAVORID("flavorid"),
        SUBNETID("subnetid"),
        FILE("file"),
        VCPU("vcpu"),
        RAM("ram"),
        DISK("disk"),
        CIDR("cidr");
        
        private String argString;
        private Arguments(String arg){
            this.argString=arg;
        }
       
        public String getArgString(){
            return this.argString;
        }
        
        public static Arguments fromString(String text) {
            if (text != null) {
              for (Arguments a : Arguments.values()) {
                  if(a!=null)
                      if (text.equalsIgnoreCase(a.argString)) {
                          return a; 
                      }
              }
            }
            return null;
          }
        
    }

}
