
package com.vinodborole.openstack4j.app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.openstack.OSFactory;

/**
 * Creates and maintains the session information with Openstack cloud
 * 
 * @author vinod borole
 */
public class Osp4jSession {
    private static final Properties configprop = new Properties();
    private static OSClient os;
    public static void loadProperties(String propertyFilePath){
        System.out.println(propertyFilePath);
        try{
            readProp(propertyFilePath);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    private static void readProp(String propertyFilePath) throws IOException, FileNotFoundException {
        try(InputStream inputStream = new FileInputStream(propertyFilePath)){
            if (inputStream != null) {
                System.out.println("Loading Properties...");
                configprop.load(inputStream);
                System.out.println("Properties file loaded successfully!!");
            }else{
                System.err.println("Invalid properties file!");
            }
        }
    }
    public static Properties getConfigProperties(){
        return configprop;
    }
    public static OSClient getOspSession(){
        if(os!=null){
            return os;
        }
        if(configprop.size()>1){
            String loggingEnabled = configprop.getProperty("OS_ENABLE_LOGGING");
            Boolean isLoggingEnabled=new Boolean(loggingEnabled);
            if(isLoggingEnabled){
                enableLogging();
            }
            final OSClient os = buildOSClient();
            if(os==null){
                System.err.println("Please verify the config passed");
            }
            return os;
        }else{
            System.err.println("Please source config.properties.");
            return null;
        }
    }
    
    private static OSClient buildOSClient() {
        String sslEnabled=configprop.getProperty("OS_ENABLE_SSL");
        Boolean isSSLEnabled = new Boolean(sslEnabled); 
        String v3Authentication=configprop.getProperty("OS_ENABLE_V3_AUTHENTICATION");
        Boolean v3AuthenticationEnabled = new Boolean(v3Authentication);
        if(v3AuthenticationEnabled)
            os = OSFactory.builderV3().useNonStrictSSLClient(isSSLEnabled).scopeToProject(Identifier.byId(configprop.getProperty("OS_TENANT_ID")), Identifier.byName(configprop.getProperty("OS_DOMAIN")))
                .credentials(configprop.getProperty("OS_USERNAME"), configprop.getProperty("OS_PASSWORD"), Identifier.byName(configprop.getProperty("OS_DOMAIN"))).endpoint(configprop.getProperty("OS_AUTH_URL"))
                .authenticate();
        else
            os = OSFactory.builderV2().endpoint(configprop.getProperty("OS_AUTH_URL")).credentials(configprop.getProperty("OS_USERNAME"), configprop.getProperty("OS_PASSWORD")).tenantId(configprop.getProperty("OS_TENANT_ID")).useNonStrictSSLClient(isSSLEnabled).authenticate();
        
        return os;
    }
    public static void enableLogging(){
      OSFactory.enableHttpLoggingFilter(true);
      System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
      System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
      System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "DEBUG");
      System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "ERROR");
    }
    
    public static void disableLogging(){
        System.clearProperty("org.apache.commons.logging.Log");
        System.clearProperty("org.apache.commons.logging.simplelog.showdatetime");
        System.clearProperty("org.apache.commons.logging.simplelog.log.org.apache.http");
        System.clearProperty("org.apache.commons.logging.simplelog.log.httpclient.wire"); 
    }
}
