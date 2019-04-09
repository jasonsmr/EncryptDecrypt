/**********************************************************************
 DoubleLinkedList.java holds the data for the secret encrypted message.

 @author Jason Rupright
 @version 4/10/2019
 **********************************************************************/
package com.example.encryptdecrypt;

public class DoubleLinkedList<E>  {
	/** The first NodeD<E> in the list */
	protected NodeD<E> top;

	/** The current NodeD<E> in the list */
	protected NodeD<E> cursor;

	/******************************************************************
	 * DoubleLinkedList default constructor for the DoubleLinkedList
	 * sets all variable vals to null.
	 * @return nothing
	 ******************************************************************/
	public DoubleLinkedList() {
		top = null;
		cursor = null;
	}

	/******************************************************************
	 * get helper Method return E data for node at a given position
	 *
	 * @param position int type to look at position
	 * @return E type return if found
	 ******************************************************************/
	public E get(int position) {
		cursor = top;
		int i = 0;
		while(i <= position){
			if(cursor == null)
				throw new RuntimeException(
				"LinkedList get cannot pass position Out of Bounds");
			if(i == position)
				return cursor.getData();
			if(cursor.getNext() == null)
				throw new RuntimeException(
				"LinkedList get cannot pass position Out of Bounds");
			cursor = cursor.getNext();
			i++;
		}
		assert(false);
		return null;

	}

	/******************************************************************
	 * getNode helper Method return NodeD on successfully found node
	 * with data type
	 *
	 * @return NodeD return type if data E type found
	 ******************************************************************/
	public NodeD<E> getNode(E data){
		NodeD<E> temp = top;
		if(temp == null)
			return null;
		while(temp != null){
			if(temp.getData() == data)
				return temp;
			temp = temp.getNext();
		}
		assert(false);
		return null;
	}

	/******************************************************************
	 * getIndex helper Method return Integer of index for given data
	 * searches for a node with data E type
	 *
	 * @param data E type to search for
	 * @return boolean value of successful data found
	 ******************************************************************/
	public Integer getIndex(E data){
		NodeD<E> temp = top;
		if(temp == null)
			return null;
		int count = 0;
		while(temp != null){
			if(temp.getData() == data)
				return count;
			temp = temp.getNext();
			count++;
		}
		assert(false);
		return null;
	}

	/******************************************************************
	 * delete helper Method return boolean value of success on delete
	 *
	 * @param data E type to delete
	 * @return boolean value of successful delete from list
	 ******************************************************************/
	public boolean delete(E data)
	{
		Integer position = getIndex(data);
		// If linked list is empty
		if (top == null)
			return false;

		// Store head node
		NodeD temp = top;

		// If head needs to be removed
		if (position == 0)
		{
			top = temp.next;   // Change head
			return true;
		}

		// Find previous node of the node to be deleted
		for (int i=0; temp!=null && i<position-1; i++)
			temp = temp.next;

		// If position is more than number of ndoes
		if (temp == null || temp.next == null)
			return false;

		// Node temp->next is the node to be deleted
		// Store pointer to the next of node to be deleted
		NodeD next = temp.next.next;

		temp.next = next;  // Unlink the deleted node from list
		return true;
	}

	/******************************************************************
	 * push helper Method adds data to top of the list
	 *
	 * @param data E type to add to list
	 * @return void
	 ******************************************************************/
	public void push(E data) {
		// case 1: no list
		if (top == null) {
			top = new NodeD<E>(data, null, null);
		}
		// case 2: is a list
		else {
			top = new NodeD<E>(data, top, null);
			top.getNext().setPrev(top);
		}
	}

	/******************************************************************
	 * append helper Method adds to end of list
	 *
	 * @param data E type to add to list
	 * @return void
	 ******************************************************************/
	public void append(E data){
		NodeD<E> last = getNthNode(getLen()-1);
		NodeD<E> curr = new NodeD<E>();
		curr.setData(data);
		curr.setNext(null);
		if(top == null)
			push(data);
		else{
			curr.setPrev(last);
			last.setNext(curr);
		}
	}

