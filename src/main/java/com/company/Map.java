// **********************************************************************************
// Class Name: Map
// Author: Ayhan Mehdiyev
// File: Cookbook/src/main/java/com/company/Map.java
// Description:
//              This class creates a basic replica for the actual Map interface.
//              Basically, a HashMap without the hashing, in other terms, a dictionary.
// **********************************************************************************
package com.company;

public class Map<K, V> implements java.io.Serializable {

    // Instance variables
    private java.util.ArrayList<Entry<K, V>> map; // An arraylist to hold all key/value pair entries

    /** Construct a default Map object */
    public Map() {
        this.map = new java.util.ArrayList<Entry<K, V>>();
    }

    /** Add key/value pairs to the Map */
    public V put(K key, V value) {
        // Edge cases:
        // Make sure the key and value are not nulls
        if (key == null || value == null)
            throw new IllegalArgumentException("The key and or value cannot be null!");

        // Make sure the Map does not already contain this key
        if (this.containsKey(key))
            throw new IllegalArgumentException("There is already an entry in the Map with this key!");

        // Create a new entry with the given key/value and add it to the Map
        this.map.add(new Entry<>(key, value));
        return value; // Return the value created
    }

    /** Remove any entry from the map */
    public void remove(K key) {
        // Edge cases:
        // Make sure the key is not null
        if (key == null)
            throw new IllegalArgumentException("The key cannot be null!");

        // Check to see if the Map has the key
        if (this.containsKey(key)) {
            // Iterate through the entry set
            for (Entry<K, V> entry : this.entrySet()) {
                // Check if the entry is equal to the given key
                if (entry.getKey().equals(key)) {
                    // Remove it
                    this.map.remove(entry);
                    // Leave this process
                    return;
                }
            }
        }
    }

    /** Given a key, retrieve the value to it */
    public V get(K key) {
        // Edge cases:
        // Make sure the key is not null
        if (key == null)
            throw new IllegalArgumentException("The key cannot be null!");

        // Check to see if the Map has the key
        if (!this.containsKey(key))
            throw new IllegalArgumentException("The Map does not contain the key!");

        // Because an error would have stopped the execution by now, we know that the key does exist in the Map
        // Therefore, just iterate through the Map and every one of its entries
        for (Entry<K, V> entry : this.map)
            // Make sure the entry is not null
            if (entry != null)
                // Check to see if the key equals to the given key
                if (entry.getKey().equals(key))
                    // Return the value once found
                    return entry.getValue();

        return null; // If it ever reaches this point, which it shouldn't, just return a null value
    }

    /** Check if any given key is contained within the Map */
    public boolean containsKey(K key) {
        // Edge cases:
        // Make sure the key is not null
        if (key == null)
            throw new IllegalArgumentException("The key cannot be null!");

        // Iterate through the Map and every one of its entries
        for (Entry<K, V> entry : this.map)
            // Make sure the entry is not null
            if (entry != null)
                // Check to see if any key equals the given one
                if (entry.getKey().equals(key))
                    return true; // Return true

        return false; // If the method gets past the loop, then return false because the key is not in the map
    }

    /** Check if any given value is contained within the Map */
    public boolean containsValue(V value) {
        // Edge cases:
        // Make sure the value is not null
        if (value == null)
            throw new IllegalArgumentException("The value cannot be null!");

        // Iterate through the Map and every one of its entries
        for (Entry<K, V> entry : this.map)
            // Make sure the entry is not null
            if (entry != null)
                // Check to see if any value equals the given one
                if (entry.getValue().equals(value))
                    return true; // Return true

        return false; // If the method gets past the loop, then return false because the value is not in the map
    }

    /** Retrieve all the keys in the map formatted as a Set */
    public java.util.Set<K> keySet() {
        // Create a new Set
        java.util.Set<K> keys = new java.util.HashSet<K>();

        // Iterate through the Map and every one of its entries
        for (Entry<K, V> entry : this.map)
            // Make sure the entry is not null
            if (entry != null)
                // Add each entry's key to the Set
                keys.add(entry.getKey());

        // Return the Set
        return keys;
    }

    /** Retrieve all the values in the map formatted as a general Collection */
    public java.util.Collection<V> valueSet() {
        // Create a new List
        java.util.List<V> values = new java.util.ArrayList<V>();

        // Iterate through the Map and every one of its entries
        for (Entry<K, V> entry : this.map)
            // Make sure the entry is not null
            if (entry != null)
                // Add each entry's value to the List
                values.add(entry.getValue());

        // Return the List
        return values;
    }

    /** Retrieve all the entries in the map formatted as a Set */
    public java.util.Set<Entry<K, V>> entrySet() {
        // Return a new Set with the contents of the Map
        return new java.util.HashSet<Entry<K, V>>(this.map);
    }

    /** Retrieve the number of entries in this Map */
    public int size() {
        return this.map.size();
    }

    /** Remove all the entries in this Map */
    public void clear() {
        this.map.clear();
    }

    /** Return true if this map has no entries and is empty */
    public boolean isEmpty() {
        return this.map.size() == 0;
    }

    /**
     * Convert this object to its most accurate representation as a String
     *
     * @return a String version of this object
     */
    @Override
    public String toString() {
        // Edge cases:
        // Make sure there are entries in the Map
        if (this.isEmpty())
            return "[]";

        // Create a new StringBuilder object to hold the String
        StringBuilder builder = new StringBuilder("[");

        // Iterate through the Map and every one of its entries
        for (Entry<K, V> entry : this.map)
            // Make sure the entry is not null
            if (entry != null)
                builder.append(entry.toString()); // Add the entry's string to the builder

        // Add the final closing bracket, convert it to a String, and return it
        return builder.append("]").toString();
    }

    /** Inner class to create the nodes of the Map known as an Entry */
    public static class Entry<K, V> implements java.io.Serializable {

        // Instance variables
        private K key; // The key
        private V value; // The value

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
