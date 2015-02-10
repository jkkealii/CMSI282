import java.lang.StringBuilder;

public class PriorityQueue {
    private String[] tree;
    private int index;

    public PriorityQueue () {
        this.tree = new String[8];
        this.index = 1;
    }

    public PriorityQueue (String input) {
        this.tree = new String[8];
        this.tree[1] = input;
        this.index = 2;
    }

    public PriorityQueue (String[] input) {
        this.tree = new String[input.length+1];
        this.index = 1;
        for (int i = 0; i < input.length; i++) {
            this.add(input[i]);
        }
    }

    public boolean add (String input) {
        // System.out.println("adding...");
        if (this.index >= this.tree.length) {
            String[] newTree = new String[this.tree.length * 2];
            for (int i = 1; i < this.tree.length; i++) {
                newTree[i] = this.tree[i];
            }
            newTree[0] = null;
            this.tree = newTree;
        }
        if (this.index < 2) {
            this.tree[0] = null;
            this.tree[1] = input;
            this.index = 2;
        } else {
            this.tree[this.index] = input;
            promote(this.index);
            this.index++;
        }    

        return true;
    }

    private void promote (int fresh) {
        int counter = fresh;
        while (counter > 1) {
            int half = counter/2;
            if (this.tree[counter].compareTo(this.tree[half]) > 0) {
                exchange(counter, half);
            }
            counter = half;
        }
    }

    private void exchange (int switcher, int switchee) {
        String bay = this.tree[switcher];
        this.tree[switcher] = this.tree[switchee];
        this.tree[switchee] = bay;
    }

    public String peek () {
        if (this.index < 1) {
            throw new UnsupportedOperationException("Empty array");
        }
        return this.tree[1];
    }

    public String remove () {
        String top = this.tree[1];
        exchange(1, this.index-1);
        this.tree[this.index-1] = null;
        demote(1);
        this.index--;
        return top;
    }

    private void demote (int input) {
        int children = 2 * input;
        int number = this.index;
        while (children <= number-2) {
            if (children < number-2 && this.tree[children].compareTo(this.tree[children+1]) < 0) {
                children++;
            }
            if (input >= children) {
                break;
            }
            exchange(input, children);
            input = children;
        }
    }

    public int length () {
        return this.index-1;
    }

    public String toString () {
        StringBuilder result = new StringBuilder();
        result.append("[ ");
        String[] copyStrings = new String[this.index - 1];
        for (int i = 0; i < copyStrings.length; i++) {
            copyStrings[i] = this.tree[i+1];
        }
        PriorityQueue copy = new PriorityQueue(copyStrings);

        for (int i = this.index; i > 1; i--) {
            result.append("\"");
            result.append(copy.remove());
            result.append("\"");
            if (i >= 3) {
                result.append(", ");
            }
        }
        result.append(" ]");
        return result.toString();
    }

    /*
    public static void main(String[] args) {
        PriorityQueue pq = new PriorityQueue(args);
        System.out.println("hello " + pq.toString());
        System.out.println("size: " + pq.length());
        System.out.println("peek: " + pq.peek());
        System.out.println(pq.toString());
        System.out.println("add \"7\": " + pq.add("7"));
        System.out.println(pq.toString());
        System.out.println("add \"9\": " + pq.add("9"));
        System.out.println(pq.toString());
        System.out.println("add \"11\": " + pq.add("11"));
        System.out.println(pq.toString());
        System.out.println("add \"3\": " + pq.add("3"));
        System.out.println(pq.toString());
        System.out.println("remove: " + pq.remove());
        System.out.println(pq.toString());
    }
    */
}