package com.vinodborole.openstack4j.app.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.compute.ServerService;
import org.openstack4j.api.storage.BlockVolumeService;
import org.openstack4j.api.storage.BlockVolumeSnapshotService;
import org.openstack4j.model.compute.ActionResponse;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.image.DiskFormat;
import org.openstack4j.model.storage.block.Volume;
import org.openstack4j.model.storage.block.VolumeAttachment;
import org.openstack4j.model.storage.block.VolumeSnapshot;
import org.openstack4j.model.storage.block.VolumeUploadImage;
import org.openstack4j.model.storage.block.options.UploadImageData;

import com.vinodborole.openstack4j.app.Osp4jSession;
import com.vinodborole.openstack4j.app.api.GlanceAPI.GlanceKey;
import com.vinodborole.openstack4j.app.api.NovaAPI.NovaKey;
import com.vinodborole.openstack4j.app.utils.TableBuilder;

public class CinderAPI {

    protected enum CinderKey{
        VOLUME_ID,VOLUME_SNAP_ID;
    }
    private static final String DEVICE_PREFIX = "/dev/vd";

    static char[] charSet;
    static {
        charSet = new char['z' - 'a' + 1];
        for (char i = 'a'; i <= 'z'; i++) {
            charSet[i - 'a'] = i;
        }
    }

    public static Set<String> createVolumes( String name, int volumeCount, int size) throws Exception {
        Set<String> volumeSet = new HashSet<String>();
        try {
            for (int i = 0; i < volumeCount; i++) {
                volumeSet.add(createVolume(size,name + i));
            }
        } catch (Exception e) {
            deleteVolumes(volumeSet);
            throw e;
        }
        return volumeSet;
    }

    public static void detachVolumes( String serverId, Set<String> volumeSet) throws Exception {
        for (String volumeId : volumeSet) {
            detachVolume(serverId, volumeId);
            Thread.sleep(5000);
        }
    }

    public static void deleteVolumes( Set<String> volumeSet) throws Exception {
        for (String volumeId : volumeSet) {
            deleteVolume(volumeId);
            Thread.sleep(5000);
        }
    }

    public static Map<String, String> attachVolumes( Set<String> volumeSet, final String serverId, int startIndex) throws Exception {
        Map<String, String> deviceMap = null;
        try {
            deviceMap = new HashMap<String, String>();
            int deviceIndex = startIndex;
            for (String volumeId : volumeSet) {
                deviceMap.put(volumeId, attachVolume(volumeId, serverId, deviceIndex));
                deviceIndex++;
            }
        } catch (Exception e) {
            detachVolumes(serverId, volumeSet);
            deleteVolumes(volumeSet);
            throw e;
        }
        return deviceMap;
    }
    
    public static VolumeSnapshot getVolumeSnapshot( String volumeId) throws Exception{
        VolumeSnapshot volSnapshot=null;
        volumeId=CommonAPI.takeFromMemory(CinderKey.VOLUME_ID, volumeId);
        BlockVolumeSnapshotService volSnapService = Osp4jSession.getOspSession().blockStorage().snapshots();
        List<? extends VolumeSnapshot> volSnapList=volSnapService.list();
        for(VolumeSnapshot volSnap : volSnapList){
            if(volSnap.getVolumeId().equals(volumeId)){
                volSnapshot=volSnap;
                CommonAPI.addToMemory(CinderKey.VOLUME_SNAP_ID, volSnapshot.getId());
                printVolumeSnapDetails(volSnapshot);
                break;
            }
        }
        return volSnapshot;
    }
    
    public static void deleteVolumeSnapshot(String volumeSnapshotId) throws Exception{
        BlockVolumeSnapshotService volSnapService = Osp4jSession.getOspSession().blockStorage().snapshots();
        volSnapService.delete(volumeSnapshotId);
    }
    
