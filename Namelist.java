/*
   Project 1
   Name: Minhngan Do netid: mvd170130
   Class: CS3345.007
   Professor: Greg Ozbirn
*/


class Node {
   
   //every Node object has a String, pointer to the next Node, pointer to the prev Node
   private String data;
   public Node prev;
   public Node next;
   
   //default constructor
   public Node(String data) {
      
      prev = null;
      this.data = data;
      next = null;
      
   }
   
   //constructor
   public Node(Node prev, String data, Node next) {
      this.prev = prev;
      this.data = data;
      this.next = next;
   }
   
   public String getData() {
      return data;
   }

}


class DoublyLinkedList {
   
   //instance variables
   private Node head;
   private int size;
   
   //default constructor
   public DoublyLinkedList() {
      head = null;
      size = 0;
   }
   
   //returns size of the linkedList
   public int getSize() {
      return size;
   }
   
   //if the first Node (head) is empty, then the linkedList is empty 
   public boolean isEmpty() {
      return head == null;
   }
   
   //pushFront adds a Node to the front of the linkedList
   public void pushFront(String data) {
      
      //if the linkedList is empty, then create a first Node (head)
      if (isEmpty()) {
         head = new Node(null, data, null);
      }
      //else, if the linkedList isn't empty, then make a new Node whose next points to the head
      else {
         //new Node's next points to the head (original first Node)
         Node newNode = new Node(null, data, head);
         //adjust the original head's pointers
         head.prev = newNode;
         //set the newNode as the linkedList's new head
         head = newNode;
         
      }
      size++;
   }
   
   //pushBack adds a Node to the end of the linkedList
   public void pushBack(String data) {
      
      //if the linkedList is empty, then create a first Node
      if (isEmpty()) {
         head = new Node(null, data, null);
      }
      
      //else, make a new Node at the end (whose prev points to the original last Node, and whose next points to null)
      else {
         
         //iterate through the linkedList til the current = lastNode
         Node current = head; 
         while(current.next != null) {
            current = current.next;
         }
         
         //make a new Node whose prev is curret (lastNode) and set the current (lastNode) to point its next to the newNode
         Node newNode = new Node(current, data, null);
         current.next = newNode;
      }
      
      size++;
   }
   
   //removes the front Node (head)
   public void popFront() {
      
      //if the linkedList is empty, can't remove anything
      if (isEmpty()) {
         return;
      }
      else {
         //set the linkedList's head to the old head's next
         head = head.next;
         //old head no longer exists, so change the old head's next's prev to skip over the old head, and point to null 
         head.prev = null;
         //decrement the size, don't need to actually delete anything because java has a garabage collector
         size--;
      }  
   }
   
   //removes the last Node 
   public void popBack() {
      
      //if the linkedList is empty, can't remove anything
      if (isEmpty()) {
         return;
      }
      
      //if there is only one Node in the linkedList
      if (head.next == null) {
         head = null;
         size--;
         return;
      }
      
      //deleting the last Node, so iterate until second to last
      Node current = head;
      while (current.next.next != null) {
         current = current.next;
      }
      
      //after the loop ends, current now holds the third to last
      //delete the last means to point the second to last to null
      current.next = null;
      size--;
      
   }
   
   //find that returns the index
   public int findIndex(String data) {
      
      int index = 1;
      
      //start at the first Node and traverse through the list until current's data matches the passed data
      Node current = head; 
      while(current != null) {
            if ((current.getData()).equals(data)) {
               break;
            }
            current = current.next;
            index++;
      }
      
      //if it traverses through the whole list and found nothing return -1
      if (index > size) {
         return -1;
      }
      
      return index;
   }
   
   //find that returns a node
   public Node findNode(String data) {
      
      Node current = head;
      int i = 1;
      while (!(current.getData()).equals(data)) {
         current = current.next;
         i++;
      }
      
      if (i > size) {
         return null;
      }
      else {
         return current;
      }
      
   }
   
   public Node findNodeAtIndex(int index) {
      
      //start at the head, traverse through the list until it reaches passed index
      Node current = head;
      int i = 1; 
      while (i < index) {
         current = current.next;
         i++;
      }
      
      //return the current Node at index
      return current;
   }
   
   public void insertBefore(String data, int index) {
      
      //don't do anything if index out of bounds
      if (index > size || index < 1) {
         return;
      }
      
      //iterate through the linkedList until current is = to the Node at the index
      Node current = head;
      int i = 1; 
      while (i < index) {
         current = current.next;
         i++;
      }
      
      //if inserting at the first Node
      if (current.prev == null) {
         pushFront(data);
      }
      else {
         //make a new Node before the current
         Node newNode = new Node(current.prev, data, current);
         //adjust current's prev's next to point to the newNode 
         current.prev.next = newNode;
         //current's prev points to the newNode
         current.prev = newNode;
         
         size++;
         
      }

   }
   
   public void insertAfter(String data, int index) {
      
      //don't do anyting if index out of bounds
      if(index > size || index < 1) {
         return;
      }
      
      //traverse until current is = to the Node at the index
      Node current = head;
      int i = 1;
      while (i < index) {
         current = current.next;
         i++;
      }
      
      //if inserting after the last Node (the loop stops at the second before last so have to do current.next.next)
      if (current.next.next == null) {
         pushBack(data);
      }
      else {
         Node newNode = new Node(current, data, current.next);
         current.next.prev = newNode;
         current.next = newNode;
         size++;
      }
      
   }
   
