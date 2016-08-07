$(document).ready(function() {
    periodicScreenTableUpdate()
});

function periodicScreenTableUpdate() {
    if (typeof location.origin === 'undefined')
        location.origin = location.protocol + '//' + location.host;
    $.ajax({
        url: location.origin + "/rest-api/screens"
    }).then(function(data) {
        updateScreensTable(data);
        setTimeout(periodicScreenTableUpdate, 5000);
    });
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
                        .text(url)
                    )
                );

        })
}