    public static List<? extends VolumeSnapshot> getAllVolumeSnapshots() throws Exception{
        BlockVolumeSnapshotService volSnapService = Osp4jSession.getOspSession().blockStorage().snapshots();
        return volSnapService.list();
    }
     

    public static String createVolume(int size,String name) throws Exception {
        BlockVolumeService volService=Osp4jSession.getOspSession().blockStorage().volumes();
        Volume volume = volService.create(Builders.volume().name(name).description("Storage").size(size).build());
        waitUntilVolumeStatus(volService, volume.getId(), Volume.Status.AVAILABLE);
        printVolumeDetails(volService.get(volume.getId()));
        CommonAPI.addToMemory(CinderKey.VOLUME_ID, volume.getId());
        return volume.getId();
    } 

    public static Volume createVolumeFromImage( String imageId,int size, String name) throws Exception{
        BlockVolumeService volService=Osp4jSession.getOspSession().blockStorage().volumes();
        imageId=CommonAPI.takeFromMemory(GlanceKey.IMAGE_ID, imageId);
        Volume volume=volService.create(Builders.volume().name(name).size(size).imageRef(imageId).bootable(true).build());
        waitUntilVolumeStatus(volService, volume.getId(), Volume.Status.AVAILABLE);        
        printVolumeDetails(volService.get(volume.getId()));
        CommonAPI.addToMemory(CinderKey.VOLUME_ID, volume.getId());
        return volume;
    }
    
    
    public static Volume createVolumeFromVolumeSnap(String volumeSnapId, int size, String name ) throws Exception{
        BlockVolumeService volService=Osp4jSession.getOspSession().blockStorage().volumes();
        volumeSnapId=CommonAPI.takeFromMemory(CinderKey.VOLUME_SNAP_ID, volumeSnapId);
        Volume volume=volService.create(Builders.volume().name(name).size(size).snapshot(volumeSnapId).bootable(true).build());
        waitUntilVolumeStatus(volService, volume.getId(), Volume.Status.AVAILABLE);        
        printVolumeDetails(volService.get(volume.getId()));
        CommonAPI.addToMemory(CinderKey.VOLUME_ID, volume.getId());
        return volume;
    }
    
    public static String uploadVolumeToImage(String volumeId,String imageNameToExport) throws Exception {
        BlockVolumeService volService=Osp4jSession.getOspSession().blockStorage().volumes();
        volumeId=CommonAPI.takeFromMemory(CinderKey.VOLUME_ID, volumeId);
        final VolumeUploadImage img = volService.uploadToImage(volumeId, UploadImageData.create(imageNameToExport).diskFormat(DiskFormat.QCOW2));
        String imageId = img.getImageId();
        boolean result=GlanceAPI.monitorImageUpload(imageId);
        return imageId;
    }
    
    public static boolean deleteVolume( String volumeId) throws Exception {
        boolean isVolDeleted = false;
        BlockVolumeService volService=Osp4jSession.getOspSession().blockStorage().volumes();
        volumeId=CommonAPI.takeFromMemory(CinderKey.VOLUME_ID, volumeId);
        volService.delete(volumeId);
        try {
            final Volume volume = volService.get(volumeId);
            if (volume == null) {
                isVolDeleted = true;
            } else if (Volume.Status.DELETING.equals(volume.getStatus())) {
                while (checkVolumeStatus(volume.getId()) != null) {
                    System.out.println("Waiting for volume to be deleted");
                }
                isVolDeleted = true;
            }

        } catch (final Exception e) {
            throw new Exception("Delete volume interrupted..", e);
        }
        return isVolDeleted;
    }

