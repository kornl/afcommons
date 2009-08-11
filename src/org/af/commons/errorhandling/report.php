<?php

include_once('Mail.php');
include_once('Mail/mime.php');

$text = "Dies ist der Bugreport\n";

foreach ($_POST as $key => $value) {
  $text = $text . $key . ": " . $value . "\n";
  echo $key . ": " . $value . "<br>";
}

$crlf = "\n";
$hdrs = array(
              'From'    => 'bugreport@small-projects.de',
              'Subject' => 'Bugreport'
              );

$mime = new Mail_mime($crlf);
$mime->setTXTBody($text);

foreach ($_FILES as $file) {
  $mime->addAttachment($file['tmp_name'], 'text/plain', basename( $file['name']));
  echo "Recieved file: " . basename( $file['name']) . "<br>";
}

$body = $mime->get();
$hdrs = $mime->headers($hdrs);

$mail =& Mail::factory('mail');
$mail->send('bugreport@small-projects.de', $hdrs, $body);

echo "The report has been sent. Thank you!";

?>