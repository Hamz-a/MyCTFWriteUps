# Forensics 50 That's No Moon

We were given an image of [moon](files/moon.png). Opening it in a hex editor, I quickly noticed there's a zip file at the end:

![hex moon](files/moon1.png?raw=true)

I used [`foremost`](http://foremost.sourceforge.net) to extract the contents. Who knows I might have overlooked other files:

    foremost -i moon.png -o output -v

![foremost](files/moon2.png?raw=true)

I tried to extract it but it was password protected. After pondering for a while and looking into the image again I thought about guessing the password. It turned out the password was "moon":

![password guessing](files/moon3.png?raw=true)

I could have bruteforced such a simple password.