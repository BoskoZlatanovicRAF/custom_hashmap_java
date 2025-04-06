public class Main {
    public static void main(String[] args) {
        HashMapImplementation<String, Integer> map = new HashMapImplementation<>();

        System.out.println("======Dodavanje elemenata======");
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        map.put("D", 4);
        System.out.println("Velicina: " + map.currentSize()); // 4

        System.out.println("\n======Dohvatanje vrednosti======");
        System.out.println("A : " + map.get("A")); // 1
        System.out.println("C : " + map.get("C")); // 3
        System.out.println("X : " + map.get("X")); // null

        System.out.println("\n======Prepisivanje kljuca======");
        map.put("A", 100);
        System.out.println("A : " + map.get("A")); // 100
        System.out.println("Velicina (ne bi trebalo da se poveca): " + map.currentSize()); // 4

        System.out.println("\n======Brisanje elemenata======");
        map.remove("B");
        System.out.println("B : " + map.get("B")); // null
        System.out.println("Velicina: " + map.currentSize()); // 3

        System.out.println("\n======Iteracija kroz mapu======");
        for (HashMapImplementation<String, Integer>.Entry entry : map) {
            System.out.println(entry);
        }

        System.out.println("\n======Dodavanje vise elemenata da se testira resize======");
        map.put("E", 5);
        map.put("F", 6);
        map.put("G", 7);

        System.out.println("Velicina nakon resize: " + map.currentSize()); // 6

        System.out.println("\n======Iteracija nakon resize======");
        for (HashMapImplementation<String, Integer>.Entry entry : map) {
            System.out.println(entry);
        }

        System.out.println("\n======Test sa null vrednostima======");
        map.put(null, null);
        System.out.println("NULL_VALUE : " + map.get(null)); // null
        System.out.println("Size: " + map.currentSize()); // 7

        System.out.println("\n======Brisanje nepostojeceg kljuca======");
        map.remove("NE POSTOJI");
        System.out.println("Velicina: " + map.currentSize()); // 7

    }
}