package com.openstack4j.app.model;

import java.util.List;

public class NetworkModel {
    private String id;
    private String name;
    private String status;
    private List<String> subnets;
    private String tenantId;
    private NetworkModel(NetworkModelBuilder builder){
        this.id=builder.id;
        this.name=builder.name;
        this.status=builder.status;
        this.subnets=builder.subnets;
        this.tenantId=builder.tenantId;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getStatus() {
        return status;
    }
    public List<String> getSubnets() {
        return subnets;
    }
    public String getTenantId() {
        return tenantId;
    }
    
    public static class NetworkModelBuilder{
        private String id;
        private String name;
        private String status;
        private List<String> subnets;
        private String tenantId;
        public NetworkModelBuilder(String id){
            this.id=id;
        }
        public NetworkModelBuilder id(String id){
            this.id=id;
            return this;
        }
        public NetworkModelBuilder name(String name){
            this.name=name;
            return this;
        }
        public NetworkModelBuilder status(String status){
            this.status=status;
            return this;
        }
        public NetworkModelBuilder subnets(List<String> subnets){
            this.subnets=subnets;
            return this;
        }
        public NetworkModelBuilder tenantId(String tenantId){
            this.tenantId=tenantId;
            return this;
        }
        public NetworkModel build(){
            NetworkModel networkModel = new NetworkModel(this);
            return networkModel;
        }
    }

}
