/**
 * @author viborole
 */
package com.openstack4j.app.api;


public interface IShellMemory<T> {

    public void addToMemory(T key, String value);
    public String getFromMemory(T key);
    public void removeFromMemory(T key);
    public void flushMemory();
}
