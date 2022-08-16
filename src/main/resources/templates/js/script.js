function setDisplayBlock(string){
    var div = document.getElementById(string);
    div.style.display = "block";
    
}

function setDisplayNone(string){
    var div = document.getElementById(string);
    div.style.display = "none";
}

document.querySelector('#friendsSearch').oninput = function (){

    let val = this.value.trim();
    let elasticItems = document.querySelectorAll('#friend');
    if(val != ''){
        elasticItems.forEach(function(elem){
            if(elem.innerText.search(val) == -1){
                elem.classList.add('hidden');
            }else {
                elem.classList.remove('hidden');
            }
        });
    }else{
        elasticItems.forEach(function(elem){
            elem.classList.remove('hidden');
        });
    }
}

document.querySelector('#usersSearch').oninput = function (){

    let val = this.value.trim();
    let elasticItems = document.querySelectorAll('#user');
    if(val != ''){
        elasticItems.forEach(function(elem){
            if(elem.innerText.search(val) == -1){
                elem.classList.add('hidden');
            }else {
                elem.classList.remove('hidden');
            }
        });
    }else{
        elasticItems.forEach(function(elem){
            elem.classList.remove('hidden');
        });
    }
}

document.querySelector('#tableSearch').oninput = function (){

    let val = this.value.trim();
    let elasticItems = document.querySelectorAll('#table');
    if(val != ''){
        elasticItems.forEach(function(elem){
            if(elem.innerText.search(val) == -1){
                elem.classList.add('hidden');
            }else {
                elem.classList.remove('hidden');
            }
        });
    }else{
        elasticItems.forEach(function(elem){
            elem.classList.remove('hidden');
        });
    }
}

document.querySelector('#usersAdmSearch').oninput = function (){

    let val = this.value.trim();
    let elasticItems = document.querySelectorAll('#userAdm');
    if(val != ''){
        elasticItems.forEach(function(elem){
            if(elem.innerText.search(val) == -1){
                elem.classList.add('hidden');
            }else {
                elem.classList.remove('hidden');
            }
        });
    }else{
        elasticItems.forEach(function(elem){
            elem.classList.remove('hidden');
        });
    }
}