function updateDetails(url, method, params) {
    let result = {};
    $.ajax({
        url: url,
        type: method,
        async: false,
        data: params,
        processData: false,
        contentType: false,
        success: function (res) {
            result = res;
        }
    });
    return result;
}

function deleteDetailsById(url, method) {

    return deleteDetailsById(url, method, {});
}

function deleteDetailsById(url, method, params) {
    let result = {};
    $.ajax({
        url: url,
        type: method,
        async: false,
        data: params,
        success: function (res) {
            result = res;
        }
    });
    return result;
}