public class KirkmanSolver {

    private static int intAt (char c) {
        int result = Character.getNumericValue(c);
        if (result < 0) {
            throw new IllegalArgumentException();
        }
        return result;
    }

    public static void main (String[] args) {
        // initialize 3D array: [day][row][group]
        char schoolgirls[][][] = new char[7][5][3];
        char girlName = 'A';
        boolean pairs[][] = new boolean[15][15];

        // fill in the first day in alphabetical order *CONSTRAINT*
        for (int i = 0; i < 5; i++) {
            for (int j = 0; i < 3; i++) {
                schoolgirls[0][i][j] = girlName++;
            }
        }

        // first group on second day will be 'ADG'
        schoolgirls[1][0][0] = 'A';
        schoolgirls[1][0][1] = 'D';
        schoolgirls[1][0][2] = 'G';

        // initialize the pairs table based off of the constraints
        for (char i = 'A'; i < 'O'; i += 4) {
            for (int j = 0; j < 3; j++) {
                if (j == 2) {
                    pairs[intAt(i - 'A')][intAt(i - 'A') - 2] = true;
                    pairs[intAt(i - 'A') - 2][intAt(i - 'A')] = true;
                } else {
                    pairs[intAt(i - 'A')][intAt(i - 'A') + 1] = true;
                    pairs[intAt(i - 'A') + 1][intAt(i - 'A')] = true;
                }
            }
        }
        

    }
}