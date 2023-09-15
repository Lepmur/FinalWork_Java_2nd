package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // Создаем приоритетную очередь для хранения игрушек
        PriorityQueue<Toy> toyQueue = new PriorityQueue<>();

        // Добавляем игрушки в очередь
        toyQueue.offer(new Toy(1, "конструктор", 2));
        toyQueue.offer(new Toy(2, "робот", 2));
        toyQueue.offer(new Toy(3, "кукла", 6));

        // Создаем файл для записи результатов
        try (FileWriter fileWriter = new FileWriter("results.txt")) {
            Random random = new Random();

            // Вызываем метод Get 10 раз и записываем результаты в файл
            for (int i = 0; i < 10; i++) {
                int randomNumber = random.nextInt(100); // Генерируем случайное число от 0 до 99

                // Вероятности выпадения игрушек: 20% на 1, 20% на 2, 60% на 3
                int toyId;
                if (randomNumber < 20) {
                    toyId = 1;
                } else if (randomNumber < 40) {
                    toyId = 2;
                } else {
                    toyId = 3;
                }

                // Ищем игрушку с соответствующим id и добавляем результат в файл
                for (Toy toy : toyQueue) {
                    if (toy.getId() == toyId) {
                        fileWriter.write(toy.getName() + "\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Toy implements Comparable<Toy> {
    private int id;
    private String name;
    private int weight;

    public Toy(int id, String name, int weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Toy other) {
        // Сравниваем игрушки по их весу (частоте выпадения)
        return Integer.compare(this.weight, other.weight);
    }
}
