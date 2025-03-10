package dp;

import java.util.ArrayList;
import java.util.HashMap;

public class Dynamo {
	static HashMap<Integer, Long> memo = new HashMap<Integer, Long>();
	static HashMap<Integer, Integer> memo2 = new HashMap<Integer, Integer>();
	static int dumbDiceCount;
	static int smartDiceCount;
	static int knapDumb = 0;
	static int knapSmart = 0;
	public static void main(String[] args) {
		//System.out.println(coinCount(new int[] { 1, 5, 10, 25 }, 34));
		System.out.println(diceCountD(8,24));
		System.out.println(diceCountM(8,24));
		System.out.println(dumbDiceCount);
		System.out.println(smartDiceCount);
		
		int[][] diceTable = new int[9][25];
		for(int i=1;i<7;i++) {
			diceTable[1][i]=1;
		}
		for(int dice=2; dice<9; dice++) {
			for(int target=1; target<25; target++) {
				for(int i=1; i<=6; i++) {
					if(target-i>=0)
						diceTable[dice][target] += diceTable[dice-1][target-i];
				}
			}
		}
		System.out.println(diceTable[8][24]);
		System.out.println(newton(4,1000));
		double[] values = {100,200,50,300,400,25};
		double[] weights = {3,5,10,1,2,1};
		System.out.println(knapsack(values,weights,10));
		System.out.println(knapDumb);
		System.out.println(knapSmart(values, weights, 10, 0, 0));
		System.out.println(knapSmart);
		
		System.out.println(catalan(7));
		System.out.println(catalanDP(7));
		
		System.out.println(LCS("ABC"));
	}
	
	public static double knapsack(double[] vals, double[] ws, double cap) {
		return knapsack(vals,ws,cap,0,0);
	}
	public static double knapsack(double[] vals, double[] ws, double cap, int i, double max) {
		knapDumb++;
		if(i==vals.length)
			return max;
		double with=0;
		if(ws[i]<=cap)
			with = knapsack(vals, ws, cap-ws[i], i+1, max+vals[i]);
		double without = knapsack(vals, ws, cap, i+1, max);
		return Math.max(with, without);
	}
	public static double knapSmart(double[] vals, double[] ws, double cap, int i, double max) {
		double[][] memo = new double[ws.length][(int)cap+1];
		for(int row=0; row<memo.length;row++) 
			for(int col = 0; col<memo[row].length;col++) 
				memo[row][col] = -1;
		return knapSmart(vals,ws,cap,i,max,memo);
	}
	public static double knapSmart(double[] vals, double[] ws, double cap, int i, double max, double[][] memo) {
		knapSmart++;
		if(i==vals.length)
			return max;
		if (memo[i][(int)cap] > -0.9)
			return memo[i][(int)cap];
		double with=0;
		if(ws[i]<=cap)
			with = knapSmart(vals, ws, cap-ws[i], i+1, max+vals[i], memo);
		double without = knapSmart(vals, ws, cap, i+1, max, memo);
		memo[i][(int)cap] = Math.max(with, without);
		return memo[i][(int)cap]; 
	}
	
	private static double function(double x) {
        return Math.sin(x);
    }

    private static double derivative(double x) {
        return Math.cos(x);
    }
	
	public static double newton(double x, int i) {
		if(i==0) {
			return Double.POSITIVE_INFINITY;
		}
		if(Math.abs(function(x)-0)<.0000000000001) {
			return x;
		}
		double xn = x - (function(x)/derivative(x));
		return newton(xn, i-1);
	}

	public static long fib(int n) {
		if (n <= 2)
			return 1;
		if (memo.containsKey(n))
			return memo.get(n);
		memo.put(n, fib(n - 1) + fib(n - 2));
		return memo.get(n);
	}

	public static int coinCount(int[] denom, int total) {
		if (total == 0)
			return 0;
		if (total < Integer.MAX_VALUE)
			return 0;
		if (memo2.containsKey(total))
			return memo2.get(total);
		int count = 0;
		memo2.put(total, count);
		for (int coin : denom) {
			count += coinCount(denom, total - coin);
		}
		return count;
	}

