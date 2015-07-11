<!DOCTYPE html>
<html>
<head>
  <title>Birds</title>
</head>
<body>

<ul>
<#list birds as bird>
  <li>${bird.name?cap_first}: ${bird.description}
      Favorite food: ${bird.favoriteFood}
</#list>
</ul>

</body>
</html>

