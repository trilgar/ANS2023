import re
import string
import sys

f = open("samsung.json", "r")
t = f.read()
f.close()

json = t.split('\n')
t = ""
for i in range(len(json)):
    t = t + " " + json[i]
# print(t)
title = re.findall('"title" : "(.+?)"source"', t)
t = ""
for i in range(len(title)):
    t = t + " " + title[i]
t = re.sub('"textBody" :', '', t)
t = re.sub('[-–“”[\]\/?0-9",.()$+»«—:;_…]', ' ', t)
t = t.upper()
t = re.sub('\s\w\s', ' ', t)
t = re.sub('\s\w\w\s', ' ', t)
t = re.sub('\s\s+', ' ', t)
word = t.split(' ')
word.sort()

# Dictionary building
d = {}
old = ""
n = 0
for i in range(len(word)):
    if (word[i] == old):
        n = n + 1
    else:
        # print(old,n)
        d[old] = n
        old = word[i]
        n = 1
d[old] = n
# print(d)

sorted_dict = {}
sorted_keys = sorted(d, key=d.get, reverse=True)  # [1, 3, 2]

for w in sorted_keys:
    sorted_dict[w] = d[w]

print(sorted_dict)

f = open("stop.txt", "r")
t = f.read()
f.close()
t = t.upper()
stop = t.split('\n')
M = 50
j = 1
sorted_dict = {}
sorted_keys = sorted(d, key=d.get, reverse=True)  # [1, 3, 2]
with open('words.txt', 'w') as output:
    sys.stdout = output
    for w in sorted_keys:
        sorted_dict[w] = d[w]
        pr = 0
        for i in range(len(stop)):
            if (stop[i] == w):
                pr = 1
        if (pr == 0):
            print(j, w, sorted_dict[w])
            j = j + 1
        if (j > M):
            break
