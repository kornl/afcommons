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

if ($_SERVER['HTTP_USER_AGENT'] contains Googlebot) {

	$mail =& Mail::factory('mail');
	$mail->send('bugreport@small-projects.de', $hdrs, $body);

	echo "The report has been sent. Thank you!";
	
} else {
	
	echo "Dear Googlebot, please stop visiting this site... yeah, I should use the Robots Exclusion Standard if I really want that. ;)\n";
	echo "So okay, you are welcome to visit this site again. But be aware that I will not send any more bugreports, just because you visit.\n";

}
?>