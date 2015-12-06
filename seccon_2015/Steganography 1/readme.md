# Steganography 1

---

**Category:** Steganography

**Points:** 100

**Description:**

Find image files in the file
[MrFusion.gpjb](files/MrFusion.gpjb?raw=true)
Please input flag like this format-->SECCON{*** ** **** ****}

# Writeup

Opening the file in a hex editor I noticed there are concatenated images (aha "MrFusion"!). I first only noticed GIF, PNG and JFIF images. After manually splitting the images I noticed there were some parts missing. Then I thought about the extension **gpjb**. What does the **b** stand for? I guess *bitmap*. I looked up the header information of bitmaps and found it started with `0x42 0x4D`. I re-itterated over the images to split the bmp files from them. Finally I could reassemble the [images](files/) to produce: `SECCON{OCT2120150728}`. When I tried submitting it, it was deemed incorrect. Apparently I had to adhere to the flag format by *including spaces*.

The flag is: **SECCON{OCT 21 2015 0728}**.