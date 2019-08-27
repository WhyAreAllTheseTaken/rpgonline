<?php
// 128bit invalid token.
$invalid_token = "ffffffffffffffffffffffffffffffff";

// These should be set for each game.
$sql_username = "example_user";
$sql_password = "example_password";
$sql_db = "users";

function token_get()
{
    global $invalid_token;
    global $sql_username;
    global $sql_db;
    global $sql_password;

    if (empty($_REQUEST["login"])) {
        http_response_code(400);
        echo $invalid_token;
        exit();
    }
    if (empty($_REQUEST["password"])) {
        http_response_code(400);
        echo $invalid_token;
        exit();
    }

    $conn = new mysqli(null, $sql_username, $sql_password, $sql_db);

    if ($conn->connect_error) {
        echo $invalid_token;
        http_response_code(500);
        exit();
    }

    $login = mysqli::real_escape_string(htmlspecialchars($_REQUEST["login"]));
    $password = $_REQUEST["password"];

    $stmt = $conn->prepare("SELECT token, password FROM users WHERE username = ?");
    $stmt->bind_param("s", $login);

    $stmt->execute();

    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $token = $row["token"];

            $hash = $row["password"];

            if (password_verify($password, $hash)) {
                echo $token;

                $stmt->close();
                $conn->close();
                exit();
            }
        }
    }

    echo $invalid_token;

    $stmt->close();
    $conn->close();
    exit();
}

function token_valid()
{
    global $invalid_token;
    global $sql_username;
    global $sql_db;
    global $sql_password;

    $token = mysqli::real_escape_string(htmlspecialchars($_REQUEST["token"]));

    if ($token == $invalid_token) {
        echo "false";
        exit();
    }

    $conn = new mysqli(null, $sql_username, $sql_password, $sql_db);

    if ($conn->connect_error) {
        echo "false";
        response_code(500);
        exit();
    }

    $stmt = $conn->prepare("SELECT username FROM users WHERE token = ?");
    $stmt->bind_param("s", $token);

    $stmt->execute();

    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        echo "true";

        $stmt->close();
        $conn->close();
        exit();
    } else {
        echo "false";

        $stmt->close();
        $conn->close();
        exit();
    }
}

/*
 * Follows the syntax of base_convert (http://www.php.net/base_convert)
 * Created by Michael Renner @ http://www.php.net/base_convert 17-May-2006 03:24
 * His comment is has since been deleted. The function will tell you why.
 */
function unfucked_base_convert($numstring, $frombase, $tobase)
{
    $chars = "0123456789abcdefghijklmnopqrstuvwxyz";
    $tostring = substr($chars, 0, $tobase);
    $length = strlen($numstring);
    $result = '';

    $number = array();
    for ($i = 0; $i < $length; $i ++) {
        $number[$i] = strpos($chars, $numstring{$i});
    }
    do {
        $divide = 0;
        $newlen = 0;
        for ($i = 0; $i < $length; $i ++) {
            $divide = $divide * $frombase + $number[$i];
            if ($divide >= $tobase) {
                $number[$newlen ++] = (int) ($divide / $tobase);
                $divide = $divide % $tobase;
            } elseif ($newlen > 0) {
                $number[$newlen ++] = 0;
            }
        }
        $length = $newlen;
        $result = $tostring{$divide} . $result;
    } while ($newlen != 0);
    return $result;
}

function get_new_token()
{
    $token = 0;

    for ($i = 0; $i < 128 / 4; $i ++) {
        $r = mt_rand(0, 15);
        $token = $token . unfucked_base_convert($r + "", 10, 16);
    }

    return $token;
}

