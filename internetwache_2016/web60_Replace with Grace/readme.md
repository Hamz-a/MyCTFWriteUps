# Web 60 : Replace with Grace

**Description:**

Regular expressions are pretty useful. Especially when you need to search and replace complex terms.

# Writeup

When opening the website. We could do some fancy regex search & replace:

![1](files/1.png?raw=true)

I then thought about using modifiers:

![2](files/2.png?raw=true)

Also seems to work. Let's try the `e` modifier which stands for `eval`:

![3](files/3.png?raw=true)

It got upper-cased! Let's see if we could list the files:

![4](files/4.png?raw=true)

Nice! Let's grab the flag:

![5](files/5.png?raw=true)

Hmmm, probably a blacklist. After fiddling with it, it seems that the blacklist procedure is case sensitive:

![6](files/6.png?raw=true)

Gotcha!