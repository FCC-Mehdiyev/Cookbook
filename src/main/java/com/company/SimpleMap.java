// **********************************************************************************
// Interface Name: SimpleMap
// Author: Ayhan Mehdiyev
// File: Cookbook/src/main/java/com/company/SimpleMap.java
// Description:
//              This interface maps out a simple generic dictionary framework.
// **********************************************************************************
package com.company;

public interface SimpleMap<K, V>  {

    /** Add key/value pairs to the Map */
    public V put(K key, V value);

    /** Remove any entry from the Map */
    public void remove(K key);

    /** Given a key, retrieve the value to it */
    public V get(K key);

    /** Check if any given key is contained within the Map */
    public boolean containsKey(K key);

    /** Check if any given value is contained within the Map */
    public boolean containsValue(V value);

    /** Retrieve all the keys in the Map formatted as a Set */
    public java.util.Set<K> keySet();

    /** Retrieve all the values in the Map formatted as a general Collection */
    public java.util.Collection<V> values();

    /** Retrieve all the entries in the map formatted as a Set */
    public java.util.Set<SimpleMap.Entry<K, V>> entrySet();

    /** Retrieve the number of entries in this Map */
    public int size();

    /** Remove all the entries in this Map */
    public void clear();

    /** Return true if this map has no entries and is empty */
    public default boolean isEmpty() {
        return this.size() == 0;
    }


    /** Inner class to create the nodes of the Map known as an Entry */
    public static class Entry<K, V> implements java.io.Serializable {

        // Instance variables
        public K key; // The key
        public V value; // The value

        /** Construct a copy object */
        public Entry(Entry<K, V> entry) {
            this.key = entry.getKey();
            this.value = entry.getValue();
        }

        /** Construct an object with the key and value */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /** Get the key */
        public K getKey() {
            return this.key;
        }

        /** Get the value */
        public V getValue() {
            return this.value;
        }

        /**
         * Convert this object to its most accurate representation as a String
         *
         * @return a String version of this object
         */
        @Override
        public String toString() {
            return "[" + this.key + ", " + this.value + "]";
        }

    }

}
