package ru.aton;

public class Main {
    public static void main(String[] args) {
        SimpleCache cache = new SimpleCache();
        User user1 = new User(1, "Ivan", 1000);
        User user2 = new User(2, "Stas", 2000);
        User user3 = new User(3, "Ivan", 3000);
        User user4 = new User(4, "Stas", 4000);
        User user5 = new User(5, "Ivan", 5000);
        User user6 = new User(6, "Stas", 6000);
        User user7 = new User(7, "Ivan", 7000);
        User user8 = new User(8, "Stas", 8000);
        User user9 = new User(9, "Ivan", 9000);
        User user10 = new User(10, "Stas", 10000);

        cache.add(user1);
        cache.add(user2);
        cache.add(user3);
        cache.add(user4);
        cache.add(user5);
        cache.add(user6);
        cache.add(user7);
        cache.add(user8);
        cache.add(user9);
        cache.add(user10);

        System.out.println(cache.getByAccountNumber(1));
        System.out.println(cache.getByName("Ivan"));
        System.out.println(cache.getByValue(2000));
        System.out.println(cache.removeByAccountNumber(1));
        System.out.println(cache.removeByName("Ivan"));
        System.out.println(cache.removeByValue(6000));
        System.out.println(cache.updateByAccountNumber(2, new User(2, "Stas", 30000)));
        System.out.println(cache.updateByName("Stas", new User(2, "Stas", 40000)));
        System.out.println(cache.updateByValue(40000, new User(2, "Stas", 50000)));
    }
}