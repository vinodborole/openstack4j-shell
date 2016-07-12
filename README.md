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

This project also supports a testsuite feature, where you write all the commands you would like to execute, best part of this feature is it uses an in memory feature to record all the important Id's of the objects created these id's are referred when the command has '$' as a parameter.

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
usage: source <config.properties full path>

usage: help

usage: flush

usage: exit

```
###### Print Commands
```
usage: print help

usage: print config

usage: print tenant-list

usage: print tenant-info
```
###### Glance Commands
```
usage: glance help

usage: glance image-list

usage: glance image-download --file <file> [--help] --id <id> --name <name>
options:
        file <file>   Download location
        help          Help
        id <id>       Image Id
        name <name>   Downloaded file name

usage: glance image-create --file <file> [--help] --name <name>
options:
        file <file>   Image location
        help          Help
        name <name>   Image name
```
###### Nova Commands
```
usage: nova help

usage: nova start [--help] --id <id>
options:
        help      Help
        id <id>   Server Id
        
usage: nova stop [--help] --id <id>
options:
        help      Help
        id <id>   Server Id
        
usage: nova restart [--help] --id <id>
options:
        help      Help
        id <id>   Server Id
        
usage: nova download --file <file> [--help] --id <id> --name <name>
options:
        file <file>   Download location
        help          Help
        id <id>       Server Id
        name <name>   Name of download file
        
usage: nova flavor-list

usage: nova boot --flavorid <flavorid> [--help] --imgid <imgid> --name <name> --netid <netid>
options:
        flavorid <flavorid>   Flavor Id
        help                  Help
        imgid <imgid>         Image Id
        name <name>           Name of server
        netid <netid>         Network Id
        
usage: nova boot-default [--help] --imgid <imgid> --name <name>
options:
        help            Help
        imgid <imgid>   Image Id
        name <name>     Name of server
        
        
usage: nova boot-custom --cidr <cidr> --disk <disk> [--help] --imgid <imgid> --name <name> --ram <ram> --vcpu <vcpu>
options:
        cidr <cidr>     Network CIDR
        disk <disk>     Disk size
        help            Help
        imgid <imgid>   Image Id
        name <name>     Name of server
        ram <ram>       Ram size
        vcpu <vcpu>     VCPU size
        
usage: nova boot-volume --flavorid <flavorid> [--help] --name <name> --netid <netid> --volumeid <volumeid>
options:
        flavorid <flavorid>   Flavor Id
        help                  Help
        name <name>           Name of server
        netid <netid>         Network Id
        volumeid <volumeid>   Volume Id
        
usage: nova boot-volume-default [--help] --name <name> --volumeid <volumeid>
options:
        help                  Help
        name <name>           Name of server
        volumeid <volumeid>   Volume Id

usage: nova boot-volume-custom --cidr <cidr> --disk <disk> [--help] --name <name> --ram <ram> --vcpu <vcpu> --volumeid <volumeid>
options:
        cidr <cidr>           Network CIDR
        disk <disk>           Disk size
        help                  Help
        name <name>           Name of server
        ram <ram>             Ram size
        vcpu <vcpu>           VCPU size
        volumeid <volumeid>   Volume Id
               
usage: nova delete [--help] --id <id>
options:
        help      Help
        id <id>   Server Id
        
usage: nova status [--help] --id <id>
options:
        help      Help
        id <id>   Server Id
        
usage: nova snapshot [--help] --id <id> --name <name>
options:
        help          Help
        id <id>       Server Id
        name <name>   Name of snapshot
        
usage: nova list

usage: nova show [--help] --id <id>
options:
        help      Help
        id <id>   Server Id

```

###### Neutron Commands
```
usage: neutron help

usage: neutron net-list

usage: neutron net-show [--help] --id <id>
options:
        help      Help
        id <id>   Network Id
        
usage: neutron net-create [--help] --name <name>
options:
        help          Help
        name <name>   Network name
        
usage: neutron net-delete [--help] --id <id>
options:
        help      Help
        id <id>   Network Id
        
usage: neutron router-create [--help] --name <name>
options:
        help          Help
        name <name>   Router name
        
usage: neutron router-delete [--help] --id <id>
options:
        help      Help
        id <id>   Router Id

usage: neutron router-list

usage: neutron router-show [--help] --id <id>
options:
        help      Help
        id <id>   Router Id
        
usage: neutron router-interface-add [--help] --id <id> --subnetid <subnetid>
options:
        help                  Help
        id <id>               Router Id
        subnetid <subnetid>   Subnet Id
        
usage: neutron router-interface-delete [--help] --id <id>
options:
        help      Help
        id <id>   Router Id
        
usage: neutron net-create-default [--help] --name <name>
options:
        help          Help
        name <name>   Name of the network

```
###### Cinder Commands
```
usage: cinder help

usage: cinder create [--help] --name <name> --size <size>
options:
        help          Help
        name <name>   Volume name
        size <size>   Size in GB
        
usage: cinder create-from-image [--help] --id <id> --name <name> --size <size>
options:
        help          Help
        id <id>       Image Id
        name <name>   Name
        size <size>   Expected volume size in GB
        
usage: cinder create-from-volume-snapshot [--help] --id <id> --name <name> --size <size>
options:
        help          Help
        id <id>       Snapshot Id
        name <name>   Name
        size <size>   Expected volume size in GB
        
usage: cinder list

usage: cinder show [--help] --id <id>
options:
        help      Help
        id <id>   Volume Id
        
usage: cinder delete [--help] --id <id>
options:
        help      Help
        id <id>   Volume Id
        
usage: cinder upload-to-image [--help] --id <id> --name <name>
options:
        help          Help
        id <id>       Volume Id
        name <name>   image name
        
```
###### Delete Commands
```
usage: delete tenant-all-instances

usage: delete tenant-all-volumes

usage: delete tenant-all-volume-snapshots

usage: delete tenant-all-images

usage: delete tenant-all-networks

usage: delete tenant-all-routers

usage: delete tenant-all-security-group-rules

usage: delete tenant-info

```


