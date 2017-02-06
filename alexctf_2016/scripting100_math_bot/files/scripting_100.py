import socket
import re
from math import floor

ip = '195.154.53.62'
port = 1337
buffer_size = 1024
pattern = r'Question\s*\d+\s*:\s*(\d+)\s*([/*+%-])\s*(\d+)'

s = socket.socket()
s.connect((ip, port))

while True:
    inp = s.recv(buffer_size).decode('ascii')
    search = re.search(pattern, inp)

    print(inp)

    if search:
        result = ""
        if search.group(2) == "+":
            result = str(int(search.group(1)) + int(search.group(3)))
        elif search.group(2) == "-":
            result = str(int(search.group(1)) - int(search.group(3)))
        elif search.group(2) == "*":
            result = str(int(search.group(1)) * int(search.group(3)))
        elif search.group(2) == "/":
            result = str(floor(int(search.group(1)) / int(search.group(3))))
        elif search.group(2) == "%":
            result = str(int(search.group(1)) % int(search.group(3)))

        result += "\n"
        print(search.group(1) + " " + search.group(2) + " " + search.group(3) + " = " + result)
        s.send(result.encode())
    else:
        print("Done")
        break
