# Code 50 : A numbers game

**Description:**

People either love or hate math. Do you love it? Prove it! You just need to solve a bunch of equations without a mistake.

# Writeup

We got an IP address + port. A simple `nc` got us the following output:

```
Hi, I heard that you're good in math. Prove it!
Level 1.: x - 13 = -3
```

It seems we need to solve the equations automagically. So I wrote a [simple PHP script](web50.php). After launching it, I got the flag:

![1](files/1.png?raw=true)