# Forensics Orcish

## Challenge description:

An army of orcs was spotted not too far from town. We were listening in on some of their communications but we have not been able to find anything containing their battle plans. Maybe they are disguising the messages some how..

[data.pcap](files/data.pcap)

--- 

## Solution:

We were given a pcap file and we had to somehow find hidden messages. The statistics tools of wireshark can give some great general insight of the traffic. I usually start from there and view the "Conversations", "Endpoints" and if there's HTTP traffic, I view the requests. I also try to scroll through the packets very fast and try to see if there's any anomaly:

![statistics tools](files/statistics_tools.png?raw=true)

While scrolling through the packets, there were a lot of HTTP traffic. So I viewed the HTTP requests under Statistics:

![http requests](files/http_requests.png?raw=true)

Some update/ubuntu/steam related stuff, not very interesting. Moving on to the end of the captured packets, I noticed a stream of (malformed?) ICMP packets:

![malformed icmp packets](files/malformed_icmp_packets.png?raw=true)

The first ICMP packet had a type of `71`, the second one had a type of `73` and the third had a type of `70`, followed by `56` and `57`. Does this sound familiar :_)?

![ICMP types to chr](files/icmp_types_to_chr.png?raw=true)

There's probably a GIF file hidden in the ICMP packets. Since I didn't want to extract all bytes by hand I wrote a small script with [pyshark](https://github.com/KimiNewt/pyshark):

```python
import pyshark

cap = pyshark.FileCapture('./data.pcap', display_filter='icmp')
data = ''

for packet in cap:
    data += chr(int(packet['icmp'].type))

with open('result.gif', 'w') as f:
    f.write(data)
print 'done'
```

Unfortunately the GIF file was corrupted and couldn't view it, but it contained `lol nice job`?!

![ICMP types to chr](files/good_job.png?raw=true)

I must be close I thought. After analyzing the packets, I noticed that most of the ICMP packets were sent by `Cybertan c8:3d:d4:7a:f3:47` but some were sent by Cisco. It turns out that the router/recipient was replying to some of the packets. I decided to filter the packets to only include packets from `c8:3d:d4:7a:f3:47`:

```python
import pyshark

cap = pyshark.FileCapture('./data.pcap', display_filter='icmp')
data = ''

for packet in cap:
    if packet.eth.src == 'c8:3d:d4:7a:f3:47':
        data += chr(int(packet['icmp'].type))

with open('done.gif', 'w') as f:
    f.write(data)
print 'done'
```

This time, the GIF file was not corrupt!

![healthy gif file](files/done.gif?raw=true)