<html>
<head>
    <meta content="main" name="layout"/>
</head>
<body>

<ul>
    <g:each in="${accountHistory}" var="transaction">
        <li>Payee/Payer ${transaction.otherAccName}</li>
        <li>Email ${transaction.otherAccEmail}</li>
        <li>Amount ${transaction.amount}</li>
        <li>Date ${transaction.date}</li>
        <br><br>
    </g:each>
</ul>

</body>
</html>