# Connect the server

---

**Category:** Web/Network

**Points:** 100

**Description:**

login.pwn.seccon.jp:10000

# Writeup

I was honestly a bit confused about this challenge. Was it too simple? After fiddling around I opened it in a browser and just waited... I got the following output:

![Output](files/output.png?raw=true)

After staring at the output for a bit I noticed the flag after the "do not brute-force" line. It was indeed already in my hands. Using a simple regex `[^\w{}]+` to filter it, I got the flag: **SECCON{Sometimes_what_you_see_is_NOT_what_you_get}**.

