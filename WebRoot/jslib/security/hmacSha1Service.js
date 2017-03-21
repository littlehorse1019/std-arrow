function generateHmacSha1(URIResource) {
    var signJson;
    $.ajax({
        url: "http/sign/generateSignature.do",
        dataType: 'json',
        data: {
            "URIResource": URIResource,
            "uid": "luoxiao"
        },
        async: false,
        success: function (data) {
            if (data == null && sha1_vm_test()) {
                signJson = b64_hmac_sha1("key", "uid");
            } else {
                $("#responseData").text("signature : " + data.signature + " timestamp : " + data.timestamp);
                signJson = data;
            }
        },
        error: function (response) {
            console.error(response);
        },
    });
    return signJson;
}