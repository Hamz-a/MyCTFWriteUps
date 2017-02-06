# Crypto 50 : Ultracoded

**Description:**

Fady didn't understand well the difference between encryption and encoding, so instead of encrypting some secret message to pass to his friend, he encoded it!
Hint: Fady's encoding doens't handly any special character

# Writeup

We got [a file](files/zero_one) with a bunch of ones and zeros: `ZERO ONE ZERO ZERO ONE ONE ZERO ZERO ZERO ONE ONE ZERO ONE ZERO ZERO ONE ZERO ZERO...`

Replacing the words with numbers is trivial. After removing spaces we end up with: `01001100011010010011000001100111010011000110100...`

Next let's use [xlate](https://paulschou.com/tools/xlate/) to decode the binary representation. We end up with a base64 encoded string: `Li0gLi0uLiAuIC0uLi0gLS4tLiAtIC4uLS4gLSAuLi4uIC4tLS0tIC4uLi4uIC0tLSAuLS0tLSAuLi4gLS0tIC4uLi4uIC4uLSAuLS0uIC4uLi0tIC4tLiAtLS0gLi4uLi4gLiAtLi0uIC4tLiAuLi4tLSAtIC0tLSAtIC0uLi0gLQ==`

Let's decode that as well, we end up with some morse code: `.- .-.. . -..- -.-. - ..-. - .... .---- ..... --- .---- ... --- ..... ..- .--. ...-- .-. --- ..... . -.-. .-. ...-- - --- - -..- -`

Searching for [a morse code decoder](https://www.google.com/?q=morse+code+decoder) and using it we get the flag: `ALEXCTFTH15O1SO5UP3RO5ECR3TOTXT`
