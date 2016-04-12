# Forensics 50 Butterfly Effect

---

**Category:** Forensics

**Points:** 50

# Writeup

We were given an image of a butterfly:

![butterfly](files/butterfly.png?raw=true)

Running [`foremost`](http://foremost.sourceforge.net) on it didn't return anything special. I realised it was probably some image stegano-fu. After trying out some tools, I thought maybe this isn't about using some random stegano tools. I opened [GIMP](https://www.gimp.org) and started fiddling with some filters and colors. I finally used **Menu > Colors > Levels**:

![butterfly solved](files/butterfly_solved.png?raw=true)