	public static int diceCountD(int dice, int target) {
		dumbDiceCount++;
		if(dice==0&&target==0) 
			return 1;
		if(target<=0||dice<=0)
			return 0;
		int count = 0;
		for(int i=1;i<7;i++)
			count+= diceCountD(dice-1,target-i);
		return count;
	}
	public static int diceCountM(int dice, int target) {
		int[][] memoT = new int[dice+1][target+1];
		return diceCountM(dice,target,memoT);
	}
	public static int diceCountM(int dice, int target, int[][] memoT) {
		smartDiceCount++;
		if(dice==0&&target==0) 
			return 1;
		if(target<=0||dice<=0)
			return 0;
		if(memoT[dice][target]!=0) 
			return memoT[dice][target];
		int count = 0;
		for(int i=1; i<7; i++)
			count+= diceCountM(dice-1,target-i,memoT);
		memoT[dice][target] = count;
		return count;
	}
	
	
	public static int catalan(int n) {
		
		if (n == 0 || n == 1) {
			return 1;
		}
		int sum = 0;
		for (int k = 1; k < n; k++) {
			sum += catalan(k)*catalan(n-k);
		}
		return sum;
	}
	
	public static int catalanDP(int n) {
		int memo[] = new int[n+1];
		memo[0] = 1;
		memo[1]= 1;
		return catalanDP(n, memo);
	}
	
	public static int catalanDP(int n, int memo[]) {
		int sum = 0;
		for (int k = 1; k < n; k++) {
			if (memo[k] == 0) {
				memo[k] = catalanDP(k, memo);
			} else if (memo[n-k] == 0) {
				memo[n-k] = catalanDP(n-k, memo);
			}
			sum += memo[k]*memo[n-k];
		}
		return sum;
	}
	
	public static int LCS(String s) {
		
		return -1;
	}
	
	// Longest subsequence
    static int LIS(int[] arr) {
        int n = arr.length;
        int res = 1;
        for (int idx = 1; idx < n; idx++)
            res = Math.max(res, lisEndingAtIdx(arr, idx));
        return res;
    }
    static int lisEndingAtIdx(int[] arr, int idx) {
        if (idx == 0)
            return 1;
        int mx = 1;
        for (int prev = 0; prev < idx; prev++)
            if (arr[prev] < arr[idx])
                mx = Math.max(mx, lisEndingAtIdx(arr, prev) + 1);
        return mx;
    }

    // Edit distance, m = s1.length, n = s2.length
    static int minEdit(String s1, String s2, int m, int n, int[][] memo) {
        // If first string is empty, the only option is to
        // insert all characters of second string into first
        if (m == 0) return n;
        // If second string is empty, the only option is to
        // remove all characters of first string
        if (n == 0) return m;
        if (memo[m][n] != -1) return memo[m][n];
        // If last characters of two strings are same, get the count for remaining strings.
        if (s1.charAt(m - 1) == s2.charAt(n - 1)) {
            memo[m][n] = minEdit(s1, s2, m - 1, n - 1, memo);
        } else {
            // If last characters are not same, consider all three operations
            // Insert, // Remove, // Replace
            memo[m][n] = 1 + Math.min(Math.min(minEdit(s1, s2, m, n - 1, memo), minEdit(s1, s2, m - 1, n, memo)), minEdit(s1, s2, m - 1, n - 1, memo));
        }
        return memo[m][n];
    }

    // Steps
    static int waysToCoverDistance(int steps, int[] memo) {
        if (steps < 0) return 0;
        if (steps == 0) return 1;
        if (memo[steps] != -1) return memo[steps];
        int ways = waysToCoverDistance(steps - 1, memo) + waysToCoverDistance(steps - 2, memo) + waysToCoverDistance(steps - 3, memo);
        memo[steps] = ways;
        return ways;
    }


    // Divisor Game
    public boolean divGame(int n, int p) {
        if(n == 1 && p == 1)
            return false;
        if(n == 1)
            return true;
        return divGame(n-1, (p+1)%2);
    }

    // Sum Subset, n = arr.length
    static boolean isSubsetSum(int[] arr, int n, int sum, int[][] memo) {
        if (sum == 0)
            return true;
        if (n == 0)
            return false;
        if (memo[n][sum] != -1) {
            return memo[n][sum] == 1;
        }
        // If last element is greater than sum, ignore it
        if (arr[n - 1] > sum) {
            memo[n][sum] = isSubsetSum(arr, n - 1, sum, memo) ? 1 : 0;
        }
        // Check if sum can be obtained by including or excluding the last element
        memo[n][sum] = (isSubsetSum(arr, n-1, sum, memo) || isSubsetSum(arr, n-1, sum - arr[n-1], memo)) ? 1 : 0;
        return memo[n][sum] == 1;
    }

