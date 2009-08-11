<?php

$file1 = "uploads/" . basename( $_FILES['uploadedfile1']['name']); 
$file2 = "uploads/" . basename( $_FILES['uploadedfile2']['name']); 

if(move_uploaded_file($_FILES['uploadedfile1']['tmp_name'], $file1) && move_uploaded_file($_FILES['uploadedfile2']['tmp_name'], $file2)) {

include_once('Mail.php');
include_once('Mail/mime.php');

$text = 'Dies ist der Bugreport';

$crlf = "\n";
$hdrs = array(
              'From'    => 'bugreport@small-projects.de',
              'Subject' => 'Bugreport'
              );


$mime = new Mail_mime($crlf);

$mime->setTXTBody($text);
// $mime->addAttachment($file, 'text/plain');
$mime->addAttachment($file1, 'application/zip');
$mime->addAttachment($file2, 'application/zip');

//do not ever try to call these lines in reverse order
$body = $mime->get();
$hdrs = $mime->headers($hdrs);

$mail =& Mail::factory('mail');
$mail->send('bugreport@small-projects.de', $hdrs, $body);

echo "The report has been sent. Thank you!";

} else {
    echo "Error with error code: " . $_FILES['uploadedfile']['error'];
}

?>