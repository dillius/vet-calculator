$(document).ready(function() {
    console.log("ready!");

    function runCalc() {
        //if ($("#weight").val() == '') {
        //    alert("Please enter instructions before adding task.");
        //} else {
        if($("#weight").val() != '' &&
           $("#dose").val() != '' &&
           $("#formulation").val() != '') {
            $.ajax({
                url: "/api/dosageCalc",
                type: "GET",
                dataType: "json",
                data:{weight: $("#weight").val(),
                      weightUnit: $("#weightUnit").val(),
                      dose: $("#dose").val(),
                      doseUnit: $("#doseUnit").val(),
                      doseUnitPer: $("#doseUnitPer").val(),
                      formulation: $("#formulation").val(),
                      formulationUnit: $("#formulationUnit").val(),
                      formulationUnitPer: $("#formulationUnitPer").val()
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
                    $("#result").val(data["result"][0]);
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

    $("input:enabled").blur(runCalc);
    $("select:enabled").change(runCalc);
});
