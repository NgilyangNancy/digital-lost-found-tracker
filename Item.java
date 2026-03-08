public class Item {
    String name, type, location, date;

    public Item(String n, String t, String l, String d) {
        name = n; type = t; location = l; date = d;
    }

    public String toString() {
        return name + " | " + type + " | " + location + " | " + date;
    }
}