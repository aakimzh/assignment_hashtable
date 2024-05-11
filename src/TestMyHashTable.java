import java.util.Random;

public class TestMyHashTable {
    private static class MyTestingClass {
        private int id;
        private String data;

        public MyTestingClass(int id, String data) {
            this.id = id;
            this.data = data;
        }

        @Override
        public int hashCode() {
            // Custom hashCode method - you can tune this for better distribution
            int hash = 17;
            hash = 31 * hash + id;
            hash = 31 * hash + (data != null ? data.hashCode() : 0);
            return hash;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyTestingClass that = (MyTestingClass) o;
            return id == that.id && (data != null ? data.equals(that.data) : that.data == null);
        }
    }

    private static class Student {
        private String name;
        private int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    public static void main(String[] args) {
        MyHashTable<MyTestingClass, Student> table = new MyHashTable<>(50); // Increase bucket count for better distribution

        // Add random 10000 elements
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            int id = rand.nextInt(10000);
            String data = "Data" + id;
            Student s = new Student("Student" + id, rand.nextInt(100));
            table.put(new MyTestingClass(id, data), s);
        }

        // Print number of elements in each bucket
        int[] bucketSizes = new int[50];
        for (int i = 0; i < 50; i++) {
            int size = 0;
            for (MyHashTable<MyTestingClass, Student>.HashNode<MyTestingClass, Student> e = ((MyHashTable<MyTestingClass, Student>) table).chainArray[i]; e != null; e = e.next) {
                size++;
            }
            bucketSizes[i] = size;
            System.out.println("Bucket " + i + ": " + size + " elements");
        }

        // You can tweak the hashCode() method if the distribution is not uniform
    }
}

