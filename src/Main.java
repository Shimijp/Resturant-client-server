public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        try
        {
            menu.loadMenuFromFile("menuItems.txt");
            System.out.println("Menu loaded successfully:");
            System.out.println(menu);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());

        }

    }
}