import java.util.*;
import java.io.*;
public class ubi { 
   static int segmax[];
   static long segsum[];
   static int a[];
   static int l, r;
   static int D[];
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      StringTokenizer st = new StringTokenizer(br.readLine());
   	
      int n = Integer.parseInt(st.nextToken()), m = Integer.parseInt(st.nextToken());
      st = new StringTokenizer(br.readLine());
      a = new int[n+1];
      for(int i=1;i<=n;i++)
         a[i] = Integer.parseInt(st.nextToken());
   	
      D = divisors();
   	
      segmax = new int[n*4];
      segsum = new long[n*4];
   	
      build(1, 1, n);
   	
      StringBuffer sb = new StringBuffer();
      while(m-->0){
         st = new StringTokenizer(br.readLine());
         int i = Integer.parseInt(st.nextToken());
         l = Integer.parseInt(st.nextToken());
         r = Integer.parseInt(st.nextToken());
      	
         if(i==1){
            replace(1, 1, n);
         }
         else{
            sb.append(query(1, 1, n)+"\n");
         }
      }
   	
      System.out.print(sb);
   }
 
   static long query(int idx, int L, int R) {
      if(l<=L && R<=r)
         return segsum[idx];
   	
      int M = (L+R)/2;
      long sum = 0;
      if(l<=M)
         sum += query(idx*2, L, M);
      if(r>M)
         sum += query(idx*2 + 1 , M+1, R);
   	
      return sum;
   }
 
   static void replace(int idx, int L, int R) {		
      if(segmax[idx] <= 2)
         return;
   	
      if(L==R){
         a[L] = D[a[L]];
         segsum[idx] = a[L];
         segmax[idx] = a[L];
         return;
      }	
   	
      int M = (L+R)/2;
      if(l<=M)
         replace(idx*2, L, M);
      if(r>M)
         replace(idx*2 + 1 , M+1, R);
   	
      segmax[idx] = Math.max(segmax[idx*2], segmax[idx*2+1]);
      segsum[idx] = segsum[idx*2] + segsum[idx*2+1];
   }
 
   static void build(int idx, int L, int R) {
      if(L==R){
         segmax[idx] = a[L];
         segsum[idx] = a[L];
         return;
      }
   	
      int M = (L+R)/2;
      build(idx*2, L, M);
      build(idx*2 + 1 , M+1, R);
   	
      segmax[idx] = Math.max(segmax[idx*2], segmax[idx*2+1]);
      segsum[idx] = segsum[idx*2] + segsum[idx*2+1];
   }
 
   static int[] divisors() {
      int D[] = new int[1000001];
   	
      for(int i=1;i<D.length;i++){
         for(int j=i;j<D.length;j+=i){
            D[j]++;
         }
      }
      return D;
   }
 
}