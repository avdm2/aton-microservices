package ru.aton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimpleCache {

    Map<Long, User> accounts = new HashMap<>();
    Map<String, Set<Long>> nameIndex = new HashMap<>();
    Map<Double, Set<Long>> valueIndex = new HashMap<>();

    public boolean add(User user) {
        if (accounts.containsKey(user.getAccount())) {
            return false;
        }
        accounts.put(user.getAccount(), user);
        nameIndex.computeIfAbsent(user.getName(), k -> new HashSet<>()).add(user.getAccount());
        valueIndex.computeIfAbsent(user.getValue(), k -> new HashSet<>()).add(user.getAccount());
        return true;
    }

    public User getByAccountNumber(long accountNumber) {
        return accounts.get(accountNumber);
    }

    public Set<User> getByName(String name) {
        return get(nameIndex.get(name));
    }

    public Set<User> getByValue(double value) {
        return get(valueIndex.get(value));
    }

    public boolean removeByAccountNumber(long accountNumber) {
        return remove(Set.of(accountNumber));
    }

    public boolean removeByName(String name) {
        return remove(nameIndex.get(name));
    }

    public boolean removeByValue(double value) {
        return remove(valueIndex.get(value));
    }

    public boolean updateByAccountNumber(long accountNumber, User user) {
        return update(Set.of(accountNumber), user);
    }

    public boolean updateByName(String name, User user) {
        return update(nameIndex.get(name), user);
    }

    public boolean updateByValue(double value, User user) {
        return update(valueIndex.get(value), user);
    }

    private Set<User> get(Set<Long> accountsById) {
        Set<User> result = new HashSet<>();
        if (accountsById != null) {
            for (Long accountNumber : accountsById) {
                result.add(accounts.get(accountNumber));
            }
        }
        return result;
    }

    private boolean remove(Set<Long> accountsById) {
        if (accountsById == null) {
            return false;
        }

        Set<Long> tempAccounts = new HashSet<>(accountsById);
        for (Long accountNumber : tempAccounts) {
            User user = accounts.remove(accountNumber);
            if (user != null) {
                nameIndex.get(user.getName()).remove(accountNumber);
                if (nameIndex.get(user.getName()).isEmpty()) {
                    nameIndex.remove(user.getName());
                }

                valueIndex.get(user.getValue()).remove(accountNumber);
                if (valueIndex.get(user.getValue()).isEmpty()) {
                    valueIndex.remove(user.getValue());
                }
            }
        }
        return true;
    }

    private boolean update(Set<Long> accountsById, User user) {
        if (accountsById == null) {
            return false;
        }

        Set<Long> tempAccounts = new HashSet<>(accountsById);
        for (Long accountNumber : tempAccounts) {
            User oldUser = accounts.remove(accountNumber);
            if (oldUser != null) {
                accounts.put(accountNumber, user);
                nameIndex.get(oldUser.getName()).remove(accountNumber);

                if (nameIndex.get(oldUser.getName()).isEmpty()) {
                    nameIndex.remove(oldUser.getName());
                }

                nameIndex.computeIfAbsent(user.getName(), k -> new HashSet<>()).add(accountNumber);
                valueIndex.get(oldUser.getValue()).remove(accountNumber);
                if (valueIndex.get(oldUser.getValue()).isEmpty()) {
                    valueIndex.remove(oldUser.getValue());
                }

                valueIndex.computeIfAbsent(user.getValue(), k -> new HashSet<>()).add(accountNumber);
            }
        }
        return true;
    }
}
