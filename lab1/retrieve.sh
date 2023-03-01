#!/bin/bash

# список RSS-фідів
rss_feeds=(
    "http://economictimes.indiatimes.com/tech/software/rssfeeds/13357555.cms"
    "http://nypost.com/tech/feed"
    "http://www.smh.com.au/rssheadlines/technology-news/article/rss.xml"
    "http://zeenews.india.com/rss/science-technology-news.xml"
    "http://www.washingtontimes.com/rss/headlines/culture/technology/"
    "https://www.thehindu.com/sci-tech/technology/?service=rss"
    "https://www.economist.com/science-and-technology/rss.xml"
    "https://feeds.skynews.com/feeds/rss/technology.xml"
    "http://www.innertemplelibrary.com/category/secure-hospitals/feed/"
    "http://e-news.com.ua/export/news.xml"
    "http://fakty.ua/rss_feed/science"
    "https://www.anandtech.com/rss"
    "https://pcper.com/feed/"
)

# директорія для збереження даних з RSS-фідів
output_dir="D:\desktop\аналіз соц мереж\labs\data"

# створення директорії, якщо її ще не існує
mkdir -p "$output_dir"

# отримання даних з кожного RSS-фіду та збереження їх у текстовому файлі
for rss_feed in "${rss_feeds[@]}"
do
    file_name=$(echo "$rss_feed" | awk -F '/' '{print $NF}' | sed 's/\.[^\.]*$/.txt/')
    curl --silent "$rss_feed" | rss2txt > "$output_dir/$file_name"
done