# Misc 70 : Rock with the wired shark!

**Description:** Sniffing traffic is fun. I saw a wired shark. Isn't that strange?

[README.txt](files/README.txt?raw=true)
[dump.pcapng](files/dump.pcapng?raw=true)

# Writeup

Opening the dump file with wireshark and filtering on http, we can see some interesting requests:

![1](files/1.png?raw=true)

I tried to extract the **flag.zip** file and check out it's content. Unfortunately it was password protected. I went back to wireshark and noticed a basic authorization header:

![2](files/2.png?raw=true)

I decoded it:

```
$ php -r "echo base64_decode('ZmxhZzphenVsY3JlbWE=');"
flag:azulcrema
```

Tried to extract the zip file again with **azulcrema** as password and got the flag.

```
$ cat flag.txt
IW{HTTP_BASIC_AUTH_IS_EASY}
```