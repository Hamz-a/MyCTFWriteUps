# Forensics 50 : some pang

**Description:** we captured some iseeempee pang pakets. do the thing. get the flag. win the POINTS!.

[somepang.pcap](files/somepang.pcap?raw=true)

# Writeup

Opening the pcap file with wireshark we can see a lot of ICMP echo and reply packets between two entities. I used a simple filter `!icmp` to filter out all ICMP packets. It turned out all the packets were ICMP packets.

![1](files/1.png?raw=true)

After analyzing a few packets the data segment stood out. It was repeating itself. Also the request ICMP data was the same as the reply ICMP data.

![2](files/2.png?raw=true)

Since there was almost 10K packets, I thought it might be a good idea to automate the extraction of the data segment. I used [pyshark](https://github.com/KimiNewt/pyshark) with some python-fu. I noticed the data was also base64 encoded, so I decoded it along the way. Here's how the [final script](files/flag_extracter.py) looked like:

```
import pyshark
import base64

cap = pyshark.FileCapture('somepang.pcap', display_filter='icmp.type==8') # filter by echo

data = ''
for packet in cap:
    data += packet['icmp'].data.decode('hex')[-2:] # get last 2 chars from data

f = open('flag.jpg', 'wb')
f.write(base64.b64decode(data))

print 'done'
```

The flag:

![flag](files/flag.jpg?raw=true)
