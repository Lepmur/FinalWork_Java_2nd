import java.util.*;

// Основной класс для розыгрыша игрушек
public class ToyStore {
    private List<Toy> toys;

    public ToyStore() {
        toys = new ArrayList<>();
    }

    // Метод для добавления новой игрушки
    public void addToy(int id, String name, int quantity, int weight) {
        Toy toy = new Toy(id, name, quantity, weight);
        toys.add(toy);
    }

    // Метод для изменения веса (частоты выпадения) игрушки
    public void updateWeight(int id, int newWeight) {
        for (Toy toy : toys) {
            if (toy.getId() == id) {
                toy.setWeight(newWeight);
                return;
            }
        }
        System.out.println("Игрушка с ID " + id + " не найдена.");
    }

    // Метод для проведения розыгрыша
    public Toy drawToy() {
        // Создаем список с учетом весов (частот выпадения)
        List<Toy> weightedToys = new ArrayList<>();
        for (Toy toy : toys) {
            for (int i = 0; i < toy.getWeight(); i++) {
                weightedToys.add(toy);
            }
        }

        // Выбираем случайную игрушку из списка с учетом весов
        Random random = new Random();
        int randomIndex = random.nextInt(weightedToys.size());
        return weightedToys.get(randomIndex);
    }

    public static void main(String[] args) {
        ToyStore toyStore = new ToyStore();

        // Добавление новых игрушек
        toyStore.addToy(1, "Конструктор", 10, 20);
        toyStore.addToy(2, "Робот", 5, 10);
        toyStore.addToy(3, "Кукла", 15, 70);

        // Изменение веса (частоты) игрушки
        toyStore.updateWeight(1, 30);

        // Розыгрыш игрушек
        for (int i = 0; i < 10; i++) {
            Toy winner = toyStore.drawToy();
            System.out.println("Победила игрушка: " + winner.getName());
        }
    }
}

// Класс для игрушек
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
}
