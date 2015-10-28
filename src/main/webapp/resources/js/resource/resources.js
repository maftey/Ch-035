$(document).ready(function(e) {
	
	// table rows hover
	$(".table tbody tr").hover(
		function(e) {
			// to avoid bugs reset all rows
			$(".table tbody tr").removeClass("info");
			$(e.delegateTarget).addClass("info");
		},
		function(e) {
			$(e.delegateTarget).removeClass("info");
		}
	);
	
	// 
	$(".btn-delete-resource").click(function(e) {
		$(e.delegateTarget).tooltip('hide');
		// add delete rest
		$("#btn-modal-delete-resource").attr("href", 
			e.delegateTarget.value + "/delete"
			);
		// append delete msg to modal		
		var resourceRow = $(e.delegateTarget).parents("tr");
		var resourceId = resourceRow.children(".resource-id").html();
		var resourceName = resourceRow.children(".resource-name").html();
		$("#delete-confirmation-modal").find(".resource-delete-msg").
			append("\"" + resourceName + "\"" + ", id=" + resourceId + "?");
		
		$("#delete-confirmation-modal").modal('show');
	});
	
});