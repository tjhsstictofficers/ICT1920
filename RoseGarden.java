import java.io.*;
import java.util.*;


public class RoseGarden
{
   public static int MAXN = 5005;
   public static int MAXM = 100005;
   public static void main(String[] args) throws IOException 
   {
      Scanner sc = new Scanner(System.in);
      StringTokenizer st = new StringTokenizer(sc.nextLine());
      int N = Integer.parseInt(st.nextToken());
      int M = Integer.parseInt(st.nextToken());
      int[] task = new int[N];
      int[] cost = new int[M];
      int[] dp = new int[N+1];
      for (int i=0; i<N; i++)
         task[i] = Integer.parseInt(sc.nextLine());
       
      Arrays.sort(task);
   
              
      for (int i=0; i<M; i++)
         cost[i] = Integer.parseInt(sc.nextLine());
     
      for (int i = M - 2; i >= 0; i--)
         cost[i] = Math.min (cost [i], cost [i + 1]);
         
      for (int i = 0; i <= N; i++)
         dp[i] = Integer.MAX_VALUE;
      dp [0] = 0;
   
      for (int n = 1; n <= N; n++)
         for (int i = 0; i < n; i++)
            dp[n] = Math.min (dp[n], dp[i] + cost[task[n - 1] - task[i]]);
   
      System.out.println(dp[N]);   
      sc.close();
   }	
}