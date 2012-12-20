//declare function to send tracking to piwik
$wnd.sendTrackingID = function(id) {
	$wnd.piwikTracker.trackGoal(id);
}