    public static Volume.Status checkVolumeStatus(String volumeId) throws Exception {
        Volume.Status volumeStatus = null; 
        try {
            BlockVolumeService volService=Osp4jSession.getOspSession().blockStorage().volumes();
            volumeId=CommonAPI.takeFromMemory(CinderKey.VOLUME_ID, volumeId);
            final Volume volume = volService.get(volumeId);
            if (volume == null) {
                System.out.println("volume is deleted ");
            } else {
                System.out.println("Status of volume.." + volume.getName() + " is.." + volume.getStatus());
                volumeStatus = volume.getStatus();
            }
            Thread.sleep(6000);
        } catch (final Exception e) {
            throw new Exception("Volume deletion interrupted..", e);
        }
        return volumeStatus;
    }

    public static String attachVolume( String volumeId, String serverId, int deviceIndex) throws Exception {
        String devicePrefix = DEVICE_PREFIX;
        String localDeviceIndex = devicePrefix + charSet[deviceIndex];
        BlockVolumeService volService=Osp4jSession.getOspSession().blockStorage().volumes();
        volumeId=CommonAPI.takeFromMemory(CinderKey.VOLUME_ID, volumeId);
        serverId=CommonAPI.takeFromMemory(NovaKey.NOVA_SERVERID, serverId);
        ServerService serverService = Osp4jSession.getOspSession().compute().servers();
        org.openstack4j.model.compute.VolumeAttachment volumeAttachment = serverService.attachVolume(serverId, volumeId, localDeviceIndex);
        waitUntilVolumeStatus(volService, volumeId, Volume.Status.IN_USE);
        printVolumeDetails(volService.get(volumeId));
        return localDeviceIndex;
    }

    public static void detachVolume(String serverId, String volumeId) throws Exception {
        BlockVolumeService volService=Osp4jSession.getOspSession().blockStorage().volumes();
        volumeId=CommonAPI.takeFromMemory(CinderKey.VOLUME_ID, volumeId);
        serverId=CommonAPI.takeFromMemory(NovaKey.NOVA_SERVERID, serverId);
        ServerService serverService = Osp4jSession.getOspSession().compute().servers();
        Volume volume = volService.get(volumeId);
        if (volume != null) {
            // Detach volume from server
            final ActionResponse response = serverService.detachVolume(serverId, volumeId);
            waitUntilVolumeStatus(volService, volume.getId(), Volume.Status.AVAILABLE);        
        }
    }

    public static int getvolumeCount(String size, String volumeSize) throws Exception {
        double expectedSize = Double.parseDouble(size);
        double volSize = Double.parseDouble(volumeSize);
        if (expectedSize <= 0) {
            throw new Exception("Expected size can not be zero or negative");
        }
        if (volSize <= 0) {
            throw new Exception("volume size can not be zero or negative");
        }

        double volumeCount = (expectedSize / volSize);
        int count = (int) Math.ceil(volumeCount);
        return count;
    }

    public static Set<String> getVolumesAttachedToServer(String serverId, OSClient os) throws Exception {
        ServerService serverService = os.compute().servers();
        serverId=CommonAPI.takeFromMemory(NovaKey.NOVA_SERVERID, serverId);
        Server server = serverService.get(serverId);
        List<String> volumeList = server.getOsExtendedVolumesAttached();
        Set<String> volSet = new TreeSet<String>();
        for (String volumeId : volumeList) {
            VolumeAttachment attachment = getAttachmentOnVolume(volumeId, os);
            if (!attachment.getDevice().contains("vda")) {
                printVolumeDetails(os.blockStorage().volumes().get(volumeId));
                volSet.add(volumeId);
            }
        }
        System.out.println("Total Attached Volumes: "+volSet.size());
        return volSet;
    }

    public static VolumeAttachment getAttachmentOnVolume(String volumeId, OSClient os) throws Exception {
        BlockVolumeService volService=os.blockStorage().volumes();
        volumeId=CommonAPI.takeFromMemory(CinderKey.VOLUME_ID, volumeId);
        Volume vol = volService.get(volumeId);
        Volume volume = volService.get(volumeId);
        if (volume != null) {
            List<? extends VolumeAttachment> attachmentList = vol.getAttachments();
            VolumeAttachment attachment = attachmentList.get(0);
            return attachment;
        } else {
            throw new Exception("volume with id:" + volumeId + " does not exist");
        }
    }
    
