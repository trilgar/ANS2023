# Define the RSS feed URLs to download
$urls = @(
"http://economictimes.indiatimes.com/tech/software/rssfeeds/13357555.cms",
"http://www.smh.com.au/rssheadlines/technology-news/article/rss.xml"
"http://nypost.com/tech/feed",
"http://zeenews.india.com/rss/science-technology-news.xml",
"http://www.washingtontimes.com/rss/headlines/culture/technology/",
"https://www.thehindu.com/sci-tech/technology/?service=rss",
"https://www.economist.com/science-and-technology/rss.xml",
"https://feeds.skynews.com/feeds/rss/technology.xml",
"http://www.innertemplelibrary.com/category/secure-hospitals/feed/",
"http://e-news.com.ua/export/news.xml",
"http://fakty.ua/rss_feed/science",
"https://www.anandtech.com/rss",
"https://pcper.com/feed/"
)

$filenames = @(
"economictimes",
"smh"
"nypost",
"zeenews",
"washingtontimes",
"thehindu",
"economist",
"skynews",
"innertemplelibrary",
"e-news",
"fakty",
"anandtech",
"pcper.com"
)

# Define the output directory to save the downloaded files
$outDir = "D:\desktop\ASN\labs\data"

# Loop through the RSS feed URLs and download each one
foreach ($url in $urls) {
    # Get the filename from the URL
    $index = [array]::IndexOf($urls, $url)
    $fileName = $filenames[$index]

    # Construct the output file path
    $outFile = "$outDir\$filename.xml"

    # Download the RSS feed and save it to the output file
    Invoke-WebRequest $url -OutFile $outFile
}


# Ім'я об'єднаного файлу RSS
$outputFileName = "combined_rss.xml"

# Створюємо порожній файл
New-Item -ItemType File -Path "$outDir\$outputFileName" -Force | Out-Null

# Для кожного файлу зі списку, додаємо вміст до об'єднаного файлу
foreach ($filename in $filenames) {
    $filePath = "$outDir\$filename.xml"
    if (Test-Path $filePath) {
        $content = Get-Content $filePath
        Add-Content -Path "$outDir\$outputFileName" -Value $content
        Write-Output "Added content from $filePath to $outputFileName"
    }
    else {
        Write-Output "File not found: $filePath"
    }
}

