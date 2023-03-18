public class Sodoku{
    public static void main(String... args) {
        var puzzle = new int[82];
        puzzle[81] = -1;
        try {
            for (int i = 0; puzzle[i] != -1; i++) {
                var num = Integer.parseInt(args[i]);
                if (num < 0 || num > 9)
                    throw new IllegalArgumentException("the number in sodoku puzzle should between 0 to 9. Please check your input.");
                puzzle[i] = num;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java -jar Sodoku.jar <sudokuNumbers>");
        }
        if (solveSudoku(puzzle)) {
            System.out.println("A solve of this Soduku:");
            printSudoku(puzzle);
        } else {
            System.out.println("Sorry but it is not solvable. Please check your input.");
        }
    }

    private static void printSudoku(int[] puzzle) {
        for (int i = 0; i < puzzle.length - 1; i++) {
            System.out.print(puzzle[i] + " ");
            if ((i + 1) % 9 == 0)
                System.out.println();
        }
    }

    private static boolean solveSudoku(int[] puzzle) {
        var indexPointer = 0;
        while (puzzle[indexPointer] != 0) {
            if (puzzle[indexPointer] == -1)
                return true;
            indexPointer++;
        }
        var availableElementsFlag = findAvailableElements(puzzle, indexPointer);
        for (int digit = 1; digit <= 9; digit++) {
            if ((availableElementsFlag >>> digit) % 2 == 0) {
                puzzle[indexPointer] = digit;
            } else {
                continue;
            }
            if (solveSudoku(puzzle))
                return true;
        }
        puzzle[indexPointer] = 0;
        return false;
    }

    private static int findAvailableElements(int[] puzzle, int indexPointer) {
        var flagRow = 0;
        var startOfRow = indexPointer / 9 * 9;
        for (int offset = 0; offset < 9; offset++) {
            flagRow |= 1 << puzzle[startOfRow + offset];
        }
        var flagColumn = 0;
        var startOfColumn = indexPointer % 9;
        for (int offset = 0; offset < 9; offset++) {
            flagColumn |= 1 << puzzle[startOfColumn + 9 * offset];
        }
        var flagSquare = 0;
        var startOfSquare = startOfRow / 27 * 27 + startOfColumn / 3 * 3;
        for (int offsetColumn = 0; offsetColumn < 3; offsetColumn++) {
            for (int offsetRow = 0; offsetRow < 3; offsetRow++) {
                flagSquare |= 1 << puzzle[startOfSquare + offsetColumn + offsetRow * 9];
            }
        }
        return flagRow | flagColumn | flagSquare;
    }
}
