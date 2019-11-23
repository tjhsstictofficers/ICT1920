import java.io.*;
import java.util.*;

public class Word_Reactions {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		Stack<String> st = new Stack<String>();
		for (int i = 0; i < n; i++) {
			String next = sc.next();
			if (st.size() == 0) st.push(next);
			else {
				String prev = st.peek();
				if (prev.equals(next)) {
					st.pop();
				}
				else {
					st.push(next);
				}
			}
		}
		System.out.println(st.size());
		sc.close();
	}

}
