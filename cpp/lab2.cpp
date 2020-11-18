#include <iostream>
#include <vector>

using namespace std;

bool IsValid(vector<vector<int> >&adj){
    int n=adj.size();
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            if(adj[i][j]!=adj[j][i]){
                return false;
            }
        }
    }
    return true;
}

void Union(vector<vector<int> >&adj1,vector<vector<int> >&adj2) {
    cout<<"Union ...."<<endl;
    int n1=adj1.size();
    int n2=adj2.size();
    int n=max(n1,n2);
    vector<vector<int> >adj(n,vector<int>(n));
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            adj[i][j]=((i<n1 && j<n1)?adj1[i][j]:0) || ((i<n2 && j<n2)?adj2[i][j]:0);
            cout<<adj[i][j]<<" ";
        }
        cout<<endl;
    }
    cout<<endl;
}

void Intersection(vector<vector<int> >&adj1,vector<vector<int> >&adj2){
    cout<<"Intersection ..."<<endl;
    int n1=adj1.size();
    int n2=adj2.size();
    int n=min(n1,n2);
    vector<vector<int> >adj(n,vector<int>(n));
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            adj[i][j]=adj1[i][j] && adj2[i][j];
            cout<<adj[i][j]<<" ";
        }cout<<endl;
    }
    cout<<endl;
}

void RingSum(vector<vector<int> >&adj1,vector<vector<int> >&adj2){
    cout<<"RingSum ..."<<endl;
    int n1=adj1.size();
    int n2=adj2.size();
    int n=max(n1,n2);
    vector<vector<int> >adj(n,vector<int>(n));
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            adj[i][j]=((i<n1 && j<n1)?adj1[i][j]:0) ^ ((i<n2 && j<n2)?adj2[i][j]:0);
            cout<<adj[i][j]<<" ";
        }cout<<endl;
    }
    cout<<endl;
}

int main(){
#ifndef ONLINE_JUDGE
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
#endif
    int n1;
    cin>>n1;
    vector<vector<int> >adj1(n1,vector<int>(n1));
    for(int i=0;i<n1;i++){
        for(int j=0;j<n1;j++){
            cin>>adj1[i][j];
        }
    }
    int n2;
    cin>>n2;
    vector<vector<int> >adj2(n2,vector<int>(n2));
    for(int i=0;i<n2;i++){
        for(int j=0;j<n2;j++){
            cin>>adj2[i][j];
        }
    }
    if(IsValid(adj1) && IsValid(adj2)){
        Union(adj1,adj2);
        Intersection(adj1,adj2);
        RingSum(adj1,adj2);
    }else{
        cout<<"Entered Graph is InValid...."<<endl;
    }
    return 0;
}