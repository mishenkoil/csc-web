let data;
let xmlhttp = new XMLHttpRequest();
xmlhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
        data = JSON.parse(this.responseText);
    }
};
xmlhttp.open("GET", "json/generated.json", true);
xmlhttp.send();

let listElm = document.querySelector('.notes');
let cardsCounter = 0;

window.onscroll = function () {
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
        loadMore();
    }
};

let loadMore = function () {
    for (let i = 0; i < 20; i++) {
        let item = document.createElement('article');
        let itemH = document.createElement('h2');
        itemH.textContent = data[cardsCounter]["title"];
        let itemP = document.createElement('p');
        itemP.textContent = data[cardsCounter]["text"];
        cardsCounter++;

        item.append(itemH);
        item.append(itemP);
        item.classList.add('notes__note');
        item.classList.add('notes__note_default');

        listElm.append(item);
    }
};