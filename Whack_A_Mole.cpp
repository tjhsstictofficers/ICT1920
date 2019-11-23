#include <iostream>
#include <algorithm>
using namespace std;
int mod = 1000000007;
int main() {
	int n, m;
    cin >> n >> m;
		int knight[100000], dragon[100000];
		for (int i = 0; i < n; i++)
			cin >> dragon[i];
		for (int j = 0; j < m; j++)
			cin >> knight[j];
		sort(dragon, dragon + n);
		sort(knight, knight + m);
		int j = 0;
		bool possible = true;
		unsigned long long sol = 0;
		for (int i = 0; i < n; i++) {
			if (j == m) {
				possible = false;
				break;
			}
			while (j < m && knight[j] < dragon[i])
				j++;
			if (j < m) {
				sol += knight[j];
				j++;
			}
		}
		if (possible)
			cout << sol%mod << endl;
		else
			cout << -1 << endl;
	return 0;
}
