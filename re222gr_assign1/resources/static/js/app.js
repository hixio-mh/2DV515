'use strict'

let id = document.getElementById("result");

document.getElementById("submit2").addEventListener("click", function(){
console.log('hej');
console.lg
});

document.getElementById("submit").addEventListener("click", function(){
	let http = new XMLHttpRequest();
	let id = document.getElementById("id").value;
	let url = "http://localhost:8080/euclidean/" + id;

    if(isNaN(id)){
        result.innerHTML= id + " is not a number";
        return;
    }

    http.open("GET", url);
    http.send();

    http.onreadystatechange=function(){
        if(this.readyState===4 && this.status===200){
        	let requestResult = JSON.stringify(http.responseText);
           
        	let resultTable = "<table> " +
            		"<tr>" +
            		requestResult.length
            		"</tr>"

            
            for(let i = 0; i < requestResult.length; i++){
            	resultTable += requestResult[i];
            }
            
            

            resultTable +="</table>";
            result.innerHTML = resultTable;
        }
    }
});