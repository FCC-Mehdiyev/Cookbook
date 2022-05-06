// **********************************************************************************
// Class Name: SimpleBST
// Author: Ayhan Mehdiyev
// File: Cookbook/src/main/java/com/company/SimpleBST.java
// Description:
//              This class creates a simple replica for the actual Binary Search Tree.
// **********************************************************************************
package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class SimpleBST<E extends Comparable<E>> implements SimpleTree<E>, Serializable {

    // Instance Variables
    protected TreeNode<E> root; // The root node of the tree
    protected int size = 0; // How big the tree is
    protected Comparator<E> comparator; // The tree's comparator

    /** Create a default SimpleBST with a natural order comparator */
    public SimpleBST() {
        this.comparator = Comparable::compareTo;
    }

    /** Create a SimpleBST with a specified comparator */
    public SimpleBST(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /** Create a SimpleBST from an array of objects */
    public SimpleBST(E[] objects) {
        this.comparator = Comparable::compareTo;
        for (E object : objects) this.add(object);
    }

    /** Create a SimpleBST from an ArrayList of objects */
    public SimpleBST(ArrayList<E> objects) {
        this.comparator = Comparable::compareTo;
        for (E object : objects) this.add(object);
    }

    /** Empty the tree and fill it with the contents from the given array of objects */
    public void populate(E[] objects) {
        this.clear();
        for (E object : objects) this.add(object);
    }

    /** Empty the tree and fill it with the contents from the given ArrayList of objects */
    public void populate(ArrayList<E> objects) {
        this.clear();
        for (E object : objects) this.add(object);
    }

    /** Return true if the element is in the tree */
    @Override
    public boolean search(E e) {
        TreeNode<E> current = this.root; // Start from the root

        while (current != null) {

            if (this.comparator.compare(e, current.element) < 0)
                current = current.left;

            else if (this.comparator.compare(e, current.element) >0)
                current = current.right;

            else // element matches current.element
                return true; // Element is found
        }

        return false;
    }

    /** Insert any give element e into the BST */
    @Override
    public boolean insert(E e) {
        if (this.root == null)
            this.root = createNewNode(e); // Create a new root

        else {
            // Locate the parent node
            TreeNode<E> parent = null;
            TreeNode<E> current = this.root;
            while (current != null)

                if (this.comparator.compare(e, current.element) < 0) {
                    parent = current;
                    current = current.left;
                }

                else if (this.comparator.compare(e, current.element) > 0) {
                    parent = current;
                    current = current.right;
                }

                else
                    return false; // Duplicate node not inserted

            // Create the new node and attach it to the parent node
            if (this.comparator.compare(e, parent.element) < 0)
                parent.left = createNewNode(e);
            else
                parent.right = createNewNode(e);
        }

        this.size++;
        return true; // Element inserted successfully
    }

    protected TreeNode<E> createNewNode(E e) {
        return new TreeNode<>(e);
    }

    /** Inorder traversal from the root */
    @Override
    public void inorder() {
        inorder(this.root);
    }

    /** Inorder traversal from a subtree */
    protected void inorder(TreeNode<E> root) {
        if (root == null) return;
        inorder(root.left);
        System.out.print(root.element + " ");
        inorder(root.right);
    }

    /** Postorder traversal from the root */
    @Override
    public void postorder() {
        postorder(this.root);
    }

    /** Postorder traversal from a subtree */
    protected void postorder(TreeNode<E> root) {
        if (root == null) return;
        postorder(root.left);
        postorder(root.right);
        System.out.print(root.element + " ");
    }

    /** Preorder traversal from the root */
    @Override
    public void preorder() {
        preorder(this.root);
    }

    /** Preorder traversal from a subtree */
    protected void preorder(TreeNode<E> root) {
        if (root == null) return;
        System.out.print(root.element + " ");
        preorder(root.left);
        preorder(root.right);
    }

    /** Inner class to create the nodes of the Tree known as a TreeNode */
    public static class TreeNode<E extends Comparable<E>> implements Serializable {
        // Instance variables
        protected E element; // The element in the node
        protected TreeNode<E> left; // The TreeNode to the left of this one
        protected TreeNode<E> right; // The Tree Node to the right of this one

        /** Construct a copy object */
        public TreeNode(TreeNode<E> node) {
            element = node.element;
            left = node.left;
            right = node.right;
        }

        /** Construct a TreeNode with an element */
        public TreeNode(E e) {
            element = e;
        }
    }

    /** Retrieve the number of nodes in the tree */
    @Override
    public int getSize() {
        return this.size;
    }

    /** Returns the root of the tree */
    public TreeNode<E> getRoot() {
        return this.root;
    }

    /** Return a path from the root leading to the specified element */
    public ArrayList<TreeNode<E>> path(E e) {
        ArrayList<TreeNode<E>> list = new ArrayList<>();
        TreeNode<E> current = this.root; // Start from the root

        while (current != null) {
            list.add(current); // Add the node to the list

            if (this.comparator.compare(e, current.element) < 0)
                current = current.left;

            else if (this.comparator.compare(e, current.element) > 0)
                current = current.right;

            else
                break;
        }

        return list; // Return an array list of nodes
    }

    /** Delete any given element from the tree */
    @Override
    public boolean delete(E e) {
        //Locate the node to be deleted and also locate its parent node
        TreeNode<E> parent = null;
        TreeNode<E> current = this.root;

        while (current != null) {

            if (this.comparator.compare(e, current.element) < 0) {
                parent = current;
                current = current.left;
            }

            else if (this.comparator.compare(e, current.element) > 0) {
                parent = current;
                current = current.right;
            }

            else
                break; // Element is in the tree pointed at by current
        }

        if (current == null)
            return false; // Element is not in the tree

        // Case 1: current has no left child
        if (current.left == null) {

            // Connect the parent with the right child of the current node
            if (parent == null)
                this.root = current.right;

            else {
                if (this.comparator.compare(e, parent.element) < 0)
                    parent.left = current.right;

                else
                    parent.right = current.right;
            }
        }
        else {
            // Case 2: The current node has a left child.
            // Locate the rightmost node in the left subtree of
            // the current node and also its parent.
            TreeNode<E> parentOfRightMost = current;
            TreeNode<E> rightMost = current.left;

            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right; // Keep going to the right
            }

            // Replace the element in current by the element in rightMost
            current.element = rightMost.element;

            // Eliminate rightmost node
            if (parentOfRightMost.right == rightMost)
                parentOfRightMost.right = rightMost.left;

            else
                // Special case: parentOfRightMost == current
                parentOfRightMost.left = rightMost.left;
        }

        size--;
        return true; // Element deleted successfully
    }


    /** Obtain an iterator. Use inorder. */
    public Iterator<E> iterator() {
        return new InorderIterator();
    }


    /** Inner class to specify the inorder iterator for this tree */
    private class InorderIterator implements Iterator<E> {

        // Instance variables
        private ArrayList<E> list = new ArrayList<>(); // Store the elements in a list
        private int current = 0; // Point to the current element in list

        /** Construct a default InorderIterator */
        public InorderIterator() {
            inorder();
        }

        /** Inorder traversal from the root */
        private void inorder() {
            inorder(root);
        }

        /** Inorder traversal from a subtree */
        private void inorder(TreeNode<E> root) {
            if (root == null) return;
            inorder(root.left);
            list.add(root.element);
            inorder(root.right);
        }

        /** Check if there are more elements for traversing */
        @Override
        public boolean hasNext() {
            return current < list.size();
        }

        /** Get the current element and move to the next */
        @Override
        public E next() {
            return list.get(current++);
        }

        /** Remove the element returned by the last next() */
        @Override
        public void remove() {
            if (current == 0)
                throw new IllegalStateException();

            delete(list.get(--current));
            list.clear();
            inorder();
        }
    }

    /** Remove all elements from the tree */
    public void clear() {
        this.root = null;
        this.size = 0;
    }


}
