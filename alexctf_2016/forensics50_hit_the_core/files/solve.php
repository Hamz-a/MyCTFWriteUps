<?php

$in = 'AeqacLtqazEigwiXobxrCrtuiTzahfFreqc{bnjrKwgk83kgd43j85ePgb_e_rwqr7fvbmHjklo3tews_hmkogooyf0vbnk0ii87Drfgh_n kiwutfb0ghk9ro987k5tfb_hjiouo087ptfcv}';

for($i = 0; $i < strlen($in); $i++) {
    if(($i % 5) == 0) {
        echo $in[$i];
    }
}