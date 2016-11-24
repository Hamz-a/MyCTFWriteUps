import pyshark
import base64

cap = pyshark.FileCapture('somepang.pcap', display_filter='icmp.type==8') # filter by echo

data = ''
for packet in cap:
    data += packet['icmp'].data.decode('hex')[-2:] # get last 2 chars from data

f = open('flag.jpg', 'wb')
f.write(base64.b64decode(data))

print 'done'
