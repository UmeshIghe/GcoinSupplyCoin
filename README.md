# GcoinSupplyCoin

def url = "https://www.example.com/path/to/file.html"
def regex = /\/[^/]+\/([^/]+)\/[^/]+$/

def matcher = url =~ regex
if (matcher.matches()) {
  def secondLastWord = matcher.group(1)
  println(secondLastWord)
}


url="https://www.example.com/path/to/file.html"
second_last_word=$(echo $url | sed 's/.*\/\([^/]*\)\/[^/]*$/\1/')
echo $second_last_word
