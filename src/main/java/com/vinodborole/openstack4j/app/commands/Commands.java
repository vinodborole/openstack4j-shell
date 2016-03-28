package com.vinodborole.openstack4j.app.commands;

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
    BOOT("boot"), 
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

}
