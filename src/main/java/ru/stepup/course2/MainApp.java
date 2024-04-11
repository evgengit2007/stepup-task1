package ru.stepup.course2;

import java.util.HashMap;

public class MainApp {
    public static void main(String[] args) throws EmptyUndo{
        Account account1 = new Account("Bob"); // корректное значение
        System.out.println(account1.getNameHolder());
        // Добавление валюты
        account1.setValuta(Valuta.EUR, 100);
        System.out.println(account1.toString());
        account1.setNameHolder("Vasya");
        System.out.println(account1.toString());
        account1.setValuta(Valuta.EUR, 300);
        System.out.println(account1.toString());
        account1.setValuta(Valuta.USD, 200);
        System.out.println(account1.toString());
        account1.setTypeAccount(TypeAccList.USUALY); // установили обычный
        System.out.println(account1.toString());
        account1.setTypeAccount(TypeAccList.PREMIUM); // установили премиальный
        System.out.println(account1.toString());
        System.out.println("======Часть 2 Начали откат===========");
        account1.undo();
        System.out.println(account1.toString());
        account1.undo();
        System.out.println(account1.toString());
        account1.undo();
        System.out.println(account1.toString());
        account1.undo();
        System.out.println(account1.toString());
        System.out.println("Нечего удалять");
//        account1.undo().undo().undo(); // раскомментарить, если хотим увидеть exception
        // ========часть 3 сохранение одиночное===========
        System.out.println("========часть 3 сохранение одиночное===========");
        Account account3 = new Account("Bob3");
        account3.setNameHolder("Vasya3");
        account3.setTypeAccount(TypeAccList.USUALY);
        System.out.println("Сохраняем");
        Loader save1 = account3.Save();
        System.out.println(account3.toString());
        account3.setNameHolder("Vasya333");
        System.out.println(account3.toString());
        System.out.println("Восстанавливаем сохранение");
        save1.load();
        System.out.println(account3.toString());

        // =========Часть 3 список сохранения==============
        System.out.println("=========Часть 3 список сохранения==============");
        Account account4 = new Account("Bob4");
        account4.setValuta(Valuta.EUR, 1000);
        account4.setNameHolder("Vasya4");
        account4.setValuta(Valuta.EUR, 3000);
        System.out.println(account4.toString());
        System.out.println("Сохраняем 1");
        account4.saveList();
        account4.setValuta(Valuta.USD, 2000);
        account4.setNameHolder("Vasya44");
        account4.setValuta(Valuta.USD, 2500);
        System.out.println(account4.toString());
        System.out.println("Сохраняем 2");
        account4.saveList();
        System.out.println("Восстанавливаем сохранение 1");
        account4.loadList(0);
        System.out.println(account4.toString());
        System.out.println("Восстанавливаем сохранение 2");
        account4.loadList(1);
        System.out.println(account4.toString());

    }
}
