$(function() {
    $("#navbar").load("nav.html");
});

function cleanUnitDisplay(unitParts) {
    var degree = unitParts[0], unit = unitParts[1];
    if(degree == "base") {
        return unit + "(s)";
    } else if(unit === undefined) {
        return degree + "(s)";
    } else {
        return degree + unit + "(s)";
    }
};
