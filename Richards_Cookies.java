import java.util.*;
public class Richards_Cookies {
   static int N;
   static int[] c;  // cookies
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      N = sc.nextInt();
      c = new int[N];
      for (int i = 0; i < N; i++) c[i] = sc.nextInt();
   
      int[] s1 = new int[N + 1];
      Arrays.fill(s1, Integer.MIN_VALUE);
   
      int[] s2 = new int[N];
      Arrays.fill(s2, Integer.MIN_VALUE);
   
      int[] s3 = new int[N - 1];
      Arrays.fill(s3, Integer.MIN_VALUE);
   
      int[] s4 = new int[N - 2];
      Arrays.fill(s4, Integer.MIN_VALUE);
   
      for (int i = N - 1; i >= 0; i--) s1[i] = Integer.max(s1[i + 1], c[i]);
      for (int i = N - 2; i >= 0; i--) s2[i] = Integer.max(s2[i + 1], s1[i + 1] - c[i]);
      for (int i = N - 3; i >= 0; i--) s3[i] = Integer.max(s3[i + 1], s2[i + 1] + c[i]);
      for (int i = N - 4; i >= 0; i--) s4[i] = Integer.max(s4[i + 1], s3[i + 1] - c[i]);
   
      System.out.println(s4[0]);
   
      sc.close();
   }
}