	/******************************************************************
	 * toString helper method that returns a String representing list
	 *
	 * @return String representing the list
	 ******************************************************************/
	public String toString() {
		String retVal = "";
		NodeD<E> cur = top;
		while (cur != null) {
			retVal += cur.getData();
			cur = cur.getNext();
		}

		return retVal;
	}

	/******************************************************************
	 * printReverse helper Method return String representing the list
	 *
	 * @return string representing the list
	 ******************************************************************/
	public String printReverse() {
		String temp = toString();
		String temp2 = "";
		for(int i = temp.length()-1; i >= 0; i--){
			temp2 += temp.charAt(i);
		}
		return temp2;
	}

	/******************************************************************
	 * getNthNode helper Method return NodeD on success fo finding
	 * a node at index
	 *
	 * @param index int where to look for node
	 * @return NodeD id node is found
	 ******************************************************************/
	public NodeD<E> getNthNode(int index){
		NodeD<E> cur = top;
		int count = 0;
		while(cur != null){
			if(count == index)
				return cur;
			count++;
			cur = cur.getNext();
		}
		assert(false);
		return null;
	}

	/******************************************************************
	 * aprend helper Method return NodeD type on success of removing
	 * to end
	 *
	 * @return NodeD value of  success
	 ******************************************************************/
	public NodeD<E> aprend(){
		NodeD<E> last = getNthNode(getLen()-1);
		NodeD<E> temp = last;
		last = last.getPrev();
		last.setNext(null);
		return temp;
	}

	/******************************************************************
	 * insertBefore helper Method return boolean value success insert
	 *
	 * @param data E data to be inserted
	 * @param index int to be inserted at location index
	 * @return boolean value of success
	 ******************************************************************/
	public boolean insertBefore(E data, int index) {
		// case 0: no list or index is 0
		if (top == null || index == 0) {
			push(data);
			return true;
		}
		// case 1: index out of bounds
		if (index < 0 || index > getLen())
			return false;
		// case 3: add to bottom;
		if (index == getLen()) {
			append(data);
			return true;
		}
		//get data at index passed
		NodeD<E> before = getNthNode(index);
		NodeD<E> curr = new NodeD<E>();
		curr.setData(data);
		//curr.setNext(before);
		//curr.setPrev(before.getPrev());
		//check cases of surrounding nodes
		//set before.getPrev
		curr.setPrev(before.getPrev());
		if(before.getPrev() != null)
			before.getPrev().setNext(curr);
		else {
			top = curr;
		}
		if(before != null)
			before.setPrev(curr);
		curr.setNext(before);
		return true;
	}

	/******************************************************************
	 * clear helper Method clears list of data
	 *
	 * @return void
	 ******************************************************************/
	public void clear(){
		top = null;
	}

	/******************************************************************
	 * removeAt helper Method NodeD type for successful removal
	 *
	 * @return NodeD type for success
	 ******************************************************************/
	public NodeD<E> removeAt(int index) {
		// case 0: no list
		if (top == null)
			return null;

		// case 1: index out of bounds
		if (index < 0 || index > getLen() - 1)
			return null;

		// case 2: remove from top, index = 0;
		if (index == 0) {
			NodeD<E> temp = top;
			top = top.getNext();
			if(temp.getData() != null)
				return temp;
			else return null;
		}

		// case 3: remove from bottom;
		if (index == getLen() - 1)
			return aprend();

		NodeD<E> temp = top;
		for (int i = 0; i <= index - 1; i++)
			temp = temp.getNext();

		E data = (E)temp.getNext().getData();
		temp.setNext(temp.getNext().getNext());
		return temp;
	}

	/******************************************************************
	 * replace helper Method replace chars
	 *
	 * @param data E type passed char to replace
	 * @param place E type passed char to place
	 * @return boolean value of success
	 ******************************************************************/
	public boolean replace(E data, E place){
		NodeD<E> replace = getNode(data);
		if(replace == null) {
			assert (false);
			return false;
		}
		replace.setData(place);
		return true;
	}

	/******************************************************************
	 * getLen helper Method return integer length of list
	 *
	 * @return int type length of list
	 ******************************************************************/
	public int getLen() {

		int count = 0;
		NodeD<E> temp = top;
		while (temp != null) {
			count++;
			temp = temp.getNext();
		}
		return count;
	}

}
