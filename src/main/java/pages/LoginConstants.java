package pages;

import java.util.EnumMap;
import java.util.Map;

public final class LoginConstants {
	public static final String FIELD_REQUIRED = "field.required";
	public static final String MISSING_DOMAIN = "missing.domain";
	public static final String MISSING_AT_SIGN = "missing.at.sign";

	public enum REGION {
		EU("0"), WS_EAST("1"), WS_WEST("2");

		public final String label;

		private REGION(String label) {
			this.label = label;
		}
	}

	public static final REGION DEFAULT_REGION = REGION.WS_EAST;

	private LoginConstants() {

	}

	public static String regionDomain(REGION region) {
		Map<REGION, String> map = new EnumMap<>(REGION.class);
		map.putAll(Map.of(REGION.EU, "eu1.app.sysdig.com", REGION.WS_EAST, "app.sysdigcloud.com", REGION.WS_WEST,
				"us2.app.sysdig.com"));
		return map.get(region);
	}

	public enum AUTH {
		GOOGLE("/oauth2/v2/auth/identifier"), OPENID("openIdAuthentication"), SAML("samlAuthentication");

		public final String path;

		private AUTH(String path) {
			this.path = path;
		}
	}
}
