#include <bits/stdc++.h>
using namespace std;

int size;

int minKey(int key[], bool mstSet[]) {
    int min = INT_MAX, min_index;

    for (int v = 0; v < size; v++)
        if (mstSet[v] == false && key[v] < min)
            min = key[v], min_index = v;

    return min_index;
}

void printMST(int parent[], vector<vector<int>>& graph) {
    cout<<"Edge \tWeight\n";
    for (int i = 0; i < graph.size()-1; i++)
        cout<<parent[i]<<" - "<<i<<" \t"<<graph[i][parent[i]]<<" \n";
}

void primMST(vector<vector<int>>& graph) {
	size = graph.size();
	int parent[graph.size()];
	bool mstSet[graph.size()];
	int key[graph.size()];

	for(int i=0;i<graph.size();i++){
		mstSet[i]=false;
		key[i]=INT_MAX;
	}

	key[5]=0;
	parent[5]=-1;
	for(int c=0;c<graph.size()-1;c++){
		int u=minKey(key, mstSet);
		mstSet[u]=true;
		for(int v=0;v<graph.size();v++){
			if(graph[u][v] && key[v]>graph[u][v] && u!=v){
				parent[v]=u, key[v]=graph[u][v];
			}
		}
	}
	printMST(parent, graph);
}

int main()  {
#ifndef ONLINE_JUDGE
    freopen("input.txt", "r",stdin);
    freopen("output.txt", "w",stdout);
#endif


    int vertex;
    cin>>vertex;
    vector<vector<int>> graph(vertex, vector<int>(vertex, 0));

    for(int i=0;i<vertex;i++){
    	for(int j=0;j<vertex;j++){
    		cin>>graph[i][j];
    	}
    }

    //Parallel Edges
    for(int i=0;i<graph.size();i++){
        for(int j=0;j<graph.size();j++){
            graph[i][j] = min(graph[i][j], graph[j][i]);
        }
    }

 	primMST(graph);

    return 0;
}