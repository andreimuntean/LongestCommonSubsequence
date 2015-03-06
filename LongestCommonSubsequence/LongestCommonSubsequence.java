import java.util.Scanner;

public class LongestCommonSubsequence
{
	private static int[] firstSequence;
	private static int[] secondSequence;
	private static int[][] dynamic;

	private static void initialize()
	{
		try (Scanner scanner = new Scanner(System.in))
		{
			firstSequence = new int[scanner.nextInt()];

			for (int index = 0; index < firstSequence.length; ++index)
			{
				firstSequence[index] = scanner.nextInt();
			}

			secondSequence = new int[scanner.nextInt()];

			for (int index = 0; index < secondSequence.length; ++index)
			{
				secondSequence[index] = scanner.nextInt();
			}
		}
	}

	private static void computeCommonSubsequenceLengths()
	{
		boolean[] wasAssigned = new boolean[secondSequence.length];

		dynamic = new int[firstSequence.length][secondSequence.length];

		for (int firstIndex = 0; firstIndex < firstSequence.length; ++firstIndex)
		{
			int firstElement = firstSequence[firstIndex];

			for (int secondIndex = 0; secondIndex < secondSequence.length; ++secondIndex)
			{
				int secondElement = secondSequence[secondIndex];
				int modifier = firstElement == secondElement ? 1 : 0;
				int value = dynamic[firstIndex][secondIndex];

				if (!wasAssigned[secondIndex] && firstElement == secondElement)
				{
					value += 1;
					wasAssigned[secondIndex] = true;
				}

				if (firstIndex > 0)
				{
					value += dynamic[firstIndex - 1][secondIndex];
				}

				if (secondIndex > 0)
				{
					value = Math.max(value, dynamic[firstIndex][secondIndex - 1]);
				}

				dynamic[firstIndex][secondIndex] = value;
			}
		}
	}

	private static int getLongestCommonSubsequenceLength()
	{
		int result = 0;

		for (int firstIndex = 0; firstIndex < dynamic.length; ++firstIndex)
		{
			for (int secondIndex = 0; secondIndex < dynamic[firstIndex].length; ++secondIndex)
			{
				result = Math.max(result, dynamic[firstIndex][secondIndex]);
			}
		}

		return result;
	}

	public static void main(String[] args)
	{
		initialize();
		computeCommonSubsequenceLengths();
		System.out.println(getLongestCommonSubsequenceLength());
	}
}