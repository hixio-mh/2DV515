'use strict'

let result = document.getElementById("result");
let submit = document.getElementById("submit"); 

submit.addEventListener("click", function(){

    let n = document.getElementById("n").value;

    if(isNaN(n)){
        result.innerHTML="You must provide a number";
        return;
    }

    let http = new XMLHttpRequest();
    let url = "http://localhost:8080/k/" + n;

    http.open("GET", url);
    http.send();

    http.onreadystatechange=function(){
        if(this.readyState===4 && this.status===200){
            let resultTable = "<table><tr><th><h1>Centroid</h1></th><th><h1>Blogs</h1></th></tr>"

            let requestResult = JSON.parse(http.responseText);
            let c = 0;
            for(let i = 0; i < n; i++){
            	c++;
                resultTable += "<tr> <td>" + c +"</td> <td>";
                let blogs = Object.entries(requestResult)[i][1].blogs;
                for(let k = 0; k < blogs.length; k++){
                    resultTable = resultTable + blogs[k] + "<br/>"
                }
                resultTable = resultTable + "</td>";
            }

            resultTable +="</table>";
            result.innerHTML = resultTable;
        }
    }
});