// Program to Find the Number of Vertices, Even Vertices, Odd Vertices and
// Number of Edges in a Graph

#include<bits/stdc++.h>

using namespace std;

int main() {
	int n;
	cin >> n;

	int a[n][n];

	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			cin >> a[i][j];
		}
	}

	int degree[n] = {};

	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			degree[i] += a[i][j];
		}
	}

	int even = 0, odd = 0, edges = 0;

	for (int i = 0; i < n; i++) {
		even += (degree[i] % 2 == 0);
		odd += (degree[i] % 2 != 0);
		edges += degree[i];
	}

	cout << "number of even vertices" << " = " << even << '\n';
	cout << "number of odd vertices" << " = " << odd << '\n';
	cout << "number of edges" << " = " << edges / 2 << '\n';
}
