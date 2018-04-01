import r2pipe

print("[+] Opening binary...")
r2 = r2pipe.open("./adventure")

print("[+] Analysing the binary...")
r2.cmd("aaa")

print("[+] Looping through foo[1-15] functions...")
flag = ''
for i in range(1, 16):
    print("[+] Disassembling foo%s function..." % i)
    instructions = r2.cmdj("pdj 4 @sym.foo" + str(i))  # Get first 4 assembled instructions
    opcode = instructions[-1]['opcode'].strip()  # Get last instruction
    character = chr(int(opcode[-4:], 16))  # Convert hex character from opcode to ASCII
    print("Opcode: " + opcode + ", chr: " + character)
    flag += character

print("flag: " + flag)