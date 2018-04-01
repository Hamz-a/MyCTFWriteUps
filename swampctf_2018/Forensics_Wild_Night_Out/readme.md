# Forensics Wild Night Out

## Challenge description:

That sure was a wild night at the old tavern.
Good thing someone was able to draw the scene for us to remember.
But the more I look at the picture, the more it seems that something isn't quite right...

![tavern](files/tavern_night.png?raw=true)

--- 

## Solution:

Our team member [@Tom K.](https://security.stackexchange.com/users/86741/tom-k) ran all kinds of tools against the image (foremost, binwalk, stegsolve etc.) and nothing came up. The description kind of hinted to look at the picture, so what else to do than use GIMP and try to mess with the colors?

![tavern](files/tavern_night_edited.png?raw=true)

The flag could be read at the top of the image.