import java.io.*;
import java.util.*;
public class Multidimensional_Cube {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int t = sc.nextInt();
		
		long[][] dp = new long[n+1][2];
		int mod = 40000;
		
		dp[0][0] = 1; // 1 way to end on starting vertex in 0 seconds
		for (int i = 1; i <= t; i++) {
			int this_col = i % 2;
			int other_col = 1 - this_col;
			long coeff1 = 0;
			long coeff2 = 0;
			for (int j = 0; j < n+1; j++) {
				if (j == 0) {
					dp[0][this_col] = dp[1][other_col];
					continue;
				}
				else if (j == n) {
					dp[n][this_col] = dp[n-1][other_col];
					continue;
				}
				coeff1 = (n+1-j) % mod;
				coeff2 = (j+1) % mod;
				dp[j][this_col] = (coeff1*dp[j-1][other_col] % mod + coeff2*dp[j+1][other_col] % mod);
				dp[j][this_col] %= mod;
			}
		}
		System.out.println(dp[0][t%2]);
		sc.close();
	}

}
