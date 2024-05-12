#include <iostream>
#include <unordered_map>
#include <stack>
#include <string>
#include <vector>

using namespace std;

int main() {
    unordered_map<string,vector<int>> variables;    // имя переменной и значения, которые она принимала
    stack<vector<string>> variableStack;            // список переменных, которые мы изменили

    vector<string> newVector; // пустой вектор, просто на всякий случай
    variableStack.push(newVector);


    string line;
    while (getline(cin, line)){
        if (line == "{") {
            variableStack.push(newVector); // новый блок - новый список изменений
        }
        else if (line == "}") {
            for(int i = 0; i < variableStack.top().size(); i++) { // блок закончился - изменения нужно стереть
                if (!variables[variableStack.top()[i]].empty()) { // просто проверка чтоб не было ошибки
                    variables[variableStack.top()[i]].pop_back(); // Мы знаем, какая переменная была изменена. От изменений надо избавиться
                }
            }
            variableStack.pop(); // От всех текущих изменений избавились, ну и кчёрту список, в котором было записано что мы меняли. Начинаем с чистого листа (ладно, вектора)
        }
        else {
            size_t pos = line.find("=");
            if (pos != string::npos) {
                string var1 = line.substr(0, pos);
                string var2 = line.substr(pos + 1);
                if (var1 != var2) {
                    if (isdigit(var2[0]) || (var2[0] == '-' && isdigit(var2[1]))) {
                        // If var2 is a number
                        variables[var1].push_back(stoi(var2)); // переменная принимает новое значение - так запишем изменение в стек, содержащий значения этой переменной
                        variableStack.top().push_back(var1); // чтоб не забыть, что бы её изменили. Так сказать "на память"
                    } else {
                        // If var2 is a variable
                        if (variables.find(var2) == variables.end() || variables[var2].empty()) { // если переменная ещё не разу не встречалась, то по умолчанию она равна нулю
                            variables[var2].push_back(0); // так и запишем
                        }
                        variables[var1].push_back(variables[var2].back()); // записываем новое значение переменной в её стек
                        variableStack.top().push_back(var1); // и про отметку об изменениях не забудем
                        cout << variables[var1].back() << endl; // Print assigned value
                    }
                } else {
                    // If var2 is a variable
                    if (variables.find(var1) == variables.end() || variables[var1].empty()) { // тоже самое, что и в else выше
                        variables[var1].push_back(0);
                        variableStack.top().push_back(var1);
                    }
                    cout << variables[var1].back() << endl;
                }
            }
        }
    }
    return 0;
}
