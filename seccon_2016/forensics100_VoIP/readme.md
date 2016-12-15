# Forensics 50 : VoIP

**Description:** Extract a voice.

The flag format is SECCON{[A-Z0-9]}.

[voip.pcap](files/voip.pcap?raw=true)

# Writeup

From the challenge description it was pretty obvious that we had to deal with some VoIP communication. Wireshark to the rescue!

![1](files/1.png?raw=true)

Wireshark successfully detected a single voice call. Selecting and clicking on "Player" would allow us to listen to the conversation:

![2](files/2.png?raw=true)

Clicking on "Decode":
![3](files/3.png?raw=true)

Next selecting the right track and clicking on "Play" would allow us to hear the flag:

![4](files/4.png?raw=true)

The flag: **SECCON{9001IVR}**
