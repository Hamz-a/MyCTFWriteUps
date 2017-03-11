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
