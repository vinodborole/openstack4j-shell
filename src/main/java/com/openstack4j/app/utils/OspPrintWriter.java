package com.openstack4j.app.utils;

public class OspPrintWriter {
    
    public static void main(String[] args) throws Exception {
        String isGBPEnabled="false1";
        String isKeystoneV3Enabled="true";
        String isBootFromVolumeEnabled="true";
        if((isGBPEnabled.equals("true") || isGBPEnabled.equals("false")) && (isKeystoneV3Enabled.equals("true") || isKeystoneV3Enabled.equals("false")) && (isBootFromVolumeEnabled.equals("true") || isBootFromVolumeEnabled.equals("false")) ){
         System.out.println("true");
        } else{
            System.out.println("false");
        }
    }
    

}
