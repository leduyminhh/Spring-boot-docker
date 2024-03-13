package ;

import org.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class CsrfUtil {
	//8 giờ
	public static final long TOKEN_EXPIRES = 28800000;
	public static final String[] IGNORE = {};
	public static final List<HttpMethod> ALLOWED_METHODS = Arrays.asList(HttpMethod.HEAD, HttpMethod.TRACE,
			HttpMethod.OPTIONS);
	public static final String CSRF_TOKEN_KEY = "csrf-token";
	public static final String CSRF_SESSION_KEY = "csrf-session";
	public static final String CSRF_TOKEN_EXPIRES = "CSRF-TOKEN-EXPIRES";
	public static final String PREFIX_KEY = "CSRF_TOKEN";

	public static void genCsrfToken(RedisTemplate redisTemplate, ServerWebExchange swe, JSONObject data) {
		ServerHttpResponse respone = swe.getResponse();
		WebSession webSession = swe.getSession().block();
		String tokenValue = SecureCRFSToken.getInstances().generateNewToken();
		String sessionId = webSession.getId();
		if (sessionId != null && !sessionId.equals("") && tokenValue != null && !tokenValue.equals("")) {
			pushToken(redisTemplate, sessionId, tokenValue, data);
		}
		respone.getHeaders().addIfAbsent(CSRF_TOKEN_KEY, tokenValue);
		respone.getHeaders().addIfAbsent(CSRF_SESSION_KEY, sessionId);
	}

	@SuppressWarnings("unchecked")
	public static void pushToken(RedisTemplate redisTemplate, String sesionId, String token, JSONObject data) {
		try {
			JSONObject dataToken = new JSONObject();
			dataToken.put(CSRF_SESSION_KEY, sesionId);
			dataToken.put("data", data);
			dataToken.put(CSRF_TOKEN_EXPIRES, Calendar.getInstance().getTimeInMillis() + TOKEN_EXPIRES);
			redisTemplate.opsForValue().set(PREFIX_KEY + token, dataToken.toString(),TOKEN_EXPIRES, TimeUnit.MILLISECONDS);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static CsrfStatusEnums verifyCsrfToken(RedisTemplate redisTemplate, ServerWebExchange swe) {
		try {
			ServerHttpRequest request = swe.getRequest();
			String csrf_token = request.getHeaders().getFirst(CsrfUtil.CSRF_TOKEN_KEY);
			String csrf_session = request.getHeaders().getFirst(CsrfUtil.CSRF_SESSION_KEY);
			String clientType = request.getHeaders().getFirst("clientType");
			String referer = request.getHeaders().getFirst(HttpHeaders.REFERER);
			if (clientType == null || (clientType != null && !clientType.equals("WEB")) || referer == null
					|| ALLOWED_METHODS.contains(request.getMethod())) {
				return CsrfStatusEnums.Ignore;
			} else if (UriMatchersConstant.PUBLIC_IGNORE_CSRF.contains(request.getPath().toString())) {
				genCsrfToken(redisTemplate, swe, new JSONObject());
				return CsrfStatusEnums.Ignore;
			}
			if (csrf_token != null && !csrf_token.equals("") && csrf_session != null && !csrf_session.equals("")) {
				String sessionInfoString = redisTemplate.opsForValue().get(PREFIX_KEY + csrf_token) != null  ? redisTemplate.opsForValue().get(PREFIX_KEY + csrf_token).toString() : "";
				JSONObject sessionInfo = !StringUtils.isNullOrEmpty(sessionInfoString)
						? new JSONObject(sessionInfoString)
						: null;
				if (sessionInfo != null && sessionInfo.getString(CSRF_SESSION_KEY).equals(csrf_session)) {
					if (sessionInfo.getLong(CSRF_TOKEN_EXPIRES) > Calendar.getInstance().getTimeInMillis()) {
						genCsrfToken(redisTemplate, swe, sessionInfo);
						return CsrfStatusEnums.valid;
					} else {
						redisTemplate.delete(PREFIX_KEY + csrf_token);
					}
				}
			} else {
				return CsrfStatusEnums.Not_found;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CsrfStatusEnums.Invalid;
	}

	public static void clearToken(RedisTemplate redisTemplate) {
		Set<String> keys = redisTemplate.keys(PREFIX_KEY + "*");
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			try {
				String sessionInfoString = redisTemplate.opsForValue().get(key).toString();
				JSONObject sessionInfo = !StringUtils.isNullOrEmpty(sessionInfoString)
						? new JSONObject(sessionInfoString)
						: null;
				if (sessionInfo != null
						&& sessionInfo.getLong(CSRF_TOKEN_EXPIRES) <= Calendar.getInstance().getTimeInMillis()) {
					iterator.remove();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
