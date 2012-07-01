/* fichier pour le changement de page en mode pagination */
//rebrancher l'événement
function replugSelectPageListEvent() {
	selectPageListConnection = dojo.connect(dijit.byId("selectPageList"), "onChange", function() { //rebrancher
		dojo.byId("selectPageForm").submit();
	});
}