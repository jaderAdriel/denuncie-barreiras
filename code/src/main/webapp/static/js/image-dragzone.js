let dragzone = document.querySelector('#dragzone');
let buttonClose = document.querySelector('#closePreview');


let file;
let preview = document.querySelector('#imagePreview');

let browseButton = document.querySelector('.dragzone__button');
let browseInput = document.querySelector('#fileInput');

browseInput.addEventListener('change', (e) => {
    file = e.target.files[0];
    readImageFile(file)
});
browseButton.addEventListener('click', () => {
    browseInput.click();
});

buttonClose.addEventListener('click', (e) => {
    preview.querySelector('.file').innerHTML = ''
    preview.style.zIndex ='-2'
});


dragzone.addEventListener('drop', (e) => {

    e.preventDefault();
    dragzone.classList.remove('active');

    file = e.dataTransfer.files[0];

    readImageFile(file)

});

dragzone.addEventListener('dragover', (e) => {
    e.preventDefault();
    if (!dragzone.classList.contains('active')) {
        dragzone.classList.add('active');
    }
});

dragzone.addEventListener('dragleave', (e) => {
    e.preventDefault();
    dragzone.classList.remove('active');
});





function readImageFile(file) {

    let validExtensions =  ['image/jpeg', 'image/jpg', 'image/png', 'image/svg+xml'],
        fileType = file.type;

    if (!validExtensions.includes(fileType)) {
        alert("File not valid. Must be a image type");
        return
    }

    let fileReader = new FileReader()

    fileReader.readAsDataURL(file);

    fileReader.onload = () => {
        let fileURL = fileReader.result;
        preview.querySelector('.file').innerHTML = `<img src="${fileURL}" alt="" class="preview-media w-full h-full object-contain"></img>`
        preview.style.zIndex = '1';
        changeImageCanvas(fileURL)
    }

}

function changeImageCanvas(src) {
    palette.innerHTML = ''
    app.setImageSource(src);
}