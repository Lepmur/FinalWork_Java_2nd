package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ToyStore {
    private List<Toy> toys;
    private List<Toy> prizeToys;

    public ToyStore() {
        toys = new ArrayList<>();
        prizeToys = new ArrayList<>();
    }

    public void addToy(int id, String name, int quantity, int weight) {
        Toy toy = new Toy(id, name, quantity, weight);
        toys.add(toy);
    }

    public void updateWeight(int id, int newWeight) {
        for (Toy toy : toys) {
            if (toy.getId() == id) {
                toy.setWeight(newWeight);
                return;
            }
        }
        System.out.println("Игрушка с ID " + id + " не найдена.");
    }

    public void addPrizeToy(Toy toy) {
        if (toys.contains(toy) && toy.getQuantity() > 0) {
            prizeToys.add(toy);
        } else {
            System.out.println("Ошибка: Игрушка не может быть добавлена в список призовых.");
        }
    }

    public Toy getPrizeToy() {
        if (!prizeToys.isEmpty()) {
            Toy prize = prizeToys.remove(0);
            prize.decrementQuantity();
            return prize;
        } else {
            System.out.println("Список призовых игрушек пуст.");
            return null;
        }
    }

    public void writePrizeToTextFile(Toy prize, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write("Победила игрушка: " + prize.getName() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Toy drawToy() {
        List<Toy> weightedToys = new ArrayList<>();
        for (Toy toy : toys) {
            for (int i = 0; i < toy.getWeight(); i++) {
                weightedToys.add(toy);
            }
        }

        Random random = new Random();
        int randomIndex = random.nextInt(weightedToys.size());
        return weightedToys.get(randomIndex);
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (Toy toy : toys) {
            totalQuantity += toy.getQuantity();
        }
        return totalQuantity;
    }

    public static void main(String[] args) {
        ToyStore toyStore = new ToyStore();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите начальное количество каждой игрушки в автомате:");
        System.out.print("Конструктор: ");
        int constructorQuantity = scanner.nextInt();
        System.out.print("Робот: ");
        int robotQuantity = scanner.nextInt();
        System.out.print("Кукла: ");
        int dollQuantity = scanner.nextInt();
        toyStore.addToy(1, "Конструктор", constructorQuantity, 20);
        toyStore.addToy(2, "Робот", robotQuantity, 10);
        toyStore.addToy(3, "Кукла", dollQuantity, 70);

        toyStore.updateWeight(1, 30);

        int initialTotalQuantity = toyStore.getTotalQuantity();
        System.out.println("Начальное количество всех игрушек в автомате: " + initialTotalQuantity);

        for (int i = 0; i < initialTotalQuantity; i++) {
            Toy prizeToy = toyStore.drawToy();
            if (prizeToy != null) {
                toyStore.addPrizeToy(prizeToy);
            }
        }

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Играть");
            System.out.println("2. Добавить игрушку");
            System.out.println("3. Выход");
            int choice = scanner.nextInt();

            if (choice == 1) {
                Toy prize = toyStore.getPrizeToy();
                if (prize != null) {
                    toyStore.writePrizeToTextFile(prize, "prize_winners.txt");
                    System.out.println("Выдана призовая игрушка: " + prize.getName());
                }
            } else if (choice == 2) {
                System.out.println("Выберите игрушку для добавления:");
                System.out.println("1. Конструктор");
                System.out.println("2. Робот");
                System.out.println("3. Кукла");
                int toyChoice = scanner.nextInt();
                if (toyChoice >= 1 && toyChoice <= 3) {
                    toyStore.addToy(toyChoice, toyChoice == 1 ? "Конструктор" : toyChoice == 2 ? "Робот" : "Кукла", 0, toyChoice == 1 ? 20 : toyChoice == 2 ? 10 : 70);
                } else {
                    System.out.println("Некорректный выбор игрушки.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Некорректный выбор. Пожалуйста, выберите 1, 2 или 3.");
            }
        }
    }
}

class Toy {
    private int id;
    private String name;
    private int quantity;
    private int weight;

    public Toy(int id, String name, int quantity, int weight) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    // Метод для уменьшения количества игрушек
    public void decrementQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }
}
