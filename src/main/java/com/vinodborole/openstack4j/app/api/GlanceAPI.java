package com.vinodborole.openstack4j.app.api;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.Payload;
import org.openstack4j.model.common.Payloads;
import org.openstack4j.model.image.ContainerFormat;
import org.openstack4j.model.image.DiskFormat;
import org.openstack4j.model.image.Image;

import com.vinodborole.openstack4j.app.Osp4jSession;
import com.vinodborole.openstack4j.app.utils.TableBuilder;

/**
 * Glance API
 *  
 * @author vinod borole
 */
public class GlanceAPI { 
    
    protected enum GlanceKey{
        IMAGE_ID;
    }
    
    public static Image imageCreate(String imagePath, String name) throws IOException {
    OSClient os=Osp4jSession.getOspSession();
    Image image = os.images().reserve(Builders.image().name(name).build());
    File file = new File(imagePath);
    final Payload<File> payload = Payloads.create(file);
    image = image.toBuilder().containerFormat(ContainerFormat.BARE).diskFormat(DiskFormat.QCOW2).build();
    try{
        image= os.images().upload(image.getId(), payload, image);   
    }catch(MessageBodyProviderNotFoundException e){
        image=os.images().get(image.getId());
        if(image.getStatus().equals(Image.Status.ACTIVE) && file.length()==image.getSize()){
            //success
        }else{
            System.out.println("Upload failed!");
            return image;
        }
    }
    System.out.println("Upload success!");
    CommonAPI.addToMemory(GlanceKey.IMAGE_ID, image.getId());
    return image;
  }
    
    public static boolean monitorImageUpload(final String imageId) throws Exception {
        boolean isActive = false;
        final Image Img = Osp4jSession.getOspSession().images().get(imageId);
        while (!isActive) {
            isActive = isImageActive(Osp4jSession.getOspSession(), imageId);
        }
        return isActive;
    }

    private static boolean isImageActive(OSClient os, final String imageId) throws Exception {
        final Image Img = os.images().get(imageId);
        Image.Status status = Img.getStatus();
        System.out.println("current Image status: "+status.toString());
        if ((Image.Status.ACTIVE.equals(status))) {
            return true;
        } else {
            Thread.sleep(10000);
        }
        return false;
    }
    
    public static Image getImageDetail(String imageId) throws Exception{
        imageId=CommonAPI.takeFromMemory(GlanceKey.IMAGE_ID,imageId);
        OSClient os=Osp4jSession.getOspSession();
        return os.images().get(imageId);
    }
     
    public static void delete(String id) throws Exception {
        OSClient os=Osp4jSession.getOspSession();
        os.images().delete(id);
    }      
    public static List<? extends Image> imageList() throws Exception{
        OSClient os=Osp4jSession.getOspSession();
        final List<? extends Image> images = os.images().list();
        return images;
    }
    
    public static void printImagesDetails(List<? extends Image> images){
        TableBuilder tb=getImageTableBuilder();
        for (final Image image : images ) {
            addImageRow(tb,image);
        }
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }

    public static void printImageDetails(Image image){
        TableBuilder tb = getImageTableBuilder();
        addImageRow(tb,image);
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }
        
    private static TableBuilder getImageTableBuilder() {
        TableBuilder tb = new TableBuilder();
        tb.addRow("Image Id", "Image Name", "Image Status","Image Size","Container Format", "Is Public");
        tb.addRow("--------","-----------","-------------","-----------","-----------------","----------");
        return tb;
     }
    private static void addImageRow(TableBuilder tb,Image image){
        String id = image.getId();
        String name = image.getName();
        String status = image.getStatus()!=null?image.getStatus().toString():"NA";
        String readableFileSize = readableFileSize(image.getSize()!=null?image.getSize():0l);
        String containerFormat = image.getContainerFormat()!=null?image.getContainerFormat().toString():"NA";
        String isPublic = String.valueOf(image.isPublic());
        tb.addRow(id,name,status,readableFileSize,containerFormat,isPublic);
    }

    public static String readableFileSize(long size) {
        if(size <= 0) return "0";
        final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
  
}
