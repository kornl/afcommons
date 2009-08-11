<?php

include_once('Mail.php');
include_once('Mail/mime.php');

$text = 'Dies ist der Bugreport<br>';

foreach ($_POST as $key) {
	$text = $text . $key . ":" . $_POST[$key] . "\n";
}

$crlf = "\n";
$hdrs = array(
              'From'    => 'bugreport@small-projects.de',
              'Subject' => 'Bugreport'
              );

$mime = new Mail_mime($crlf);
$mime->setTXTBody($text);

foreach ($_FILES as $file) {
  $mime->addAttachment($file['tmp_name'], 'application/zip', basename( $file['name']));
}

// $mime->addAttachment($file, 'text/plain');

$body = $mime->get();
$hdrs = $mime->headers($hdrs);

$mail =& Mail::factory('mail');
$mail->send('bugreport@small-projects.de', $hdrs, $body);

echo "The report has been sent. Thank you!";

?>