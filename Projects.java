import java.io.*;
import java.util.*;
public class Projects
{
   public static int INF = Integer.MAX_VALUE;
   public static int MAXN = 1001;
   
   public static void main(String[] args) throws IOException 
   {
      Scanner in = new Scanner(System.in);
      StringTokenizer st = new StringTokenizer(in.nextLine());
      int N, X, Y, Z;
      int n1 = 0, n2 = 0;
      N = Integer.parseInt(st.nextToken());
      X = Integer.parseInt(st.nextToken());
      Y = Integer.parseInt(st.nextToken());
      Z = Integer.parseInt(st.nextToken());		
      int[] A = new int[MAXN];
      int[] B = new int[MAXN];
      int[][] DP = new int[MAXN][MAXN];
      for (int i=0; i<N; i++)
      {
         st = new StringTokenizer(in.nextLine());
         int first = Integer.parseInt(st.nextToken());
         while (first > 0) 
         { 
            A[++n1] = i; 
            first--; 
         } 
         int second = Integer.parseInt(st.nextToken());
         while (second > 0) 
         { 
            B[++n2] = i; 
            second--; 
         }     
      }
      for (int j=0; j<=n2; j++) 
         DP[0][j] = j * X;
      for (int j=0; j<=n1; j++) 
         DP[j][0] = j * Y;
      for (int i=1; i<=n1; i++)
      {
         for (int j=1; j<=n2; j++) 
         {
            DP[i][j] = INF;
            DP[i][j] = Math.min(DP[i][j], DP[i][j-1] + X);
            DP[i][j] = Math.min(DP[i][j], DP[i-1][j] + Y);
            DP[i][j] = Math.min(DP[i][j], DP[i-1][j-1] + Z * Math.abs(A[i]-B[j]));
         }
      }
      System.out.println(DP[n1][n2]);
      in.close();
   }
}
