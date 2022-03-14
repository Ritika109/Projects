package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {

		AddPlaceStepDef ad = new AddPlaceStepDef();

		if (AddPlaceStepDef.placeID == null) {
			ad.add_place_payload_with("Ritika", "French", "Asia");
			ad.user_call_with_request("AddPlaceAPI", "POST");
			ad.verify_the_placeId_created_maps_to("Ritika", "GetPlaceAPI");

		}

	}
}
