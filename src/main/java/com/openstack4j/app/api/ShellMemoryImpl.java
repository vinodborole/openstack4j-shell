/**
 * @author viborole
 */
package com.openstack4j.app.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ShellMemoryImpl<T> implements IShellMemory<T> {
    private Map<T, String> memory= new HashMap<T, String>();   
    
    public void addToMemory(T key, String value) {
       memory.put(key, value);
    }

    public String getFromMemory(T key) {
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
