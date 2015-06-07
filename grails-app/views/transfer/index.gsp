<html>
<head>
    <meta content="main" name="layout"/>
</head>
<body>
${message}
<form name="transferForm" action="makeTransfer" controller="transfer">
	From Account: 
	<g:select optionKey="accountName" optionValue="accountName"
          name="fromAcc" from="${accounts}" />
    <br>
    To Account:
    <g:select optionKey="accountName" optionValue="accountName"
          name="toAcc" from="${accounts}" />
    <br>
    Amount:
    <input type="number" name="amount" min=1 required="required"/>
    <br>
    <input type="submit" value="Make Transfer" />
</form>

</body>
</html>