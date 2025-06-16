import java.util.*;

// Generic Bag data structure implementation
public class Bag<T> {
    // Internal storage using HashMap to store items and their counts
    private Map<T, Integer> items;
    private int totalSize;
    
    // Constructor: Initialize an empty bag
    public Bag() {
        items = new HashMap<>();
        totalSize = 0;
    }
    
    // Add an item to the bag
    // If item already exists, increment its count
    public void add(T item) {
        if (items.containsKey(item)) {
            items.put(item, items.get(item) + 1);
        } else {
            items.put(item, 1);
        }
        totalSize++;
    }
    
    // Remove one occurrence of an item from the bag
    // If item count becomes 0, remove it completely from the bag
    public void remove(T item) {
        if (items.containsKey(item)) {
            int count = items.get(item);
            if (count > 1) {
                items.put(item, count - 1);
            } else {
                items.remove(item);
            }
            totalSize--;
        }
    }
    
    // Check if an item exists in the bag
    public boolean contains(T item) {
        return items.containsKey(item);
    }
    
    // Count the number of occurrences of an item in the bag
    public int count(T item) {
        return items.getOrDefault(item, 0);
    }
    
    // Get the total size of the bag (including duplicates)
    public int size() {
        return totalSize;
    }
    
    // Get the number of unique items in the bag
    public int uniqueSize() {
        return items.size();
    }
    
    // Check if the bag is empty
    public boolean isEmpty() {
        return totalSize == 0;
    }
    
    // Get all unique items in the bag
    public Set<T> getUniqueItems() {
        return new HashSet<>(items.keySet());
    }
    
    // Clear all items from the bag
    public void clear() {
        items.clear();
        totalSize = 0;
    }
    
    // String representation of the bag showing items and their counts
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Bag is empty";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Bag contents: ");
        
        List<T> sortedKeys = new ArrayList<>(items.keySet());
        // Sort for consistent output (if items are comparable)
        try {
            Collections.sort((List<Comparable>) sortedKeys);
        } catch (ClassCastException e) {
            // Items are not comparable, keep original order
        }
        
        for (int i = 0; i < sortedKeys.size(); i++) {
            T item = sortedKeys.get(i);
            int count = items.get(item);
            sb.append(item).append(" (").append(count).append(")");
            if (i < sortedKeys.size() - 1) {
                sb.append(", ");
            }
        }
        
        sb.append(" | Total size: ").append(totalSize);
        return sb.toString();
    }
    
    // Simple interactive main method for testing the Bag class
    // Users can input commands to test bag operations
    // Usage: java Bag
    // Commands: add <item>, remove <item>, contains <item>, count <item>, display, exit
    public static void main(String[] args) {
        Bag<String> bag = new Bag<>();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Simple Bag Tester - Enter commands:");
        System.out.println("add <item> | remove <item> | contains <item> | count <item> | display | exit");
        
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            
            if (input.isEmpty()) continue;
            
            String[] parts = input.split("\\s+", 2);
            String command = parts[0].toLowerCase();
            
            switch (command) {
                case "add":
                    if (parts.length > 1) {
                        bag.add(parts[1]);
                        System.out.println("Added: " + parts[1]);
                    } else {
                        System.out.println("Usage: add <item>");
                    }
                    break;
                    
                case "remove":
                    if (parts.length > 1) {
                        if (bag.contains(parts[1])) {
                            bag.remove(parts[1]);
                            System.out.println("Removed: " + parts[1]);
                        } else {
                            System.out.println("Item not found: " + parts[1]);
                        }
                    } else {
                        System.out.println("Usage: remove <item>");
                    }
                    break;
                    
                case "contains":
                    if (parts.length > 1) {
                        System.out.println(parts[1] + ": " + bag.contains(parts[1]));
                    } else {
                        System.out.println("Usage: contains <item>");
                    }
                    break;
                    
                case "count":
                    if (parts.length > 1) {
                        System.out.println(parts[1] + ": " + bag.count(parts[1]));
                    } else {
                        System.out.println("Usage: count <item>");
                    }
                    break;
                    
                case "display":
                    System.out.println(bag);
                    break;
                    
                case "exit":
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("Unknown command. Try: add, remove, contains, count, display, exit");
            }
        }
    }
}