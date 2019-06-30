'use strict';

var validate = function() {
    let $input = $( "input[type='text'][name='amount']" );

    let timer;
    $input.on("keyup", function(e) {
        if (timer) {
            clearTimeout(timer);
        }

        // staggered check request
        timer = setTimeout(function() {
            $.ajax({
                url: '/validate',
                type: 'GET',
                data: {'amount': $input.val()},
                success: warnUser,
                error: onError,
                contentType: 'application/json',
                dataType: 'json'
            });
        }, 1000);  
    });
};

const warnUser = function(data, status) {
    const $msg = $("#amount-over-balance");
    $msg.text(data['content']);
};

const onError = function(xhr, status, errorMsg) {
    const $msg = $("#amount-over-balance");
    $msg.text(errorMsg);
    $msg.css("font-weight: bold; color: red;");
}

$(document).ready(validate);