function token2_get()
{
    global $invalid_token;
    global $sql_username;
    global $sql_db;
    global $sql_password;

    $conn = new mysqli(null, $sql_username, $sql_password, $sql_db);

    if ($conn->connect_error) {
        echo $invalid_token;
        response_code(500);
        exit();
    }

    $token = mysqli::real_escape_string(htmlspecialchars($_REQUEST["token"]));

    if ($token == $invalid_token) {
        echo $invalid_token;

        $conn->close();
        exit();
    }

    $stmt2 = $conn->prepare("SELECT token2 FROM tokens WHERE token2 = ?");

    $token2 = get_new_token();
    while ($token2 == $invalid_token) {
        $token2 = get_new_token();
    }
    while (true) {
        $stmt2->bind_param("s", $token2);
        $stmt2->execute();

        $result = $stmt2->get_result();

        if ($result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $token2_local = $row["token2"];

                if ($token2_local != $token2) {
                    break 2;
                }
            }
        }

        $token2 = get_new_token();
        while ($token2 == $invalid_token) {
            $token2 = get_new_token();
        }
    }

    $stmt2->close();

    $stmt = $conn->prepare("INSERT INTO tokens (token1, token2) VALUES (?, ?)");
    $stmt->bind_param("ss", $token, $token2);

    $stmt->execute();

    $stmt->close();
    $conn->close();

    echo $token2;
    exit();
}

function token2_valid()
{
    global $invalid_token;
    global $sql_username;
    global $sql_db;
    global $sql_password;

    $conn = new mysqli(null, $sql_username, $sql_password, $sql_db);

    if ($conn->connect_error) {
        echo "false";
        response_code(500);
        exit();
    }

    $token = mysqli::real_escape_string(htmlspecialchars($_REQUEST["token"]));

    if ($token == $invalid_token) {
        echo "false";

        $conn->close();
        exit();
    }

    $stmt = $conn->prepare("SELECT token2 FROM tokens WHERE token2 = ?");
    $stmt->bind_param("s", $token);

    $stmt->execute();

    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        echo "true";

        $stmt->close();
        $conn->close();
        exit();
    } else {
        echo "false";

        $stmt->close();
        $conn->close();
        exit();
    }
}

function token2_clear()
{
    global $invalid_token;
    global $sql_username;
    global $sql_db;
    global $sql_password;

    $conn = new mysqli(null, $sql_username, $sql_password, $sql_db);

    if ($conn->connect_error) {
        response_code(500);
        exit();
    }

    $token = mysqli::real_escape_string(htmlspecialchars($_REQUEST["token"]));

    if ($token == $invalid_token) {
        $conn->close();
        exit();
    }

    $stmt = $conn->prepare("DELETE FROM tokens WHERE token2 = ?");
    $stmt->bind_param("s", $token);

    $stmt->execute();

    $stmt->close();
    $conn->close();
    exit();
}

function token_clear()
{
    global $invalid_token;
    global $sql_username;
    global $sql_db;
    global $sql_password;
    
    $conn = new mysqli(null, $sql_username, $sql_password, $sql_db);
    
    if ($conn->connect_error) {
        response_code(500);
        exit();
    }
    
    $token = mysqli::real_escape_string(htmlspecialchars($_REQUEST["token"]));
    
    if ($token == $invalid_token) {
        $conn->close();
        exit();
    }
    
    // Delete linked tokens.
    $stmt = $conn->prepare("DELETE FROM tokens WHERE token = ?");
    $stmt->bind_param("s", $token);
    $stmt->execute();
    $stmt->close();
    
    // Get a token
    $stmt2 = $conn->prepare("SELECT token FROM users WHERE token = ?");
    
    $token2 = get_new_token();
    while ($token2 == $invalid_token) {
        $token2 = get_new_token();
    }
    while (true) {
        $stmt2->bind_param("s", $token2);
        $stmt2->execute();
        
        $result = $stmt2->get_result();
        
        if ($result->num_rows == 0) {
            break;
        }
        
        $token2 = get_new_token();
        while ($token2 == $invalid_token) {
            $token2 = get_new_token();
        }
    }
    
    $stmt2->close();
    
    // Update tokens
    $stmt = $conn->prepare("UPDATE users SET token = ? WHERE token = ?");
    $stmt->bind_param("ss", $token2, $token);
    $stmt->execute();
    $stmt->close();
    
    $conn->close();
    exit();
}

