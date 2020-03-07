import java.util.*;
import java.util.stream.Stream;
public class Prerequisites {
	
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      int n = sc.nextInt();
      int q = sc.nextInt();
      List<Integer>[] tree = Stream.generate(ArrayList::new).limit(n).toArray(List[]::new);
      for (int i = 0; i < n-1; i++) {
         int a = sc.nextInt()-1;
         int b = sc.nextInt()-1;
         tree[a].add(b);
         tree[b].add(a);
      }
      LcaSparseTable t = new LcaSparseTable(tree, 0);
      for (int i = 0; i < q; i++) {
         int a = sc.nextInt()-1;
         int b = sc.nextInt()-1;
         System.out.println(t.lca(a, b) + 1);
      }
      sc.close();
   }
}

// code from codelibrary implementations
class LcaSparseTable {

   int len;
   int[][] up;
   int[] tin;
   int[] tout;
   int time;

   void dfs(List<Integer>[] tree, int u, int p) {
      tin[u] = time++;
      up[0][u] = p;
      for (int i = 1; i < len; i++)
         up[i][u] = up[i - 1][up[i - 1][u]];
      for (int v : tree[u])
         if (v != p)
            dfs(tree, v, u);
      tout[u] = time++;
   }

   public LcaSparseTable(List<Integer>[] tree, int root) {
      int n = tree.length;
      len = 32 - Integer.numberOfLeadingZeros(n);
      up = new int[len][n];
      tin = new int[n];
      tout = new int[n];
      dfs(tree, root, root);
   }

   boolean isParent(int parent, int child) {
      return tin[parent] <= tin[child] && tout[child] <= tout[parent];
   }

   public int lca(int a, int b) {
      if (isParent(a, b))
         return a;
      if (isParent(b, a))
         return b;
      for (int i = len - 1; i >= 0; i--)
         if (!isParent(up[i][a], b))
            a = up[i][a];
      return up[0][a];
   }
}