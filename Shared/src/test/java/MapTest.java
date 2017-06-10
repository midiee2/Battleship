import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import Model.*;
import org.junit.Test;

public class MapTest {
    @Test
    public void constructorTest() {
        Map map = new Map();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals(map.getFieldState(i, j), FieldState.EMPTY);
            }
        }
    }

    @Test
    public void getFieldStateTest() {
        Map map = new Map();

        assertEquals(map.getFieldState(0, 0), FieldState.EMPTY);
    }

    @Test
    public void setFieldStateTest() {
        Map map = new Map();
        FieldState newFieldState = FieldState.SHIP;

        map.setFieldState(0, 0, newFieldState);
        assertEquals(map.getFieldState(0, 0), newFieldState);
    }

    @Test
    public void getGridTest() {
        Map map = new Map();
        FieldState[][] correctGrid = new FieldState[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                correctGrid[i][j] = FieldState.EMPTY;
            }
        }

        assertArrayEquals(map.getGrid(), correctGrid);
    }

    @Test
    public void setGridTest() {
        Map map = new Map();
        FieldState newFieldState = FieldState.SHIP;
        FieldState[][] newGrid = new FieldState[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                newGrid[i][j] = FieldState.EMPTY;
            }
        }
        newGrid[0][0] = newFieldState;

        map.setGrid(newGrid);
        assertArrayEquals(map.getGrid(), newGrid);
    }

    @Test
    public void equalsTest() {
        Map same1 = new Map();
        Map same2 = new Map();
        Map diff = new Map();
        diff.setFieldState(0, 0, FieldState.SHOTED);

        assertEquals(same1, same1);
        assertEquals(same1, same2);
        assertNotEquals(same1, diff);
    }

    @Test
    public void countFieldsTest() {
        Map map = new Map();

        map.setFieldState(0, 0, FieldState.SHIP);
        map.setFieldState(0, 1, FieldState.SHIP);
        map.setFieldState(0, 2, FieldState.SHOTED);

        int countShip = map.countFields(FieldState.SHIP);
        int countShoted = map.countFields(FieldState.SHOTED);
        int countEmpty = map.countFields(FieldState.EMPTY);

        assertEquals(2, countShip);
        assertEquals(1, countShoted);
        assertEquals(97, countEmpty);
    }

    @Test
    public void updateMapWithShotTestWithEmptyToShoted() {
        Map map = new Map();

        int[] coordinates = {0, 0};

        assertEquals(false, map.updateMapWithShot(coordinates));
        assertEquals(1, map.countFields(FieldState.SHOTED));
        assertEquals(99, map.countFields(FieldState.EMPTY));
        assertEquals(0, map.countFields(FieldState.HIT));
        assertEquals(0, map.countFields(FieldState.SHIP));
    }

    @Test
    public void updateMapWithShotTestWithShipToHit() {
        Map map = new Map();
        map.setFieldState(0, 0, FieldState.SHIP);

        int[] coordinates = {0, 0};

        assertEquals(true, map.updateMapWithShot(coordinates));
        assertEquals(0, map.countFields(FieldState.SHOTED));
        assertEquals(99, map.countFields(FieldState.EMPTY));
        assertEquals(1, map.countFields(FieldState.HIT));
        assertEquals(0, map.countFields(FieldState.SHIP));
    }

    @Test
    public void placeShipTest() {
        Map map = new Map();
        int[] firstCoordinates = {0, 0};
        int[] lastCoordinates = {0, 4};

        map.placeShip(firstCoordinates, lastCoordinates);
        for (int i = 0; i < 5; ++i) {
            assertEquals(FieldState.SHIP, map.getFieldState(0, i));
        }

        assertEquals(map.countFields(FieldState.EMPTY), 95);
        assertEquals(map.countFields(FieldState.SHOTED),0);
        assertEquals(map.countFields(FieldState.HIT), 0);
    }
}
