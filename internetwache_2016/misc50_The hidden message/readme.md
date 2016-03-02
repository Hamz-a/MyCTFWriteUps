# Misc 50 : The hidden message

**Description:**

My friend really can't remember passwords. So he uses some kind of obfuscation. Can you restore the plaintext?

```
0000000 126 062 126 163 142 103 102 153 142 062 065 154 111 121 157 113
0000020 122 155 170 150 132 172 157 147 123 126 144 067 124 152 102 146
0000040 115 107 065 154 130 062 116 150 142 154 071 172 144 104 102 167
0000060 130 063 153 167 144 130 060 113 012
0000071
```

# Writeup

First thing I noticed was the use of 3 digits. Furthermore, the range of a single digit was between 0 and 7. This led me to think it's base 8. So I converted it to base 10:

```php
$in = '126 062 126 163 142 103 102 153 142 062 065 154 111 121 157 113 122 155 170 150 132 172 157 147 123 126 144 067 124 152 102 146 115 107 065 154 130 062 116 150 142 154 071 172 144 104 102 167 130 063 153 167 144 130 060 113 012';
$chunks = explode(' ', $in);

echo implode(' ', array_map(function($v) {
    return base_convert($v, 8, 10);
}, $chunks));
```
This resulted into:

```
86 50 86 115 98 67 66 107 98 50 53 108 73 81 111 75 82 109 120 104 90 122 111 103 83 86 100 55 84 106 66 102 77 71 53 108 88 50 78 104 98 108 57 122 100 68 66 119 88 51 107 119 100 88 48 75 10
```
Looking at the range of those numbers, it was obviously ASCII values. So let's convert that as well:

```php
$in = '86 50 86 115 98 67 66 107 98 50 53 108 73 81 111 75 82 109 120 104 90 122 111 103 83 86 100 55 84 106 66 102 77 71 53 108 88 50 78 104 98 108 57 122 100 68 66 119 88 51 107 119 100 88 48 75 10';
$chunks = explode(' ', $in);

echo implode('', array_map('chr', $chunks));
```
This resulted into:
```
V2VsbCBkb25lIQoKRmxhZzogSVd7TjBfMG5lX2Nhbl9zdDBwX3kwdX0K
```
This was obviously base64:

```php
echo base64_decode('V2VsbCBkb25lIQoKRmxhZzogSVd7TjBfMG5lX2Nhbl9zdDBwX3kwdX0K');
```

Yay, a wild flag appeared!

```
Well done! Flag: IW{N0_0ne_can_st0p_y0u} 
```