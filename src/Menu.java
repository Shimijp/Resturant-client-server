import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Menu implements Serializable {
    private static  HashMap<Integer , MenuItem > menu;
   ;
    public Menu() {
        menu = new HashMap<>();
    }
    public void addMenuItem(MenuItem item) {
        menu.put(item.getId(), item);
    }
    public void removeMenuItem(int id) {
        menu.remove(id);
    }
    public MenuItem getMenuItem(int id) {
        return menu.get(id);
    }
    public List<MenuItem> getAllMenuItems() {
        return new ArrayList<>(menu.values());
    }
    public void loadMenuFromFile(String fileName) throws Exception
    {
       try
       {
           Scanner scam = new Scanner(new File(fileName));
           while (scam.hasNextLine())
           {
               String line = scam.nextLine();
               if (line.trim().isEmpty()) continue; //skip empty lines


               //parse line to the item format id,type,desc,price
               String[] parts = line.split(",");
               int id = Integer.parseInt(parts[0].trim());
               String type = parts[1].trim();
               String desc = parts[2].trim();
                double price = Double.parseDouble(parts[3].trim());
                MenuItem item = new MenuItem(id, type, desc,  price);
                addMenuItem(item);

           }
           scam.close();
       }
         catch (Exception e)
         {
              System.out.println("Error loading menu from file: " + e.getMessage());
              throw new RuntimeException(e);
         }




    }
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        for (MenuItem item : menu.values()) {
            result.append(item.toString()).append("\n\n");
        }
        return result.toString();
    }
    public static HashMap<Integer, MenuItem> getMenu() {
        return menu;
    }
    public List<MenuItem> getItemsByType(String type) {
        List<MenuItem> itemsByType = new ArrayList<>();
        for (MenuItem item : menu.values()) {
            if (item.getType().equalsIgnoreCase(type)) {
                itemsByType.add(item);
            }
        }
        return itemsByType;
    }

}
