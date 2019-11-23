import java.util.*;
import java.io.*;
import java.util.*;

public class MST
{
   public static int V[];
   public static Route E[];
   
   public static int find(int x) 
   {
      if (x == V[x])
         return x;
      else
      {
         V[x] = find(V[x]);
         return V[x];
      }
   }
   
   public static boolean merge(int x, int y) {
      int X = find(x);
      int Y = find(y);
      if(X == Y) 
         return false;
      V[X] = V[Y] = V[x] = V[y] = Math.min( X , Y);
      return true;
   }
   
   public static void main(String[] args) throws IOException 
   {
      Scanner in = new Scanner(System.in);
      char flag = 'S';
      StringTokenizer stn = new StringTokenizer(in.nextLine());
      int N = Integer.parseInt(stn.nextToken());
      V = new int[N];
      for (int i = 0; i < N; i++)
         V[i] = i;
      int M = Integer.parseInt(stn.nextToken());
      E = new Route[M];
      for(int i = 0; i < M; i++) 
      {
         stn = new StringTokenizer(in.nextLine());
         int x = Integer.parseInt(stn.nextToken());
         int y = Integer.parseInt(stn.nextToken());
         int z = Integer.parseInt(stn.nextToken());
         E[i] = new Route(x-1, y-1, z);
      }
      Arrays.sort(E);
       
      long cost = 0;
      int mergs = 0;
      for(int i = 0; i < M; ) 
      {
         int j;
         int num = 0;
         int tot = 0;
         Set<Pair> st = new HashSet<Pair>();
         for(j = i; j < M && E[j].c == E[i].c; j++) 
         {
            int A = find(E[j].a);
            int B = find(E[j].b);
            
            if (B < A)
            {
               int temp = B;
               B = A;
               A = temp;
            }
            if(A != B) 
            {
               st.add(new Pair(A, B));
               tot++;
            }
         }
         
         for(; i < j; i++) 
         {
            if (merge(E[i].a, E[i].b))
               num += 1;
         }
         mergs += num;
         cost += num * E[i - 1].c;
         if(tot == 3) 
         {
            if(num == 1 || num == 2 && st.size() == 3) 
               flag = 'M';
               
            if(num == 2 && st.size() == 2) 
               flag = 'M';
               
         }
         if(tot == 2 && num == 1) 
            flag = 'M';
            
      }   
      System.out.println("" + cost + " " + flag);
      in.close();
   }
}

class Route implements Comparable<Route>

{
   public int a;
   public int b;
   public int c;
   
   public Route(int x, int y, int z)       {
      a = x;
      b = y;
      c = z;
   }
   
   public int compareTo(Route other)
   {
      return (this.c - other.c);   
   }
}

class Pair    
{
   public int first;
   public int second;
   
   public Pair(int x, int y)       
   {
      first = x;
      second = y;
   }   
}


