package ru.otr.phonebook;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PhonebookTest {

    private Phonebook phonebook = new Phonebook();

    @Test
    public void printPhones() {
        ByteArrayOutputStream actualOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(actualOutput));

        phonebook.printPhones("Петров П.П.");
        assertEquals("1. +8 800 2000 700\n", actualOutput.toString());
        actualOutput.reset();

        String undefined = "Такогонетв Б.Д.";
        phonebook.printPhones(undefined);
        assertEquals("Такого ФИО нет в БД: " + undefined + "\n", actualOutput.toString());
    }

    @Test
    public void getPhonesByName() {
        List<String> expected = new ArrayList<>();
        expected.add("+8 800 2000 500");
        expected.add("+8 800 2000 600");

        assertEquals(expected, phonebook.getPhonesByName("Иванов И.И."));
    }

    @Test
    public void addPhoneSuccess() {
        assertEquals(3, phonebook.getPhonesByName("Сидоров С.С.").size());
        assertTrue(phonebook.addPhone("Сидоров С.С.", "+8 800 2000 666"));
        assertEquals(4, phonebook.getPhonesByName("Сидоров С.С.").size());
    }

    @Test
    public void addPhoneNewName() {
        assertEquals(0, phonebook.getPhonesByName("Шарашин М.А.").size());
        assertTrue(phonebook.addPhone("Шарашин М.А.", "+7 952 692 44 88"));
        assertEquals(1, phonebook.getPhonesByName("Шарашин М.А.").size());
    }

    @Test
    public void addPhoneDuplicate() {
        assertEquals(2, phonebook.getPhonesByName("Иванов И.И.").size());
        assertFalse(phonebook.addPhone("Иванов И.И.", "+8 800 2000 600"));
        assertEquals(2, phonebook.getPhonesByName("Иванов И.И.").size());
    }

    @Test
    public void removePhoneExisted() {
        assertEquals(3, phonebook.getPhonesByName("Сидоров С.С.").size());
        assertTrue(phonebook.removePhone("Сидоров С.С.", "+8 800 2000 900"));
        assertEquals(2, phonebook.getPhonesByName("Сидоров С.С.").size());
    }

    @Test
    public void removePhoneNoNumber() {
        assertEquals(3, phonebook.getPhonesByName("Сидоров С.С.").size());
        assertFalse(phonebook.removePhone("Сидоров С.С.", "+8 800 2222 999"));
        assertEquals(3, phonebook.getPhonesByName("Сидоров С.С.").size());
    }

    @Test
    public void removePhoneNoSuchName() {
        assertEquals(0, phonebook.getPhonesByName("Шарашин М.А.").size());
        assertFalse(phonebook.removePhone("Шарашин М.А.", "+7 952 692 44 88"));
        assertEquals(0, phonebook.getPhonesByName("Шарашин М.А.").size());
    }
}