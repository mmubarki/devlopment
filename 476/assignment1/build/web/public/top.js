
function changeSignupLink() {
    if($.trim($("#email").val()) !== ""){
        $("#signupLink").attr("href",
            $("#signupLink").attr("href")+"&email="+$.trim($("#email").val()))
    }
  
}
