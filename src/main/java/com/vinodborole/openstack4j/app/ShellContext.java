
package com.vinodborole.openstack4j.app;

import com.vinodborole.openstack4j.app.api.IShellMemory;
import com.vinodborole.openstack4j.app.api.ShellMemoryImpl;

/**
 * Shell Context holds the shell memory.
 * 
 * @author vinod borole
 */
public class ShellContext<T, V> {

    private static ShellContext INSTANCE = new ShellContext();
    private IShellMemory<T,V> shellMemory = new ShellMemoryImpl<T,V>();
    private ShellContext(){}
    public static ShellContext getContext(){
        return INSTANCE;
    }
    public IShellMemory<T,V> getShellMemory(){
        return shellMemory;
    }

}
