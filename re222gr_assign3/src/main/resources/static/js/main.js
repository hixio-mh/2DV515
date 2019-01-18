'use strict';

let submitButton = document.getElementById("submit");

submitButton.addEventListener("click", function(){
    searchEngine();
});

function searchEngine(){
     let query = document.getElementById("search").value;
     // fixes "clean" words
     let searchQuerys = query.split(/(\s+)/).filter(function(e) { 
    	return e.trim().length > 0; 
    	});

    for(let i = 0; i < searchQuerys.length; i++){
        let searchQuery = searchQuerys[i];
        let duplicateWord = searchQuery;
        for(let k = 0; k < searchQuery.length; k++){
            let char = searchQuery.charAt(k);
            if(char.match(/[^A-Za-z0-9]+/)){
            	// from char to hex
            	duplicateWord = duplicateWord.replace(char, "%"+char.charCodeAt(0).toString(16));
            }
        }
        searchQuerys[i] = duplicateWord;
    }

    let searchWords = "";

    for(let i = 0; i < searchQuerys.length; i++){
        if(i != 0){
        	searchWords += "&";
        }
        searchWords += "query=" + searchQuerys[i];

    }

    let http = new XMLHttpRequest();
    let url = "http://localhost:8080/search?" + searchWords;

    http.open("GET", url);
    http.send();

    http.onreadystatechange=function(){
        if(this.readyState===4 && this.status===200){
        	let searchResult = JSON.parse(http.responseText);
            let q = "<table> " +
            		"<th>Page</th>" +
            		"<th>Score</th> " +
            		"<th>Content</th> " +
            		"<th>Location</th> " +
            		"<th>PageRank</th>"
            		
            for(let i = 0; i < 5; i++){
                q += "<tr>";
                q += "<td>" + Object.entries(searchResult)[i][1].page.replace(/_/g, " ") +"</td>";
                q += "<td>" + Object.entries(searchResult)[i][1].totSum.toFixed(2) + "</td>";
                q += "<td>" + Object.entries(searchResult)[i][1].wordFrequency.toFixed(2) + "</td>";
                q += "<td>" + Object.entries(searchResult)[i][1].documentLocation.toFixed(2) + "</td>";
                q += "<td>" + Object.entries(searchResult)[i][1].pageRank.toFixed(2) + "</td>";
                q += "</tr>";
            }
            q +="</table>";
            result.innerHTML = q;
        }
    }
}