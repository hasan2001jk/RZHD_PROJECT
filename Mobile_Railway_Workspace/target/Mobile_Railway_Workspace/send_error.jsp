<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./css/send_error.css">
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
                <form action="ErrorServlet" name="myForm" method="GET">
                    <div class="wrapper_input"> 
                        <label for="road_code">Введите код дороги: </label>
                        <input type="number" min="1" id="road_code" name="road_code" value="63" readonly>
                        <label for="region_code">Введите код региона: </label>
                        <input type="number" min="1" id="region_code" name="region_code" value="63" readonly>
                    </div>
                    <div class="wrapper_input">
                        <label for="sel">Выберите участок: </label>
                        <select name="sel" id="mySelect" onChange="changeField()" >
	                        <c:forEach items="${dan}" var="n">
	                        	<c:if test="${n.isResultState()==false}">
	                        	    <option value="${n.getAreaCode()}" selected>${n.getAreaCode()}</option>
                        	    </c:if>
	                        </c:forEach>	
                        </select>
                        <label for="kilometr">Километр пути: </label>
                        <input type="number" min="1" name="kilometr"  id="kilometr" value="10">
                    </div>
                    <div class="wrapper_input">
                    	
	                        <label for="station_start">Начальная станция: </label>
	                        <input type="text" name="station_start" id="station_start"  value="" readonly >
	                        <label for="station_finish">Конечная станция: </label>
	                        <input type="text" name="station_finish" id="station_finish"  value="" readonly>
	                    
                    <label for="nb_type" class="nb">Тип нарушения: </label>
                    <input type="number" min="1" name="nb_type" id="nb_type" value="2">
                    </div>
                    <input type="submit" value="Отправить" class="floating-button">    
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
      	
      
        
        var sel=document.getElementById('mySelect');
      	
        
        
        var statStart = [
        	<c:forEach items="${dan}" var="item">
        		<c:if test="${item.isResultState()==false}">
        	            "${item.getStationStartName()}",
        	    </c:if>        
        	</c:forEach>
        	            ];
        
       
        	
        	
        
        var myArray = [
        	<c:forEach items="${dan}" var="item">
        		<c:if test="${item.isResultState()==false}">
        	            "${item.getStationFinishName()}",
     	        </c:if>    
        	</c:forEach>
        	            ];
       	
       
      
        
        function changeField() {
        	document.getElementById('station_start').value=JSON.parse(JSON.stringify(statStart[sel.selectedIndex]));
            document.getElementById('station_finish').value=JSON.parse(JSON.stringify(myArray[sel.selectedIndex]));	
        }
       	
        document.getElementById('station_start').value=JSON.parse(JSON.stringify(statStart[sel.selectedIndex]));
        document.getElementById('station_finish').value=JSON.parse(JSON.stringify(myArray[sel.selectedIndex]));	
        
       
    </script>
</body>
</html>