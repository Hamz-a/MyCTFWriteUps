# Reverse-Engineering Android APK 1

---

**Category:** Binary

**Point:** 100

**Description:**

Please win 1000 times in rock-paper-scissors.

[rps.apk](files/rps.apk?raw=true)

# Writeup

When running the app, you'll notice that you really need to win 1000 times *in a row*. Which is a pain unless you're lucky (and patient).

I decided to decompile it to see what's under the hood. So I went to [www.decompileandroid.com](http://www.decompileandroid.com). Downloading, extracting [source.zip](files/source.zip?raw=true) and snooping around led me to [MainActivity.java](files/source/src/com/example/seccon2015/rock_paper_scissors/MainActivity.java). There's an `int cnt;` variable presumably for holding the amount of victories. Later on there's an interesting if-statement:

```java
if (1000 == cnt)
{
	textview.setText((new StringBuilder()).append("SECCON{").append(String.valueOf((cnt + calc()) * 107)).append("}").toString());
}
```

Basically when the counter hits 1000 then it will give us the flag. The flag seems quite simple: `"SECCON{" + (cnt + calc()) * 107 + "}"`. So I thought if we could get the result of `calc()` then we could get the flag without winning 1000 times. At the end of the code I noticed the following code:

```java
static 
{
	System.loadLibrary("calc");
}
```

So there's where the `calc()` function came from! After extracting **rps.apk** with 7-zip I could find the library file. Luckily it was also compiled under [**x86**](files/rps/lib/x86/libcalc.so?raw=true). I moved this to my linux machine and fired some commands:

![Firing commands](files/commands.png?raw=true)

```
Dump of assembler code for function Java_com_example_seccon2015_rock_1paper_1scissors_MainActivity_calc:
   0x00000400 <+0>:	mov    eax,0x7
   0x00000405 <+5>:	ret    
End of assembler dump.
```

Basically it returns **7**.

Doing the math gives (1000 + 7) * 107 = **107749**.

The flag is **SECCON{107749}**.