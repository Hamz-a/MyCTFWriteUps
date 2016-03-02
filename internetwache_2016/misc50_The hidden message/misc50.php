<?php

$in = '126 062 126 163 142 103 102 153 142 062 065 154 111 121 157 113 122 155 170 150 132 172 157 147 123 126 144 067 124 152 102 146 115 107 065 154 130 062 116 150 142 154 071 172 144 104 102 167 130 063 153 167 144 130 060 113 012';

$base8 = explode(' ', $in);

$base10 = array_map(function($v) {
    return base_convert($v, 8, 10);
}, $base8);

$ascii = array_map('chr', $base10);

echo base64_decode(implode($ascii));