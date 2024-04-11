package ru.stepup.course2;

import java.util.*;

public class Account {
    public Loader Save() {return new SaveAccount();}
    private class SaveAccount implements Loader {
        private String nameHolder;
        private HashMap<Valuta, Integer> valutaIntegerHashMap;
        private TypeAccList typeAccount;

        public SaveAccount() {
            this.nameHolder = Account.this.nameHolder;
            this.valutaIntegerHashMap = new HashMap<>(Account.this.valutaIntegerHashMap);
            this.typeAccount = Account.this.typeAccount;
        }

        @Override
        public void load() {
            Account.this.nameHolder = this.nameHolder;
            Account.this.valutaIntegerHashMap = new HashMap<>(this.valutaIntegerHashMap);
            Account.this.typeAccount = this.typeAccount;
        }
    }
    private String nameHolder;
    private HashMap<Valuta, Integer> valutaIntegerHashMap;

    private ArrayDeque<ExecuteOper> executeOpers = new ArrayDeque<>();

    // проверка при добавлении нового поля метод undo с ним работает и менять его не надо
    private TypeAccList typeAccount;

    // сохранение Account часть 3 задания
    private List<Loader> loaderList = new ArrayList<>();
    public Account(String nameHolder) {
        setNameHolder(nameHolder);
        this.valutaIntegerHashMap = new HashMap<>();
    }

    public String getNameHolder() {
        return nameHolder;
    }

    public void setNameHolder(String nameHolder) {
        // проверяем, что имя владельца не пустое
        if (nameHolder == null || nameHolder.isEmpty()) {
            throw new NullPointerException("Необходимо указать Имя владельца!");
        }
        // сохраним старое значение
        String oldNameHolder = this.nameHolder;
        if ((oldNameHolder == null||oldNameHolder.isEmpty()) == false) {
            executeOpers.push(() -> this.nameHolder = oldNameHolder);
        }
        // устанавливаем новое
        this.nameHolder = nameHolder;
    }

    public HashMap<Valuta, Integer> getValutaIntegerHashMap(HashMap<Valuta, Integer> valutaIntegerHashMap) {
        return new HashMap<Valuta, Integer>(this.valutaIntegerHashMap);
    }

    public void setValuta(Valuta nameVal, Integer countVal) {
        // проверяем значение countVal на меньше нуля
        if (countVal < 0) {
            throw new IllegalArgumentException("Количество должно быть больше или равно нулю");
        }
        // ищем валюту у счета, если не нашли, создаем, если нашли, обновляем
        // сохраняем прежнее значение
        if (valutaIntegerHashMap.containsKey(nameVal)) {
            Integer oldCountVal = valutaIntegerHashMap.get(nameVal);
            executeOpers.push(() -> this.valutaIntegerHashMap.put(nameVal, oldCountVal));
        } else {
            executeOpers.push(() -> this.valutaIntegerHashMap.remove(nameVal));
        }
        // устанавливаем новое значение
        this.valutaIntegerHashMap.put(nameVal, countVal);

    }

    public void setTypeAccount(TypeAccList typeAccount) {
        // сохраним старое значение
        TypeAccList oldTypeAccount = this.typeAccount;
        executeOpers.push(() -> this.typeAccount = oldTypeAccount);
        this.typeAccount = typeAccount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "nameHolder='" + nameHolder + '\'' +
                ", valutaIntegerHashMap=" + valutaIntegerHashMap +
                ", typeAccount=" + typeAccount +
                '}';
    }

    public Account undo() throws EmptyUndo {
        if (executeOpers.isEmpty()) throw new EmptyUndo();
        executeOpers.pop().comply();
        return this;
    }

    public void saveList() {
        this.loaderList.add(this.Save());
    }

    public void loadList(Integer integer) {
        this.loaderList.get(integer).load();
    }


}
