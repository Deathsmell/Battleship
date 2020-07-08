package by.deathsmell.battleship.Validators;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.*;
import java.awt.Point;


public class CodeWarsTests {

    @Test
    public void fixedTests() {

        assertTrue(BattleFieldValidator.fieldValidator(new int[][]
                {{1, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                        {1, 0, 1, 0, 1, 1, 1, 0, 1, 0},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}), "Example test");

        assertTrue(BattleFieldValidator.fieldValidator(new int[][]
                {{1, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                        {1, 0, 1, 0, 1, 1, 1, 0, 1, 0},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}), "Must return true for valid field");

        assertFalse(BattleFieldValidator.fieldValidator(new int[][]
                {{1, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                        {1, 0, 1, 0, 1, 1, 1, 0, 1, 0},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}), "Must return false if unwanted ships are present");

        assertFalse(BattleFieldValidator.fieldValidator(new int[][]
                {{1, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                        {1, 0, 1, 0, 1, 1, 1, 0, 1, 0},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 1, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}), "Must return false if number of ships of some type is incorrect");

        assertFalse(BattleFieldValidator.fieldValidator(new int[][]
                {{0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {0, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 1, 0, 1, 1, 1, 0, 1, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}), "Must return false if some of ships is missing");

        assertFalse(BattleFieldValidator.fieldValidator(new int[][]
                {{1, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                        {1, 0, 1, 0, 1, 1, 1, 0, 1, 0},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 1, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}), "Must return false if ships are in contact");

        assertFalse(BattleFieldValidator.fieldValidator(new int[][]
                {{1, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                        {1, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                        {1, 1, 0, 0, 1, 1, 1, 0, 1, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}), "Must return false if some of ships has incorrect shape (non-straight)");

        assertFalse(BattleFieldValidator.fieldValidator(new int[][]
                {{1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                        {1, 0, 1, 0, 1, 1, 1, 0, 1, 0},
                        {1, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}), "Must return false if the number and length of ships is not ok");
    }



    /* *********************
     *     RANDOM TESTS
     * *********************/


    final static private List<Point> MOVES = Arrays.asList(
            new Point(0, 1), new Point(0, -1), new Point(1, 0), new Point(-1, 0),
            new Point(1, 1), new Point(-1, -1), new Point(-1, 1), new Point(1, -1)
    );

    final private static Map<Integer, Integer> VALID_BY_SIZE = new HashMap<Integer, Integer>() {{
        put(4, 1);
        put(3, 2);
        put(2, 3);
        put(1, 4);
    }};


    private static boolean validationator(int[][] field) {
        if (Stream.of(field).mapToLong(a -> IntStream.of(a).sum()).sum() != 20)
            return false;

        int[][] copyField = Stream.of(field).map(a -> Arrays.copyOf(a, a.length)).toArray(int[][]::new);
        Map<Integer, Integer> cnts = new HashMap<Integer, Integer>();
        flood(0, 0, copyField, cnts);

        return VALID_BY_SIZE.equals(cnts);
    }


    private static void flood(int x, int y, int[][] field, Map<Integer, Integer> cnts) {

        while (x < 10 && field[x][y] == 0) {
            int z = 10 * x + y + 1;
            x = z / 10;
            y = z % 10;
        }

        if (x > 9) return;

        Set<Point> bag = new HashSet<>(Arrays.asList(new Point(x, y))),
                found = new HashSet<>();

        while (!bag.isEmpty()) {
            found.addAll(bag);
            bag.forEach(p -> field[p.x][p.y] = 0);

            bag = bag.stream().flatMap(p -> MOVES.stream().map(m -> new Point(p.x + m.x, p.y + m.y)))
                    .filter(p -> 0 <= p.x && p.x < 10 && 0 <= p.y && p.y < 10 && field[p.x][p.y] == 1)
                    .collect(Collectors.toSet());
        }
        boolean valid = found.stream().map(p -> p.x).distinct().count() == 1 || found.stream().map(p -> p.y).distinct().count() == 1;
        int key = valid ? found.size() : -1;
        cnts.put(key, cnts.getOrDefault(key, 0) + 1);

        if (valid) flood(x, y, field, cnts);
    }


    private final boolean DEBUG = false;
    private final int N_TESTS = 200;


    @Test
    public void randomTests() {

        int nValids = 0;
        for (int r = 0; r < N_TESTS; r++) {
            int[][] field = buildCase();

            if (DEBUG) Stream.of(field).forEachOrdered(a -> System.out.println(Arrays.toString(a)));

            boolean expected = validationator(field),
                    actual = BattleFieldValidator.fieldValidator(field);
            nValids += expected ? 1 : 0;

            if (DEBUG) System.out.println("RESULT = " + expected);

            assertEquals(expected, actual);
        }
        if (DEBUG) System.out.println(String.format("%d/%d valid", nValids, N_TESTS));
    }


    private Random rnd = new Random();

    private int rand(int x) {
        return rand(0, x);
    }

    private int rand(int x, int y) {
        return x + rnd.nextInt(y - x);
    }

    private boolean proba(double d) {
        return rnd.nextDouble() < d;
    }


    private int[][] buildCase() {

        while (true) {
            int[][] field = new int[10][10];
            boolean exclude = proba(.5);
            int[] bboats = proba(.75) ? new int[]{4, 3, 3, 2, 2, 2, 1, 1, 1, 1}
                    : (proba(.5) ? IntStream.range(0, rand(1, 13))
                    : IntStream.range(8, rand(9, 13))).map(x -> rand(1, proba(.9) ? 4 : 5)).sorted().toArray();
            int checkLoop = 0,
                    limitLoop = 100,
                    sumBoats = (int) IntStream.of(bboats).sum();

            if (DEBUG)
                System.out.println(String.format("excluded=%s\n%d boats: %s", exclude, sumBoats, Arrays.toString(bboats)));

            if (sumBoats > 25 && exclude) continue;

            for (int i = bboats.length - 1; i >= 0; i--) {
                int size = bboats[i];

                while (true) {
                    int xx = 0, yy = 0;
                    while (true) {
                        xx = rand(10);
                        yy = rand(10);
                        if (checkLoop++ == limitLoop || field[xx][yy] == 0) break;
                    }
                    if (checkLoop++ >= limitLoop) break;

                    final Point dp = MOVES.get(rand(4));
                    final int x = xx, y = yy;

                    if (IntStream.range(0, size).anyMatch(n -> {
                        int a = x + dp.x * n, b = y + dp.y * n;
                        return a < 0 || b < 0 || a > 9 || b > 9 || field[a][b] != 0;
                    }))
                        continue;
                    IntStream.range(0, size).forEach(n -> {
                        int a = x + dp.x * n, b = y + dp.y * n;
                        field[a][b] = 1;
                    });

                    if (exclude) {
                        for (xx = x - (dp.x == 0 ? 1 : 0); xx <= x + (dp.x == 0 ? 1 : 0); xx++)
                            for (yy = y - (dp.x == 0 ? 0 : 1); yy <= y + (dp.x == 0 ? 0 : 1); yy++)
                                for (int n = -1; n <= size; n++) {
                                    int a = xx + dp.x * n, b = yy + dp.y * n;
                                    if (a < 0 || b < 0 || a > 9 || b > 9 || field[a][b] == 1) continue;
                                    field[a][b] = 2;
                                }
                    }
                    break;
                }
            }
            if (checkLoop >= limitLoop) continue;

            for (int x = 0; x < 10; x++) for (int y = 0; y < 10; y++) if (field[x][y] == 2) field[x][y] = 0;
            return field;
        }
    }
}