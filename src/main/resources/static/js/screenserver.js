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

function periodicScreenGroupsUpdate() {
    if (typeof location.origin === 'undefined')
        location.origin = location.protocol + '//' + location.host;
    $.ajax({
        url: location.origin + "/rest-api/screengroups"
    }).then(function(data, textStatus, jqXHR) {
        if (!editEnabled()) {
            updateScreenGroups(data)
        } else {
            console.log("Not updating because we are in edit mode")
        }
        setTimeout(periodicScreenGroupsUpdate, 5000)
    }, function(jqXHR, textStatus, errorThrown) {
        setTimeout(periodicScreenGroupsUpdate, 60000)
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

            var url = value.current_url
            if (url != null) {
                var cutOffIndex = value.current_url.indexOf('?')
                var url = value.current_url
                if (cutOffIndex > 0 ) {
                    url = value.current_url.substring(0, cutOffIndex)
                }
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

function loadScreenGroups() {
    if (typeof location.origin === 'undefined')
        location.origin = location.protocol + '//' + location.host;
    $.ajax({
        url: location.origin + "/rest-api/screengroups"
    }).then(function(data, textStatus, jqXHR) {
        updateScreenGroups(data)
    })
}

function updateScreenGroups(data) {
    console.log('Trigger screenGroup update with edit:' + editEnabled())
    $('#screencontainer').empty();

    jQuery.each(data, function(index, value) {
        var screendiv = $('<div>')
            .attr('class','row screenrow')

        jQuery.each(value.deviceIds, function(index, screenvalue) {
            var screen = $("<div>")
                .attr('class','col-md-1 screen')
                .append($("<div>")
                    .attr("class", "screen-inside")
                    .append($("<p>")
                        .text(screenvalue)
                    )
                )
            screendiv.append(screen)
        })

        if (editEnabled() && !(value.groupName == "Unassigned") ) {
            var screen = $("<div>")
                            .attr('class','col-md-1 screen addscreen')
                            .append($("<span>")
                                .attr('data-group',value.groupName)
                                .attr('class','glyphicon glyphicon-plus-sign')
                                .attr('aria-hidden','true')
                            )
            screendiv.append(screen)
        }

        if (value.groupName == "Unassigned") {
            // Update the global with the list of unassigned screens
            unassignedScreens = value.deviceIds
        }

        $('#screencontainer')
            .append($('<div>')
                .attr('class','row')
                .append($('<div>')
                    .attr('class','col-md-12')
                    .append($('<h1>')
                        .text(value.groupName)
                    )
                )
            )
            .append(screendiv)

        $('.addscreen').click(function(event) {
            console.log ( '#addscreen was clicked' );
            $('#unassigned-select').empty()
            $.each(unassignedScreens, function(index, value) {
                 $('#unassigned-select')
                      .append($('<option>', { value : value })
                      .text(value));
                 $('#addScreenModal .target-group')
                        .val(event.target.dataset["group"])
            });
            $("#addScreenModal").modal('show');
        })
    })

    if (editEnabled()) {
        $('#screencontainer')
            .append($('<div>')
                .attr('class','row')
                .append($('<div>')
                    .attr('class','col-md-12')
                    .attr('id', 'addgroup')
                    .append($('<h1>')
                          .append($("<span>")
                                .attr('class','glyphicon glyphicon-plus-sign')
                                .attr('aria-hidden','true')
                            )
                    )
                )
            )
        $('#addgroup').click(function() {
            console.log ( '#addgroup was clicked' );
            $("#addGroupModal").modal('show');
        })
    }
}

function editEnabled() {
    return $('.global-edit')[0].checked
}


