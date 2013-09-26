$(function() {
    $("#navbar").load("nav.html");
});

function cleanUnitDisplay(unitParts) {
    var degree = unitParts[0], unit = unitParts[1];
    if(degree == "base") {
        return unit + "(s)";
    } else {
        return degree + unit + "(s)";
    }
};
