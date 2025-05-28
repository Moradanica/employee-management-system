$('#searchInput').on('keyup', function () {
    const keyword = $(this).val();

    console.log(keyword);

    $.ajax({
        url : "http://localhost:8080/search", 
        method : "GET",
        data : {keyword : keyword},
        success: function (response) {
            $("#employeeTableBody").html(response);
        }, 
        error : function (xhr, status, error) {
            console.error("Error fetching data: ", error, status);
        }
    });

});