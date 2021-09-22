window.onload = function removeTags() {
    const x = document.getElementsByTagName("a");

    for(let i = x.length -1; i >= 0 ; i--) {
        const att = x[i].getAttribute("href");
        console.log(att);
        if (att.toString().indexOf("http://www.scs.ubbcluj.ro") === 0)
            x[i].remove();
    }
}
