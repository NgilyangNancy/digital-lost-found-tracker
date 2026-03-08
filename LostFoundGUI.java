import javax.swing.*;
import java.awt.*;

public class LostFoundGUI {
    public static void main(String[] args) {
        Tracker.load();
        JFrame frame = new JFrame("Digital Lost & Found");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10,10));

        // Input Panel
        JPanel input = new JPanel(new GridLayout(2,4,10,10));
        JTextField nameF = new JTextField();
        JTextField locF = new JTextField();
        JTextField dateF = new JTextField();
        JComboBox<String> typeB = new JComboBox<>(new String[]{"lost","found"});
        input.add(new JLabel("Name:")); input.add(nameF);
        input.add(new JLabel("Location:")); input.add(locF);
        input.add(new JLabel("Date:")); input.add(dateF);
        input.add(new JLabel("Type:")); input.add(typeB);
        frame.add(input, BorderLayout.NORTH);

        // Display Area
        JTextArea display = new JTextArea();
        display.setEditable(false);
        frame.add(new JScrollPane(display), BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttons = new JPanel();
        JButton add = new JButton("Add");
        JButton del = new JButton("Delete");
        JTextField searchF = new JTextField(10);
        JButton search = new JButton("Search");
        buttons.add(add); buttons.add(del); buttons.add(new JLabel("Search:")); buttons.add(searchF); buttons.add(search);
        frame.add(buttons, BorderLayout.SOUTH);

        Runnable showAll = () -> {
            display.setText("");
            for (int i=0;i<Tracker.items.size();i++)
                display.append((i+1) + ": " + Tracker.items.get(i) + "\n");
        };
        showAll.run();

        // Add Item
        add.addActionListener(e -> {
            String n = nameF.getText().trim(), l = locF.getText().trim(), d = dateF.getText().trim(), t = (String) typeB.getSelectedItem();
            if(n.isEmpty() || l.isEmpty() || d.isEmpty()) { JOptionPane.showMessageDialog(frame,"Fill all fields"); return; }
            Item newItem = new Item(n,t,l,d);

            for(Item i : Tracker.items)
                if(!i.type.equals(t) && i.name.equalsIgnoreCase(n) && i.location.equalsIgnoreCase(l))
                    JOptionPane.showMessageDialog(frame,"Match Found! Type: "+i.type);

            Tracker.items.add(newItem);
            Tracker.save();
            showAll.run();
            nameF.setText(""); locF.setText(""); dateF.setText("");
        });

        // Delete Item
        del.addActionListener(e -> {
            try{
                int idx = Integer.parseInt(JOptionPane.showInputDialog(frame,"Enter item number"))-1;
                Tracker.delete(idx); showAll.run();
            }catch(Exception ex){ JOptionPane.showMessageDialog(frame,"Invalid number");}
        });

        // Search Item
        search.addActionListener(e -> {
            String kw = searchF.getText().toLowerCase().trim();
            display.setText("");
            boolean found = false;
            for(int i=0;i<Tracker.items.size();i++){
                Item it = Tracker.items.get(i);
                if(it.name.toLowerCase().contains(kw)||it.location.toLowerCase().contains(kw)){
                    display.append((i+1)+": "+it+"\n"); found=true;
                }
            }
            if(!found) display.setText("No matching items found.");
            searchF.setText("");
        });

        frame.setVisible(true);
    }
}