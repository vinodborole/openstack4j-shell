/**
 * @author viborole
 */
package com.openstack4j.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openstack4j.api.OSClient;
import org.openstack4j.core.transport.Config;
import org.openstack4j.openstack.OSFactory;


public class Osp4jSession {
    private static final Properties configprop = new Properties();
    public static void loadProperties(String propertyFilePath){
        InputStream inputStream = null;
        try {
            System.out.println(propertyFilePath);
            inputStream = new FileInputStream(propertyFilePath);
            if (inputStream != null) {
                System.out.println("Loading Properties...");
                configprop.load(inputStream);
                System.out.println("Properties file loaded successfully!!");
            }else{
                System.err.println("Invalid properties file!");
            }
        } catch (final Exception e) {
            System.err.println("Exception: "+e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (final IOException e) {
                    System.err.println("Exception: "+e.getMessage());
                }
            }
        }
    }
    public static Properties getConfigProperties(){
        return configprop;
    }
    public static OSClient getOspSession(){
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
        OSClient os=null;
        String sslEnabled=configprop.getProperty("OS_ENABLE_SSL");
        Boolean isSSLEnabled = new Boolean(sslEnabled); 
        String modifyHttpConfig = configprop.getProperty("OS_MODIFY_HTTP_CONFIG");
        Boolean ismodifyHttpConfig=new Boolean(modifyHttpConfig); 
        if(ismodifyHttpConfig){ 
            System.out.println("with config");
            Config config=Config.newConfig().withMaxConnectionsPerRoute(Integer.parseInt(configprop.getProperty("OS_MAX_ROUTE_CONNECTION"))).withMaxConnections(Integer.parseInt(configprop.getProperty("OS_MAX_CONNECTION")));
            os = OSFactory.builder().withConfig(config).endpoint(configprop.getProperty("OS_AUTH_URL")).credentials(configprop.getProperty("OS_USERNAME"), configprop.getProperty("OS_PASSWORD")).tenantId(configprop.getProperty("OS_TENANT_ID")).useNonStrictSSLClient(isSSLEnabled).authenticate();
        }else{
           os = OSFactory.builder().endpoint(configprop.getProperty("OS_AUTH_URL")).credentials(configprop.getProperty("OS_USERNAME"), configprop.getProperty("OS_PASSWORD")).tenantId(configprop.getProperty("OS_TENANT_ID")).useNonStrictSSLClient(isSSLEnabled).authenticate();
        }
        return os;
    }
    public static void enableLogging(){
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
