/* fichier de changement du mot de passe utilisateur */
//traiter la reponse
function handleChangePasswordResponse(response) {
	dijit.byId("waitingPanel").hide(); //cacher le panel d'attente
	dojo.style("changePasswordLabel", "opacity", "0"); //opacité
	dojo.style("changePasswordLabel", "color", (response.returnValue ? "#23E882" : "#ff0000")); //couleur
	dijit.byId("changePasswordLabel").set("content", response.message); //definir le contenu
	dojo.fadeIn({node: "changePasswordLabel",duration: 3000}).play(); //afficher le texte de retour
}

//action
dojo.addOnLoad(function() {
	//requete ajax
	dojo.connect(dijit.byId("passwordChangeConfirmButton"), "onClick", function() {
		dojo.style("changePasswordLabel", "opacity", "1");
		dojo.fadeOut({node: "changePasswordLabel", duration: 1000}).play();
		dijit.byId("waitingPanel").show(); //afficher le panel d'attente
		dojo.xhrPost({
			url: changePasswordAjaxURL,	
			handleAs: "json",
			content: {
				password: dijit.byId("firstPasswordField").get("value"),
				confirmPassword: dijit.byId("secondPasswordField").get("value")
			},
			
				load: function(response, ioArgs){
					handleChangePasswordResponse(response);							  							
				return response;
				},
				
				error: function(response, ioArgs){
					handleChangePasswordResponse(response);
				return response;
				}
		});			
	});
	
	//effacement des champs
	dojo.connect(dijit.byId("passwordChangeResetButton"), "onClick", function() {
		dojo.query("#firstPasswordField, #secondPasswordField").forEach(function(node) {
			dijit.byId(node.id).set("value", "");	
		});					
	});
});