    //TABULATION SUM
    static boolean isSubsetSum(int[] arr, int sum) {
        int n = arr.length;

        // Create a 2D array for storing results of subproblems
        boolean[][] dp = new boolean[n + 1][sum + 1];

        // If sum is 0, then answer is true (empty subset)
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }
        // Fill the dp table in bottom-up manner
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                if (j < arr[i - 1]) {
                    // Exclude the current element
                    dp[i][j] = dp[i - 1][j];
                }
                else {
                    // Include or exclude
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - arr[i - 1]];
                }
            }
        }
        return dp[n][sum];
    }


    // Shortest Common Subsequence

    static int superSeq(String s1, String s2, int m, int n, int[][] memo) {
        if (m == 0)
            return n;
        if (n == 0)
            return m;

        // If the result has already been computed, return it
        if (memo[m][n] != -1)
            return memo[m][n];

        // If the last characters of both strings match
        if (s1.charAt(m - 1) == s2.charAt(n - 1))
            return memo[m][n] = 1 + superSeq(s1, s2, m - 1, n - 1, memo);

        // If the last characters do not match, take the minimum of excluding the last character of either s1 or s2, and add 1 for the current character in the supersequence
        return memo[m][n] = 1 + Math.min(superSeq(s1, s2, m - 1, n, memo), superSeq(s1, s2, m, n - 1, memo));
    }


    // Are there two subsets that sum to each other? sum = sum/2
    static boolean isSubsetSum(int n, int[] arr, int sum, int[][] memo) {
        if (sum % 2 != 0)
            return false;
        if (sum == 0) return true;
        if (n == 0) return false;
        if (memo[n-1][sum] != -1) return memo[n-1][sum] == 1;

        // If element is greater than sum, then ignore it
        if (arr[n-1] > sum)
            memo[n-1][sum] = isSubsetSum(n - 1, arr, sum, memo) ? 1 : 0;

        // Check if sum can be obtained by any of the following
        // (a) including the current element
        // (b) excluding the current element
        memo[n-1][sum] = isSubsetSum(n-1, arr, sum, memo) || isSubsetSum(n-1, arr, sum-arr[n-1], memo) ? 1 : 0;
        return memo[n-1][sum] == 1;
    }


    // Cut Rod Max Profit, i = price.length
    static int cutRodMax(int i, int[] price, int[] memo) {
        if (i == 0) return 0;
        if (memo[i-1] != -1) return memo[i-1];
        int max = 0;
        for (int j = 1; j <= i; j++) {
            max = Math.max(max, price[j-1] + cutRodMax(i-j, price, memo));
        }
        return memo[i - 1] = max;
    }
	
	
	public static ArrayList<String> substrings(String s, ArrayList<String> subs, int i) {
		if (i == s.length())
			return subs;
		else {
			substrings(s, subs, i+1);
			subs.add(s.substring(0, i));
			substrings(s, subs, i+1);
		}	
		ArrayList<String> out = new ArrayList<String>();
		
		return out;
	}
	
//	Kattio io = new Kattio();
//    int N = io.nextInt();
//    int[] height = new int[N];
//    for (int i = 0; i < N; ++i) { height[i] = io.nextInt(); }
//
//    // dp[N] is the minimum cost to get to the Nth stone
//    int[] dp = new int[N];
//    Arrays.fill(dp, Integer.MAX_VALUE);
//    // dp[0] = 0 is our base case since we're already at the first stone
//    dp[0] = 0;
//
//    // for each state, calculate the states it leads to
//    for (int i = 0; i < N - 1; ++i) {
//            // jump one stone
//            dp[i + 1] =
//                Math.min(dp[i + 1], dp[i] + Math.abs(height[i] - height[i + 1]));
//            // jump two stones
//            if (i + 2 < N) {
//                    dp[i + 2] =
//                        Math.min(dp[i + 2], dp[i] + Math.abs(height[i] - height[i + 2]));
//            }
//    }
//
//    System.out.println(dp[N - 1]);
	
}
