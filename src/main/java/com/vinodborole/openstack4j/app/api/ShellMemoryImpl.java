/**
 * @author viborole
 */
package com.vinodborole.openstack4j.app.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ShellMemoryImpl<T, V> implements IShellMemory<T,V> {
    private Map<T, V> memory= new HashMap<T, V>();   
    
    public void addToMemory(T key, V value) {
       memory.put(key, value);
    }

    public V getFromMemory(T key) {
        return memory.get(key);
    }

    public void removeFromMemory(T key) {
        memory.remove(key);
    }
    public void flushMemory() {
        Iterator<T> entriesIterator = memory.keySet().iterator();
        while (entriesIterator.hasNext()) {
            memory.remove(entriesIterator.next().toString());
        }  
    }

} 
