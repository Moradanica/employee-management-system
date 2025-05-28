//search
$("#searchInput").on("input", function () {
  let keyword = $(this).val(); // Get the value of the search input

  console.log("Keyword:", keyword); // Log the current value

  // Otherwise, make the AJAX call to perform the search
  $.ajax({
    url: "http://localhost:8080/search",
    method: "GET",
    data: { keyword: keyword },
    success: function (response) {
      console.log("AJAX response:", response);

      if (keyword.trim() === "") {
        window.location.href = "http://localhost:8080/home";
        $("#searchInput").val("").focus(); // Clear and refocus
      } else {
        let html = $($.parseHTML(response));
        const resultContent = html.find("#resultContainer").html();

        $("#resultContainer").html(resultContent); // Show search results
      }
    },
    error: function (xhr, status, error) {
      console.error("Error fetching data:", error, status); // Log any errors
    },
  });
});

$(".add-btn").on("click", function () {
  let emplopyeeForm = $(".add-emp-form").serialize();
  console.log($("#empId"));

  $.ajax({
    url: "http://localhost:8080/rest/employee/add",
    method: "POST",
    data: emplopyeeForm,
    success: function () {
      console.log(emplopyeeForm);
      alert("Added Successfully");
      location.reload();
    },
    error: function () {
      console.error("error adding employee");
    },
  });
});

$(document).ajaxError(function (event, jqXHR, ajaxSettings, thrownError) {
  if (jqXHR.status === 403 || jqXHR.status === 419) {
    alert(
      "Session expired or invalid CSRF token. Please reload the page and log in again."
    );
    // Optionally, redirect to login page:
    window.location.href = "/login";
  }
});

$(document).ready(function () {
  let token = $('meta[name="_csrf"]').attr("content");
  let header = $('meta[name="_csrf_header"]').attr("content");
  let userRole = $('meta[name="user-role"]').attr("content");
  let empId = null;

  console.log(userRole);

  $.ajaxSetup({
    beforeSend: function (xhr) {
      xhr.setRequestHeader(header, token);
    },
  });

  $(".edit-btn").on("click", function () {
    empId = $(this).data("bs-id");
    console.log(empId);

    $.ajax({
      url: "http://localhost:8080/rest/employee/edit/" + empId,
      method: "GET",
      success: function (data) {
        $("#empId").val(data.id);
        $("#first-name").val(data.firstName);
        $("#last-name").val(data.lastName);
        $("#email").val(data.email);
        $("#job-title").val(data.jobTitle);
        $("#salary").val(data.salary);
        console.log(data.firstName);
      },
      error: function () {
        alert("Failed to fetch employee data.");
      },
    });
  });

  $(".update-btn").on("click", function () {
    empId = $("#empId").val();
    console.log(empId);
    const formData = $(".edit-emp-form").serialize();
    console.log(formData);

    $.ajax({
      url: "http://localhost:8080/rest/employee/update",
      method: "PUT",
      data: formData,
      success: function () {
        alert("Employee Updated Successfully.");
        // window.location.href = "http://localhost:8080/home";
        location.reload();
      },
      error: function (jqXHR, textStatus, errorThrown) {
        // Show detailed error info
        console.error("Error details:", jqXHR.responseText);
        console.log(textStatus);
        alert("Failed to Update Employee: " + jqXHR.status + " " + errorThrown);
        location.reload();
      },
    });
  });

  $(".delete-btn").on("click", function () {
    empId = $(this).data("bs-id");
    console.log(empId);

    if (confirm("Are you sure you want to delete this employee?")) {
      $.ajax({
        url: "http://localhost:8080/rest/employee/delete/" + empId,
        method: "DELETE",
        success: function () {
          // window.location.href = "/home";
          location.reload();
        },
        error: function () {
          alert("Failed to Delete Employee");
        },
      });
    }
  });
});
