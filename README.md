# openstack4j-shell
Ability to execute openstack4j api's through shell

Build:
------
In order to build this project use following maven goal

mvn -X clean compile assembly:single


On successful build it creates a single jar naming "openstack4j-shell.jar". 

To execute this jar from command like use following command
java -jar openstack4j-shell.jar

This will display the following command prompt 

osp>


In order to find out what all commands are supported. Please type 

osp>help


TestSuite:
---------
This project also support a testsuite feature, where you write all the commands you would like to execute, best part of this feature is it uses an in memory feature to record all the important Id's of the objects created these id's are referred when the command has '$' as a parameter.

Please refer to the example testsuite.txt here src\main\resources\testsuite.txt


Commands Supported:
------------------
osp>help

File name should end with '.properties'
Example entries expected:

OS_AUTH_URL=https://[URL]:5000/v2.0
OS_TENANT_ID=afafagagag
OS_TENANT_NAME=PROJECT_NAME
OS_USERNAME=username
OS_PASSWORD=password
OS_REGION_NAME=region
OS_ENABLE_SSL=true
OS_ENABLE_LOGGING=false

help

logging-yes

logging-no

flush

source <config.properties full path>

exit

print help

print config

print tenant-list

print tenant-info


glance help

glance image-list

glance image-create <imagePath> <name>

glance image-download <imageId> <downloadlocation> <name>


nova help

nova start <serverId>

nova stop <serverId>

nova restart <serverId>

nova download <serverId> <downloadlocation> <name>

nova flavor-list

nova boot <imageId> <flavorId> <netId> <name>

nova boot-volume <volumeId> <flavorId> <netId> <name>

nova delete <serverId>

nova status <serverId>

nova snapshot <serverId> <name>


neutron help

neutron net-list


cinder help

cinder create <size-in-gb> <name>

cinder create-from-image <imageId> <size-in-gb> <name>

cinder create-from-volume-snapshot <snapshotId> <size-in-gb> <name>

cinder list

cinder show <volumeId>

cinder volume-attach <serverId> <volumeId>

cinder volume-dettach <serverId> <volumeId>

cinder delete <volumeId>

cinder upload-to-image <volumeId> <name>


delete tenant-all-instances

delete tenant-all-volumes

delete tenant-all-volume-snapshots

delete tenant-all-images

delete tenant-all-networks

delete tenant-all-routers

delete tenant-all-security-group-rules

delete tenant-info

osp>
