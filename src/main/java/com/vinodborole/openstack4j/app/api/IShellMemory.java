package com.vinodborole.openstack4j.app.api;

/**
 * Shell memory API's
 *  
 * @author vinod borole
 */
public interface IShellMemory<T, V> {

    public void addToMemory(T key, V value);
    public V getFromMemory(T key);
    public void removeFromMemory(T key);
    public void flushMemory();
}
