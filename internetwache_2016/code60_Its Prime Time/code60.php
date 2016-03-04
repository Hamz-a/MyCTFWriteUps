<?php

$address = '127.0.0.1';
$port = 1337;


echo "Creating a socket...\n";

$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
if ($socket === false) {
    echo "socket_create() failed: reason: " . socket_strerror(socket_last_error()) . "\n";
} else {
    echo "OK.\n";
}

echo "Connecting to $address : $port...\n";

$result = socket_connect($socket, $address, $port);
if ($result === false) {
    echo "socket_connect() failed.\nReason: ($result) " . socket_strerror(socket_last_error($socket)) . "\n";
} else {
    echo "OK.\n";
}

while ($out = socket_read($socket, 2048)) {
    echo "Read $out \n";
    if(preg_match('/prime number after (\d+)/i', $out, $m)) {
        $gmp_next_prime = gmp_nextprime($m[1]);
        $next_prime = gmp_strval($gmp_next_prime);
        
        echo "Writing $next_prime\n";
        socket_write($socket, $next_prime, strlen($next_prime));
    }
    sleep(0.5);
}


socket_close($socket);