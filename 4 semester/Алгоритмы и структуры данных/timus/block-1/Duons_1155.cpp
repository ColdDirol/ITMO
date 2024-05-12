#include <vector>
#include <iostream>

using namespace std;

int sum(int a, int b, int c, int d, int e, int f, int g, int h) {return (a + b + c + d + e + f + g + h);}

void min_x_y(vector<string> &answer, string xy, int &x, int &y) {
    int min = std::min(x, y); // удаляем все смежные дуоны
    x -= min;
    y -= min;
    for (int i = 0; i < min; i++) {
        answer.push_back(xy);
    }
}

void min_x_y_plus(vector<string> &answer, string xy1, string xy2, string xy3, int &x, int &y) {
    int min = std::min(x, y); // для противолежащих вершин добавляем доп ребро и удаляем с его помошью дуоны
    x -= min;
    y -= min;
    for (int i = 0; i < min; i++) {
        answer.push_back(xy1);
    }
    for (int i = 0; i < min; i++) {
        answer.push_back(xy2);
    }
    for (int i = 0; i < min; i++) {
        answer.push_back(xy3);
    }
}

int main()
{
    vector<string> answer;
    int a, b, c, d, e, f, g, h;
    cin >> a >> b >> c >> d >> e >> f >> g >> h;
    if (!(sum(a, b, c, d, e, f, g, h) % 2 == 0)) {
        cout << "IMPOSSIBLE";
    } else {
        while (sum(a, b, c, d, e, f, g, h) > 0) {
            bool flag = false;
            if (a > 0) { // если в вершине есть дуоны и всмежной вершине есть дуоны, то удаляем и так для всех вершин
                flag = true;
                if (b > 0) {min_x_y(answer, "AB-", a, b);}
                else if (d > 0) {min_x_y(answer, "AD-", a, d);}
                else if (e > 0) {min_x_y(answer, "AE-", a, e);}
                else if (g > 0) {min_x_y_plus(answer, "BC+", "AB-", "CG-", a, g);} // для противо лежащих вершин добавляем дополнительное ребро
                else {flag = false;}
            } else if (b > 0) {
                flag = true;
                if (c > 0) {min_x_y(answer, "BC-", b, c);}
                else if (f > 0) {min_x_y(answer, "BF-", b, f);}
                else if (h > 0) {min_x_y_plus(answer, "CD+", "BC-", "DH-", b, h);}
                else {flag = false;}
            } else if (c > 0) {
                flag = true;
                if (d > 0) {min_x_y(answer, "CD-", c, d);}
                else if (g > 0) {min_x_y(answer, "CG-", c, g);}
                else if (e > 0) {min_x_y_plus(answer, "AD+", "CD-", "AE-", c, e);}
                else {flag = false;}
            } else if (d > 0) {
                flag = true;
                if (h > 0) {min_x_y(answer, "DH-", d, h);}
                else if (f > 0) {min_x_y_plus(answer, "AB+", "AD-", "BF-", d, f);}
                else {flag = false;}
            } else if (e > 0) {
                flag = true;
                if (f > 0) {min_x_y(answer, "EF-", e, f);}
                else if (h > 0) {min_x_y(answer, "EH-", e, h);}
                else {flag = false;}
            } else if (f > 0) {
                if (g > 0) {min_x_y(answer, "FG-", f, g);
                    flag = true;
                }
            } else if (g > 0) {
                if (h > 0) {min_x_y(answer, "GH-", g, h);
                    flag = true;
                }
            } if (!flag) { // проверяем, что были внесены изменения - если нет - выходим
                break;
            }
        }
        if (sum(a, b, c, d, e, f, g, h) > 0) {
            cout << "IMPOSSIBLE";
        } else {
            for (int i = 0; i < answer.size(); i++) {
                cout << answer[i] << endl;
            }
        }
    }
    return 0;
}
