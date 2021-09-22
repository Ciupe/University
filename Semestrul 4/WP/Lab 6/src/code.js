
setTimeout(function generateRandomImage() {
    setTimeout($("img").remove())

    let scoreElement = $("#score");
    let scoreText = parseInt(scoreElement.text());
    if (scoreText === 4) {
        alert("Congratulations! You won.")
        return;
    }

    let container = $("#container");
    let xPosition = getRandomInterval(0, container.width() - 100)
    let yPosition = getRandomInterval(0, container.height() - 100)

    container.append($("<img>").css({
        position: "absolute",
        top: yPosition,
        left: xPosition,
        width: 100,
        height: 100
    }).attr("src", "uwu.jpg").click(killImage));



    setTimeout(generateRandomImage, getRandomInterval(1000, 4000));
}, getRandomInterval(1000, 4000));

function getRandomInterval(min, max) {
    return Math.random() * (max - min) + min;
}

function killImage() {
    let scoreElement = $("#score");
    let scoreText = parseInt(scoreElement.text());
    scoreElement.text(scoreText + 1);

    $("img").remove();
}

$("img").on("click", function() {
    console.log("clicked")
});
