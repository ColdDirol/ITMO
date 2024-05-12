#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

// Для того, чтобы число одинаковых подряд идущих знаков было минимальным, важно не допустить,
// чтобы в начал использовались все знаки, которых меньше по количеству, и не остались только те знаки, которых больше.
// Для этого отсортируе знаки по убыванию количества, и будем брать знак,
// которого больше всего, проверяя, что предыдущий знак отличается.

void sort1(vector<int> &a, int n, vector<int> &numbers) { // сортировка пузырьком по убыванию, на основании количества знаков данного
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n - 1; j++) {
            if (a[j] < a[j + 1]) {
                int temp = a[j];
                a[j] = a[j + 1];
                a[j + 1] = temp;
                temp = numbers[j];
                numbers[j] = numbers[j + 1];
                numbers[j + 1] = temp;
            }
        }
    }
}

int main() {
    int n;
    cin >> n;
    vector<int> count;
    int sumCount = 0; // всего знаков
    int notNull = 0;
    vector<int> numbers;
    for (int i = 0; i < n; i++) {
        int b;
        cin >> b;
        count.push_back(b); // количество знаков каждого вида
        sumCount += b; // всего знаков
        numbers.push_back(i + 1); // номера знаков
        if (b > 0) {
            notNull++; // число знаков, количество которых не равно 0
        }
    }
    int currentCount = 0; // знаков расставленно
    int last = 0; // последний поставленный знак
    if (n > 1) { // если знаков больше одного вида
        sort1(count, n, numbers); // сортируес знаки по их поличеству
        while (currentCount < sumCount) { // пока расставили не все знаки
            for (int k = 0; k < n; k++) {
                if (numbers[k] != last and count[k] > 0) { // если знак с максимальным количеством отличается от последнего поставленного
                    count[k]--; // то ставим его. соответственно, его количество уменьшается
                    cout << numbers[k] << ' '; // выводим этот знак
                    if (count[k] == 0) { // если такие знаки закончились, то количество не закончившихся видов знаков уменьшается
                        notNull--;
                    }
                    last = numbers[k]; // перезаписываем вид последнего поставленного знака
                    for (int i = k + 1; i < n; i++) { // знаки нужно отсортировать, но изменился только один, соответственно только его и сдвегаем, пока массив не отсортируется
                        if (k + 1 < n and count[k] < count[k + 1]) {
                            int t = count[k];
                            count[k] = count[k + 1];
                            count[k + 1] = t;
                            t = numbers[k];
                            numbers[k] = numbers[k + 1];
                            numbers[k + 1] = t;
                        } else {
                            break;
                        }
                    }
                    break;
                }
            }
            currentCount++; // количество поставленных знаков увеличилось
            if (notNull == 1) { // если остался один вид
                for (int k = 0; k < n; k++) { // то дальше ставим только его
                    if (count[k] > 0) {
                        for (int y = 0; y < count[k]; y++) {
                            cout << numbers[k] << ' ';
                            currentCount++;
                        }
                    }
                }
            }
        }
    } else {
        for (int i = 0; i < count[0]; i++) { // если был только один вид знаков, то ставим его
            cout << 1 << ' ';
        }
    }
    return 0;
}
