# Web 10 : BotBot

**Description:**

Should not ask for the description of a 5 marker.
`botbot.bitsctf.bits-quark.org`

# Writeup

Opening the website we see a simple html5 website. Viewing the source gives us an interesting hint:

```
<!DOCTYPE HTML>
<!--
	Nothing to see here. Maybe try looking up seo .txt
-->
<html>
	<head>
		<title>Photon by HTML5 UP</title>
```

Probably refering to `robots.txt`. I've opened: `http://botbot.bitsctf.bits-quark.org/robots.txt` and got:

```
Useragent *
Disallow: /fl4g
```

Let's check that out `http://botbot.bitsctf.bits-quark.org/fl4g/`. Which gives us the flag: `BITCTF{take_a_look_at_googles_robots_txt}`.