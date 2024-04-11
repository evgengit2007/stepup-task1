import lombok.*;
import org.junit.jupiter.api.*;
import ru.stepup.course2.*;

public class TestPart1 {
    @Test
    @SneakyThrows(NullPointerException.class)
    public void test1() throws EmptyUndo {
        // корректно создан экземпляр
        Assertions.assertDoesNotThrow(() -> new Account("Bob"));
        // создание с пустым значением
        Assertions.assertThrows(NullPointerException.class, () -> new Account(""));
        // проверка setNameHolder, getNameHolder
        Account account1 = new Account("AAA");
        Account account2 = new Account("BBB");
        account1.setNameHolder("BBB");
        Assertions.assertEquals(account1.getNameHolder(), account2.getNameHolder());
        // проверка setValuta на отрицательное значение
        Assertions.assertThrows(IllegalArgumentException.class, ()-> account1.setValuta(Valuta.EUR, -1));
        // проверка работы undo
        String oldName = account1.getNameHolder();
        account1.setNameHolder("CCC");
        account1.undo();
        Assertions.assertEquals(oldName, account1.getNameHolder());
        // проверка работы исключения, когда откатываться уже некуда
        Assertions.assertThrows(EmptyUndo.class, ()-> account1.undo().undo());
    }

    @Test
    @SneakyThrows(NullPointerException.class)
    public void test2() throws EmptyUndo {

    }


}
