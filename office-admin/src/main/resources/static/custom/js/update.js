function updateTemplate(url, method, params) {
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

function deleteTemplate(url, method) {

    return deleteTemplate(url, method, {});
}

function deleteTemplate(url, method, params) {
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