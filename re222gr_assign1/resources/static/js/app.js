'use strict'
let weightedScore = document.getElementById("weightedScore");
let euclidean = document.getElementById("euclidean");

weightedScore.addEventListener("click", function() {
	let http = new XMLHttpRequest();
	let id = document.getElementById("idWS").value;
	let uri = "http://localhost:8080/ws/" + id;

	http.open("GET", uri);
	http.send();

	http.onreadystatechange = function() {
        	let recommendations = JSON.stringify(http.responseText);
        	let weightedScore = "";
          
        	for(let i = 0; i < recommendations.length; i++){
        		test = recommendations.replaceAll("{","");
        		weightedScore = weightedScore + test[i];
            }
            resultWS.innerHTML = recommendations;
	}
});

euclidean.addEventListener("click", function(){
	let http = new XMLHttpRequest();
	let id = document.getElementById("idE").value;
	let uri = "http://localhost:8080/euclidean/" + id;

    http.open("GET", uri);
    http.send();

    http.onreadystatechange = function(){
        	let euclideanSimilarity = JSON.stringify(http.responseText);
        	let matches = "";
            
            for(let i = 0; i < euclideanSimilarity.length; i++){
            	matches = matches + euclideanSimilarity[i];
            }       
            resultE.innerHTML = matches;
    }
});