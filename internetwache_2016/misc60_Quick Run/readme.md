# Misc 60 : Quick Run

**Description:**

Someone sent me a file with white and black rectangles. I don't know how to read it. Can you help me?

[README.txt](files/README.txt?raw=true)

# Writeup

When I opened README.txt, I noticed that it might contain several chunks of base 64 strings. So I wrapped a quick php script with some regex-fu:

```php
<?php

$content = file_get_contents('README.txt');

// Just for formatting purposes
echo '<pre>';

// Regex, now I have two problems :-)
if(preg_match_all('/(?:.{76}\R)+.+/', $content, $matches)) {
    foreach($matches[0] as $match) {
        echo base64_decode($match) . PHP_EOL;
    }
} else {
    echo 'no matches';
}
```

Which resulted into weird characters:

```
â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆ
â–ˆâ–ˆ              â–ˆâ–ˆ  â–ˆâ–ˆ  â–ˆâ–ˆ  â–ˆâ–ˆ              â–ˆâ–ˆ
â–ˆâ–ˆ  â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆ  â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆ  â–ˆâ–ˆ  â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆ  â–ˆâ–ˆ
â–ˆâ–ˆ  â–ˆâ–ˆ      â–ˆâ–ˆ  â–ˆâ–ˆ  â–ˆâ–ˆâ–ˆâ–ˆ    â–ˆâ–ˆ  â–ˆâ–ˆ      â–ˆâ–ˆ  â–ˆâ–ˆ
â–ˆâ–ˆ  â–ˆâ–ˆ      â–ˆâ–ˆ  â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆ  â–ˆâ–ˆ  â–ˆâ–ˆ      â–ˆâ–ˆ  â–ˆâ–ˆ
â–ˆâ–ˆ  â–ˆâ–ˆ      â–ˆâ–ˆ  â–ˆâ–ˆ  â–ˆâ–ˆâ–ˆâ–ˆ  â–ˆâ–ˆâ–ˆâ–ˆ  â–ˆâ–ˆ      â–ˆâ–ˆ  â–ˆâ–ˆ
â–ˆâ–ˆ  â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆ  â–ˆâ–ˆâ–ˆâ–ˆ    â–ˆâ–ˆ  â–ˆâ–ˆ  â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆ  â–ˆâ–ˆ
â–ˆâ–ˆ              â–ˆâ–ˆ  â–ˆâ–ˆ  â–ˆâ–ˆ  â–ˆâ–ˆ              â–ˆâ–ˆ
```
First I thought the message might be hidden and that this was some kind of ascii art. After some thoughts I realised that it might be an encoding issue. So I quickly added an UTF-8 header to the script:

```php
<?php

$content = file_get_contents('README.txt');

// UTF-8 everywhere!
header('Content-Type: text/html; charset=utf-8');

// Just for formatting purposes
echo '<pre>';

// Regex, now I have two problems :-)
if(preg_match_all('/(?:.{76}\R)+.+/', $content, $matches)) {
    foreach($matches[0] as $match) {
        echo base64_decode($match) . PHP_EOL;
    }
} else {
    echo 'no matches';
}
```

The result was astonishing, I got a few UTF-8 QR codes:

![qrcode](files/qrcode.png?raw=true)

Since there were several QR codes, I thought to automate this by taking a screenshot and using python:

```python
import glob
import os
import qrtools

parts = glob.glob("parts/*.png")
qr = qrtools.QR()

for png in parts:
    print(qr.decode(png))
```
Unfortunately the script wasn't able to decode them. So I went and manually decoded them using an Android app. After a while I got:

```
Flagis:IW{QR_C0DES_RUL3}
```