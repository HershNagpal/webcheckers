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
      <p>AI Challenge:
        <#if currentPlayer??>
          <a href="/game?gameID=${currentPlayer.name}+AI">AI Player</a>
        <#else>
          Sign in to challenge an AI Player
        </#if>
     </p>
      <p>Online Players:
        <#if currentPlayer??>
          <#if playerList??>
            <#list playerList as p>
              <li><a href="/game?gameID=${currentPlayer.name}+${p}">${p}</a></li>
            </#list>
          <#else>
            No other players online.
          </#if>
        <#else>
          ${numPlayers} players online
        </#if>
      </p>

      <p>Ongoing Games:
        <#if currentPlayer??>
          <#if gamesList??>
            <#list gamesList as g>
              <li><a href="/spectator/game?gameID=${g.gameID}">${g.gameName}</a></li>
            </#list>
          <#else>
            No ongoing games.
          </#if>
        <#else>
          ${numGames} games being played.
        </#if>
      </p>
      <p>Finished Games:
        <#if currentPlayer??>
          <#if finishedList??>
            <#list finishedList as g>
              <li><a href="/replay/game?gameID=${g.gameID}">${g.gameName}</a></li>
            </#list>
          <#else>
            No finished games.
          </#if>
        <#else>
          ${numFinished} games have finished.
        </#if>
      </p>
    </div>
    
  </div>
</body>
</html>
