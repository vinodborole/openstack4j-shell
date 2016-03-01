/**
 * @author viborole
 */
package com.openstack4j.app;

import com.openstack4j.app.api.IShellMemory;
import com.openstack4j.app.api.ShellMemoryImpl;


public class ShellContext<T> {

    private static ShellContext INSTANCE = new ShellContext();
    private IShellMemory<T> shellMemory = new ShellMemoryImpl<T>();
    private ShellContext(){}
    public static ShellContext getContext(){
        return INSTANCE;
    }
    public IShellMemory<T> getShellMemory(){
        return shellMemory;
    }
}
