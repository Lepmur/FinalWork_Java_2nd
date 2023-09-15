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

    public static void main(String[] args) {
        ToyStore toyStore = new ToyStore();

        toyStore.addToy(1, "Конструктор", 10, 20);
        toyStore.addToy(2, "Робот", 5, 10);
        toyStore.addToy(3, "Кукла", 15, 70);

        toyStore.updateWeight(1, 30);

        for (int i = 0; i < 5; i++) {
            Toy prizeToy = toyStore.drawToy();
            if (prizeToy != null) {
                toyStore.addPrizeToy(prizeToy);
            }
        }

        for (int i = 0; i < 3; i++) {
            Toy prize = toyStore.getPrizeToy();
            if (prize != null) {
                toyStore.writePrizeToTextFile(prize, "prize_winners.txt");
                System.out.println("Выдана призовая игрушка: " + prize.getName());
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

    public void decrementQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }
}
