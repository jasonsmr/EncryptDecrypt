/**********************************************************************
 NodeD.java is a container node class that holds the doubly linked
 list data for both the clipBdLinkedList and the DoubleLinkedList.

 @author Jason Rupright
 @version 4/10/2019
 **********************************************************************/
package com.example.encryptdecrypt;

import java.io.Serializable;

public class NodeD<E> implements Serializable {
    /** data stores a generic in the project scope */
    public E data;
    /** next pointer to next NodeD in LinkedList*/
    public NodeD next;
    /** prev points to previous NodeD in LinkedList */
    public NodeD prev;
    /******************************************************************
     * NodeD default constructor for the NodeD class calls on super.
     *
     * @return nothing
     ******************************************************************/
    public NodeD() {
        super();
    }
    /******************************************************************
     * NodeD second constructor for the NodeD class fills NodeD values
     * for data, next, and prev with user requirements.
     *
     * @param data E generic type data store
     * @param next NodeD the next node
     * @param prev NodeD the previous node
     * @return nothing
     ******************************************************************/
    public NodeD(E data, NodeD next, NodeD prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
    /******************************************************************
     * getData helper Method return data.
     *
     * @return E type data generic
     ******************************************************************/
    public E getData() {
        return data;
    }
    /******************************************************************
     * setData helper Method sets data
     *
     * @return void
     ******************************************************************/
    public void setData(E data2) {
        this.data = data2;
    }
    /******************************************************************
     * getNext helper Method returns next node in list
     *
     * @return NodeD type for next node in list
     ******************************************************************/
    public NodeD getNext() {
        return next;
    }
    /******************************************************************
     * setNext helper Method sets next node in list
     *
     * @param next NodeD next node user input
     * @return void
     ******************************************************************/
    public void setNext(NodeD next) {
        this.next = next;
    }
    /******************************************************************
     * getPrev helper Method returns previous node in list
     *
     * @return NodeD type for previous node in list
     ******************************************************************/
    public NodeD getPrev() {
        return prev;
    }
    /******************************************************************
     * setPrev helper Method sets previous node in list.
     *
     * @param prev NodeD previous node user input
     * @return void
     ******************************************************************/
    public void setPrev(NodeD prev) {
        this.prev = prev;
    }
}
