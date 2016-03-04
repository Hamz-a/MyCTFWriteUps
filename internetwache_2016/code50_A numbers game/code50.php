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
    if(preg_match('/level \d+[.]: x ([\/*+-]) (-?\d+) = (-?\d+)/i', $out, $m)) {
        list($equation, $operator, $operand, $result) = $m;
        switch($operator) {
            case '+':
                $send_value = $result - $operand;
                break;
            case '-':
                $send_value = $result + $operand;
                break;
            case '*':
                    $send_value = $result / $operand;
                break;
            case '/':
                $send_value = $result * $operand;
                break;
        }
        echo "Writing $send_value\n";
        socket_write($socket, $send_value, strlen($send_value));
    }
    sleep(0.5);
}


socket_close($socket);