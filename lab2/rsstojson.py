import re
import datetime
import sys

f = open("D:/desktop/ASN/labs/data/anandtech.xml", "r")
t = f.read()
f.close()

rss = t.split('\n')
t = ""
for i in range(len(rss)):
    t = t + " " + rss[i]

t = re.sub('^\s', '', t)

# Формування масиву заголовків
title = re.findall('<title>(.+?)<\/title>', t)

# Перший заголовок – назва фіду, далі – його специфічна обробка
source = title[0]
source = re.sub('[\s\-]*$', '', source)
source = re.sub('"', '\"', source)

# Розмірність масиву заголовків
x = range(len(title))

# Формування масиву описів
text = re.findall('<description>(.*?)<\/description>', t)

# Формування масиву гіперпосилань
link = re.findall('<link>(.+?)<\/link>', t)

# Формування дати і часу в форматі "YY-MM-DDTHH:MM:00Z"
now = datetime.datetime.now()
tim = now.strftime("%Y-%m-%dT%H:%M:00Z")

with open('anandtech.json', 'w') as f:
    sys.stdout = f
    # Виведення результатів
    for i in range(1, len(title) - 1):
        print("{\n\"title\":\"" + title[i] + "\",")

        # Специфічна обробка тексту
        text[i] = re.sub('[\s\-]*$', '', text[i])
        text[i] = re.sub('"', '\"', text[i])
        text[i] = re.sub('&', ' & amp;', text[i])

        # Подальша виведення результатів
        print("\"textBody\":\"" + text[i] + "\",")

        print("\"source\":\"" + source + "\",")

        print("\"PubDate\":\"" + tim + "\",")

        print("\"URL\":\"", link[i], "\"\n}")

        if i < len(title) - 2:
            print(",")
