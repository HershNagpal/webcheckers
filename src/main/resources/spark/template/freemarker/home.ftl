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
      <#if currentPlayer??>
        <a href="/">my home</a> |
        <a href="/signout">sign out [${currentPlayer.name}]</a>
      <#else>
        <a href="/signin">sign in</a>
      </#if>
    </div>

    <div class="body">
      <p>Welcome to the world of online Checkers.
        <#if message??>
          <div id="message" class="${message.type}">${message.text}</div>
        <#else>
          <div id="message" class="info" style="display:none"></div>
        </#if>
      </p>
      <p>Online Players:
        <#if currentPlayer??>
          <#if playerList??>
            <#list playerList as p>
              <li><a href="/game?pid=${p}">${p}</a></li>
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
