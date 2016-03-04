# Misc 80 : 404 Flag not found

**Description:** I tried to download the flag, but somehow received only 404 errors :( Hint: The last step is to look for flag pattern.

[flag.pcapng](files/flag.pcapng?raw=true)

# Writeup

Opening the dump file with wireshark. I see several requests to **flag.txt**. The interesting part was that the URLs were all different:

![1](files/1.png?raw=true)

The subdomain seems like hex encoded, after decoding the first one I got:
```
496e2074686520656e642c206974277320616c6c2061626f757420666c61
In the end, it's all about fla
```

It seems I'm on the right path. I went and decoded the rest:

```
496e2074686520656e642c206974277320616c6c2061626f757420666c61
67732e0a5768657468657220796f752077696e206f72206c6f736520646f
65736e2774206d61747465722e0a7b4f66632c2077696e6e696e67206973
20636f6f6c65720a44696420796f752066696e64206f7468657220666c61
67733f0a4e6f626f62792066696e6473206f7468657220666c616773210a
53757065726d616e206973206d79206865726f2e0a5f4845524f2121215f
0a48656c70206d65206d7920667269656e642c2049276d206c6f73742069
6e206d79206f776e206d696e642e0a416c776179732c20616c776179732c
20666f72206576657220616c6f6e652e0a437279696e6720756e74696c20
49276d206479696e672e0a4b696e6773206e65766572206469652e0a536f
20646f20492e0a7d210a
```

This resulted into:

```
In the end, it's all about flags.
Whether you win or lose doesn't matter.
{Ofc, winning is cooler
Did you find other flags?
Noboby finds other flags!
Superman is my hero.
_HERO!!!_
Help me my friend, I'm lost in my own mind.
Always, always, for ever alone.
Crying until I'm dying.
Kings never die.
So do I.
}!
```

After looking at it for a while I figured that we needed to look at the first letters: `IW{DNS_HACKS}`