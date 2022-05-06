// **********************************************************************************
// Class Name: SimpleHashMap
// Author: Ayhan Mehdiyev
// File: Cookbook/src/main/java/com/company/SimpleHashMap.java
// Description:
//              This class creates a simple replica for the actual HashMap.
// **********************************************************************************
package com.company;

import java.io.Serializable;
import java.util.*;

public class SimpleHashMap<K, V> implements SimpleMap<K, V>, Serializable {

    // Instance & Static Variables
    private static int DEFAULT_INITIAL_CAPACITY = 4; // HashTable size
    private static int MAXIMUM_CAPACITY = 1 << 30; // Maximum HashTable size
    private static float DEFAULT_MAX_LOAD_FACTOR = 0.75f; // Default load factor
    private int capacity; // Current HashTable capacity
    private float loadFactorThreshold; // Load factor within HashTable
    private int size = 0; // Number of entries in this HashMap
    private LinkedList<SimpleMap.Entry<K, V>>[] table; // HashTable

    /** Construct a default HashMap */
    public SimpleHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    /** Construct a HashMap with an initial capacity and a max load factor */
    public SimpleHashMap(int initialCapacity, float loadFactorThreshold) {
        this.capacity = (initialCapacity > MAXIMUM_CAPACITY)? MAXIMUM_CAPACITY: trimToPowerOf2(initialCapacity);
        this.loadFactorThreshold = loadFactorThreshold;
        this.table = new LinkedList[this.capacity];
    }

    /** Add an entry into this HashMap */
    @Override
    public V put(K key, V value) {

        // Check if the key is already in the map
        if (this.get(key) != null) {

            int bucketIndex = this.hash(key.hashCode());

            LinkedList<SimpleMap.Entry<K, V>> bucket = this.table[bucketIndex];
            for (SimpleMap.Entry<K, V> entry : bucket) {

                if (entry.getKey().equals(key)) {

                    V oldValue = entry.getValue();
                    entry.value = value;
                    return oldValue;
                }
            }
        }

        // Check load factor
        if (this.size >= this.capacity * this.loadFactorThreshold) {
            if (this.capacity == MAXIMUM_CAPACITY)
                throw new RuntimeException("Exceeding maximum capacity");

            this.rehash();
        }

        int bucketIndex = this.hash(key.hashCode());

        // Create a linked list for the bucket if not already created
        if (this.table[bucketIndex] == null)
            this.table[bucketIndex] = new LinkedList<>();


        // Add a new entry to the HashTable
        this.table[bucketIndex].add(new SimpleMap.Entry<>(key, value));

        this.size++; // Increase the size

        return value;
    }

    /** Remove any entry from the HashMap */
    @Override
    public void remove(K key) {
        int bucketIndex = this.hash(key.hashCode());

        // Remove the first entry that matches
        if (this.table[bucketIndex] != null) {

            LinkedList<SimpleMap.Entry<K, V>> bucket = this.table[bucketIndex];
            for (SimpleMap.Entry<K, V> entry : bucket) {

                if (entry.getKey().equals(key)) {

                    bucket.remove(entry);
                    size--;
                    break;
                }
            }
        }
    }

    /** Given a key, retrieve the value to it */
    @Override
    public V get(K key) {
        int bucketIndex = this.hash(key.hashCode());

        if (this.table[bucketIndex] != null) {

            LinkedList<SimpleMap.Entry<K, V>> bucket = this.table[bucketIndex];
            for (SimpleMap.Entry<K, V> entry : bucket)

                if (entry.getKey().equals(key))
                    return entry.getValue();
        }

        return null;
    }

    /** Check if any given key is contained within the HashMap */
    @Override
    public boolean containsKey(K key) {
        return this.get(key) != null;
    }

    /** Check if any given value is contained within the HashMap */
    @Override
    public boolean containsValue(V value) {

        for (int i = 0; i < this.capacity; i++) {

            if (this.table[i] != null) {

                LinkedList<SimpleMap.Entry<K, V>> bucket = this.table[i];
                for (SimpleMap.Entry<K, V> entry : bucket)

                    if (entry.getValue().equals(value))
                        return true;
            }
        }

        return false;
    }

    /** Retrieve all the keys in the Map formatted as a Set */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();

        for (int i = 0; i < this.capacity; i++) {

            if (this.table[i] != null) {

                LinkedList<SimpleMap.Entry<K, V>> bucket = this.table[i];
                for (SimpleMap.Entry<K, V> entry : bucket)
                    set.add(entry.getKey());
            }
        }

        return set;
    }
    /** Retrieve all the values in the Map formatted as a general Collection */
    @Override
    public Collection<V> values() {
        ArrayList<V> list = new ArrayList<>();

        for (int i = 0; i < this.capacity; i++) {

            if (this.table[i] != null) {

                LinkedList<SimpleMap.Entry<K, V>> bucket = this.table[i];
                for (SimpleMap.Entry<K, V> entry : bucket)
                    list.add(entry.getValue());
            }
        }

        return list;
    }
    /** Retrieve all the entries in the map formatted as a Set */
    @Override
    public Set<SimpleMap.Entry<K, V>> entrySet() {
        Set<SimpleMap.Entry<K, V>> set = new HashSet<>();

        for (int i = 0; i < this.capacity; i++) {

            if (this.table[i] != null) {

                LinkedList<SimpleMap.Entry<K, V>> bucket = this.table[i];
                set.addAll(bucket);
            }
        }

        return set;
    }

    /** Return the number of entries in this HashMap */
    @Override
    public int size() {
        return this.size;
    }

    /** Remove all entries from this HashMap */
    @Override
    public void clear() {
        this.size = 0;
        this.removeEntries();
    }

    /** Hash */
    private int hash(int hashCode) {
        return supplementalHash(hashCode) & (this.capacity - 1);
    }

    /** Helper function for Hash */
    private static int supplementalHash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /** Return a power of 2 for initial capacity */
    private int trimToPowerOf2(int initialCapacity) {
        int capacity = 1;

        while (capacity < initialCapacity)
            capacity <<= 1;

        return capacity;
    }

    /** Remove all entries */
    private void removeEntries() {
        for (int i = 0; i < this.capacity; i++) {
            if (this.table[i] != null)
                this.table[i].clear();
        }
    }

    /** Rehash function */
    private void rehash() {
        Set<SimpleMap.Entry<K, V>> set = this.entrySet();

        this.capacity <<= 1;
        this.table = new LinkedList[this.capacity];
        this.size = 0;

        for (SimpleMap.Entry<K, V> entry : set)
            this.put(entry.getKey(), entry.getValue());
    }

    /**
     * Convert this object to its most accurate representation as a String
     *
     * @return a String version of this object
     */
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < this.capacity; i++) {
            if (this.table[i] != null && this.table[i].size() > 0) {
                for (SimpleMap.Entry<K, V> entry : this.table[i])
                    builder.append(entry);
            }
        }

        return builder.append("]").toString();
    }

}
