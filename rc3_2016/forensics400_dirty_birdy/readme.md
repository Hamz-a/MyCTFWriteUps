# Forensics 400 : Dirty birdy

**Description:** We had an employee that was up to no good. Our SIEM caught him uploading files to a website from our file server but we canceled the transmission. We currently have an image of home directory that we store on our server. Take a look for yourself on what he stole.

Download Link: https://drive.google.com/file/d/0Bw7N3lAmY5PCUWExQUJVZGVySXc/view?usp=sharing


# Writeup

We were given an image. I loaded it in [Autopsy](http://www.sleuthkit.org/autopsy/) but couldn't find any juicy information. I've proceeded to extracting the image  manually. There was an interesting folder `secrets` which contained:

```
ls -a
.  ..  document.txt  .git  README.md  Workbook1.xlsx.gpg

cat document.txt
passowrd123
```

Trying to decrypt:

```
gpg --output workbook.xlsx --decrypt Workbook1.xlsx.gpg
gpg: keyring `/root/.gnupg/secring.gpg' created
gpg: keyring `/root/.gnupg/pubring.gpg' created
gpg: encrypted with RSA key, ID E22CB12D
gpg: decryption failed: secret key not available
```

I guess we need some private key... Let's try to figure out if we can recover some files from the `.git` folder with [GitTools](https://github.com/internetwache/GitTools):

```
git clone https://github.com/internetwache/GitTools
cd GitTools/Extractor/
./extractor.sh ../../ ./dump
Destination folder does not exist
Creating...
Found commit: 5fe6ff3f3eaf629e3e7cdc8a2ae456253b186370
Found file: <omitted>/dtrump.img/dtrump/secretfiles/GitTools/Extractor/./dump/0-5fe6ff3f3eaf629e3e7cdc8a2ae456253b186370/private.key
Finished
```
Nice! Let's import it:

```
gpg --import dump/0-5fe6ff3f3eaf629e3e7cdc8a2ae456253b186370/private.key 
gpg: key 8FFDF6D6: secret key imported
gpg: /root/.gnupg/trustdb.gpg: trustdb created
gpg: key 8FFDF6D6: public key "ThugG (lolz) <nope@gmail.com>" imported
gpg: Total number processed: 1
gpg:               imported: 1  (RSA: 1)
gpg:       secret keys read: 1
gpg:   secret keys imported: 1
```

Going back and trying to decrypt the excel sheet:

```
gpg --output workbook.xlsx --decrypt Workbook1.xlsx.gpg
gpg: encrypted with 1024-bit RSA key, ID E22CB12D, created 2016-11-18
      "ThugG (lolz) <nope@gmail.com>"

ls
document.txt  GitTools  README.md  Workbook1.xlsx.gpg  workbook.xlsx
```

Trying to open the file, it prompted for a password. The password found earlier in `document.txt` contained a spelling error in "password", correcting it gave `password123`. Opening sheet 2 revealed the flag:

![1](files/1.png?raw=true)
