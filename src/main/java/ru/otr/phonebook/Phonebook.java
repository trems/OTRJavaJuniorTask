package ru.otr.phonebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Phonebook {

    private HashMap<String, ArrayList<String>> phonebook = new HashMap<>();

    {
        this.phonebook.put("Иванов И.И.", new ArrayList<String>());
        this.phonebook.get("Иванов И.И.").add("+8 800 2000 500");
        this.phonebook.get("Иванов И.И.").add("+8 800 2000 600");
        this.phonebook.put("Петров П.П.", new ArrayList<String>());
        this.phonebook.get("Петров П.П.").add("+8 800 2000 700");
        this.phonebook.put("Сидоров С.С.", new ArrayList<String>());
        this.phonebook.get("Сидоров С.С.").add("+8 800 2000 800");
        this.phonebook.get("Сидоров С.С.").add("+8 800 2000 900");
        this.phonebook.get("Сидоров С.С.").add("+8 800 2000 000");
    }

    /**
     * Выводит в System.out список телефонов для данного ФИО в формате:<br>
     * 1. номер_телефона<br>
     * 2. номер_телефона<br>
     * 3. номер_телефона<br>
     * 4. ...
     * @param name ФИО, для которого нужно вывести телефоны
     */
    public void printPhones(String name) {
        List<String> phones = getPhonesByName(name);
        if (phones.size() > 0) {
            for (int i = 0; i < phones.size(); i++) {
                System.out.printf("%d. %s\n", i+1, phones.get(i));
            }
        } else {
            System.out.println("Такого ФИО нет в БД: "+name);
        }
    }

    /**
     * Возвращает список телефонов пользователя с именем {@code name}
     * @param name имя пользователя, телефоны которого нужно получить
     * @return {@code List<String>} с телефонами (пустой или нет) или пустой неизменяемый список если пользователя нет в БД
     */
    public List<String> getPhonesByName(String name) {
        List<String> phones = this.phonebook.get(name);
        if (phones != null) {
            return phones;
        }
        return Collections.emptyList();
    }

    /**
     * Добавляет телефон для соответствующего ФИО. Дубликаты не добавляются. При отсутствии нужного ФИО в БД добавляет его.
     * @param name ФИО, для которого нужно добавить телефон
     * @param phone номер телефона для добавления
     * @return {@code true} если телефон был успешно добавлен для существующего или вновь созданного ФИО.
     * {@code false} если у данного ФИО уже есть такой телефон.
     */
    public boolean addPhone(String name, String phone) {
        List<String> phones = getPhonesByName(name);
        if (phones.size() > 0) {
            if (!phones.contains(phone)) {
                return phones.add(phone);
            }
            return false;
        } else {
            this.phonebook.put(name, new ArrayList<String>());
            return this.phonebook.get(name).add(phone);
        }
    }

    /**
     * Удаляет телефон у данного ФИО.
     * @param name ФИО, у которого нужно удалить телефон
     * @param phone номер телефона для удаления
     * @return {@code true} если телефон был представлен в БД и затем успешно удален
     */
    public boolean removePhone(String name, String phone) {
        List<String> phones = getPhonesByName(name);
        return phones.remove(phone);
    }


    public static void main(String[] args) throws IOException {
        Phonebook phonebook = new Phonebook();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Введите ФИО, чтобы получить список телефонов или exit для выхода");

        String input;
        while (true) {
            input = reader.readLine();
            if (!input.equalsIgnoreCase("exit")) {
                phonebook.printPhones(input);
            } else break;
        }
    }
}
