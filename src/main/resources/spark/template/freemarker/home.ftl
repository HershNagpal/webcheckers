<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">
  
    <h1>Web Checkers</h1>
    
    <div class="navigation">
      <a href="/">my home</a>
      <#if currentPlayer??>
        <a href="/signout">sign out [${currentPlayer.name}]</a>
      <#else>
        <a href="/signin">sign in</a>
      </#if>
    </div>

    <div class="body">
      <p>Welcome to the world of online Checkers.</p>
      <p>Online Players:
        <#if currentPlayer??>
          <#if playerList??>
            <#list playerList as p>
              <li><a href="/game">${p}</a></li>
            </#list>
          <#else>
            No other players online.
          </#if>
        <#else>
          ${numPlayers} players online
        </#if>
      </p>
    </div>
    
  </div>
</body>
</html>
