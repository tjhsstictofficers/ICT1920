
#include <fstream>
#include <vector>
#include <algorithm>
#include <set>
#include <iostream>
#include <climits>

using namespace std;

#define MOD 1000000007

long long V, M, D;
//---------------------------------------
struct Machine
{
	bool operator < (const Machine & m2)
	{
		return day < m2.day;
	}
	int day, price, resale, moneyPerDay;
};
struct Solution
{
	friend bool operator<(const Solution & sol1, const Solution & sol2);

	long long startDay, moneyPerDay;
	long long prevMoney;
	Solution()
	{

	}

	Solution(long long ss, long long mm, long long pp)
	{
		startDay = ss;
		moneyPerDay = mm;
		prevMoney = pp;
	}

	long long moneyEarned(long long day)
	{
		return prevMoney + (day - startDay)*moneyPerDay;
	}

	long long dayOvertake(Solution sol2)
	{
		long long left = max(startDay, sol2.startDay);
		long long right = left;

		while (moneyEarned(left) > sol2.moneyEarned(right))
		{
			left = right;
			right *= 2;
		}
		long long middle;
		while (left < right)
		{
			middle = left + (right-left)/2;

			long long one = moneyEarned(middle);
			long long two = sol2.moneyEarned(middle);
			if (one > two)
				left = middle+1;
			else
				right = middle;
		}
		return right;
	}
};

bool operator<(const Solution & sol1, const Solution & sol2)
{
	return sol1.moneyPerDay < sol2.moneyPerDay;
}
//---------------------------------------
vector<Machine> machines;
set<Solution> solutions;
//---------------------------------------
bool readInput();
long long go();
//---------------------------------------
int main()
{
	readInput();
	cout << go() % MOD << endl;
	return 0;
}
//---------------------------------------
bool readInput()
{
	cin >> M >> V >> D;
	if (M == 0)
		return false;
	machines.clear();
	machines.resize(M);
	for (int i = 0; i < M; i++)
	{
		cin >> machines[i].day >> machines[i].price;
		cin >> machines[i].resale >> machines[i].moneyPerDay;
	}
	return true;
}
//---------------------------------------
long long go()
{
	solutions.clear();
	sort(machines.begin(), machines.end());

	set<Solution>::iterator it, firstIt, secondIt, preIt, nextIt;
	Solution sol, firstSol, secondSol, preSol, nextSol;
	long long day1, day2;

	long long maxMoney = M;

	for (int i = 0; i < machines.size(); i++)
	{
		Machine m = machines[i];

		//If the second partial solution overtakes the first one before day d
		//remove the first partial solution (eliminate redundant first solutions)
		while (solutions.size() >= 2)
		{
			firstIt = solutions.begin();
			secondIt = firstIt;
			secondIt++;
			firstSol = *firstIt;
			secondSol = *secondIt;
			if (firstSol.moneyEarned(m.day - 1) > (secondSol.moneyEarned(m.day - 1)))
				break;
			solutions.erase(firstSol);
		}

		//firstSol make maxMoney in m.day-1.
		if (solutions.size() > 0)
		{
			sol = *solutions.begin();
			maxMoney = max(maxMoney, sol.moneyEarned(m.day - 1));
		}

		if (m.price > maxMoney)
			continue;

		long long prevMoney = maxMoney - m.price + m.resale;
		Solution newSol(m.day, m.moneyPerDay, prevMoney);

		//Parallel lines
		it = solutions.find(newSol);
		if (it != solutions.end()) //There is a parallel line
		{
			Solution oldSol = *it;
			if (oldSol.moneyEarned(m.day) >= newSol.prevMoney) //oldSol is better. No need to insert newSol.
				continue;
			solutions.erase(it);	//newSol is better. Remove oldSol.
		}

		//newSol is redundant
		nextIt = solutions.upper_bound(newSol);
		if (nextIt != solutions.end())
		{
			nextSol = *nextIt;
			if (nextSol.moneyEarned(m.day) >= newSol.prevMoney)
				continue;
		}

		//nextSol is redundant. Keep removing it.
		while (true)
		{
			nextIt = solutions.upper_bound(newSol);
			if (nextIt != solutions.end())
			{
				nextSol = *nextIt;
				if (newSol.prevMoney > nextSol.moneyEarned(m.day) && newSol.moneyEarned(D) >= nextSol.moneyEarned(D))
				{
					solutions.erase(nextIt);
					continue;
				}
			}
			break;
		}

		//preSol is redundant. Keep removing it.
		while (true)
		{
			preIt = solutions.upper_bound(newSol);
			if (preIt != solutions.end() && preIt != solutions.begin())
			{
				preIt--;
				preSol = *preIt;
				if (newSol.prevMoney > preSol.moneyEarned(m.day))
				{
					solutions.erase(preIt);
					continue;
				}
			}
			break;
		}

		//Invariant between preSol, newSol and nextSol. newSol may become redundant.
		nextIt = solutions.upper_bound(newSol);
		if (nextIt != solutions.end() && nextIt != solutions.begin())
		{
			preIt = nextIt;  preIt--;
			preSol = *preIt;
			if (preSol.moneyEarned(m.day) > newSol.prevMoney && preSol.moneyEarned(D) >= newSol.moneyEarned(D))
				continue;
			nextSol = *nextIt;
			if (nextSol.moneyEarned(m.day) >= newSol.prevMoney)
				continue;
			day1 = preSol.dayOvertake(newSol);
			day2 = newSol.dayOvertake(nextSol);

			if (day1 >= day2)
				continue;
		}

		//Invariant between newSol and next two solutions. nextSol may become redundant.
		while (true)
		{
			nextIt = solutions.upper_bound(newSol);
			if (distance(nextIt, solutions.end()) < 2)
				break;
			auto nextNextIt = nextIt; nextNextIt++;
			nextSol = *nextIt;
			Solution nextNextSol = *nextNextIt;
			day1 = newSol.dayOvertake(nextSol);
			day2 = nextSol.dayOvertake(nextNextSol);

			if (day1 < day2)
				break;
			solutions.erase(nextIt);
		}

		//Invariant between new sol and previous two solutions. preSol may become redundant.
		while (true)
		{
			auto preIt = solutions.lower_bound(newSol);
			if (distance(solutions.begin(), preIt) < 2)
				break;
			preIt--;
			auto prePreIt = preIt; prePreIt--;

			preSol = *preIt;
			Solution prePreSol = *prePreIt;

			day1 = prePreSol.dayOvertake(preSol);
			day2 = preSol.dayOvertake(newSol);
			if (day1 < day2)
				break;
			solutions.erase(preIt);
		}

		solutions.insert(newSol);

	}

	for (Solution s : solutions)
		maxMoney = max(maxMoney, s.moneyEarned(D));

	return maxMoney;
}