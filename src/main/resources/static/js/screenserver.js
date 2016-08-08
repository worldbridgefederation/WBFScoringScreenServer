$(document).ready(function() {
    periodicScreenTableUpdate()
});

function periodicScreenTableUpdate() {
    if (typeof location.origin === 'undefined')
        location.origin = location.protocol + '//' + location.host;
    $.ajax({
        url: location.origin + "/rest-api/screens"
    }).then(function(data, textStatus, jqXHR) {
        updateScreensTable(data)
        setTimeout(periodicScreenTableUpdate, 5000)
    }, function(jqXHR, textStatus, errorThrown) {
        setMessage(errorThrown)
        setTimeout(periodicScreenTableUpdate, 60000)
    });
}

function setError(message) {
    $('#screenTable tbody').empty();
    $('#screenTable tbody')
                .append($('<tr>')
                    .append($('<td>')
                    )
                    .append($('<td class=".error">')
                        .attr("colspan","3")
                        .text(message)
                    )
                );
}

function updateScreensTable(data) {
        $('#screenTable tbody').empty();
        jQuery.each(data, function(index, value) {
            var lastSeen = Date(value.last_seen)

            var cutOffIndex = value.current_url.indexOf('?')
            var url = value.current_url
            if (cutOffIndex > 0 ) {
                url = value.current_url.substring(0, cutOffIndex)
            }

            var resolution = "<unknown>"
            if (value.screen_details != null) {
                resolution = value.screen_details.width + "x" + value.screen_details.heigth
            }

            $('#screenTable tbody')
                .append($('<tr>')
                    .append($('<th>')
                        .text(index)
                    )
                    .append($('<td>')
                        .text(value.device_id)
                    )
                    .append($('<td>')
                        .text(value.ip_address)
                    )
                    .append($('<td>')
                        .text(value.last_seen)
                    )
                )
                .append($('<tr>')
                    .append($('<td>')
                    )
                    .append($('<td class=".screenurl">')
                        .attr("colspan","3")
                        .text(resolution)
                    )
                )
                .append($('<tr>')
                    .append($('<td>')
                    )
                    .append($('<td class=".screenurl">')
                        .attr("colspan","3")
                        .text(url)
                    )
                );

        })
}

