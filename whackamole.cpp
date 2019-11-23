// Problem C, Whackamole

#include <iostream>
#include <algorithm>
using namespace std;
int mod = 1000000007;
int main() {
	int p, q;
    cin >> p >> q;
	int mole[100000], mallet[100000];
	for (int i = 0; i < p; i++)
		cin >> mole[i];
	for (int j = 0; j < q; j++)
		cin >> mallet[j];
	sort(mole, mole + p);
	sort(mallet, mallet + q);
	int j = 0;
	bool possible = true;
	unsigned long long sol = 0;
	for (int i = 0; i < p; i++) {
		if (j == q) {
			possible = false;
			break;
		}
		while (j < q && mole[j] < mallet[i])
			j++;
		if (j < q) {
			sol += mole[j];
			j++;
		}
	}
	if (possible)
		cout << sol%mod << endl;
	else
		cout << -1 << endl;
	return 0;
}