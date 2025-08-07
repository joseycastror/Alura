let lista = [];

function agregarAmigo(){
    let amigo = String(document.querySelector("#amigo").value);
    if (amigo.trim().length != 0){
        lista.push(amigo);
    } else {
        alert("Por favor ingrese un nombre válido");
    }
    document.querySelector("#amigo").value = "";

    nameList = document.querySelector(".name-list");
    nameList.innerHTML = "";

    for(n = 0; n < lista.length; n++){
        let elemento = document.createElement("li");
        elemento.textContent = lista[n];
        nameList.appendChild(elemento);
    }

    //Opción más corta al loop for...
    //nameList.innerHTML = lista.join("<br>");
}

function sortearAmigo(){
    numRandom = Math.floor(Math.random() * lista.length);
    amigoRandom = String(lista[numRandom]);
    document.querySelector(".result-list").innerHTML = amigoRandom;
}