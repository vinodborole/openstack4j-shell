/**
 * @author viborole
 */
package com.openstack4j.app.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.image.ImageService;
import org.openstack4j.model.common.Payload;
import org.openstack4j.model.common.Payloads;
import org.openstack4j.model.image.ContainerFormat;
import org.openstack4j.model.image.DiskFormat;
import org.openstack4j.model.image.Image;

import com.openstack4j.app.Osp4jSession;


public class GlanceAPI { 
    public static Image imageCreate(String imagePath, String name, String version) throws IOException {
      System.out.println("imagePath: "+imagePath+", ImageName: "+name +", Upload Version: "+version);
      // Upload Version - 1
      OSClient os=Osp4jSession.getOspSession();
      if("1".equalsIgnoreCase(version)){
        System.out.println("upload version - 1");
        System.out.println("Reserve image...");
       Image image = os.images().reserve(Builders.image().name(name).build());
        System.out.println("Uploading  image: " + name);
        final Payload<File> payload = Payloads.create(new File(imagePath));
        image = image.toBuilder().containerFormat(ContainerFormat.BARE).diskFormat(DiskFormat.QCOW2).build();
       return os.images().upload(image.getId(), payload, image);
      }
      // Upload Version - 2 
      if("2".equalsIgnoreCase(version)){
        System.out.println("not supported!");
      }
      // Upload Version -3
      if("3".equalsIgnoreCase(version)){
          System.out.println("upload version - 3");
          final Payload<File> payload = Payloads.create(new File(imagePath));
          System.out.println("payload: " + payload.open().available());
          Image image = Builders.image().name(name).containerFormat(ContainerFormat.BARE).diskFormat(DiskFormat.QCOW2).build();
          System.out.println("image: " + image.toString());
          System.out.println("Upload Complete...");
          System.out.println("Size: " + image.getSize() + ",Status :" + image.getStatus() + ",DiskFormat :" + image.getDiskFormat());
          return image = os.images().create(image, payload);
      }
    return null;
  }
     
    
    public static List<? extends Image> imageList(){
        OSClient os=Osp4jSession.getOspSession();
        final List<? extends Image> images = os.images().list();
        return images;
    }
    

    
}
