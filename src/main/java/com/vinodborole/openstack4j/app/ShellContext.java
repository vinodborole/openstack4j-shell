/**
 * @author viborole
 */
package com.vinodborole.openstack4j.app;

import com.vinodborole.openstack4j.app.api.IShellMemory;
import com.vinodborole.openstack4j.app.api.ShellMemoryImpl;


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
