# Code 60 : It's Prime Time!

**Description:**

We all know that prime numbers are quite important in cryptography. Can you help me to find some?

# Writeup

We got an IP address + port. A simple `nc` got us the following output:

```
Hi, you know that prime numbers are important, don't you? Help me calculating the next prime!
Level 1.: Find the next prime number after 5:
```

If you have completed some [Project Euler](https://projecteuler.net) challenges, this might be actually quite easy. Instead of using some fancy algorithm to find those primes, I used the [gmp library](http://php.net/manual/en/book.gmp.php) which has [`gmp_nextprime()`](http://php.net/manual/en/function.gmp-nextprime.php) to find the next prime.

After executing this [simple script](code60.php):

![1](files/1.png?raw=true)