Openstack4j-shell
=================

[![Build Status](https://travis-ci.org/vinodborole/openstack4j-shell.svg?branch=master)](https://travis-ci.org/vinodborole/openstack4j-shell) 

Ability to execute openstack4j api's through shell

## Download:

Download [openstack4j-shell] (http://www.vinodborole.com/projects/openstack4j-shell/openstack4j-shell.jar)

## Bug Reports:

* GitHub Issues: [Click Here](https://github.com/vinodborole/openstack4j-shell/issues)

## Build:

In order to build this project use following maven goal
```
mvn -X clean compile assembly:single

```

On successful build it creates a single jar naming "openstack4j-shell.jar". 

To execute this jar from command like use following command
```
java -jar openstack4j-shell.jar
```
This will display the following command prompt 

```
osp>

```

In order to find out what all commands are supported. Please type 

```
osp>help

```

## TestSuite Feature:

This project also support a testsuite feature, where you write all the commands you would like to execute, best part of this feature is it uses an in memory feature to record all the important Id's of the objects created these id's are referred when the command has '$' as a parameter.

In order to run shell for a testsuite use following command
```
java -jar openstack4j-shell.jar testsuite [testsuite.txt location]

```
Please refer to the example testsuite.txt here src\main\resources\testsuite.txt

## Using Openstack4j Shell

Create a config file simlar to the openstack RC file for API access; following is the format

File name should end with '.properties'

Entries expected:
```
OS_AUTH_URL=https://[URL]:5000/v2.0
OS_TENANT_ID=afafagagag
OS_TENANT_NAME=PROJECT_NAME
OS_USERNAME=username
OS_PASSWORD=password
OS_REGION_NAME=region
OS_ENABLE_SSL=true
OS_ENABLE_V3_AUTHENTICATION=false
OS_DOMAIN=
OS_ENABLE_LOGGING=false
```

Execute shell by using command 'java -jar openstack4j-shell.jar', source the config file to the shell using following command
```
osp>source 'config.properties full path'

```
Type 'help' to checkout all the list of commands supported.


## Commands Supported:

###### Basic Commands
```
source <config.properties full path>
help
flush
exit
```
###### Print Commands
```
print help
print config
print tenant-list
print tenant-info
```
###### Glance Commands
```
glance help
glance image-list
glance image-create <imagePath> <name>
glance image-download <imageId> <downloadlocation> <name>
```
###### Nova Commands
```
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
```

###### Neutron Commands
```
neutron help
neutron net-list
```
###### Cinder Commands
```
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
```
###### Delete Commands
```
delete tenant-all-instances
delete tenant-all-volumes
delete tenant-all-volume-snapshots
delete tenant-all-images
delete tenant-all-networks
delete tenant-all-routers
delete tenant-all-security-group-rules
delete tenant-info
```