function idlogin()
{
    global $invalid_token;
    global $sql_username;
    global $sql_db;
    global $sql_password;

    $conn = new mysqli(null, $sql_username, $sql_password, $sql_db);

    if ($conn->connect_error) {
        echo "-1";
        response_code(500);
        exit();
    }

    $login = mysqli::real_escape_string(htmlspecialchars($_REQUEST["login"]));

    $stmt = $conn->prepare("SELECT uid FROM users WHERE login = ?");
    $stmt->bind_param("s", $login);
    $stmt->execute();

    $result = $stmt->get_result();
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $id = $row["uid"];

            echo $id;
            break;
        }
    } else {
        echo "-1";
    }

    $stmt->close();
    $conn->close();
    exit();
}

function idtoken()
{
    global $invalid_token;
    global $sql_username;
    global $sql_db;
    global $sql_password;

    $conn = new mysqli(null, $sql_username, $sql_password, $sql_db);

    if ($conn->connect_error) {
        echo "-1";
        response_code(500);
        exit();
    }

    $token = mysqli::real_escape_string(htmlspecialchars($_REQUEST["token"]));
    if ($token == $invalid_token) {
        echo "-1";

        $conn->close();
        exit();
    }

    $stmt = $conn->prepare("SELECT uid FROM users WHERE token = ?");
    $stmt->bind_param("s", $token);
    $stmt->execute();

    $result = $stmt->get_result();
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $id = $row["uid"];

            echo $id;
            break;
        }
    } else {
        echo "-1";
    }

    $stmt->close();
    $conn->close();
    exit();
}

function idtoken2()
{
    global $invalid_token;
    global $sql_username;
    global $sql_db;
    global $sql_password;

    $conn = new mysqli(null, $sql_username, $sql_password, $sql_db);

    if ($conn->connect_error) {
        echo "-1";
        response_code(500);
        exit();
    }

    $token2 = mysqli::real_escape_string(htmlspecialchars($_REQUEST["token"]));

    if ($token2 == $invalid_token) {
        echo "-1";

        $conn->close();
        exit();
    }

    $stmt = $conn->prepare("SELECT token FROM tokens WHERE token2 = ?");
    $stmt->bind_param("s", $token2);
    $stmt->execute();

    $token = - 1;

    $result = $stmt->get_result();
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $id = $row["token"];

            $token = $id;
            break;
        }
    }

    $stmt->close();

    if ($token == $invalid_token) {
        echo "-1";

        $conn->close();
        exit();
    }

    $stmt = $conn->prepare("SELECT uid FROM users WHERE token = ?");
    $stmt->bind_param("s", $token);
    $stmt->execute();

    $result = $stmt->get_result();
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $id = $row["uid"];

            echo $id;
            break;
        }
    } else {
        echo "-1";
    }

    $stmt->close();
    $conn->close();
    exit();
}

function idlogin()
{
    global $invalid_token;
    global $sql_username;
    global $sql_db;
    global $sql_password;

    $conn = new mysqli(null, $sql_username, $sql_password, $sql_db);

    if ($conn->connect_error) {
        echo "-1";
        response_code(500);
        exit();
    }

    $login = mysqli::real_escape_string(htmlspecialchars($_REQUEST["login"]));

    $stmt = $conn->prepare("SELECT uid FROM users WHERE login = ?");
    $stmt->bind_param("s", $login);
    $stmt->execute();

    $result = $stmt->get_result();
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $id = $row["uid"];

            echo $id;
            break;
        }
    } else {
        echo "-1";
    }

    $stmt->close();
    $conn->close();
    exit();
}

function username()
{
    global $invalid_token;
    global $sql_username;
    global $sql_db;
    global $sql_password;

    $conn = new mysqli(null, $sql_username, $sql_password, $sql_db);

    if ($conn->connect_error) {
        echo "-1";
        response_code(500);
        exit();
    }

    $uid = mysqli::real_escape_string(htmlspecialchars($_REQUEST["id"]));

    $stmt = $conn->prepare("SELECT username FROM users WHERE uid = ?");
    $stmt->bind_param("s", $uid);
    $stmt->execute();

    $result = $stmt->get_result();
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $username = $row["username"];

            echo $username;
            break;
        }
    } else {
        echo unfucked_base_convert($uid, 10, 16);
    }

    $stmt->close();
    $conn->close();
    exit();
}

