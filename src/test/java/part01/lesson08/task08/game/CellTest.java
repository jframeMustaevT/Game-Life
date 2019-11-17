package part01.lesson08.task08.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    @Test
    public void testCellInvalidValue() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 1, 1);
    }


    @Test
    public void testGenerationOneBecomesZero() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 1, 1);
        assertEquals(map.getCellAtPosition(1, 1).getNextGenerationValue(), 0);
    }

    @Test
    public void testGenerationOneBecomesZero2() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 1, 1);
        map.setCellValueAtPosition(2, 2, 1);
        assertEquals(map.getCellAtPosition(1, 1).getNextGenerationValue(), 0);
    }

    @Test
    public void testGenerationOneBecomesZero3() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 1, 1);
        map.setCellValueAtPosition(1, 2, 1);
        map.setCellValueAtPosition(1, 3, 1);
        map.setCellValueAtPosition(2, 1, 1);
        map.setCellValueAtPosition(2, 2, 1);
        assertEquals(map.getCellAtPosition(2, 2).getNextGenerationValue(), 0);
    }

    @Test
    public void testGenerationOneBecomesZero4() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 5, 1);
        map.setCellValueAtPosition(2, 5, 1);
        map.setCellValueAtPosition(3, 7, 1);
        map.setCellValueAtPosition(4, 7, 1);
        map.setCellValueAtPosition(6, 7, 1);
        assertEquals(map.getCellAtPosition(3, 7).getNextGenerationValue(), 0);
    }
}
