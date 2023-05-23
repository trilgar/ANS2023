import re
import string
import sys

import numpy as np

f = open("samsung.json", "r")
t = f.read()
f.close()
json = t.split('\n')
t = ""
for i in range(len(json)):
    t = t + " " + json[i]
title = re.findall('"title" : "(.+?)"source"', t)
t = ""
for i in range(len(title)):
    t = t + " " + title[i]
t = re.sub('"textBody" :', '.', t)
t = re.sub('[-–%“”[\]\/0-9",()$+»«—:;_…]', ' ', t)
t = t.upper()
sent = t.split('.')
print(sent)
f = open("words.txt", "r")
t = f.read()
f.close()
t = t.upper()
w = t.split('\n')
for i in range(len(w)-1):
    s = w[i].split(' ')
    w[i] = s[1]

mtr = np.eye(len(w))
for i in range(len(w)):
    mtr[i][i] = 0
stroka = ""
for i in range(len(w)):
    stroka = stroka + ";" + w[i]
print(stroka)

for i in range(len(sent)):
    for j in range(len(w)):
        for k in range(len(w)):
            if (j != k):
                if (re.search(w[j], sent[i])):
                    if (re.search(w[k], sent[i])):
                        mtr[k][j] = mtr[k][j] + 1

with open('matrix.csv', 'w') as output:
    sys.stdout = output
    for i in range(len(w)):
        stroka = w[i] + ";"
        for j in range(len(w)):
            a = int(mtr[i][j])
            b = a.__str__()
            if (j < len(w) - 1):
                stroka = stroka + b + ";"
            else:
                stroka = stroka + b
        print(stroka)
