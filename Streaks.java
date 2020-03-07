import java.util.*;
import java.io.*;

public class Streaks {
   public static void main(String[] args){
      Scanner sc = new Scanner(System.in);
      String[] nums = sc.nextLine().split(" ");
      int M = Integer.parseInt(nums[0]);
      int N = Integer.parseInt(nums[1]);
      int Q = Integer.parseInt(nums[2]);
      HashSet<String> dictionary = new HashSet<>();
      for(int i = 0;i<M;i++){
         dictionary.add(sc.nextLine());
      }
      HashMap<String, Integer> freq = new HashMap<>();
      PriorityQueue<Streak> ordering = new PriorityQueue<>();
      int scount = 0;
      for(int i = 0;i<N;i++){
         String word = sc.nextLine();
         if(dictionary.contains(word)){
            scount++;
            if(freq.containsKey(word)){
               freq.put(word,freq.get(word)+1);
            }
            else{
               freq.put(word,1);
            }
         }
         else{
            if(scount>=Q){
               String temp = "";
               int currMax = 0;
               for(String key:freq.keySet()){
                  if(freq.get(key)>currMax){
                     temp = key;
                     currMax = freq.get(key);
                  }
                  else if(freq.get(key)==currMax && key.compareTo(temp)<0){
                     temp = key;
                  }
               }
               ordering.add(new Streak(scount,temp));
            }
            freq.clear();
            scount = 0;
         }
      }
      if(scount >= Q){
         String temp = "";
         int currMax = 0;
         for(String key:freq.keySet()){
            if(freq.get(key)>currMax){
               temp = key;
               currMax = freq.get(key);
            }
            else if(freq.get(key)==currMax && key.compareTo(temp)<0){
               temp = key;
            }
         }
         ordering.add(new Streak(scount,temp));
      }
      while(!ordering.isEmpty()){
         Streak c = ordering.poll();
         System.out.println(c.count+" "+c.common);
      }
   }
}

class Streak implements Comparable<Streak>{
   public int count;
   public String common;

   public Streak(int c, String co){
      count = c;
      common = co;
   }
   public int compareTo(Streak other){
      if(count>other.count){
         return -1;
      }
      else if(count<other.count){
         return 1;
      }
      else{
         return common.compareTo(other.common);
      }
   }
}
