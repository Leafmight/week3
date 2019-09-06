
var local = "http://localhost:8080/jpareststarter/api/xxx/all";
var droplet = "https://leafmight.dk/rest-jpa-devops-starter/api/xxx/all"

function getAllMovies() {
fetch(local)
  .then(res => res.json()) //in flow1, just do it
  .then(data => {
   console.log("data",data);
        let list = data.map(function (m){
            
            return "<tr><td>" + m.movieName + "</td>" + 
               "<td>" + m.director + "</td>";
        }).join(" ");
        document.getElementById("movielist").innerHTML = list;
})


}

var button = document.getElementById("button");
button.onclick = getAllMovies;
