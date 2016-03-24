
$(document).ready(function () {
    $(":submit").on("click",function(event){
        if ($("#password").val() !== $("#confirmPassword").val()) {
                alert("passwords do not match");
                return false;
            }
        });
        
});
