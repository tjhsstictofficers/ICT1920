import java.util.*;

public class Popeyes {
    public static int djikstra(HashMap<Integer,ArrayList<Edge>> adj,int start, int[] chic){
        int[] distances = new int[chic.length];
        int[] chickens = new int[chic.length];
        distances[start] = 0;
        chickens[start] = chic[start];
        for(int i = 0;i<chic.length;i++){
            if(i!=start){
                distances[i] = Integer.MAX_VALUE;
                chickens[i] = 0;
            }
        }
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(start,0,chickens[start]));
        HashSet<Integer> visited = new HashSet<>();
        while(queue.size()>0){
            Node node = queue.poll();
            visited.add(node.getNode());
            for(Edge e:adj.get(node.getNode())){
                int end = e.getEnd();
                int cost = e.getCost();
                if(!visited.contains(end)){
                    int newdist = node.getDistance() + cost;
                    int newchicken = node.getChicken() + chic[end];
                    if(newdist < distances[end]){
                        distances[end] = newdist;
                        chickens[end] = newchicken;
                    }
                    queue.add(new Node(end,newdist,newchicken));
                }
            }
        }
        double maxratio = -1.0;
        int max = start;
        for(int j = 0;j<chic.length;j++){
            if(j!=start){
                double ratio = (double)chickens[j]/(double)distances[j];
                if(ratio > maxratio){
                    maxratio = ratio;
                    max = j;
                }
            }
        }
        return max;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String[] inp = sc.nextLine().split(" ");
        int V = Integer.parseInt(inp[0]);
        int E = Integer.parseInt(inp[1]);
        HashMap<Integer, ArrayList<Edge>> adj = new HashMap<>();
        int[] chickens = new int[V];
        for(int i = 0;i<V;i++){
            String[] po = sc.nextLine().split(" ");
            int index = Integer.parseInt(po[0]);
            int ch = Integer.parseInt(po[1]);
            chickens[index] = ch;
        }
        for(int j = 0;j<E;j++){
            String[] indexs = sc.nextLine().split(" ");
            int start = Integer.parseInt(indexs[0]);
            int end = Integer.parseInt(indexs[1]);
            int cost = Integer.parseInt(indexs[2]);
            if(adj.containsKey(start)){
                ArrayList<Edge> t = adj.get(start);
                t.add(new Edge(end,cost));
                adj.put(start,t);
            }
            else{
                ArrayList<Edge> n = new ArrayList<>();
                n.add(new Edge(end,cost));
                adj.put(start,n);
            }
            if(adj.containsKey(end)){
                ArrayList<Edge> te = adj.get(end);
                te.add(new Edge(start,cost));
                adj.put(end,te);
            }
            else{
                ArrayList<Edge> ne = new ArrayList<>();
                ne.add(new Edge(start,cost));
                adj.put(end,ne);
            }
        }
        int start = Integer.parseInt(sc.nextLine());
        int ans = djikstra(adj,start,chickens);
        System.out.println(ans);
    }
}

class Node implements Comparable<Node>{
    private int node;
    private int distance;
    private int chicken;

    public Node(int n, int d, int c){
        node = n;
        distance = d;
        chicken = c;
    }

    public int getDistance() {
        return distance;
    }

    public int getChicken() {
        return chicken;
    }

    public int getNode() {
        return node;
    }

    public void setChicken(int chicken) {
        this.chicken = chicken;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public int compareTo(Node c){
        if(distance<c.getDistance()){
            return -1;
        }
        else if(distance>c.getDistance()){
            return 1;
        }
        else{
            return 0;
        }
    }

}

class Edge{
    private int end;
    private int cost;

    public Edge(int e, int c){
        end = e;
        cost = c;
    }

    public int getCost() {
        return cost;
    }

    public int getEnd() {
        return end;
    }
}
