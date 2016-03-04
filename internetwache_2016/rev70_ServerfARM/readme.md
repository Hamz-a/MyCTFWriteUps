# Rev 70 : ServerfARM

**Description:** Someone handed me this and told me that to pass the exam, I have to extract a secret string. I know cheating is bad, but once does not count. So are you willing to help me?

[serverfarm](files/serverfarm?raw=true)

# Writeup

I downloaded the file and tried to extract some basic information:

```
root@kali:~/ctf/iw# file serverfarm 
serverfarm: ELF 32-bit LSB executable, ARM, EABI5 version 1 (SYSV), dynamically linked, interpreter /lib/ld-linux-armhf.so.3, for GNU/Linux 2.6.32, BuildID[sha1]=9d6ff6a0d099cb4bb6e62c10848f83bb23108b09, not stripped
```

Since I'm not running on ARM I thought let's directly use an online decompiler like https://retdec.com/decompilation/ . I got some [C code](files/serverfarm.c?raw=true), after analysing it I saw some interesting `printf()` calls:

```c
// ...
printf("%s", "Here's your 1. block:");
if (*(int32_t *)(v2 - 16) < 36) {
    // 0x10810
    printf("%s", "IW{");         // Interesting
    putchar(83);
    printf("%c%c\n", 46, 69);
    // branch -> 0x10918
    // 0x10918
    puts_rc = v10;
    // branch -> 0x10924
} else {
    // 0x10804
    puts_rc = puts("I{WAQ3");
    // branch -> 0x10924
}
// ....
case 1: {
    // 0x10838
    printf("%s", "Here's your 2. block:");
    uint32_t v11 = (int32_t)a2->e0; // 0x10848
    uint32_t v12 = (int32_t)*(char *)((int32_t)a2 + 1); // 0x10858
    if (v11 % v12 == 65) {
        // 0x10874
        printf("%s", ".R.");
        putchar(86);
        printf("%c%c\n", 46, 69);
        // branch -> 0x10918
    } else {
        // 0x1089c
        puts("WI{QA3");
        // branch -> 0x10918
    }
    // 0x10918
    puts_rc = v11 / v12;
    // branch -> 0x10924
    break;
}
case 2: {
    // 0x108a8
    printf("%s", "Here's your 3. block:");
    if (strcmp(&a2->e0, "1337") == 0) {
        // 0x108cc
        puts(".R>=F:");
        // branch -> 0x10918
    } else {
        // 0x108d8
        printf("%c%s%c\n", 46, "Q.D.Q", 33);
        // branch -> 0x10918
    }
    // 0x10918
    puts_rc = 0;
    // branch -> 0x10924
    break;
}
case 3: {
    // 0x108f0
    int32_t chars_printed; // 0x109201
    if (a2->e0 != 0) {
        // 0x10900
        chars_printed = printf("%c%s%c\n", 65, ":R:M", 125);
        // branch -> 0x10914
    } else {
        chars_printed = 3;
    }
    // 0x10914
    // branch -> 0x10918
    // 0x10918
    puts_rc = chars_printed;
    // branch -> 0x10924
    break;
}
```

We know that the flag format is `IW{}`, so we can safely assume we got the first part `IW{S.E`:
```c
printf("%s", "IW{");
putchar(83); // S
printf("%c%c\n", 46, 69); // .E
```

Looking at block 2:
```c
if (v11 % v12 == 65) {
    // 0x10874
    printf("%s", ".R.");
    putchar(86);  // V
    printf("%c%c\n", 46, 69); // .E
    // branch -> 0x10918
} else {
    // 0x1089c
    puts("WI{QA3");   // Doesn't fit the flag format
    // branch -> 0x10918
}
```

Until now we got `IW{S.E.R.V.E`. Looking at block 3

```c
if (strcmp(&a2->e0, "1337") == 0) {
    // 0x108cc
    puts(".R>=F:"); // Makes much more sense
    // branch -> 0x10918
} else {
    // 0x108d8
    printf("%c%s%c\n", 46, "Q.D.Q", 33);
    // branch -> 0x10918
}
```

We got `IW{S.E.R.V.E.R>=F:`. Finally we reached block 4 which only has one `printf()` statement:

```c
printf("%c%s%c\n", 65, ":R:M", 125); // A:R:M}
```

The flag is: `IW{S.E.R.V.E.R>=F:A:R:M}`