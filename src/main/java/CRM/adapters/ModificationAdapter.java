package CRM.adapters;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import CRM.model.Modification;

public class ModificationAdapter implements JsonSerializer<Modification>{

	@Override
	public JsonElement serialize(Modification modification, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject json = new JsonObject();
		json.addProperty("modification", modification.getHistorique());
		return json;
	}

}
