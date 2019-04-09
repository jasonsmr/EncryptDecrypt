/**********************************************************************
 clipBdLinkedList.java holds the singly linked list that is used to
 store the multi clipboard data. A multi clipboard was created to
 emulate Microsoft's new office copy paste command function.

 @author Jason Rupright
 @version 4/10/2019
 **********************************************************************/
package com.example.encryptdecrypt;
public class clipBdLinkedList {
    private NodeCB top;
    private NodeCB tail;
    DoubleLinkedList<Character> tempMessage;
    public clipBdLinkedList() { tail = top = null; }

    /******************************************************************
     * push helper Method add data to top of list
     *
     * @param index Integer type for what value index data type for
     *              this list is index
     * @param message DoubleLinkedList<Character> type for NodeD point
     * @return void
     ******************************************************************/
    public void push(Integer index,
                     DoubleLinkedList<Character> message) {
        //loop to make a deep copy of each NodeD in DoublyLinked List
        tempMessage = new DoubleLinkedList<Character>();
        for(int i = 0; i < message.getLen(); i++){
            tempMessage.push(message.get(i));
        }
        // no list
        if (top == null)
            tail = top = new NodeCB(index, tempMessage.top, top);
        else
            // there is a list
            top = new NodeCB(index, tempMessage.top, top);
    }

    /******************************************************************
     * DoubleLinkedList method to remove the first node
     * of the linked list
     *
     * @return boolean value of button flag status
     ******************************************************************/
    public DoubleLinkedList<Character> get(Integer index) {
        DoubleLinkedList<Character> tempCB = new DoubleLinkedList<>();

        NodeCB temp = top;
        int i = 0;
        if (temp == null)
            return null;
        while(temp != null) {
            i = temp.getIndex();
            if (i == index)
                break;
            temp = temp.getNext();
        }
        if(temp == null)
            return null;
        NodeD<Character> tempD  = temp.getTopOfClipBoard();
        if(tempD == null)
            return null;
        while (tempD != null) {
            tempCB.push(tempD.data);
            tempD = tempD.getNext();
        }
        return tempCB;
    }

    /******************************************************************
     * toString helper Method return String representing the list
     *
     * @return String val representing the list
     ******************************************************************/
    public String toString() {
        String retVal = "";
        NodeCB cur = top;
        while (cur != null) {
            retVal += cur.getIndex();
            cur = cur.getNext();
        }
        return retVal;
    }

    /******************************************************************
     * printReverse helper Method return String for list representation
     *
     * @return String representing list
     ******************************************************************/
    public String printReverse() {
        String temp = toString();
        String temp2 = "";
        for(int i = temp.length(); i <= 0; i++){
            temp2 += temp.charAt(i);
        }
        return temp2;
    }

    /******************************************************************
     * getNthNode helper Method return NodeD at getNthNode
     *
     * @param index int type for index to get
     * @return NodeD at getNthNode
     ******************************************************************/
    public NodeD<Character> getNthNode(int index){
        NodeCB cur = top;
        int count = 0;
        while(cur != null){
            if(count == index)
                return cur.getTopOfClipBoard();
            count++;
            cur = cur.getNext();
        }
        assert(false);
        return null;
    }

    /******************************************************************
     * getLen helper Method return int value of getLen
     *
     * @return int type return for length
     ******************************************************************/
    public int getLen() {
        int count = 0;
        NodeCB temp = top;
        while (temp != null) {
            count++;
            temp = temp.getNext();
        }
        return count;
    }

}
