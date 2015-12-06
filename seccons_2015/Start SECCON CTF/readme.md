# Start SECCON CTF

---

**Category:** Exercises

**Point:** 50

**Description:**

```
ex1
Cipher:PXFR}QIVTMSZCNDKUWAGJB{LHYEO
Plain: ABCDEFGHIJKLMNOPQRSTUVWXYZ{}

ex2
Cipher:EV}ZZD{DWZRA}FFDNFGQO
Plain: {HELLOWORLDSECCONCTF}

quiz
Cipher:A}FFDNEVPFSGV}KZPN}GO
Plain: ?????????????????????
```

# Writeup

By observing ex1 and ex2, you'll notice that the characters are "translated". We could solve this by using a translation table:

```php
<?php

$k = 'PXFR}QIVTMSZCNDKUWAGJB{LHYEO';
$v = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ{}';

$c = 'A}FFDNEVPFSGV}KZPN}GO';

$trans_table = array_combine(str_split($k), str_split($v));
$c_arr = str_split($c);

foreach ($c_arr as $c_val) {
    echo $trans_table[$c_val];
}
```

Running this will give us the flag:

```
$ php trans_table.php
SECCON{HACKTHEPLANET}
```

**PS:** The last challenge was similar but used a different cipher `A}FFDNEA}}HDJN}LGH}PWO` which led to `SECCON{SEEYOUNEXTYEAR}`.