// remove note
let remove = document.getElementsByClassName("notes__button-delete");
let i;
for (i = 0; i < remove.length; i++) {
    remove[i].onclick = function () {
        let div = this.parentElement.parentElement.parentElement;
        div.style.display = "none";
    }
}

// submit
document.querySelector(".note-maker__button-submit").onclick = function () {
    let item = document.createElement("article");
    let itemH = document.createElement("h2");
    itemH.textContent = document.querySelector(".note-maker__title").value;
    let itemP = document.createElement("p");
    itemP.textContent = document.querySelector(".note-maker__text").value;

    item.append(itemH);
    item.append(itemP);
    item.classList.add("notes__note");
    item.classList.add("notes__note_default");

    let btn = document.createElement("button");
    btn.classList.add("notes__button-delete");
    let img = document.createElement("img");
    img.src = "img/delete.svg";
    img.alt = "delete";
    btn.append(img);

    let btnsRight = document.createElement("div");
    btnsRight.classList.add("notes__buttons-right");
    btnsRight.append(btn);

    let btns = document.createElement("div");
    btns.classList.add("notes__buttons");
    btns.append(btnsRight);

    item.append(btns);

    if (itemH.textContent === "" || itemP.textContent === "") {
        alert("You must write something!");
    } else {
        // document.getElementById("notes").appendChild(item);
        document.querySelector(".notes").append(item);
        document.querySelector(".note-maker__title").value = "";
        document.querySelector(".note-maker__text").value = "";
    }

    for (i = 0; i < remove.length; i++) {
        remove[i].onclick = function () {
            let div = this.parentElement.parentElement.parentElement;
            div.style.display = "none";
        }
    }
};

// reset
document.querySelector(".note-maker__button-cancel").onclick = function () {
    document.querySelector(".note-maker__title").value = "";
    document.querySelector(".note-maker__text").value = "";
};
