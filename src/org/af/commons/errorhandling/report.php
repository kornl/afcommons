<?php

include_once('Mail.php');
include_once('Mail/mime.php');

$text = "Dies ist der Bugreport\n";
$subject = 'Bugreport';

$text = $text . "USER AGENT: " . $_SERVER['HTTP_USER_AGENT']. "\n";
$text = $text . "REFERER   : " . $_SERVER['HTTP_REFERER']. "\n";
$text = $text . "REMOTE IP : " . $_SERVER['REMOTE_ADDR']. "\n";
$text = $text . "HOST NAME : " .gethostbyaddr ($_SERVER['REMOTE_ADDR']). "\n";

foreach ($_POST as $key => $value) {
  $text = $text . $key . ": " . $value . "\n";
  echo $key . ": " . $value . "<br>";
  if ($key == "Subject") {
  	$subject = $value;
  	$subject = str_replace ("@", "(at)", $subject);
  	$subject = str_replace ("\\", "/", $subject);  
  }
}

$crlf = "\n";
$hdrs = array(
              'From'    => 'bugreport@small-projects.de',
              'Subject' => $subject
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