   public void sortedInsert(String data) {
      
      int index = findIndex(data);
      

         //if the item doesn't already exist in the list & the list is empty, just push Front
         if (isEmpty()) {
            pushFront(data);
            return;
         }
         
         //if the list isn't empty   
         else {
            
            int i = 1;
            boolean found = false;
            
            //traverse through until the right spot is found by comparing to see if the current iteration comes before the passed data
            while (findNodeAtIndex(i) != null && !found) {
               
               //i increments to hold the index of the item that the new name will be inserted before
               if (findNodeAtIndex(i).getData().toUpperCase().compareTo(data.toUpperCase()) < 0) {
                  i++;
               }
               else {
                  found = true;
               }
            }
            
            //if not trying to insert at the end
            if(findNodeAtIndex(i) != null && found) {
               insertBefore(data, i);
            }
            
            //just pushBack if trying to insert at the end
            if (findNodeAtIndex(i) == null) {
               pushBack(data);
            }
            
         }
     
   }
   
   public void removeAt(int index) {
      
      //can't remove anything from an empty list
      if (isEmpty()) {
         return;
      }
      //can't remove anything if out of bounds
      if (index > size || index < 1) {
         return;
      }
      
      Node current = head;
      int i = 1;
      while (i < index) {
         current = current.next;
         i++;
      }
      
      //if deleting the last Node
      if (current.next == null) {
         popBack();
      }
      //if deleting first Node
      else if (current.prev == null) {
         popFront();
      }
      //deleting in between
      else {
         current.prev.next = current.next;
         current.next.prev = current.prev;
         size--;
      }

   }
   
   //goes through and prints the contents of the doubly linkedList
   public void debug() {
      Node current = head; 
      while(current != null) {
         System.out.println(current.getData());
         current = current.next;
      }
   } 
   
   
}





public class Namelist {
      
   private DoublyLinkedList list;
   
   public Namelist() {
      list = new DoublyLinkedList();
   }   
   
  /* 
   * adds a new name. Names must be at least 2 characters long. Adds a 
   * letter node if not already present
   */
   public void add(String name) {
      
      list.sortedInsert(name);
      
      //letter stuff
      //store first letter of passed name into a string
      //go through list, and count how many names start with that letter
      //if num of names that start with that letter is == 1, then add a letterNode with sortedInsert
      String letter = name.substring(0,1).toUpperCase();
      
      int count = 0;
      Node current = list.findNodeAtIndex(1);
      
      while(current != null) {
         if(current.getData().substring(0,1).toUpperCase().equals(letter)) {
            count++;        
         }
         current = current.next;
      }
      
      if(count == 1) {
         list.sortedInsert(letter);
      }
     
   }
   
  /* 
   * removes a name. If the name is the last one for a letter,
   * the letter node should also be removed
   */
   public void remove(String name) {
      
      list.removeAt(list.findIndex(name));
      
      //store the first letter of passed name into a string
      //go through list and count how many nodes now start with that letter
      //if only one node start with that letter, that is the letter node, so delete that
      String letter = name.substring(0,1).toUpperCase();
      
      int count = 0;
      Node current = list.findNodeAtIndex(1);
      
      while(current != null) {
         if (current.getData().substring(0,1).toUpperCase().equals(letter)) {
            count++;
         }
         current = current.next;
      }
      
      if (count == 1) {
         list.removeAt(list.findIndex(letter));
      }   
   }
   
  /* 
   * Removes a letter and all names for that letter
   */
   public void removeLetter(String letter) {
      
      //traverse through the list, if the current node's name's first letter matches the passed letter, store the name into currentName
      //then call removeAt(findIndex(currentName)) to delete that 
      String currentName = "";
      
      Node current = list.findNodeAtIndex(1);
      
      while (current != null) {
         if (current.getData().substring(0,1).toUpperCase().equals(letter.toUpperCase())) {
            currentName = current.getData();
            list.removeAt(list.findIndex(currentName));
         }
         current = current.next;
      }
      
      
   }
    
  /* 
   * Finds a name by traversing the nodes
   * 
   */
   public boolean find(String name) {
      if (list.findIndex(name) != -1) {
         return true;
      }
      else {
         return false;
      }
   }
   
  /* 
   * Returns a string of the list formatted
   *
   */
   public String toString() {
      String output = "";
      
      Node current = list.findNodeAtIndex(1);
      while(current != null) {
      
         if(current.getData().length() > 1) {
            output += "\t" + current.getData() + "\n";
         }
         else {
            output += current.getData() + "\n";
         }
         current = current.next;
      }
      
      return output;
      
   }
   
   
   public static void main(String[] args) {
      Namelist myNameList = new Namelist();
      
      System.out.println("Testing add:");
      myNameList.add("Tanner");
      myNameList.add("Mason");
      myNameList.add("Tina");
      myNameList.add("Jean Pierre");
      myNameList.add("Mochi");
      myNameList.add("Jake");
      myNameList.add("Esmeralda");
      myNameList.add("Austin");
      myNameList.add("mcCarty");
      myNameList.add("Pablo");
      System.out.println(myNameList.toString());
      
      
      System.out.println("Testing remove:");
      System.out.println("Removing \"Esmeralda\"...");
      myNameList.remove("Esmeralda");
      System.out.println(myNameList.toString());
      System.out.println("Removing \"Mason\"...");
      myNameList.remove("Mason");
      System.out.println(myNameList.toString());
      
      System.out.println("Testing removeLetter:");
      System.out.println("Removing letter \'T\'...");
      myNameList.removeLetter("T");
      System.out.println(myNameList.toString());
      
      System.out.println("Testing find:");
      System.out.println(myNameList.toString());
      
      if(myNameList.find("Esmeralda")) {
         System.out.println("The name \"Esmeralda\" was found");
      }
      else {
         System.out.println("The name \"Esmeralda\" was not found.");
      }
      
      if(myNameList.find("Jean Pierre")) {
         System.out.println("The name \"Jean Pierre\" was found.");
      }
      else {
         System.out.println("The name \"Jean Pierre\" was not found.");
      }
   }
}