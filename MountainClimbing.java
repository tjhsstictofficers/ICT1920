import java.util.*;
import java.io.*;

class State
{
   int[] st;
   int num;
   
   public State(int[] st1, int num1)
   {
      st = new int[4096];
      st = st1;
      num = num1;
   }
   
   public void set(int x, int y)
   {
      st[x] = y;
   }
}

public class MountainClimbing
{
   public static int MAXN = 15;
   public static int MAXM = 99999999;
   public static State[] a;;
   public static int m;
   public static int n;
   public static int[][] f;
   
   public static void getstate(int i, int t)
   {
      int num = 0;
      for (int j = 0; j < (1 << n); j++)
      {
         if ( (j & (j << 1)) != 0 )
            continue;
         if ( (j & (j >> 1)) != 0 )
            continue;
         if  ( (j & t) != 0 )
            continue;
         
         ++num;
         a[i].set(num, j);   
         
      }
      a[i].num = num;
   }
   
    
   public static int dp()
   {
      int ans = 0;
      
      for (int i=1; i<=a[1].num; i++)
         f[1][i] = 1;
      
      for (int i=2; i<=m; i++)
      {
         for (int j=1; j<=a[i].num; j++)
         {
            f[i][j] = 0;
            for (int k=1; k<=a[i-1].num; k++)
            {
               if ( (a[i].st[j] & a[i-1].st[k]) != 0 )
                  continue;
               f[i][j] += f[i-1][k];
            }
         }
      }
      
      for (int i=1; i<=a[m].num; i++)
         ans = (ans + f[m][i]) % 100000000; 
      return ans;
   }
   
   public static void main(String[] args) throws IOException 
   {
      Scanner sc = new Scanner(System.in);
      StringTokenizer st = new StringTokenizer(sc.nextLine());
      m = Integer.parseInt(st.nextToken());
      n = Integer.parseInt(st.nextToken());
      int t;
      int x;
      a= new State[MAXN];
      for (int i=0; i<MAXN; i++)
      {
         int[] temp = new int[4096];
         int temp2 = 0;
         a[i] = new State(temp, temp2);
      }
      f = new int[MAXN][1000];
            
      for (int i=1; i<=m; i++)
      {
         t = 0;
         st = new StringTokenizer(sc.nextLine());
         for (int j=1; j<=n; j++)
         {
            x = Integer.parseInt(st.nextToken());
            t = (t << 1) + 1 - x;
         }
         getstate(i, t);
      }   
   
      int ans = dp();
    
      System.out.println(ans);   
      sc.close();
   }	



}