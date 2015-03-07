import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Determines the longest common subsequence of two sequences.
 *
 * @author Andrei Muntean
 */
public class LongestCommonSubsequence
{
    private static int[] firstSequence;
    private static int[] secondSequence;

    // dp[i][j] = the size of the longest common subsequence up to the i-th element of
    // firstSequence and the j-th element of secondSequence.
    // The name "dp" derives from the concept used to calculate it: dynamic programming.
    private static int[][] dp;

    private static void read(String path) throws IOException
    {
        try (Scanner scanner = new Scanner(new FileReader(path)))
        {
            // One-based indexing.
            firstSequence = new int[scanner.nextInt() + 1];
            secondSequence = new int[scanner.nextInt() + 1];

            for (int index = 1; index < firstSequence.length; ++index)
            {
                firstSequence[index] = scanner.nextInt();
            }

            for (int index = 1; index < secondSequence.length; ++index)
            {
                secondSequence[index] = scanner.nextInt();
            }
        }
    }

    private static void computeCommonSubsequenceLengths()
    {
        dp = new int[firstSequence.length][secondSequence.length];

        // i = the current index of the first sequence.
        for (int i = 1; i < firstSequence.length; ++i)
        {
            // j = the current index of the second sequence.
            for (int j = 1; j < secondSequence.length; ++j)
            {
                if (firstSequence[i] == secondSequence[j])
                {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                else
                {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
    }

    // Magically (i.e. recursively) prints a common subsequence of maximum length.
    private static void doMagic(PrintWriter printWriter, int i, int j)
    {
        if (0 < i && 0 < j)
        {
            if (firstSequence[i] == secondSequence[j])
            {
                doMagic(printWriter, i - 1, j - 1);
                printWriter.printf("%d ", firstSequence[i]);
            }
            else if (dp[i][j - 1] >= dp[i - 1][j])
            {
                doMagic(printWriter, i, j - 1);
            }
            else
            {
                doMagic(printWriter, i - 1, j);
            }
        }
    }

    private static void write(String path) throws IOException
    {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(path)))
        {
            // Outputs the length of the longest common subsequence.
            printWriter.println(dp[dp.length - 1][dp[0].length - 1]);

            // Outputs the longest common subsequence.
            doMagic(printWriter, dp.length - 1, dp[0].length - 1);
        }
    }

    public static void main(String[] args) throws IOException
    {
        // Opens the file "cmlsc.in" by default.
        read(args != null && args.length > 0 ? args[0] : "cmlsc.in");
        computeCommonSubsequenceLengths();
        write(args != null && args.length > 1 ? args[1] : "cmlsc.out");
    }
}