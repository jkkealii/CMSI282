public class KirkmanSolver {
    private static final int GIRLS = 105, FINAL_SLOT = GIRLS - 1, DAYS = 7, ROWS = 5, GROUPS = GIRLS / DAYS;
    private static int checkCounter = 1; 
    private static String[][] groups;
    private static double startTime, endTime, totalTime;
    private static char[] lineup = new char[GIRLS];
    private static boolean pairs[][] = new boolean[15][15];

    private static void printFemales () {
        generateGroups();
        for (int rows = 0; rows < 5; rows++) {
            String row = "";
            for (int days = 0; days < 7; days++) {
                row += groups[rows][days] + " ";
            }
            System.out.println(row);
        }
    }

    private static void generateGroups () {
        groups = new String[ROWS][DAYS]; int cantada = 0;
        for (int days = 0; days < DAYS; days++) {
            for (int rows = 0; rows < ROWS; rows++) {
                groups[rows][days] = "";
                for (int i = 0; i < 3; i++) {
                    groups[rows][days] += String.valueOf(lineup[cantada]);
                    cantada++;
                }
            }
        }
    }

    private static boolean femaleAllowed (int index, char female) {
        int girlsBefore = index % GROUPS, dayCount = (index/15) * 15;
        char penultimateFemale = lineup[index - 1], antepenultimateFemale = lineup[index - 2];
        boolean firstGirl = index % 3 == 0, secondGirl = index % 3 == 1;

        // check if female has been used that day
        if (girlsBefore > 0) {
            for (int i = dayCount; i <= girlsBefore + dayCount; i++) {
                if (female == lineup[i]) {
                    return false;
                }
            }
        }
        // check if pair of females has been used
        if (firstGirl) {
            return true;
        } else if (secondGirl) {
            return !pairExists(female, penultimateFemale);
        } else {
            return !(pairExists(female, penultimateFemale) || pairExists(female, antepenultimateFemale));
        }
    }

    private static char startGirl (int slot) {
        if (slot % 15 == 0) { //very first girl
            return 'A';
        } else if (slot % 3 == 0) { // first column girl
            return (char)(lineup[slot - 3] + 1);
        } else if (slot % 15 == 1) { // first row, second girl
            return (char)(lineup[slot - 15] + 1);
        } else if (slot % 3 > 0) { // second or third girl in a group will be greater than the previous
            return (char)(lineup[slot - 1] + 1);
        }
        return 'D';
    }

    private static char endGirl (int slot) {
        if (slot % 3 < 2) { // if girl is 1st or 2nd in group: M, N, O cannot be used
            return 'L';
        }  
        return 'O';
    }

    // if girl is in the first column of one of the first three rows (A, B or C)
    private static boolean girlSkipped (int slot) {
        return (slot % 3 == 0 && slot % 15 < 7);
    }

    // "insert" (true) pair into table or "take out" (false) pair
    private static void setPair (char ekahi, char elua, boolean isPair) {
        pairs[(ekahi - 'A')][(elua - 'A')] = isPair;
        pairs[(elua - 'A')][(ekahi - 'A')] = isPair;
    }

    // have these two girls already walked together?
    private static boolean pairExists (char uno, char dos) {
        return pairs[(uno - 'A')][(dos - 'A')] && pairs[(dos - 'A')][(uno - 'A')];
    }

    private static void leSolver (int slot, char starter, boolean forward) {
        boolean firstGirl = slot % 3 == 0, secondGirl = slot % 3 == 1; checkCounter++;
        int indexOfPenultimateFemale = slot - 1, indexOfAntepenultimateFemale = slot - 2;
        char penultimateFemale = lineup[indexOfPenultimateFemale];
        char antepenultimateFemale = lineup[indexOfAntepenultimateFemale];
        char newStart = startGirl(slot), endGirl = endGirl(slot);

        if (starter < newStart) { // chooses the start girl that is greater
            starter = newStart;
        }

        if (!forward) {
            // erase the pairs
            if (secondGirl) {
                setPair((char)(starter - 1), penultimateFemale, false);
            } else if (!firstGirl) {
                setPair((char)(starter - 1), penultimateFemale, false);
                setPair((char)(starter - 1), antepenultimateFemale, false);
            }
            lineup[slot] = ' ';

            if (girlSkipped(slot)) {
                leSolver(indexOfPenultimateFemale, ++penultimateFemale, false);
                return;
            }
        }

        for (char lassie = starter; lassie <= endGirl; lassie++) {
            if (femaleAllowed(slot, lassie)) {
                lineup[slot] = lassie;
                // record the apparent pairs
                if (secondGirl) {
                    setPair(lassie, penultimateFemale, true);
                } else if (!firstGirl) {
                    setPair(lassie, penultimateFemale, true);
                    setPair(lassie, antepenultimateFemale, true);
                }

                if (slot == FINAL_SLOT) {
                    System.out.println("finished");
                    endTime = System.currentTimeMillis();
                    totalTime = (endTime - startTime)/1000.00;
                    System.out.println("KIRKMAN TOOK... " + totalTime + " SECONDS, BITCH");
                    printFemales();
                    return;            
                } else {
                    leSolver(slot + 1, 'A', true);
                    return;
                }
            }
        }
        // here is the backtracking, sir
        leSolver(indexOfPenultimateFemale, ++penultimateFemale, false);
    }

    public static void main (String[] args) {
        // initialize lineup using constraints
        char girlName = 'A';
        for (int i = 0; i < 15; i++) {
            lineup[i] = girlName++;
        }
        lineup[15] = 'A'; lineup[16] = 'D'; lineup[17] = 'G';
        // fill in the pairs table based off of the constraints
        for (char i = 'A'; i < 'O'; i += 3) {
            setPair((char)(i), (char)(i + 1), true);
            setPair((char)(i), (char)(i + 2), true);
            setPair((char)(i + 1), (char)(i + 2), true);
        }
        // 'ADG' constraint
        setPair('A', 'D', true); setPair('A', 'G', true); setPair('D', 'G', true);

        startTime = System.currentTimeMillis();
        leSolver(18, 'A', true);
        System.out.println("checks: " + checkCounter);
    }
}