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