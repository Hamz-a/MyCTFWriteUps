# Web The Vault

## Challenge description:

Has it been days? Weeks? You can't remember how long you've been standing at the door to the vault.
You can't remember the last time you slept
or ate,
or had a drop of water, for that matter.
But all of that is insignificant, in the presence of the untold fortunes that must lie just beyond the threshold.

But the door. It won't budge. It says it will answer only to the DUNGEON_MASTER.
Have you not shown your worth?
But more than that,
It demands to know your secrets.

Nothing you've tried has worked.
You've pled, begged, cursed, but the door holds steadfast, harshly judging your failed requests.

But with each failed attempt you start to notice more and more
that there's something peculiar about the way the door responds to you.

Maybe the door knows more than it's letting on.
...Or perhaps it's letting on more than it knows?

**NOTE: DO NOT USE AUTOMATED TOOLS**
Connect
http://chal1.swampctf.com:2694

--- 

## Solution:

We are presented with a login screen and since this is a web challenge, I tend to use my favourite http proxy: Burp. This allows me to view all traffic received/sent from the webserver. Sending `foobar` as username and `bazqux` as password, the UI pops up an alert stating "invalid credentials". Burp however intercepted the following response:

```
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Error</title>
</head>
<body>
<pre>No such user: foobar</pre>
</body>
</html>
```

The website and the challenge description both state that only the `DUNGEON_MASTER` may enter the vault, let's try that as username:

![only dungeon master may enter](files/only_dungeon_master.png?raw=true)

Wrong credentials again but the response was different this time:

```
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Error</title>
</head>
<body>
<pre>test_hash [972c5e1203896784a7cf9dd60acd443a1065e19ad5f92e59a9180c185f065c04] does not match real_hash[40f5d109272941b79fdf078a0e41477227a9b4047ca068fff6566104302169ce]</pre>
</body>
</html>
```

The passwords are hashed but showing the expected hash in the response is definitely not right. Turns out that the hashing algorithm is SHA256.
Next step is to figure out the original value of the hash. Since hashing algorithms are one way, there's no way to turn back unless we use bruteforce, rainbow tables or just google for websites with huge databases that have done this before. Some sites didn't contain the hash but the following did: https://crackstation.net/ . The original value for hash `40f5d109272941b79fdf078a0e41477227a9b4047ca068fff6566104302169ce`
is `smaug123`.

Get flag!

![get flag](files/flag.png?raw=true)

Btw, here's a nice tool for hash identification: https://github.com/psypanda/hashID