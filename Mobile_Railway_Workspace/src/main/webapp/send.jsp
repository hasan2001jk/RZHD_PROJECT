<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ru.rzhd.BD_CLASS" %>
<%@ page import="ru.rzhd.EKASUI" %>
<%@ page import="jakarta.servlet.RequestDispatcher" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./css/send.css">
    <link rel="shortcut icon" href="./icons/лого.png" type="image/x-icon">
    <title>MWS «Railway Master»</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100;0,200;0,500;0,600;1,100;1,500&family=Radio+Canada:wght@500&display=swap" rel="stylesheet">

    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script>
   

        jQuery(document).ready(function(){
         $("h1").mousemove(function(e){
         var rXP = (e.pageX - this.offsetLeft-$(this).width()/1.5);
         var rYP = (e.pageY - this.offsetTop-$(this).height()/1.5);
         $("h1").css('text-shadow', +rYP/10+'px '+rXP/80+'px rgba(227,6,19,.8), '+rYP/8+'px '+rXP/60+'px rgba(255,237,0,1), '+rXP/70+'px '+rYP/12+'px rgba(0,159,227,.7)');
       });
    });
    </script>

</head>
<body>

    <div class="header">
        <div class="log">
            <h1 class="header_text">Мобильное рабочее место мастера пути</h1>
        </div>
    </div>
    <video autoplay muted loop src="./video/Train going through a tunnel.mp4" alt="first"></video>
    <div class="wrapper">
    
        <div class="left_side">
          
        </div>

            <article>
                <form action="SendServlet" method="POST">
                  <label for="sel">Выберите участок</label>
                  <select id="sel" name="sel">
                  <c:forEach items="${list}" var="s">
                  	<c:if test="${s.isResultState()==false}">
                      <option value="<c:out value='${s.getAreaCode()}'/>"><c:out value='${s.getAreaCode()}'/></option>
                    </c:if>
                  </c:forEach>
                  </select>
                  <div class="wrapper_input">
                    <label for="result">Проверено:</label>
                      <label class="checkbox style-e">
                        <input type="checkbox" name="result" id="result"/>
                        <div class="checkbox__checkmark"></div>
                        <div class="checkbox__body"></div>
                      </label>
                  </div>
                  <input type="submit" class="floating-button">
                </form>
            </article>
              

                    <div class="right_side">

                    </div>

    </div>
    <footer>
        <div class="footer_icon">
            <img src="./icons/РЖД_маска.jfif" alt="RZHD"  id="photo">
        </div>
        <h6 class="footer_text">МРМ 2022</h6>
    </footer>
    
    <script>
        var link=document.getElementById('photo');
        link.addEventListener('click',toogle);    
            
        function toogle(){
            var video=document.getElementsByTagName('video');

            if(video[0].getAttribute('alt')=="first"){
                video[0].setAttribute("src","./video/Train leaving a tunnel.mp4");
                video[0].load();
                video[0].setAttribute("autoplay","");
                video[0].setAttribute("alt","second")
            }else{
                video[0].setAttribute("src","./video/Train going through a tunnel.mp4");
                video[0].load();
                video[0].setAttribute("autoplay","");
                video[0].setAttribute("alt","first")
            }
        }

    </script>
</body>
</html>