define('UID_INT_MAX', 9223372036854775807);
define('UID_INT_MIN', -9223372036854775808);

function get_new_uid(mysqli $conn): string
{
    $uid = mt_rand(UID_INT_MIN, UID_INT_MAX);

    // Check for invalid ids.
    if ($uid == - 1) {
        return get_new_uid();
    }

    $uid = $uid . "";

    // Check for existing ids.
    $stmt = $conn->prepare("SELECT id FROM users WHERE uid = ?");
    $stmt->bind_param("s", $uid);
    $stmt->execute();

    $result = $stmt->get_result();
    if ($result->num_rows > 0) {
        $stmt->close();

        return get_new_uid($conn);
    }
    $stmt->close();

    return $uid;
}

function register()
{
    global $invalid_token;
    global $sql_username;
    global $sql_db;
    global $sql_password;

    $conn = new mysqli(null, $sql_username, $sql_password, $sql_db);

    if ($conn->connect_error) {
        echo "-1";
        response_code(500);
        exit();
    }

    $login = mysqli::real_escape_string(htmlspecialchars($_REQUEST["login"]));
    $password = mysqli::real_escape_string(htmlspecialchars($_REQUEST["password"]));
    $username = mysqli::real_escape_string(htmlspecialchars($_REQUEST["username"]));

    $hash = password_hash($password, PASSWORD_DEFAULT);

    $stmt = $conn->prepare("SELECT id FROM users WHERE login = ?");
    $stmt->bind_param("s", $login);
    $stmt->execute();

    $result = $stmt->get_result();
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            echo "alreadyregistered";

            $stmt->close();
            $conn->close();
            exit();
        }
    }
    $stmt->close();
    $conn->close();

    $stmt = $conn->prepare("SELECT id FROM users WHERE username = ?");
    $stmt->bind_param("s", $username);
    $stmt->execute();

    $result = $stmt->get_result();
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            echo "alreadyusername";

            $stmt->close();
            $conn->close();
            exit();
        }
    }
    $stmt->close();
    $conn->close();

    // Get a token
    $stmt2 = $conn->prepare("SELECT token FROM users WHERE token = ?");
    
    $token = get_new_token();
    while ($token == $invalid_token) {
        $token = get_new_token();
    }
    while (true) {
        $stmt2->bind_param("s", $token);
        $stmt2->execute();
        
        $result = $stmt2->get_result();
        
        if ($result->num_rows == 0) {
            break;
        }
        
        $token = get_new_token();
        while ($token == $invalid_token) {
            $token = get_new_token();
        }
    }
    
    $stmt2->close();
    
    // Get a uid
    $uid = get_new_uid($conn);

    $stmt = $conn->prepare("INSERT INTO users (login, password, username, uid, token) VALUES (?, ?, ?, ?)");
    $stmt->bind_param("ssss", $login, $hash, $username, $uid, $token);
    $stmt->execute();
    $stmt->close();
    $conn->close();

    echo "success";
    exit();
}

// For security.
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $type = $_REQUEST["mode"];

    switch ($type) {
        case "tokenget":
            token_get();
            break;
        case "tokenvalid":
            token_valid();
            break;
        case "token2get":
            token2_get();
            break;
        case "token2valid":
            token2_valid();
            break;
        case "token2clear":
            token2_clear();
            break;
        case "tokenclear":
            token_clear();
            break;
        case "idlogin":
            idlogin();
            break;
        case "idtoken":
            idtoken();
            break;
        case "idtoken2":
            idtoken2();
            break;
        case "idusername":
            idlogin();
            break;
        case "usernameget":
            username();
            break;
        case "register":
            register();
            break;
        default:
            http_response_code(400);
            exit();
            break;
    }
} else {
    http_response_code(405);
    exit();
}
