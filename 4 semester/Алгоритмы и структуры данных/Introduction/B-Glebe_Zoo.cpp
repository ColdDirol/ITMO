#include <iostream>
#include <string>
#include <stack>

using namespace std;
int main() {
    stack<char> big;
    stack<char> low;
    stack<char> chars;

    stack<int> bigNums;
    stack<int> lowNums;
    int bigCnt = 0;
    int lowCnt = 0;

    string request;
    cin >> request;

    int len = request.size()/2;
    int indexes[len];

    for (char c : request) {
        int asciiValue = static_cast<int>(c);
        if (asciiValue >= 97 && asciiValue <= 122) {
            // Диапазон для маленьких букв a-z
            low.push(c);
            lowCnt++;
            lowNums.push(lowCnt);

            if(!chars.empty() && chars.top() == toupper(c)) {
                indexes[bigNums.top() - 1] = lowNums.top();
                bigNums.pop();
                lowNums.pop();
                big.pop();
                low.pop();

                chars.pop();
            } else {
                chars.push(c);
            }
        } else {
            // Диапазон для больших букв A-Z
            big.push(c);
            bigCnt++;
            bigNums.push(bigCnt);

            if(!chars.empty() && chars.top() == tolower(c)) {
                indexes[bigNums.top() - 1] = lowNums.top();
                bigNums.pop();
                lowNums.pop();
                big.pop();
                low.pop();

                chars.pop();
            } else {
                chars.push(c);
            }
        }
    }

    if(big.empty() && low.empty()) {
        cout << "Possible" << endl;
        for(int i : indexes) {
            cout << i << " ";
        }
    } else {
        cout << "Impossible";
    }
}