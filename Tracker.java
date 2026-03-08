import java.io.*;
import java.util.ArrayList;

public class Tracker {
    static ArrayList<Item> items = new ArrayList<>();
    static final String FILE = "items.txt";

    public static void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length >= 4) items.add(new Item(p[0], p[1], p[2], p[3]));
            }
        } catch (Exception e) {}
    }

    public static void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            for (Item i : items)
                bw.write(i.name + "|" + i.type + "|" + i.location + "|" + i.date + "\n");
        } catch (Exception e) {}
    }

    public static void delete(int index) {
        if (index >= 0 && index < items.size()) items.remove(index);
        save();
    }
}