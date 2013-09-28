$(document).ready(function() {
    console.log("ready!");

    function runCalc() {
        //if ($("#weight").val() == '') {
        //    alert("Please enter instructions before adding task.");
        //} else {
        if($("#weight").val() != '' &&
           $("#maintenance").val() != '' &&
           $("#givingSet").val() != '') {
            $.ajax({
                url: "/api/flowRates",
                type: "GET",
                dataType: "json",
                data:{weight: $("#weight").val(),
                      weightUnit: $("#weightUnit").val(),
                      multiplier: $("#multiplier").val(),
                      maintenance: $("#maintenance").val(),
                      maintenanceUnitPer: $("#maintenanceUnitPer").val(),
                      maintenanceUnitPerPer: $("#maintenanceUnitPerPer").val(),
                      givingSet: $("#givingSet").val()
                     },
                success: function(data, status, xhr){
                    //console.log('data');
                    //console.log(data);
                    //console.log('status');
                    //console.log(status);
                    //console.log('xhr');
                    //console.log(xhr);
                    //console.log(data);
                    //console.log(data["result"]);
                    //console.log(data["result"][0]);
                    var mlPerHour = parseFloat(data["resultFluidRate"][0]).toFixed(3);
                    $("#resultFluidRate").val(mlPerHour);
                    var dropPerMin = parseFloat(data["resultDripRate"][0]).toFixed(1);
                    $("#resultDripRate").val(dropPerMin);

                    $("#oneEvery").text(parseFloat(1 / (dropPerMin / 60)).toFixed(2));
                    $("#everyOne").text(parseFloat(dropPerMin / 60).toFixed(2));
                    $("#everyFive").text(parseFloat(dropPerMin / 60 * 5).toFixed(2));
                    $("#totalFourHour").text(parseFloat(mlPerHour * 4).toFixed(1));
                    $("#totalFourHourLiter").text(parseFloat(mlPerHour * 4 / 1000).toFixed(1));
                    $("#totalDay").text(parseFloat(mlPerHour * 24).toFixed(1));
                    $("#totalDayLiter").text(parseFloat(mlPerHour * 24 / 1000).toFixed(1));
                },
                error: function (xhr, status, thrownError){
                    console.log('xhr');
                    console.log(xhr);
                    console.log('status');
                    console.log(status);
                    console.log('thrownError');
                    console.log(thrownError);
                    alert(xhr.status);
                    alert(thrownError);
                }
            });
        }
    };

    $("input").blur(runCalc);
    $("select:not(.ignore)").change(runCalc);

    $("#maintenanceRateAuto").change(function() {
        var rateVal = $("#maintenanceRateAuto").val();
        $("#maintenance").val(rateVal);
        if(parseFloat(rateVal) > 9) {
            $("#maintenanceUnitPerPer").val("day");
        } else {
            $("#maintenanceUnitPerPer").val("hour");
        }
        $("#maintenance").blur();
    });

    $("#givingSetAuto").change(function() {
        $("#givingSet").val($("#givingSetAuto").val());
        $("#givingSet").blur();
    });
});
