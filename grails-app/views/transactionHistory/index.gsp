<html>
<head>
    <meta content="main" name="layout"/>
</head>
<body>
%{--Use AccountService--}%
<g:set var="service" bean="accountService"/>

<form name="accountSelectionForm" action="list" controller="transactionHistory">
	<g:select optionKey="accountName" optionValue="accountName"
          name="accountName" from="${accounts}" />
    <input type="submit" value="View Transaction History" />
</form>

          
</body>
</html>