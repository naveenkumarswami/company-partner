<?xml version="1.0" encoding="utf-8"?>
<partner-detail>
	<title>$title.name$</title>
	<firstname>$firstName$</firstname>
	<lastname>$lastName$</lastname>	
	
$phoneList : { it | 
	<phones>
	  <phone>$it.phone$</phone>
	</phones>
}$

$circleSet : { it |
   <circles>
     <circle>$it.name$</circle>
   </circles>
}$
</partner-detail>