# Forensics 50 : hit the core

# Writeup

We were given [a core file](files/fore1.core). I thought to first load it in gdb since it's a core dump but after messing around with it for a while a decided to do a simple string command with some regex-fu:

```
strings fore1.core | grep -P '{.*}'
cvqAeqacLtqazEigwiXobxrCrtuiTzahfFreqc{bnjrKwgk83kgd43j85ePgb_e_rwqr7fvbmHjklo3tews_hmkogooyf0vbnk0ii87Drfgh_n kiwutfb0ghk9ro987k5tfb_hjiouo087ptfcv}
```

We know that the flag begins with `ALEXCTF`. I noticed a pattern in the string: cvq**A**eqac**L**tqaz**E**igwi**X**obxr**C**rtui**T**zahf**F**re. If we remove the first 3 characters, skip 4 characters each time then we get the flag. I wrote [a simple php script](files/solve.php):

```
$in = 'AeqacLtqazEigwiXobxrCrtuiTzahfFreqc{bnjrKwgk83kgd43j85ePgb_e_rwqr7fvbmHjklo3tews_hmkogooyf0vbnk0ii87Drfgh_n kiwutfb0ghk9ro987k5tfb_hjiouo087ptfcv}';

for($i = 0; $i < strlen($in); $i++) {
    if(($i % 5) == 0) {
        echo $in[$i];
    }
}
```

Running it in the command line:

```
php solve.php
ALEXCTF{K33P_7H3_g00D_w0rk_up}
```
