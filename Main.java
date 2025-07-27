import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class CustomLinkedList implements Iterable<Integer> {
    private Node head;
    private int size;

    public CustomLinkedList() {
        this.head = null;
        this.size = 0;
    }

    // Insert method - adds element at the end of the list
    public void insert(int data) {
        Node newNode = new Node(data);
        
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // Insert at beginning for better performance if needed
    public void insertAtBeginning(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
        size++;
    }

    // Delete method - removes first occurrence of the data
    public boolean delete(int data) {
        if (head == null) {
            return false;
        }

        // If head node contains the data to be deleted
        if (head.data == data) {
            head = head.next;
            size--;
            return true;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.data == data) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false; // Data not found
    }

    // Get size of the list
    public int size() {
        return size;
    }

    // Check if list is empty
    public boolean isEmpty() {
        return head == null;
    }

    // Display method for debugging
    public void display() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }
        
        Node current = head;
        System.out.print("LinkedList: ");
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println();
    }

    // Load data from text file
    public void loadFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            
            while (scanner.hasNextInt()) {
                int data = scanner.nextInt();
                insert(data);
            }
            scanner.close();
            System.out.println("Data loaded successfully from " + filename);
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new LinkedListIterator();
    }

    // Node class
    private class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    // Iterator implementation
    private class LinkedListIterator implements Iterator<Integer> {
        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the list");
            }
            int data = current.data;
            current = current.next;
            return data;
        }

        // Optional: remove method for iterator
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation not supported");
        }
    }
}

// Main class for demonstration
class Main {
    public static void main(String[] args) {
        CustomLinkedList linkedList = new CustomLinkedList();

        System.out.println("=== Custom LinkedList Demonstration ===\n");

        // Test basic insertion
        System.out.println("1. Testing insertion:");
        linkedList.insert(10);
        linkedList.insert(20);
        linkedList.insert(30);
        linkedList.insert(40);
        linkedList.display();
        System.out.println("Size: " + linkedList.size() + "\n");

        // Test iterator traversal
        System.out.println("2. Testing iterator traversal:");
        System.out.print("Elements using iterator: ");
        Iterator<Integer> iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println("\n");

        // Test enhanced for loop (uses iterator internally)
        System.out.println("3. Testing enhanced for loop:");
        System.out.print("Elements using enhanced for loop: ");
        for (Integer element : linkedList) {
            System.out.print(element + " ");
        }
        System.out.println("\n");

        // Test deletion
        System.out.println("4. Testing deletion:");
        System.out.println("Deleting 20: " + linkedList.delete(20));
        linkedList.display();
        System.out.println("Deleting 50 (not in list): " + linkedList.delete(50));
        linkedList.display();
        System.out.println("Size after deletions: " + linkedList.size() + "\n");

        // Test insertion at beginning
        System.out.println("5. Testing insertion at beginning:");
        linkedList.insertAtBeginning(5);
        linkedList.display();
        System.out.println();

        // Demonstrate file reading capability
        System.out.println("6. File reading demonstration:");
        CustomLinkedList fileList = new CustomLinkedList();
        
        // Create a sample data string to simulate file content
        System.out.println("Note: To test file reading, create a text file named 'data.txt'");
        System.out.println("with integers separated by spaces or newlines, for example:");
        System.out.println("100 200 300 400 500");
        System.out.println();
        
        // Try to load from file (will show error message if file doesn't exist)
        fileList.loadFromFile("data.txt");
        
        if (!fileList.isEmpty()) {
            System.out.print("Data from file: ");
            for (Integer element : fileList) {
                System.out.print(element + " ");
            }
            System.out.println();
        }

        // Test edge cases
        System.out.println("\n7. Testing edge cases:");
        CustomLinkedList emptyList = new CustomLinkedList();
        System.out.println("Empty list size: " + emptyList.size());
        System.out.println("Delete from empty list: " + emptyList.delete(10));
        emptyList.display();
        
        // Test iterator on empty list
        Iterator<Integer> emptyIterator = emptyList.iterator();
        System.out.println("Empty list has next: " + emptyIterator.hasNext());
        
        System.out.println("\n=== Demonstration Complete ===");
    }
}
