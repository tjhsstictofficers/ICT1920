import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
 
public class Corona {
 
   static int R,C,Q,MAX_SCORE;
   static final int[] dr = new int[] {-1,1,0,0};
   static final int[] dc = new int[] {0,0,-1,1};
	
   static int[][] col, anyToAny;
   static int[][][] closestCol;
   static ArrayList<Query> qs;
   static queue q;
   static int answers[];
	
	
   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      PrintWriter out = new PrintWriter(System.out);
      R = in.nextInt();
      C = in.nextInt();
      MAX_SCORE = in.nextInt();
      col = new int[R][C];
      for(int i = 0; i < R; i++)
         for(int j = 0; j < C; j++)
            col[i][j] = in.nextInt()-1;
   	
      qs = new ArrayList<Query>();
   
      Q = in.nextInt();
      for(int i = 0; i < Q; i++) {
         int a = in.nextInt()-1;
         int b = in.nextInt()-1;
         int c = in.nextInt()-1;
         int d = in.nextInt()-1;
         qs.add(new Query(a,b,c,d,i));
      }
   	
      q = new queue(2*R*C);
      anyToAny = new int[MAX_SCORE][MAX_SCORE];
      closestCol = new int[MAX_SCORE][R][C];
      for(int a[] : anyToAny) 
         Arrays.fill(a, R*C);
      for(int a[][]:closestCol)
         for(int b[]:a)
            Arrays.fill(b, -1);
      for(int i = 0; i < MAX_SCORE; i++) solve(i);
   			
      for(int k = 0; k < MAX_SCORE; k++)
         for(int i = 0; i < MAX_SCORE; i++)
            for(int j = 0; j < MAX_SCORE; j++)
               if(anyToAny[i][j] > anyToAny[i][k] + anyToAny[k][j])
                  anyToAny[i][j] = anyToAny[i][k] + anyToAny[k][j];
   
   	
      answers = new int[Q];
      Arrays.fill(answers, R*C);
      for(Query q : qs) {
      	// just walk
         answers[q.id] = min(answers[q.id], abs(q.r1-q.r2) + abs(q.c1-q.c2)); 
      	// warp
         answers[q.id] = min(answers[q.id], 1 + anyToAny[col[q.r1][q.c1]][col[q.r2][q.c2]] + 1);
      	
         for(int w1 = 0; w1 < MAX_SCORE; w1++) {
            answers[q.id] = min(answers[q.id], closestCol[w1][q.r1][q.c1] + 1 + closestCol[w1][q.r2][q.c2]);
            for(int w2 = 0; w2 < MAX_SCORE; w2++) {
               int val = 0;
               val += closestCol[w1][q.r1][q.c1];
               val += closestCol[w2][q.r2][q.c2];
               val += 1 + anyToAny[w1][w2];
               answers[q.id] = min(answers[q.id], val);
            }
         }
      	
      }
   	
   	
      for(int ii : answers) out.println(ii);
      out.close();
   }
	
   static void solve(int k) {
      q.clear();
      anyToAny[k][k] = 1;
   	
      for(int r = 0; r < R; r++) {
         for(int c = 0; c < C; c++) {
            if(col[r][c] == k) {
               closestCol[k][r][c] = 0;
               q.add(r); q.add(c);
            }
         }
      }
   	
      while(!q.isEmpty()) {
         int r = q.poll();
         int c = q.poll();
         for(int kk = 0; kk < 4; kk++) {
            int nr = r+dr[kk];
            int nc = c+dc[kk];
            if(nr < 0 || nr >= R || nc < 0 || nc >= C || closestCol[k][nr][nc] != -1) 
               continue;
            closestCol[k][nr][nc] = closestCol[k][r][c]+1;
            anyToAny[k][col[nr][nc]] = min(anyToAny[k][col[nr][nc]], 1 + closestCol[k][nr][nc]);
            q.add(nr); q.add(nc);
         }
      }
   }
	
	
   static int min(int a, int b) { 
      return a < b ? a : b;}
   static int abs(int x) { 
      return x < 0 ? -x : x;}
	
	
   static class Query {
      int r1,c1,r2,c2,id;
      public Query(int a, int b, int c, int d, int e) {
         r1=a;
         c1=b;
         r2=c;
         c2=d;
         id = e;
      }
   }
	
   static class queue{
      int v[],l,r;
      public queue(int sz) {
         v = new int[sz+2];
         l=r=0;
      }
      boolean isEmpty() { 
         return l==r;}
      void clear() { l=r=0;}
      void add(int x) { v[r++] = x;}
      int poll() { 
         return v[l++];}
   }
}