# Crypto 100 : Many time secrets

**Description:**

This time Fady learned from his old mistake and decided to use onetime pad as his encryption technique, but he never knew why people call it one time pad!



# Writeup

We got [a file](files/msg) with a hex encoded message: `0529242a631234122d2b36...`

Using [CyberChef](https://gchq.github.io/CyberChef) we can fiddle around with the input. I've guessed XOR encryption since it's a classic. We know that the flag starts with `ALEXCTF{`, trying that as the key gives us a partial readable output!

![1](files/1.png?raw=true)

We can now presume that the key might be the flag. Which means the key will end with `}`. Since it's a cyclic xor encryption, we can find out the length of the key by adding chars and checking out the output. I've ended up with:
`ALEXCTF{_________________}`

![2](files/2.png?raw=true)

We can guess the beginning of the message `Dear Friend`. Which means we can XOR `end` with the corresponding cipher to get that part of the key (`HER`).

![3](files/3.png?raw=true)

I continued analysing and finding bits of the key step by step until I found the flag:

![4](files/4.png?raw=true)