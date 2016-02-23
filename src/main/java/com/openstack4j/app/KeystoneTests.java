/**
 * @author viborole
 */
package com.openstack4j.app;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.openstack.OSFactory;

/**
 * @author Cisco Systems, Inc
 *
 */
public class KeystoneTests {

    /**
     * @author viborole
     */
//    private String userName = "u1";
//    private String userId = "49f50522ae254cf6909cf2d79bcba1b2";
//    private String projectId = "185488958f314ded9e1f777cff1e38f2";
//    private String domainId = "05073226fffc4f9c92c2d99c061d7ab2";
//    private String domainName = "d1";
//    private String projectDomainId = "05073226fffc4f9c92c2d99c061d7ab2";
//    private String password = "testing";
    
    
    
    
    private String userName = "admin";
    private String userId = "3c0e772064df407ea254ab3855e7baa3";
    private String projectId = "185488958f314ded9e1f777cff1e38f2";
    private String domainId = "default";
    private String domainName = "Default";
    private String projectDomainId = "05073226fffc4f9c92c2d99c061d7ab2";
    private String password = "abc123";

    
    
    
    
    private OSClient os;
    private String host;
    public static void main(String[] args) {
        KeystoneTests test = new KeystoneTests();
        System.setProperty("org.apache.commons.logging.Log","org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "DEBUG");
        System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "ERROR");

        //test.authenticate_userId_password_projectId_projectDomainId_Test();
        //test.authenticate_userId_password_domainId_Test();
        test.authenticate_userName_password_domainId_Test();
    }
    
    private String getHost() { 
        if (host == null)
            return "10.193.82.169";
        return host;
    }
    private String authURL(String path) {
        return String.format("http://%s:5000%s", getHost(), path);
    }

    public void authenticate_userId_password_projectId_projectDomainId_Test()  {
        System.out.println("****** authenticate_userId_password_projectId_projectDomainId ************");
        try{
            OSClient os =OSFactory.builderV3()
                    .endpoint(authURL("/v3"))
                    .credentials(userId, password)
                    .scopeToProject(Identifier.byId(projectId), Identifier.byId(projectDomainId))
                    .authenticate();
    
           System.out.println("AuthVersion: "+ os.getToken().getVersion());
           System.out.println("userId: "+os.getAccess().getUser().getId()); 
           System.out.println("Token: "+os.getToken().getId());
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("*************************** END ************************************************");
    }
    
    public void authenticate_userId_password_domainId_Test()  {
        try{
            System.out.println("****** authenticate_userId_password_domainId ************");
            OSClient os =OSFactory.builderV3()
                    .endpoint(authURL("/v3"))
                    .credentials(userId, password)
                    .scopeToDomain(Identifier.byId(domainId))
                    .authenticate();
            System.out.println("AuthVersion: "+ os.getToken().getVersion());
            System.out.println("userId: "+os.getAccess().getUser().getId()); 
            System.out.println("Token: "+os.getToken().getId());
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("*************************** END ************************************************");
    }
    
    public void authenticate_userName_password_domainId_Test() {
        try{
            System.out.println("****** authenticate_userName_password_domainId ************");
            OSClient os =OSFactory.builderV3()
                    .endpoint(authURL("/v3"))
                    .credentials(userName, password, Identifier.byId(domainId)) 
                    .scopeToDomain(Identifier.byId(domainId))
                    .authenticate();
    
            System.out.println("AuthVersion: "+ os.getToken().getVersion());
            System.out.println("userId: "+os.getAccess().getUser().getId()); 
            System.out.println("Token: "+os.getToken().getId());
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("*************************** END ************************************************");
    }
}
