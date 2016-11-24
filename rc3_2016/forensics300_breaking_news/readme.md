# Forensics 300 : Breaking news

**Description:** We just received this transmission from our news correspondents. We need to find out what they are telling us.

[Forensics-300.tar.gz](files/Forensics-300.tar.gz?raw=true)

# Writeup

We were given 20 zip files:

```
$ ls
Chapter0.zip   Chapter12.zip  Chapter16.zip  Chapter2.zip  Chapter6.zip
Chapter1.zip   Chapter13.zip  Chapter17.zip  Chapter3.zip  Chapter7.zip
Chapter10.zip  Chapter14.zip  Chapter18.zip  Chapter4.zip  Chapter8.zip
Chapter11.zip  Chapter15.zip  Chapter19.zip  Chapter5.zip  Chapter9.zip
```

I extracted them all, got some text files but could not find anything interesting. I decided to go through the zip files with a hex editor. Something stood out in a few of them:

![1](files/1.png?raw=true)

Apparently there were some base64 encoded chunks appended to some of the zip files. I went trough them manually and decoded them:

```
chapter  4: UkMK      -> RC
chapter  9: My0yMAo=  -> 3-20
chapter 10: MTYtRFUK  -> 16-DU
chapter 15: S1lGCg==  -> KYF
chapter 18: QkxTCg==  -> BLS
```

The flag: `RC3-2016-DUKYFBLS`