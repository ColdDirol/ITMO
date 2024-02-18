#include <iostream>
#include <vector>

using namespace std;

int main()
{
    int n;
    cin >> n;
    int numbers[n];

    for (int i = 0; i < n; i++) {
        cin >> numbers[i];
    }

    vector<int> vctr;
    int tmplen = 0;
    int maxlen = 0;
    int first = 0;
    for (int i = 0; i < n; i++) {
        vctr.push_back(numbers[i]);

        if(i == n-1) {
            if(vctr[vctr.size() - 1] != vctr[vctr.size() - 2] || i == n-1 && vctr[vctr.size() - 1] != vctr[vctr.size() - 3]) {
                tmplen = vctr.size();
                if(tmplen > maxlen) {
                    maxlen = tmplen;
                    first = i - maxlen + 1;
                }
                break;
            }
        }

        if(vctr[vctr.size() - 1] == vctr[vctr.size() - 2] && vctr[vctr.size() - 1] == vctr[vctr.size() - 3]) {
            tmplen = vctr.size() - 1;
            if(tmplen > maxlen) {
                maxlen = tmplen;
                first = i - maxlen;
            }
            vctr.erase(vctr.begin(), vctr.end() - 2);
        }

    }

    cout << first + 1 << " " << first + maxlen << endl;
}
