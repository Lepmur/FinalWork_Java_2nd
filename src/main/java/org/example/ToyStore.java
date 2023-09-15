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

    // Метод для добавления призовой игрушки в список ожидания
    public void addPrizeToy(Toy toy) {
        if (toys.contains(toy) && toy.getQuantity() > 0) {
            prizeToys.add(toy);
        } else {
            System.out.println("Ошибка: Игрушка не может быть добавлена в список призовых.");
        }
    }

    // Метод для получения призовой игрушки
    public Toy getPrizeToy() {
        if (!prizeToys.isEmpty()) {
            Toy prize = prizeToys.remove(0); // Удаляем первую игрушку из списка призовых
            prize.decrementQuantity(); // Уменьшаем количество игрушек
            return prize;
        } else {
            System.out.println("Список призовых игрушек пуст.");
            return null;
        }
    }

    // Метод для записи призовой игрушки в текстовый файл
    public void writePrizeToTextFile(Toy prize, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write("Победила игрушка: " + prize.getName() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для розыгрыша игрушек
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

    // Метод для определения начального количества всех игрушек в автомате
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

        // Ввод начального количества каждой игрушки с клавиатуры
        System.out.println("Введите начальное количество каждой игрушки в автомате:");
        System.out.print("Конструктор: ");
        int constructorQuantity = scanner.nextInt();
        System.out.print("Робот: ");
        int robotQuantity = scanner.nextInt();
        System.out.print("Кукла: ");
        int dollQuantity = scanner.nextInt();

        // Добавление новых игрушек
        toyStore.addToy(1, "Конструктор", constructorQuantity, 20);
        toyStore.addToy(2, "Робот", robotQuantity, 10);
        toyStore.addToy(3, "Кукла", dollQuantity, 70);

        // Изменение веса (частоты) игрушек
        toyStore.updateWeight(1, 30);

        // Вывод начального количества всех игрушек
        int initialTotalQuantity = toyStore.getTotalQuantity();
        System.out.println("Начальное количество всех игрушек: " + initialTotalQuantity);

        // Добавляем призовые игрушки в список ожидания
        for (int i = 0; i < 5; i++) {
            Toy prizeToy = toyStore.drawToy(); // Вызываем метод розыгрыша
            if (prizeToy != null) {
                toyStore.addPrizeToy(prizeToy);
            }
        }

        // Получаем призовые игрушки и записываем их в файл
        for (int i = 0; i < 3; i++) {
            Toy prize = toyStore.getPrizeToy();
            if (prize != null) {
                toyStore.writePrizeToTextFile(prize, "prize_winners.txt");
                System.out.println("Выдана призовая игрушка: " + prize.getName());
            }
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

    // Метод для уменьшения количества игрушек
    public void decrementQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }
}