    public static List<? extends Volume> listvolumes() throws Exception{
        BlockVolumeService volService=Osp4jSession.getOspSession().blockStorage().volumes();
        List<? extends Volume> volList=volService.list();
        return volList;
    }
    
    public static Set<String> getAllAvailaibleVolumes() throws Exception{
        Set<String> volSet = new HashSet<String>();
        BlockVolumeService volService=Osp4jSession.getOspSession().blockStorage().volumes();
        List<? extends Volume> volList=volService.list();
        for(Volume vol: volList){
            if(Volume.Status.AVAILABLE.equals(vol.getStatus())){
                volSet.add(vol.getId());
            }
        }
        return volSet;
    }
    
    public static void show(String volumeId) throws Exception {
        BlockVolumeService volService=Osp4jSession.getOspSession().blockStorage().volumes();
        volumeId=CommonAPI.takeFromMemory(CinderKey.VOLUME_ID, volumeId);
        Volume volume=volService.get(volumeId);
        printVolumeDetails(volume);
    }
    
    private static void waitUntilVolumeStatus(BlockVolumeService volService, String volumeId, Volume.Status expectedStatus) throws Exception {
        Volume volume = volService.get(volumeId);
        while (!expectedStatus.equals(volume.getStatus())) {
            if (Volume.Status.ERROR.equals(volume.getStatus())) {
                deleteVolume(volume.getId());
                System.out.println("Error in creating volume");
                throw new Exception("Error in creating volume");
            }
            volume = volService.get(volume.getId());
            Thread.sleep(5000);
        }
    }
    public static void printVolumeDetails(List<? extends Volume> volumes){
        TableBuilder tb=getTableBuilder("Volume");
        for(Volume volume: volumes){
            addVolumeRow(tb, volume);
        }
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }
    public static void printVolumeDetails(Volume volume){
        TableBuilder tb = getTableBuilder("Volume");
        addVolumeRow(tb,volume);
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }
    
    public static void printVolumeSnapDetails(List<? extends VolumeSnapshot> volSnaps){
        TableBuilder tb = getTableBuilder("VolumeSnap");
        for(VolumeSnapshot volSnap : volSnaps){
            addVolumeSnapRow(tb, volSnap);
        }
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }
    public static void printVolumeSnapDetails(VolumeSnapshot volSnap){
        TableBuilder tb = getTableBuilder("VolumeSnap");
        addVolumeSnapRow(tb,volSnap);
        System.out.println(tb.toString());
        System.out.println("TOTAL RECORDS: "+tb.totalrecords());
    }
        
    private static void addVolumeSnapRow(TableBuilder tb, VolumeSnapshot volSnap) {
        tb.addRow(volSnap.getId(),volSnap.getName(),String.valueOf(volSnap.getSize()),volSnap.getStatus().toString(),volSnap.getVolumeId());
    }

    private static void addVolumeRow(TableBuilder tb,Volume volume){
        tb.addRow(volume.getId(), volume.getName(), volume.getMetaData().toString(),String.valueOf(volume.getSize()),volume.getSnapshotId(),volume.getStatus().toString(), volume.getVolumeType());
    }
    private static TableBuilder getTableBuilder(String type) {
        TableBuilder tb = new TableBuilder();
        if(type.equals("Volume")){
        tb.addRow("Volume Id", "Volume Name", "Metadata","Size","SnapshotId","Status","Type");
        tb.addRow("----------", "-------------","----------","------","------","------------","------------");
        }
        else if(type.equals("VolumeSnap")){
            tb.addRow("Volume Snapshot Id", "Name","Size", "Status","Volume Id");
            tb.addRow("-------------------","-----","-------","-----","-------");
        }
        return tb;
     }
}
