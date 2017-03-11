# Rev 75-200: skipper 1 & 2

We were given a linux binary in [`32`](files/skipper-32) and [`64`](files/skipper-64) bit. For the sake of simplicity I chose to work with the 32 bit variant. So let's open it in [radare2](https://github.com/radare/radare2) with `r2 skipper-32` and analyse all the functions with `aaaaa`:

![1](files/1.png?raw=true)

We can now go to visual mode and see all the functions with `Vv`. Since we want to check out the main function we can "seek" to it by using the up/down arrows on the keyboard (or `j` and `k`) and then `g` for "go":

![2](files/2.png?raw=true)

We're now in visual mode:

![3](files/3.png?raw=true)

For help just type `?` but since we want to get an overview of the flow of the program, we want to enter visual graph mode (ASCII art!) so we'll type `V` (uppercase):

![4](files/4.png?raw=true)

After manually analysing the program I've come up with the following remarks on each of the marked points:

1. Get computer name
2. Compare computer name with `hax0rz!~`, if it's true then continue execution otherwise show error message and exit
3. Get os version
4. Compare os version with `2.4.31`, if it's true then continue execution otherwise show error message and exit
5. Get cpu name
6. Compare cpu name with `AMDisbetter!`, if it's true then continue execution otherwise show error message and exit
7. Print flag!
8. Just some stack protection mechanism against buffer overflows (not relevant to us)
9. Terminate program in case of a buffer overflow (not relevant to us)
10. Exit (end of program)

There are several approaches to getting the flag. We could set a break point in main and directly call the print flag function. We could also patch the binary at point 2, 4 and 6 to always jump. We could also debug it with r2 and set `eax` before the `test eax, eax` statement to `0` so that it will always jump. I've opted for [`r2pipe`](https://github.com/radare/radare2-r2pipe) and scripted it in python for learning purposes but we could have done this manually in the r2 debugger! So [the script](files/get_flag_skipper75.py) looks like this:

```
import r2pipe

break_points = [
    "0x0804a0cb",  # pc name
    "0x0804a133",  # os name
    "0x0804a1b8",  # cpu name
]

r2 = r2pipe.open("./skipper-32", ["-d"])
r2.cmd("aaaaa")  # Analyse binary
r2.cmd("dc")  # Continue

for break_point in break_points:
	r2.cmd("db " + break_point)  # Set break point
	r2.cmd("dc")  # Continue
	r2.cmd("dr eax=0")  # Skip those tests

print(r2.cmd("dc"))  # Get flag!
```

Basically we first define some break points at each critical `test eax, eax` instruction. We then open the binary with r2pipe in debug mode `-d`, analyse the binary with `aaaaa` like we did earlier manually. The first `dc` will start the program. We then use `db {breakpoint address}` to set a breakpoint, `dc` to continue and reach that breakpoint and `dr eax=0` to set the `eax` register to `0` to skip those checks. Finally we let the program continue it's execution and we'll get the flag:

![5](files/5.png?raw=true)

-----

There was a second variant called skipper-2 worth 200 points. Again we were given [`32`](files/skipper2-32) and [`64`](files/skipper2-64) bit variants. The flow was almost the same except this time pc name, os name and cpu name were concatenated each time in a single variable. That variable was given to the print flag function. Basically the print flag funcion used the content of that variable to decrypt and print the flag. Which means skipping those checks wasn't enough. So we had to fill the memory with the right passcode to decrypt correctly the flag. Here's how the [final script](files/get_flag_skipper200.py) looked like:

```
import r2pipe

passcode = "hax0rz!\\~2.4.31AMDisbetter!"
break_point_flag = "0x0804d152"
break_points = [
    "0x0804d00a",  # pc name
    "0x0804d08b",  # os name
    "0x0804d107",  # cpu name
]

r2 = r2pipe.open("./skipper2-32", ["-d"])
r2.cmd("aaaaa")  # Analyse binary
r2.cmd("dc")  # Continue

for break_point in break_points:
    r2.cmd("db " + break_point)  # Set break point
    r2.cmd("dc")  # Continue
    r2.cmd("dr eax=0")  # skip those tests

r2.cmd("db " + break_point_flag)  # Break before getting the flag
r2.cmd("dc")  # continue
r2.cmd("s eax")  # Seek to parameter that's passed to the flag function
r2.cmd("wb 00")  # clear memory block with 0x00's
r2.cmd("wz " + passcode)  # Write passcode as paramater for the flag func in the memory block
print(r2.cmd("dc"))  # Get flag!
```

![6](files/6.png?raw=true)