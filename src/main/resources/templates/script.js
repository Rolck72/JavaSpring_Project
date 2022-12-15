const clonBtn =() => {
    const elem = document.getElementById('firstEl');
    const cloneElement = elem.cloneNode(true);

    const elems = document.querySelector('.image');
    elems.after(cloneElement);
}


const addElemen = () => {

    const elementImage = document.querySelector('.abusteku-deagulus1');
    const newEl = document.createElement('img');

    newEl.setAttribute('src', 'img/952x476.jpg');
    newEl.classList.add('add_image');

    elementImage.append(newEl);

}