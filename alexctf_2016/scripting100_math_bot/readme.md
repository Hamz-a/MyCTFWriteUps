# Scripting 100 : Math bot

**Description:**

It is well known that computers can do tedious math faster than human.

`nc 195.154.53.62 1337`

Update
we got another mirror here

`nc 195.154.53.62 7331`

# Writeup

Connecting to the server in the command line we get this nice ascii art with a math question:

```
root@kali:~# nc 195.154.53.62 1337
                __________
         ______/ ________ \______
       _/      ____________      \_
     _/____________    ____________\_
    /  ___________ \  / ___________  \
   /  /XXXXXXXXXXX\ \/ /XXXXXXXXXXX\  \
  /  /############/    \############\  \
  |  \XXXXXXXXXXX/ _  _ \XXXXXXXXXXX/  |
__|\_____   ___   //  \\   ___   _____/|__
[_       \     \  X    X  /     /       _]
__|     \ \                    / /     |__
[____  \ \ \   ____________   / / /  ____]
     \  \ \ \/||.||.||.||.||\/ / /  /
      \_ \ \  ||.||.||.||.||  / / _/
        \ \   ||.||.||.||.||   / /
         \_   ||_||_||_||_||   _/
           \     ........     /
            \________________/

Our system system has detected human traffic from your IP!
Please prove you are a bot
Question  1 :
104402060467554541228371688334216 * 213458949022195272896869634135283 =
```

Quite clear we need a script to answer these for us. I wrote a python script which uses a regex to extract the two operands and operator and applies the result. I've encountered a problem with division questions. I first wrote:

```
result = str(int(search.group(1)) / int(search.group(3)))
```

Turns out the server was using python 2 while I was using python 3. Apparently python 2 truncates the output when doing division with ints while python 3 doesn't. So I had to truncate it manually. I've used `math.floor()`. You can find the whole [script here](files/scripting_100.py):

```
         ______/ ________ \______
       _/      ____________      \_
     _/____________    ____________\_
    /  ___________ \  / ___________  \
   /  /XXXXXXXXXXX\ \/ /XXXXXXXXXXX\  \
  /  /############/    \############\  \
  |  \XXXXXXXXXXX/ _  _ \XXXXXXXXXXX/  |
__|\_____   ___   //  \\   ___   _____/|__
[_       \     \  X    X  /     /       _]
__|     \ \                    / /     |__
[____  \ \ \   ____________   / / /  ____]
     \  \ \ \/||.||.||.||.||\/ / /  /
      \_ \ \  ||.||.||.||.||  / / _/
        \ \   ||.||.||.||.||   / /
         \_   ||_||_||_||_||   _/
           \     ........     /
            \________________/

Our system system has detected human traffic from your IP!
Please prove you are a bot
Question  1 :
105601893061542402407983273243515 + 100707803876425630599340366717975 =

105601893061542402407983273243515 + 100707803876425630599340366717975 = 206309696937968033007323639961490

Question  2 :
238933961218595715534937887646234 % 298327416546354323060003247462566 =

238933961218595715534937887646234 % 298327416546354323060003247462566 = 238933961218595715534937887646234

...omitted...

Question  249 :
227181214652204935447311742513981 - 21184804274834621736158619036628 =

227181214652204935447311742513981 - 21184804274834621736158619036628 = 205996410377370313711153123477353

Question  250 :
95338245440792805816718194226134 - 82271772321972195871119688358568 =

95338245440792805816718194226134 - 82271772321972195871119688358568 = 13066473118820609945598505867566

Well no human got time to solve 500 ridiculous math challenges
Congrats MR bot!
Tell your human operator flag is: ALEXCTF{1_4M_l33t_b0t}

Done
```
