<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">

    <h1>Web Checkers</h1>

    <div class="navigation">
      <a href="/">my home</a>
      <a href="/signin">sign in</a>
    </div>

    <div class="body">
      <p>Welcome to the world of online Checkers.
        <#if message??>
          <div id="message" class="${message.type}">${message.text}</div>
        <#else>
          <div id="message" class="info" style="display:none"></div>
        </#if>
      </p>
      <form action="./signin" method="POST">
        Username
        <input name="myName" />
        <br/><br/>
        <button type="submit">Sign in</button>
      </form>
    </div>

  </div>
</body>
</html>