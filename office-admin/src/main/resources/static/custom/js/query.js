function queryAjaxTemplate(url, method) {
    return queryAjaxTemplate(url, method, {});
}

function queryAjaxTemplate(url, method, params) {
    let result = {};

    $.ajax({
        url: url,
        type: method,
        async: false,
        data: params,
        dataType: 'JSON',
        success: function (res) {
            result = res;
        }
    });
    return result;
}


function queryCategories(url) {
    let categories = [];
    let result = queryAjaxTemplate(url, 'GET');

    if (result.code === 50001) {
        categories = result.data;
    }
    return categories;
}

function queryUsers(url, params) {
    let users = [];
    let result = queryAjaxTemplate(url, 'GET', params);

    if (result.code === 40001) {
        users = result.data;
    }
    return users;
}

function queryPaymentTypes(url) {
    let paymentTypes = [];
    let result = queryAjaxTemplate(url, 'GET');

    if (result.code === 80001) {
        paymentTypes = result.data;
    }
    return paymentTypes;
}