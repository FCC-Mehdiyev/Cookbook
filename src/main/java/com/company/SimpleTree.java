// **********************************************************************************
// Interface Name: SimpleTree
// Author: Ayhan Mehdiyev
// File: Cookbook/src/main/java/com/company/SimpleTree.java
// Description:
//              This interface maps out a simple generic tree framework.
// **********************************************************************************
package com.company;

public interface SimpleTree<E> {

    /** Return true if the element is in the tree */
    public boolean search(E e);

    /** Insert any given element e into the tree */
    public boolean insert(E e);

    /** Delete any given element e from the tree */
    public boolean delete(E e);

    /** Retrieve the number of elements in the tree */
    public int getSize();

    /** Inorder traversal from the root */
    public void inorder();

    /** Postorder traversal from the root */
    public void postorder();

    /** Preorder traversal from the root */
    public void preorder();

    /** Return true if the tree is empty */
    public default boolean isEmpty() {
        return getSize() == 0;
    }

    /** Check if any given Object e is within the tree */
    public default boolean contains(Object e) {
        return search((E) e);
    }

    /** Add any give element e into the tree */
    public default boolean add(E e) {
        return insert(e);
    }

    /** Remove any given Object e from the tree */
    public default boolean remove(Object e) {
        return delete((E) e);
    }

}
