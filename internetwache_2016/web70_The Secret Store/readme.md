# Web 70 : The Secret Store

**Description:**

We all love secrets. Without them, our lives would be dull. A student wrote a secure secret store, however he was babbling about problems with the database. Maybe I shouldn't use the 'admin' account. Hint1: Account deletion is intentional. Hint2: I can't handle long usernames.

# Writeup

The provided website was quite simple: login, register and logout.

![1](files/1.png?raw=true)

The second hint gave it all out. It probably is some sql truncation vulnerability. So I went to the registration page and entered the following username:
```
admin                           wut
```
and a password: **trunc**. The secret value could be anything.

I went to the login page and used the new credentials **admin** & **trunc**:

![2](files/2.png?raw=true)