#include <iostream>
#include <vector>
#include <set>
#include <queue>

using namespace std;

#define MOD 1000000007

//------------------------------------------
int N, S;
vector<set<pair<int,int>>> tree; //id and cost
vector<int> distances;
vector<int> structures;
vector<vector<int>> sockets;
//------------------------------------------
int eulerianTour()
{
    int sum = 0;
    for (int i = 1; i <= N; i++)
        for (auto node : tree[i])
            sum += node.second;
    return sum;
}
//------------------------------------------
int bfs(int startNode)
{
    distances.clear();
    distances.resize(N + 1, -1);
    distances[startNode] = 0;
    queue<int> q;
    q.push(startNode);
    while (!q.empty())
    {
        int u = q.front();
        q.pop();
        for (auto node : tree[u])
        {
            int v = node.first;
            if (distances[v] > -1)
                continue;
            distances[v] = distances[u] + node.second;
            q.push(v);
        }
    }

    int endNode = 1;
    for (int i = 2; i <= N; i++)
        if (distances[i] > distances[endNode])
            endNode = i;
    return endNode;
}
//------------------------------------------
int diameter()
{
    int k = 0; 
    for(; k<=N && tree[k].size()==0; k++);
   
    int u = bfs(k);
    int v = bfs(u);
    return distances[v];
}
//------------------------------------------
int go() 
{
    N = sockets.size()+1;
    vector<bool> targets(N + 1, false);
    for (int x : structures)
        targets[x] = true;

    //adjacency list of the tree
    tree.clear();
    tree.resize(N + 1);
    for (int i = 0; i < N - 1; i++)
    {
        int u = sockets[i][0];
        int v = sockets[i][1];
        int c = sockets[i][2];
        tree[u].insert(make_pair(v, c));
        tree[v].insert(make_pair(u, c));
    }

    //Remove leaves that are not target
    queue<int> q;
    for (int i = 1; i <= N; i++)
        if (tree[i].size() == 1 && !targets[i])
            q.push(i);
    
    while (!q.empty())
    {
        int child = q.front();
        q.pop();
        int parent = tree[child].begin()->first;
        tree[parent].erase(make_pair(child, tree[child].begin()->second));
        tree[child].clear();
        if (tree[parent].size()==1 && !targets[parent])
            q.push(parent);            
    }

    int eulerianLen = eulerianTour();
    int diameterLen = diameter();
    return eulerianLen - diameterLen;
}

bool readInput() {
    cin >> N >> S;
    structures.resize(S);
    for(int i = 0; i < S; i++) {
        cin >> structures[i];
    }
    sockets.resize(N-1);
    for(int sock = 0; sock < N - 1; sock++) {
        sockets[sock].resize(3);
        for(int itr = 0; itr < 3; itr++) {
            cin >> sockets[sock][itr];
        }
    }
}

int main()
{
    readInput();
    cout << go() % MOD << endl;
    return 0;
}