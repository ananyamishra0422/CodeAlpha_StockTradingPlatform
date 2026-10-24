import java.util.*;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() { return symbol; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return symbol + " : $" + price;
    }
}

class Portfolio {
    private Map<String, Integer> holdings = new HashMap<>();
    private double cashBalance;

    public Portfolio(double initialBalance) {
        this.cashBalance = initialBalance;
    }

    public void buyStock(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;
        if (cost <= cashBalance) {
            cashBalance -= cost;
            holdings.put(stock.getSymbol(), holdings.getOrDefault(stock.getSymbol(), 0) + quantity);
            System.out.println("Bought " + quantity + " shares of " + stock.getSymbol());
        } else {
            System.out.println("Insufficient funds to buy " + stock.getSymbol());
        }
    }

    public void sellStock(Stock stock, int quantity) {
        if (holdings.containsKey(stock.getSymbol()) && holdings.get(stock.getSymbol()) >= quantity) {
            holdings.put(stock.getSymbol(), holdings.get(stock.getSymbol()) - quantity);
            cashBalance += stock.getPrice() * quantity;
            System.out.println("Sold " + quantity + " shares of " + stock.getSymbol());
        } else {
            System.out.println("Not enough shares to sell " + stock.getSymbol());
        }
    }

    public void displayPortfolio(Map<String, Stock> market) {
        System.out.println("\n=== Portfolio Summary ===");
        System.out.println("Cash Balance: $" + cashBalance);
        double totalValue = cashBalance;
        for (String symbol : holdings.keySet()) {
            int qty = holdings.get(symbol);
            double value = qty * market.get(symbol).getPrice();
            totalValue += value;
            System.out.println(symbol + " : " + qty + " shares (Value: $" + value + ")");
        }
        System.out.println("Total Portfolio Value: $" + totalValue);
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Simulated market data
        Map<String, Stock> market = new HashMap<>();
        market.put("AAPL", new Stock("AAPL", 150.0));
        market.put("GOOG", new Stock("GOOG", 2800.0));
        market.put("TSLA", new Stock("TSLA", 700.0));

        Portfolio portfolio = new Portfolio(10000.0); // initial balance

        while (true) {
            System.out.println("\n=== Stock Trading Menu ===");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n=== Market Data ===");
                    for (Stock stock : market.values()) {
                        System.out.println(stock);
                    }
                    break;
                case 2:
                    System.out.print("Enter stock symbol: ");
                    String buySymbol = sc.next().toUpperCase();
                    if (market.containsKey(buySymbol)) {
                        System.out.print("Enter quantity: ");
                        int qty = sc.nextInt();
                        portfolio.buyStock(market.get(buySymbol), qty);
                    } else {
                        System.out.println("Invalid stock symbol.");
                    }
                    break;
                case 3:
                    System.out.print("Enter stock symbol: ");
                    String sellSymbol = sc.next().toUpperCase();
                    if (market.containsKey(sellSymbol)) {
                        System.out.print("Enter quantity: ");
                        int qty = sc.nextInt();
                        portfolio.sellStock(market.get(sellSymbol), qty);
                    } else {
                        System.out.println("Invalid stock symbol.");
                    }
                    break;
                case 4:
                    portfolio.displayPortfolio(market);
                    break;
                case 5:
                    System.out.println("Exiting... Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
