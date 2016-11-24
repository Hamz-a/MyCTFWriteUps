# Forensics 100 : mylildroid

**Description:** Sometimes not all files are needed.

[youtube.apk](https://drive.google.com/file/d/0Bw7N3lAmY5PCOFNQZFgtSVlFZ3M/view)

# Writeup

I thought that I might need to find the version of the youtube apk we were given, download the official one with the same version and then compare the two files to find any differences. I extracted the apk file with 7-zip and found a [`build-data.properties`](files/build-data.properties) file. The last line was quite interesting:

```
...
build.timestamp=1464732141
build.changelist=123676479
UkMz-2016-R09URU0yMQ==
```

Looks like base64 to me. The flag: `RC3-2016-GOTEM21`.
