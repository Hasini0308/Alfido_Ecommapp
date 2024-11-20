import java.util.ArrayList;
import java.util.Scanner;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}

class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Price: $%.2f", id, name, price);
    }
}

class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format("%s, Quantity: %d, Total: $%.2f",
                product.toString(), quantity, getTotalPrice());
    }
}

public class ECommerceApp {
    private static final ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<Product> products = new ArrayList<>();
    private static final ArrayList<CartItem> cart = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    private static User loggedInUser = null;
    private static int nextProductId = 1;

    public static void main(String[] args) {
        seedProducts();

        while (true) {
            System.out.println("\nE-Commerce Application");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> registerUser();
                case 2 -> loginUser();
                case 3 -> {
                    System.out.println("Exiting application. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void seedProducts() {
        products.add(new Product(nextProductId++, "Laptop", 1000.00));
        products.add(new Product(nextProductId++, "Smartphone", 500.00));
        products.add(new Product(nextProductId++, "Headphones", 50.00));
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists. Try another.");
                return;
            }
        }

        users.add(new User(username, password));
        System.out.println("Registration successful!");
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                loggedInUser = user;
                System.out.println("Login successful!");
                userMenu();
                return;
            }
        }

        System.out.println("Invalid username or password.");
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\nUser Menu");
            System.out.println("1. View Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Place Order");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> viewProducts();
                case 2 -> addToCart();
                case 3 -> viewCart();
                case 4 -> placeOrder();
                case 5 -> {
                    System.out.println("Logging out...");
                    loggedInUser = null;
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void viewProducts() {
        System.out.println("\nAvailable Products:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private static void addToCart() {
        System.out.print("Enter product ID to add to cart: ");
        int productId = scanner.nextInt();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        for (Product product : products) {
            if (product.getId() == productId) {
                cart.add(new CartItem(product, quantity));
                System.out.println("Product added to cart.");
                return;
            }
        }

        System.out.println("Product not found.");
    }

    private static void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("\nYour Cart:");
        for (CartItem item : cart) {
            System.out.println(item);
        }
    }

    private static void placeOrder() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Add items before placing an order.");
            return;
        }

        double totalAmount = 0;
        System.out.println("\nOrder Summary:");
        for (CartItem item : cart) {
            System.out.println(item);
            totalAmount += item.getTotalPrice();
        }
        System.out.printf("Total Amount: $%.2f\n", totalAmount);

        cart.clear();
        System.out.println("Order placed successfully!");
    }
}
