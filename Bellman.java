import java.util.*;

public class Bellman {
   public static int bellman(int[] distance, HashMap<Integer,ArrayList<Edge>> edges, int start){
      boolean[] inQueue = new boolean[distance.length];
      for(int i = 1;i<distance.length;i++){
         distance[i] = Integer.MAX_VALUE;
      }
      distance[start] = 0;
      Queue<Integer> q = new LinkedList<>();
      ((LinkedList<Integer>) q).push(start);
      inQueue[start] = true;
      while(!q.isEmpty()) {
         int node = ((LinkedList<Integer>) q).pop();
         inQueue[node] = false;
         if (edges.containsKey(node)) {
            for (Edge e : edges.get(node)) {
               if (distance[node] != Integer.MAX_VALUE && distance[node] + e.weight < distance[e.dest]) {
                  distance[e.dest] = distance[node] + e.weight;
                  if (!inQueue[e.dest]) {
                     ((LinkedList<Integer>) q).push(e.dest);
                     inQueue[e.dest] = true;
                  }
               }
            }
         }
      }
      for(int key:edges.keySet()){
         for(Edge f:edges.get(key)){
            if(distance[key] != Integer.MAX_VALUE && distance[key]+f.weight<distance[f.dest]){
               return -1;
            }
         }
      }
      return 0;
   }



   public static void main(String[] args){
      Scanner sc = new Scanner(System.in);
      String[] nums = sc.nextLine().split(" ");
      int V = Integer.parseInt(nums[0]);
      int E = Integer.parseInt(nums[1]);
      int st = Integer.parseInt(nums[2]);
      HashMap<Integer,ArrayList<Edge>> neighs = new HashMap<>();
      for(int i = 0;i<E;i++){
         String[] info = sc.nextLine().split(" ");
         int s = Integer.parseInt(info[0]);
         int e = Integer.parseInt(info[1]);
         int w = Integer.parseInt(info[2]);
         if(!neighs.containsKey(s)){
            ArrayList<Edge> n = new ArrayList<>();
            n.add(new Edge(e,w));
            neighs.put(s,n);
         }
         else{
            ArrayList<Edge> t = neighs.get(s);
            t.add(new Edge(e,w));
            neighs.put(s,t);
         }
      }
      int[] dist = new int[V+1];
      int res = bellman(dist,neighs,st);
      if(res == -1){
         System.out.println("Gained Infinite Time");
      }
      else{
         int minNode = 0;
         int minVal = Integer.MAX_VALUE;
         for(int i = 1;i<dist.length;i++){
            if(i!=st){
               if(dist[i]<minVal){
                  minNode = i;
                  minVal = dist[i];
               }
            }
         }
         String result = "";
         if(minVal<0){
            result += "Gained ";
            minVal *= -1;
         }
         else{
            result += "Lost ";
         }
         System.out.println("Destination: "+minNode);
         result += (minVal/3600)+" hrs "+((minVal%3600)/60)+" mins "+((minVal%3600)%60)+" sec";
         System.out.println(result);
      }
   }
}

class Edge{
   public int weight;
   public int dest;

   public Edge(int d, int w){
      weight = w;
      dest = d;
   }
}
