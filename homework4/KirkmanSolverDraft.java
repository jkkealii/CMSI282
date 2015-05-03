import java.util.Map;
import java.util.HashMap;

public class KirkmanSolverDraft {

    // initialize 3D array: [day][row][group]
    private static char schoolgirls[][][] = new char[7][5][3];

    // initialize array to keep track of pairs
    private static boolean pairs[][] = new boolean[15][15];

    // keep track of which girls have already been assigned that day
    private static Map<Character, Boolean> roster = new HashMap<Character, Boolean>();

    private static void rosterReset () {
        for (char i = 'A'; i <= 'O'; i++) {
            roster.put(i, false);
        }
    }

    // false if girl isn't used, true if girl is used
    private static void shiftGirl (char name, boolean chosen) {
        roster.put(name, chosen);
    }

    // choose the next girl on the roster (alphabetical order)
    private static char chooseNext () {
        for (char i = 'A'; i <= 'O'; i++) {
            if (!roster.get(i)) {
                shiftGirl(i, true);
                return i;
            }
        }
        return 'n';
    }
    
    private static char chooseNext (char first) {
        char result = 'Z';
        for (char i = 'A'; i <= 'O'; i++) {
            if (!roster.get(i)) {
                result = i;
                break;
            }
        }

        while (pairExists(first, result)) {
            do {
                result++;
            } while (roster.get(result))
        }
        shiftGirl(result, true);
        setPair(first, result, true);

        return result;
    }

    private static char chooseNext (char first, char second) {
        char result = 'Z';
        for (char i = 'A'; i <= 'O'; i++) {
            if (!roster.get(i)) {
                result = i;
                break;
            }
        }

        while (pairExists(first, result) && pairExists(second, result)) {
            do {
                result++;
            } while (roster.get(result))
        }
        shiftGirl(result, true);
        setPair(first, result, true);
        setPair(second, result, true);

        return result;
    }

    // have these two girls already walked together?
    private static boolean pairExists (char uno, char dos) {
        return pairs[(uno - 'A')][(dos - 'A')];
    }

    // "insert" (true) pair into table or "take out" (false) pair
    private static void setPair (char ekahi, char elua, boolean isPair) {
        pairs[(ekahi - 'A')][(elua - 'A')] = isPair;
        pairs[(elua - 'A')][(ekahi - 'A')] = isPair;
    }

    private static void setPair (int alpha, int beta, boolean isPair) {
        pairs[alpha][beta] = isPair; pairs[beta][alpha] = isPair;
    }

    public static void main (String[] args) {
        char girlName = 'A';

        // fill in the first day in alphabetical order *CONSTRAINT*
        for (int i = 0; i < 5; i++) {
            for (int j = 0; i < 3; i++) {
                schoolgirls[0][i][j] = girlName++;
            }
        }

        // first group on second day will be 'ADG' *CONSTRAINT*
        schoolgirls[1][0][0] = 'A'; schoolgirls[1][0][1] = 'D'; schoolgirls[1][0][2] = 'G';

        // fill in the pairs table based off of the constraints
        for (int i = 0; i < 15; i += 4) {
            for (int j = 0; j < 3; j++) {
                if (j == 2) {
                    setPair(i + j, i + j - 2, true);
                } else {
                    setPair(i + j, i + j + 1, true);
                }
            }
        }

        // 'ADG' constraint
        setPair('A', 'D', true); setPair('A', 'G', true); setPair('D', 'G', true);

        // set up second day
        rosterReset();
        shiftGirl('A', true); shiftGirl('D', true); shiftGirl('G', true);

        
    }
}