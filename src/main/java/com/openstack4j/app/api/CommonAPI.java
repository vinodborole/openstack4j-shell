/**
 * @author viborole
 */
package com.openstack4j.app.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.openstack4j.api.OSClient;
import org.openstack4j.api.image.ImageService;
import org.openstack4j.model.image.Image;

import com.openstack4j.app.Osp4jSession;
import com.openstack4j.app.ShellContext;
import com.openstack4j.app.api.GlanceAPI.GlanceKey;

public class CommonAPI {
      
    public static <T> void addToMemory(T key, String value){
        IShellMemory<T> memory=ShellContext.getContext().getShellMemory();
        memory.addToMemory(key, value);
    }
    public static <T>  String getFromMemory(T key){
        IShellMemory<T> novaMemory=ShellContext.getContext().getShellMemory();
        return novaMemory.getFromMemory(key);
    }
    public static <T>  void removeFromMemory(T key){
        IShellMemory<T> novaMemory=ShellContext.getContext().getShellMemory();
        novaMemory.removeFromMemory(key);
    }
    public static <T> String takeFromMemory(T key , String serverId) {
        if(serverId.equalsIgnoreCase("$")){
            return ShellContext.getContext().getShellMemory().getFromMemory(key);
        }else{
            return serverId;
        }
    }

    public static boolean downloadImage(String imageId, String downloadLocation, String name){
        OSClient os=Osp4jSession.getOspSession();
        ImageService imgService= os.images();
        imageId=takeFromMemory(GlanceKey.IMAGE_ID, imageId);
        Image image=imgService.get(imageId);
        if(image!=null){
            System.out.println("image info: "+image.toString());
            InputStream is =imgService.getAsStream(imageId);
            if(is!=null){
                try{
                    return writer_v1(downloadLocation, name, is);
                }catch(Exception e){
                    e.printStackTrace();
                    System.err.println("Exception : "+e.getMessage());
                    return false;
                }
            }else{
                System.err.println("Download error.. inputstream is null");
                return false;
            }
        }else{
            System.err.println("Image with ID "+imageId+" not found!");
            return false;
        }
    }

    private static boolean writer_v1(String downloadLocation, String name, InputStream is) throws FileNotFoundException, IOException {
        String downLoadFileName = downloadLocation + File.separator + name + ".qcow2";
        FileOutputStream outputStream = new FileOutputStream(new File(downLoadFileName));
        int read = 0;
        final byte[] bytes = new byte[1048576];
        while ((read = is.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
            outputStream.flush();
        } 
        System.out.println("download complete.."+ downLoadFileName);
        return true;
    }
}
