/**********************************************************************
 NodeCB.java is the Node class used in the clipBdLinkedList class as
 the container for its singly linked list with a tail.

 @author Jason Rupright
 @version 4/10/2019
 **********************************************************************/

package com.example.encryptdecrypt;
public class NodeCB {

    /** store value of list index data type for NodeCB is index */
    private Integer index;

    /** store location of top of new linked list type NodeD */
    private NodeD<Character> topOfClipBoard;

    /** store location of node top */
    private NodeCB top;

    /** store value of node tail */
    private NodeCB tail;

    /** store location of node next */
    private NodeCB next;

    /******************************************************************
     * NodeCB simple default constructor, calls super
     *
     * @return nothing
     ******************************************************************/
    public NodeCB() {
        super();
    }

    /******************************************************************
     * isFlagged constructor method takes several arguments from user
     * sets them all.
     *
     * @param index Integer type stores data type index for NodeCB
     * @param topOfClipBoard NodeD<Character> type location of top of
     *                       another NodeD type Linked list.
     * @param next NodeCB stores next node location
     * @return nothing
     ******************************************************************/
    public NodeCB(Integer index, NodeD<Character> topOfClipBoard, NodeCB next) {
        this.index = index;
        this.topOfClipBoard = topOfClipBoard;
        this.next = next;
    }

    /******************************************************************
     * getIndex helper Method returns data index for the NodeD class.
     *
     * @return int for index is the data type of the NodeCB class
     ******************************************************************/
    public int getIndex() {
        return index;
    }

    /******************************************************************
     * getTopOfClipBoard helper Method returns top location for NodeD
     * Double Linked List.
     *
     * @return NodeD type points to top of another list
     ******************************************************************/
    public NodeD getTopOfClipBoard() {
        return topOfClipBoard;
    }

    /******************************************************************
     * getNext helper Method return pointer to next NodeCB location.
     *
     * @return NodeCB type return pointer to next node.
     ******************************************************************/
    public NodeCB getNext() {
        return next;
    }
}
